package application;

import ru.ifmo.cs.bcomp.Utils;
import ru.ifmo.cs.elements.Memory;

import javax.swing.*;
import java.awt.*;

/**
 * @author Arthur Kupriyanov
 */
public class MemoryView extends BCompComponent {
    private Memory mem;
    private int addrBitWidth;
    private int valueBitWidth;
    private int lineX;
    private int lastPage = 0;
    private JLabel[] addrs = new JLabel[16];
    private JLabel[] values = new JLabel[16];

    public MemoryView(Memory mem, int x, int y) {
        super(mem.name, 16);
        this.mem = mem;
        this.addrBitWidth = mem.getAddrWidth();
        int addrWidth = DisplayStyles.FONT_COURIER_BOLD_25_WIDTH * (1 + Utils.getHexWidth(this.addrBitWidth));
        this.valueBitWidth = mem.getWidth();
        int valueWidth = DisplayStyles.FONT_COURIER_BOLD_25_WIDTH * (1 + Utils.getHexWidth(this.valueBitWidth));
        this.lineX = 1 + addrWidth;
        this.setBounds(x, y, 3 + addrWidth + valueWidth);

        for(int i = 0; i < 16; ++i) {
            this.addrs[i] = this.addValueLabel(DisplayStyles.COLOR_TITLE);
            this.addrs[i].setBounds(1, this.getValueY(i), addrWidth, 25);
            this.values[i] = this.addValueLabel(DisplayStyles.COLOR_VALUE);
            this.values[i].setBounds(this.lineX + 1, this.getValueY(i), valueWidth, 25);
        }

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(this.lineX, 27, this.lineX, this.height - 2);
    }

    void updateValue(JLabel label, int value) {
        label.setText(Utils.toHex(value, this.valueBitWidth));
    }

    private void updateValue(int offset) {
        this.updateValue(this.values[offset], this.mem.getValue(this.lastPage + offset));
    }

    public void updateMemory() {
        for(int i = 0; i < 16; ++i) {
            this.addrs[i].setText(Utils.toHex(this.lastPage + i, this.addrBitWidth));
            this.updateValue(i);
        }

    }

    private int getPage(int addr) {
        return addr & -16;
    }

    private int getPage() {
        return this.getPage(this.mem.getAddrValue());
    }

    public void updateLastAddr() {
        this.lastPage = this.getPage();
    }

    public void eventRead() {
        int addr = this.getPage();
        if (addr != this.lastPage) {
            this.lastPage = addr;
            this.updateMemory();
        }

    }

    public void eventWrite() {
        int addr = this.mem.getAddrValue();
        int page = this.getPage(addr);
        if (page != this.lastPage) {
            this.lastPage = page;
            this.updateMemory();
        } else {
            this.updateValue(addr - page);
        }

    }
}
