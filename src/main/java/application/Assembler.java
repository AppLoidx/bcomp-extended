package application;


import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import ru.ifmo.cs.bcomp.CPU;
import ru.ifmo.cs.bcomp.Instruction;
import ru.ifmo.cs.bcomp.Utils;

import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Arthur Kupriyanov
 */
public class Assembler {
    private ArrayList<Assembler.Label> labels;
    private ArrayList<Assembler.Command> cmds;
    private Instruction[] instrset;
    private final RSyntaxTextArea textArea;

    public Assembler(Instruction[] instrset, RSyntaxTextArea textArea) {
        this.instrset = instrset;
        this.textArea = textArea;
    }

    public void compileProgram(String program) throws Exception {
        String[] prog = program.replace("\r", "").toUpperCase().split("\n");
        int addr = 0;
        int lineno = 0;
        this.labels = new ArrayList<>();
        this.cmds = new ArrayList<>();

        try {

            for (String l : prog) {
                ++lineno;
                String[] line = l.trim().split("[#;]+");
                if (line.length != 0 && !line[0].equals("")) {
                    line = line[0].trim().split("[ \t]+");
                    if (line.length != 0 && !line[0].equals("")) {
                        if (line[0].equals("ORG")) {
                            if (line.length != 2) {
                                throw new Exception("Директива ORG требует один и только один аргумент");
                            }

                            this.checkAddr(addr = Integer.parseInt(line[1], 16));
                        } else {
                            int col = 0;
                            String v;
                            if (line[col].charAt(line[col].length() - 1) == ':') {
                                v = line[col].substring(0, line[col].length() - 1);
                                Label label = this.getLabel(v);
                                if (label.hasAddress()) {
                                    throw new Exception("Метка " + v + " объявлена повторно");
                                }

                                label.setAddr(addr);
                                ++col;
                            }

                            if (col != line.length) {
                                String labelname;

                                if (line[col].matches("(ACOM|BRCOM)")){
                                    line = compileUserCustomCommand(line);
                                }

                                if (line[col].equals("WORD")) {
                                    if (col++ == line.length - 1) {
                                        throw new Exception("Директива WORD должна иметь как минимум один аргумент");
                                    }

                                    if (line.length - col == 3 && line[col + 1].equals("DUP")) {
                                        int size = Integer.parseInt(line[col], 16);
                                        if (size >= 1 && addr + size <= 2047) {
                                            col += 2;
                                            if (line[col].charAt(0) == '(' && line[col].charAt(line[col].length() - 1) == ')') {
                                                labelname = line[col].substring(1, line[col].length() - 1);
                                                this.createWord(addr, labelname, size);
                                                addr += size;
                                                continue;
                                            }

                                            throw new Exception("Значение после DUP должно быть в скобках");
                                        }

                                        throw new Exception("Указано недопустимое количество значений");
                                    } else {
                                        v = line[col++];
                                        while (col < line.length) {
                                            v = v.concat(" ").concat(line[col++]);
                                        }

                                        String[] values = v.split(",");
                                        String[] arr2 = values;
                                        int len2 = values.length;

                                        for (int i = 0; i < len2; ++i) {
                                            String value = arr2[i];
                                            this.createWord(addr++, value.trim(), 1);
                                        }
                                    }
                                } else {
                                    Instruction instr = this.findInstruction(line[col]);
                                    if (instr == null) {
                                        throw new Exception("Неизвестная команда " + line[col]);
                                    }

                                    switch (instr.getType()) {
                                        case ADDR:
                                            if (col != line.length - 2) {
                                                throw new Exception("Адресная команда " + line[col] + " требует один аргумент");
                                            }

                                            labelname = line[col + 1];
                                            short addrtype;
                                            if (labelname.charAt(0) == '(') {
                                                if (labelname.charAt(labelname.length() - 1) != ')') {
                                                    throw new Exception("Нет закрывающей скобки");
                                                }

                                                labelname = labelname.substring(1, labelname.length() - 1);
                                                addrtype = 2048;
                                            } else {
                                                addrtype = 0;
                                            }

                                            if (Utils.isHexNumeric(labelname)) {
                                                this.cmds.add(new Command(addr++, instr.getInstr() + addrtype + Integer.parseInt(labelname, 16)));
                                            } else {
                                                this.cmds.add(new Command(addr++, instr.getInstr() + addrtype, this.getLabel(labelname)));
                                            }
                                            break;
                                        case NONADDR:
                                            if (col != line.length - 1) {
                                                throw new Exception("Безадресная команда " + line[col] + " не требует аргументов");
                                            }

                                            this.cmds.add(new Command(addr++, instr.getInstr()));
                                            break;
                                        case IO:
                                            if (col != line.length - 2) {
                                                throw new Exception("Строка " + lineno + ": Команда ввода-вывода " + line[col] + " требует один и только один аргумент");
                                            }

                                            this.cmds.add(new Command(addr++, instr.getInstr() + (Integer.parseInt(line[col + 1], 16) & 255)));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Iterator i$ = this.labels.iterator();

            Assembler.Label label;
            do {
                if (!i$.hasNext()) {
                    return;
                }

                label = (Assembler.Label)i$.next();
            } while(label.hasAddress());

            throw new Exception("Ссылка на неопределённую метку " + label.label);
        } catch (Exception var17) {
            setErrorLine(lineno);
            throw new Exception("Строка " + lineno + ": " + var17.getMessage());
        }
    }
    private void setErrorLine(int line){
        try {
            this.textArea.addLineHighlight(line - 1, DisplayStyles.ERROR_COLOR);
        } catch (BadLocationException ignored) {
        }
    }
    private Assembler.Label findLabel(String labelname) {
        Iterator i$ = this.labels.iterator();

        Assembler.Label label;
        do {
            if (!i$.hasNext()) {
                return null;
            }

            label = (Assembler.Label)i$.next();
        } while(!label.label.equals(labelname));

        return label;
    }

    private Assembler.Label getLabel(String labelname) throws Exception {
        Assembler.Label label = this.findLabel(labelname);
        if (label == null) {
            label = new Assembler.Label(labelname);
            this.labels.add(label);
        }

        return label;
    }

    private void createWord(int addr, String value, int size) throws Exception {
        if (value.equals("")) {
            throw new Exception("Пустое значение");
        } else if (!value.equals("?")) {
            int cmd = 0;
            Assembler.Label arg = null;
            if (Utils.isHexNumeric(value)) {
                cmd = Integer.parseInt(value, 16);
            } else {
                arg = this.getLabel(value);
            }

            this.cmds.add(new Assembler.Command(addr, cmd, arg, size));
        }
    }

    private void checkAddr(int addr) throws Exception {
        if (addr < 0 || addr > 2047) {
            throw new Exception("Адрес выходит из допустимых значений");
        }
    }

    public Instruction findInstruction(String mnemonics) {
        Instruction[] arr$ = this.instrset;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Instruction instr = arr$[i$];
            if (instr.getMnemonics().equals(mnemonics)) {
                return instr;
            }
        }

        return null;
    }

    public void loadProgram(CPU cpu) throws Exception {
        if (cpu.isRunning()) {
            throw new Exception("Операция невозможна: выполняется программа");
        } else {

            for (Command cmd : this.cmds) {
                if (!cpu.runSetAddr(cmd.addr)) {
                    throw new Exception("Операция прервана: выполняется программа");
                }

                for (int i = 0; i < cmd.size; ++i) {
                    if (!cpu.runWrite(cmd.getCommand())) {
                        throw new Exception("Операция прервана: выполняется программа");
                    }
                }
            }

            if (!cpu.runSetAddr(this.getBeginAddr())) {
                throw new Exception("Операция прервана: выполняется программа");
            }
        }
    }

    public int getLabelAddr(String labelname) throws Exception {
        Assembler.Label label = this.findLabel(labelname);
        if (label == null) {
            throw new Exception("Метка " + labelname + " не найдена");
        } else {
            return label.addr;
        }
    }

    public int getBeginAddr() throws Exception {
        return this.getLabelAddr("BEGIN");
    }

    private String[] compileUserCustomCommand(String[] line){
        int addr;
        try {
            addr = Integer.parseInt(line[1], 16);
        } catch (NumberFormatException e){
            return line;
        }
        if (line[0].toUpperCase().equals("ACOM")){
            return ("WORD " + Integer.toString(Integer.parseInt("7000", 16) + addr, 16)).split(" ");
        } else if (line[0].toUpperCase().equals("BRCOM")) {
            return ("WORD " + Integer.toString(Integer.parseInt("D000", 16) + addr, 16)).split(" ");
        } else {
            return line;
        }
    }

    protected class Command {
        private final Assembler.Label arg;
        private final int cmd;
        private final int addr;
        private final int size;

        private Command(int addr, int cmd, Assembler.Label arg, int size) throws Exception {
            this.addr = addr;
            this.cmd = cmd;
            this.arg = arg;
            this.size = size;
            Assembler.this.checkAddr(addr);
        }

        private Command(int addr, int cmd, Assembler.Label arg) throws Exception {
            this(addr, cmd, arg, 1);
        }

        private Command(int addr, int cmd) throws Exception {
            this(addr, cmd, null, 1);
        }

        protected int getCommand() {
            return this.arg == null ? this.cmd : this.cmd + this.arg.addr;
        }
    }

    private class Label {
        private final String label;
        private Integer addr;

        private Label(String label) throws Exception {
            this.label = label;
            if (label.equals("")) {
                throw new Exception("Имя метки не может быть пустым");
            } else if (Utils.isHexNumeric(label)) {
                throw new Exception("Имя метки не должно быть шестнадцатеричным числом");
            }
        }

        private void setAddr(int addr) throws Exception {
            Assembler.this.checkAddr(addr);
            this.addr = addr;
        }

        private boolean hasAddress() {
            return this.addr != null;
        }
    }
}
