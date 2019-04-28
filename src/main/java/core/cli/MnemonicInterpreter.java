package core.cli;

/**
 * @author Arthur Kupriyanov
 */
public class MnemonicInterpreter {
    public static String interpret(String cmd){
        if (!cmd.matches("[0-9A-Fa-f]{4}")) return cmd;

        if (cmd.matches("F.*")) {
            switch (cmd) {
                case "F200":
                    return "CLA";
                case "F300":
                    return "CLC";
                case "F400":
                    return "CMA";
                case "F500":
                    return "CMC";
                case "F600":
                    return "ROL";
                case "F700":
                    return "ROR";
                case "F800":
                    return "INC";
                case "F900":
                    return "DEC";
                case "F000":
                    return "HLT";
                case "F100":
                    return "NOP";
                case "FA00":
                    return "EI";
                case "FB00":
                    return "DI";
            }
        }

            boolean indirectAddr = checkIndirectAddr(cmd);
            String firstBit = cmd.substring(0,1);
            String addr = deleteAddrTypeBit(cmd);

            if (indirectAddr) addr = "(" + addr + ")";
            switch (firstBit){
                case "1":
                    return "AND " + addr;
                case "2":
                    return "JSR " + addr;
                case "3":
                    return "MOV " + addr;
                case "4":
                    return "ADD " + addr;
                case "5":
                    return "ADC " + addr;
                case "6":
                    return "SUB " + addr;
                case "8":
                    return "BCS " + addr;
                case "9":
                    return "BPL " + addr;
                case "A":
                    return "BMI " + addr;
                case "B":
                    return "BEQ " + addr;
                case "C":
                    return "BR " + addr;
                case "0":
                    return "ISZ " + addr;
            }

            String twoFirstBit = cmd.substring(0,2);
            addr = cmd.substring(2,4);
            switch (twoFirstBit){
                case "E0":
                    return "CLF " + addr;
                case "E1":
                    return "TSF " + addr;
                case "E2":
                    return "IN "  + addr;
                case "E3":
                    return "OUT " + addr;
            }

        return cmd;
    }

    private static boolean checkIndirectAddr(String cmd){
        return cmd.split("")[1].matches("[89A-Fa-f]");
    }
    private static String deleteAddrTypeBit(String cmd){
        String originAddr = cmd.substring(1,4);
        if (checkIndirectAddr(cmd)){
            int addr = Integer.parseInt(originAddr, 16);
            int indirectAddrBit = Integer.parseInt("800", 16);
            addr = addr - indirectAddrBit;

            return String.format("%X", addr);
        }
        return originAddr;
    }

}
