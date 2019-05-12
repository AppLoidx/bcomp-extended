package application;


import ru.ifmo.cs.bcomp.CPU;
import ru.ifmo.cs.bcomp.RunningCycle;

import javax.swing.*;

/**
 * @author Arthur Kupriyanov
 */
public class RunningCycleView extends BCompComponent {
    private CPU cpu;
    private static final String[] cycles = new String[]{"Выборка команды", "Выбора адреса", "Исполнение", "Прерывание", "Пультовая операция", "Программа"};
    private final JLabel[] labels;
    private RunningCycle lastcycle;
    private int lastprogram;

    public RunningCycleView(CPU cpu, int x, int y) {
        super("Устройство управления", cycles.length);
        this.labels = new JLabel[cycles.length];
        this.lastcycle = RunningCycle.NONE;
        this.lastprogram = 0;
        this.cpu = cpu;
        this.setBounds(x, y, DisplayStyles.REG_16_WIDTH);

        for(int i = 0; i < cycles.length; ++i) {
            this.labels[i] = this.addValueLabel(cycles[i]);
            this.labels[i].setBounds(1, this.getValueY(i), this.width - 2, 25);
        }

    }

    public void update() {
        RunningCycle newcycle = this.cpu.getRunningCycle();
        int newProgram = this.cpu.getStateValue(8);
        if (newcycle != this.lastcycle) {
            if (newcycle == RunningCycle.INTERRUPT){
                CycleActionManager.onInterruptCycle();
            }
            if (this.lastcycle != RunningCycle.NONE) {
                this.labels[this.lastcycle.ordinal()].setForeground(DisplayStyles.COLOR_TEXT);
            }

            if (newcycle != RunningCycle.NONE) {
                this.labels[newcycle.ordinal()].setForeground(DisplayStyles.COLOR_ACTIVE);
            }



            this.lastcycle = newcycle;
        }

        if (newProgram != this.lastprogram) {
            this.labels[this.labels.length - 1].setForeground(newProgram == 0 ? DisplayStyles.COLOR_TEXT : DisplayStyles.COLOR_ACTIVE);
            this.lastprogram = newProgram;
        }

    }
}
