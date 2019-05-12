package application;

import ru.ifmo.cs.bcomp.CPU;

/**
 * @author Arthur Kupriyanov
 */
public class CycleActionManager {
    private static GUI gui;
    public static void onCommandSelectCycle(){
        if (gui==null){
            System.err.println("gui не инициализирован [CycleActionManager]");
            return;
        }
        CPU cpu = gui.getCPU();
        int addr = cpu.getMemory().getAddrValue();
        System.out.println(addr);
        if (Debugger.checkIsMarked(addr)){
            if (1== cpu.getStateValue(7)){      // если активный инвертируем
                gui.getComponentManager().cmdInvertRunState();
            }
        }

    }

    public static void setGui(GUI gui){
        CycleActionManager.gui = gui;
    }
}
