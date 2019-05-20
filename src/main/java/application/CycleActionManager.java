package application;

import ru.ifmo.cs.bcomp.CPU;

/**
 * @author Arthur Kupriyanov
 */
public class CycleActionManager {
    private static GUI gui;
    public static void onInterruptCycle(){
        if (gui==null){
            System.err.println("gui не инициализирован [CycleActionManager]");
            return;
        }
        CPU cpu = gui.getCPU();
        gui.getComponentManager().getRegisterView(CPU.Reg.ADDR);
        int addr = cpu.getRegister(CPU.Reg.IP).getValue();
        if (Debugger.checkIsMarked(addr)){
            if (1 == cpu.getStateValue(7)){      // если активный инвертируем
                gui.getComponentManager().cmdInvertRunState();
            }
        }

    }

    public static void setGui(GUI gui){
        CycleActionManager.gui = gui;
    }
}
