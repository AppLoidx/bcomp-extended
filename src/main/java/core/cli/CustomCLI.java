package core.cli;


import core.cli.interpretator.CLIInterpreter;
import ru.ifmo.cs.bcomp.*;
import util.UserIOStream;

import java.util.ArrayList;

/**
 * Custom CLI from original bcomp by se.ifmo
 *
 * @author Kupriyanov Arthur
 */
public class CustomCLI {
    private final BasicComp bcomp;
    private final MicroProgram mp;
    private final CPU cpu;
    private final IOCtrl[] ioctrls;
    private final Assembler asm;
    private final ArrayList<Integer> writelist = new ArrayList<>();
    private int sleeptime = 1;
    private volatile int addr;
    private volatile boolean printOnStop = true;
    private volatile boolean printRegsTitle = false;
    private volatile boolean printMicroTitle = false;
    private volatile int sleep = 0;
    private UserIOStream outputStream;
    private boolean exitStatus = false;
    private boolean fromGui = true;

    public CustomCLI(MicroProgram mp, UserIOStream outputStream, BasicComp bc){
        this.outputStream = outputStream;
        this.bcomp = bc;
        this.mp = mp;
        this.cpu = this.bcomp.getCPU();
        this.bcomp.addDestination(ControlSignal.MEMORY_WRITE, value -> {
            int addr = CustomCLI.this.cpu.getRegValue(CPU.Reg.ADDR);
            if (!CustomCLI.this.writelist.contains(addr)) {
                CustomCLI.this.writelist.add(addr);
            }

        });
        this.cpu.setCPUStartListener(() -> {
            if (CustomCLI.this.printOnStop) {
                CustomCLI.this.writelist.clear();
                CustomCLI.this.addr = CustomCLI.this.getIP();
                CustomCLI.this.printRegsTitle();
            }

        });
        this.cpu.setCPUStopListener(() -> {
            CustomCLI.this.sleep = 0;
            if (CustomCLI.this.printOnStop) {
                String add;
                if (CustomCLI.this.writelist.isEmpty()) {
                    add = "";
                } else {
                    add = " " + CustomCLI.this.getMemory(CustomCLI.this.writelist.get(0));
                    CustomCLI.this.writelist.remove(0);
                }

                CustomCLI.this.printRegs(add);

                for (Integer wraddr : CustomCLI.this.writelist) {
                    outputStream.writeln(String.format("%1$34s", " ") + CustomCLI.this.getMemory(wraddr));
                }
            }

        });

        this.asm = new Assembler(this.cpu.getInstructionSet());
        this.ioctrls = this.bcomp.getIOCtrls();
    }

    public CustomCLI(MicroProgram mp, UserIOStream outputStream) throws Exception {
        this(mp, outputStream, new BasicComp(mp));
        this.fromGui = false;
        CPU cpu = this.bcomp.getCPU();
        cpu.setTickFinishListener(() -> {
            if (CustomCLI.this.sleep > 0) {
                try {
                    Thread.sleep((long) CustomCLI.this.sleep);
                } catch (InterruptedException ignored) {
                }
            }

        });
    }


    public static String getHelp() {
        return "Доступные команды:\nfirst[ddress]\t- Пультовая операция \"Ввод адреса\"\nw[rite]\t\t- Пультовая операция \"Запись\"\nr[ead]\t\t- Пультовая операция \"Чтение\"\ns[tart]\t\t- Пультовая операция \"Пуск\"\nc[continue]\t- Пультовая операция \"Продолжить\"\nru[n]\t\t- Переключение режима Работа/Останов\ncl[ock]\t\t- Переключение режима потактового выполнения\nma[ddress]\t- Переход на микрокоманду\nmw[rite]\t- Запись микрокоманды\nmr[ead]\t\t- Чтение микрокоманды\nio\t\t- Вывод состояния всех ВУ\nio addr\t\t- Вывод состояния указанного ВУ\nio addr value\t- Запись value в указанное ВУ\nflag addr\t- Установка флага готовности указанного ВУ\nasm\t\t- Ввод программы на ассемблере\nsleep value\t- Задержка между тактами при фоновом выполнении\n{exit|quit}\t- Выход из эмулятора\nvalue\t\t- Ввод шестнадцатеричного значения в клавишный регистр\nlabel\t\t- Ввод адреса метки в клавишный регистр";
    }

    private String getReg(CPU.Reg reg) {
        return Utils.toHex(this.cpu.getRegValue(reg), this.cpu.getRegWidth(reg));
    }

    private String getFormattedState(int flag) {
        return Utils.toBinaryFlag(this.cpu.getStateValue(flag));
    }

    private void printRegsTitle() {
        if (this.printRegsTitle) {
            outputStream.writeln(this.cpu.getClockState() ? "Адр\t Знчн\t  СК\t  РА\t  РК\t  РД\t     А\t C\t  Адр\tЗнчн\n" : "Адр\t МК\t   СК\t  РА\t  РК\t  РД\t     А\t C\t   БР\t  N\t Z\t СчМК\n");
            this.printRegsTitle = false;
        }

    }

    private String getMemory(int addr) {
        return Utils.toHex(addr, 11) + " " + Utils.toHex(this.cpu.getMemoryValue(addr), 16);
    }

    private String getMicroMemory(int addr) {
        return Utils.toHex(addr, 8) + " " + Utils.toHex(this.cpu.getMicroMemoryValue(addr), 16);
    }

    private void printMicroMemory(int addr) {
        if (this.printMicroTitle) {
            outputStream.writeln("Адр МК");
            this.printMicroTitle = false;
        }

        outputStream.writeln(this.getMicroMemory(addr) + " " + this.mp.decodeCmd(this.cpu.getMicroMemoryValue(addr)));
    }

    private String getRegs() {
        return this.getReg(CPU.Reg.IP) + " " + this.getReg(CPU.Reg.ADDR) + " " + this.getReg(CPU.Reg.INSTR) + " " + this.getReg(CPU.Reg.DATA) + " " + this.getReg(CPU.Reg.ACCUM) + " " + this.getFormattedState(0);
    }

    private void printRegs(String add) {
        outputStream.writeln((this.cpu.getClockState() ? this.getMemory(this.addr) + " " + this.getRegs() + add : this.getMicroMemory(this.addr) + " " + this.getRegs() + " " + this.getReg(CPU.Reg.BUF) + " " + this.getFormattedState(2) + " " + this.getFormattedState(1) + "  " + this.getReg(CPU.Reg.MIP)).replace(" ", "\t"));
    }

    private void printIO(int ioaddr) {
        outputStream.writeln("ВУ" + ioaddr + ": Флаг = " + Utils.toBinaryFlag(this.ioctrls[ioaddr].getFlag()) + " РДВУ = " + Utils.toHex(this.ioctrls[ioaddr].getData(), 8));
    }

    private int getIP() {
        return this.cpu.getClockState() ? this.cpu.getRegValue(CPU.Reg.IP) : this.cpu.getRegValue(CPU.Reg.MIP);
    }

    private boolean checkCmd(String cmd, String check) {
        return cmd.equalsIgnoreCase(check.substring(0, Math.min(check.length(), cmd.length())));
    }

    private void checkResult(boolean result) throws Exception {
        if (!result) {
            throw new Exception("операция не выполнена: выполняется программа");
        }
    }

    private void printHelp() {
        outputStream.writeln(getHelp());
    }

    public void cli(UserIOStream inputStream) {
        this.bcomp.startTimer();
        outputStream.writeln("Эмулятор Базовой ЭВМ." + "\n" + "Загружена " + this.cpu.getMicroProgramName() + " микропрограмма\n" + "Цикл прерывания начинается с адреса " + Utils.toHex(this.cpu.getIntrCycleStartAddr(), 8) + "\n" + "БЭВМ готова к работе.\n" + "Используйте ? или help для получения справки\n\n");
        do {
            String line;
            String[] cmds;
            do {
                try {
                    while (true) {
                        if (inputStream.available()) {
                            line = inputStream.readString();
                            if (line.matches(".*&.*")){

                                line = CLIInterpreter.interpret(line).first;

                            }
                            break;
                        }
                        Thread.sleep(500);
                    }

                } catch (Exception var11) {
//                    var11.printStackTrace();
//                    //System.exit(0);
                    return;
                }
                outputStream.writeln("");
                cmds = line.split("[ \t]+");
            } while (cmds.length == 0);

            int i = 0;

            label214:
            for (this.printRegsTitle = this.printMicroTitle = true; i < cmds.length; ++i) {
                String cmd = cmds[i];
                if (!cmd.equals("")) {
                    if (cmd.charAt(0) == '#') {
                        break;
                    }


                    if (this.checkCmd(cmd, "exit") || this.checkCmd(cmd, "quit")) {
                        if (!fromGui) exitStatus = true;
                        else outputStream.writeln("Команда exit недоступна для GUI режима");
                        break;
                    }

                    if (!this.checkCmd(cmd, "?") && !this.checkCmd(cmd, "help")) {
                        int value;
                        try {
                            if (this.checkCmd(cmd, "address")) {
                                this.checkResult(this.cpu.runSetAddr());
                                continue;
                            }

                            if (this.checkCmd(cmd, "writeln")) {
                                this.checkResult(this.cpu.runWrite());
                                continue;
                            }

                            if (this.checkCmd(cmd, "read")) {
                                this.checkResult(this.cpu.runRead());
                                continue;
                            }

                            if (this.checkCmd(cmd, "start")) {
                                if (i == cmds.length - 1) {
                                    this.sleep = this.sleeptime;
                                    this.checkResult(this.cpu.startStart());
                                } else {
                                    this.checkResult(this.cpu.runStart());
                                }
                                continue;
                            }

                            if (this.checkCmd(cmd, "continue")) {
                                if (i == cmds.length - 1) {
                                    this.sleep = this.sleeptime;
                                    this.checkResult(this.cpu.startContinue());
                                } else {
                                    this.checkResult(this.cpu.runContinue());
                                }
                                continue;
                            }

                            if (this.checkCmd(cmd, "clock")) {
                                outputStream.writeln("Такт: " + (this.cpu.invertClockState() ? "Нет" : "Да"));
                                continue;
                            }

                            if (this.checkCmd(cmd, "run")) {
                                this.cpu.invertRunState();
                                outputStream.writeln("Режим работы: " + (this.cpu.getStateValue(7) == 1 ? "Работа" : "Останов"));
                                continue;
                            }

                            if (this.checkCmd(cmd, "maddress")) {
                                this.checkResult(this.cpu.runSetMAddr());
                                this.printMicroMemory(this.cpu.getRegValue(CPU.Reg.MIP));
                                continue;
                            }

                            int ioaddr;
                            if (this.checkCmd(cmd, "mwrite")) {
                                ioaddr = this.cpu.getRegValue(CPU.Reg.MIP);
                                this.checkResult(this.cpu.runMWrite());
                                this.printMicroMemory(ioaddr);
                                continue;
                            }

                            if (this.checkCmd(cmd, "mread")) {
                                ioaddr = this.cpu.getRegValue(CPU.Reg.MIP);
                                this.checkResult(this.cpu.runMRead());
                                this.printMicroMemory(ioaddr);
                                continue;
                            }

                            if (this.checkCmd(cmd, "io")) {
                                if (i == cmds.length - 1) {
                                    ioaddr = 0;

                                    while (true) {
                                        if (ioaddr >= 4) {
                                            continue label214;
                                        }

                                        this.printIO(ioaddr);
                                        ++ioaddr;
                                    }
                                }

                                ++i;
                                ioaddr = Integer.parseInt(cmds[i], 16);
                                if (i < cmds.length - 1) {
                                    ++i;
                                    value = Integer.parseInt(cmds[i], 16);
                                    this.ioctrls[ioaddr].setData(value);
                                }

                                this.printIO(ioaddr);
                                continue;
                            }

                            if (this.checkCmd(cmd, "flag")) {
                                if (i == cmds.length - 1) {
                                    throw new Exception("команда flag требует аргумент");
                                }

                                ++i;
                                ioaddr = Integer.parseInt(cmds[i], 16);
                                this.ioctrls[ioaddr].setFlag();
                                this.printIO(ioaddr);
                                continue;
                            }

                            if (this.checkCmd(cmd, "asm") || this.checkCmd(cmd, "assembler")) {
                                String code = "";
                                outputStream.writeln("Введите текст программы. Для окончания введите END");

                                while (true) {
                                    while (true) {
                                        if (inputStream.available()) {
                                            line = inputStream.readString();
                                            break;
                                        }
                                        Thread.sleep(600);
                                    }
                                    if (line.equalsIgnoreCase("END")) {
                                        this.printOnStop = false;
                                        this.asm.compileProgram(code);
                                        this.asm.loadProgram(this.cpu);
                                        outputStream.writeln("Программа начинается с адреса " + Utils.toHex(this.asm.getBeginAddr(), 11));
                                        this.printOnStop = true;

                                        try {
                                            outputStream.writeln("Результат по адресу " + Utils.toHex(this.asm.getLabelAddr("R"), 11));
                                        } catch (Exception ignored) {
                                        }
                                        continue label214;
                                    }

                                    code = code.concat(line.concat("\n"));
                                }
                            }

                            if (this.checkCmd(cmd, "sleep")) {
                                if (i == cmds.length - 1) {
                                    throw new Exception("команда sleep требует аргумент");
                                }

                                ++i;
                                this.sleeptime = Integer.parseInt(cmds[i], 16);
                                continue;
                            }
                        } catch (Exception var12) {
                            this.printOnStop = true;
                            outputStream.writeln("Ошибка: " + var12.getMessage());
                            continue;
                        }

                        try {
                            if (Utils.isHexNumeric(cmd)) {
                                value = Integer.parseInt(cmd, 16);
                            } else {
                                value = this.asm.getLabelAddr(cmd.toUpperCase());
                            }

                            this.cpu.setRegKey(value);
                        } catch (Exception var9) {
                            outputStream.writeln("Неизвестная команда " + cmd);
                        }
                    } else {
                        this.printHelp();
                    }
                }
            }
        } while (!exitStatus);
    }

    public int getSleep(){
        return this.sleep;
    }


}
