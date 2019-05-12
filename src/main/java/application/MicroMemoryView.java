package application;

import ru.ifmo.cs.bcomp.CPU;
import ru.ifmo.cs.bcomp.MicroProgram;

import javax.swing.*;

/**
 * @author Arthur Kupriyanov
 */
public class MicroMemoryView extends MemoryView {
    private final MicroProgram mp;

    public MicroMemoryView(CPU cpu, int x, int y) {
        super(cpu.getMicroMemory(), x, y);
        this.mp = cpu.getMicroProgram();
    }

    void updateValue(JLabel label, int value) {
        super.updateValue(label, value);
        label.setToolTipText(this.mp.decodeCmd(value));
    }
}
