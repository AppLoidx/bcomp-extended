package application;

import ru.ifmo.cs.bcomp.StateReg;
import ru.ifmo.cs.bcomp.Utils;
import ru.ifmo.cs.elements.Register;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @author Arthur Kupriyanov
 */
public class StateRegisterView extends RegisterView {
    private final int formattedWidth;
    private MouseMotionAdapter listener = new MouseMotionAdapter() {
        private String tooltip = null;

        public void mouseMoved(MouseEvent e) {
            int bitno = Utils.getBitNo(e.getX(), StateRegisterView.this.formattedWidth, DisplayStyles.FONT_COURIER_BOLD_25_WIDTH);
            if (bitno < 0) {
                StateRegisterView.this.value.setToolTipText(this.tooltip = null);
            } else {
                String newtooltip = StateReg.FULLNAME[bitno];
                if (newtooltip != this.tooltip) {
                    StateRegisterView.this.value.setToolTipText(this.tooltip = newtooltip);
                }

            }
        }
    };

    public StateRegisterView(Register reg) {
        super(reg);
        this.formattedWidth = Utils.getBinaryWidth(reg.getWidth());
    }

    public void setProperties(int x, int y, boolean fullView) {
        super.setProperties(x, y, !fullView, fullView ? this.getRegWidth() : 1);
        if (fullView) {
            this.value.addMouseMotionListener(this.listener);
        } else {
            this.value.removeMouseMotionListener(this.listener);
            this.value.setToolTipText((String)null);
        }

    }
}
