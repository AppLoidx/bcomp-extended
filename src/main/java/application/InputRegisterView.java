package application;

/**
 * @author Arthur Kupriyanov
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import ru.ifmo.cs.bcomp.Utils;
import ru.ifmo.cs.elements.Register;

import java.awt.event.*;

public class InputRegisterView extends RegisterView {
    private final ComponentManager cmanager;
    private final Register reg;
    private final ActiveBitView activeBitView;
    private boolean active = false;
    private int regWidth;
    private int bitno;
    private int formattedWidth;

    public InputRegisterView(ComponentManager cmgr, Register reg) {
        super(reg, DisplayStyles.COLOR_INPUT_TITLE);
        this.cmanager = cmgr;
        this.reg = reg;
        this.activeBitView = this.cmanager.getActiveBit();
        this.bitno = (this.regWidth = reg.getWidth()) - 1;
        this.formattedWidth = Utils.getBinaryWidth(this.regWidth);
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!InputRegisterView.this.value.isFocusOwner()) {
                    InputRegisterView.this.reqFocus();
                }

            }
        });
        this.value.setFocusable(true);
        this.value.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                InputRegisterView.this.active = true;
                InputRegisterView.this.setActiveBit(InputRegisterView.this.bitno);
            }

            public void focusLost(FocusEvent e) {
                InputRegisterView.this.active = false;
                InputRegisterView.this.setValue();
            }
        });
        this.value.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                case 8:
                case 37:
                    InputRegisterView.this.moveLeft();
                    break;
                case 38:
                    InputRegisterView.this.invertBit();
                    break;
                case 39:
                    InputRegisterView.this.moveRight();
                    break;
                case 48:
                case 96:
                    InputRegisterView.this.setBit(0);
                    break;
                case 49:
                case 97:
                    InputRegisterView.this.setBit(1);
                    break;
                default:
                    InputRegisterView.this.cmanager.keyPressed(e);
                }

            }
        });
        this.value.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!InputRegisterView.this.value.isFocusOwner()) {
                    InputRegisterView.this.reqFocus();
                }

                int bitno = Utils.getBitNo(e.getX(), InputRegisterView.this.formattedWidth, DisplayStyles.FONT_COURIER_BOLD_25_WIDTH);
                if (bitno >= 0) {
                    InputRegisterView.this.setActiveBit(bitno);
                    if (e.getClickCount() > 1) {
                        InputRegisterView.this.invertBit();
                    }

                }
            }
        });
    }

    private void setActiveBit(int bitno) {
        this.activeBitView.setValue(this.bitno = bitno);
        this.setValue();
    }

    private void moveLeft() {
        this.setActiveBit((this.bitno + 1) % this.regWidth);
    }

    private void moveRight() {
        this.setActiveBit((this.bitno == 0 ? this.regWidth : this.bitno) - 1);
    }

    private void invertBit() {
        this.reg.invertBit(this.bitno);
        this.setValue();
    }

    private void setBit(int value) {
        this.reg.setValue(value, this.bitno);
        this.moveRight();
    }

    public void setValue() {
        if (this.active) {
            StringBuilder str = new StringBuilder("<html>" + Utils.toBinary(this.reg.getValue(), this.regWidth) + "</html>");
            int pos = 6 + this.formattedWidth - Utils.getBinaryWidth(this.bitno + 1);
            str.insert(pos + 1, "</font>");
            str.insert(pos, String.format("<font color=\"#%s\">", Integer.toHexString(DisplayStyles.COLOR_INPUT_REGISTER_ACTIVE_BIT.getRGB()).substring(2)));
            this.setValue(str.toString());
        } else {
            super.setValue();
        }

    }

    public void reqFocus() {
        try {
            this.value.requestFocus();
        } catch (Exception var2) {
        }

        this.value.requestFocusInWindow();
    }

    public void setActive() {
        this.reqFocus();
        this.active = true;
        this.setActiveBit(this.bitno);
    }
}
