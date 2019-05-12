package application;

import javax.swing.*;

/**
 * @author Arthur Kupriyanov
 */
public class BCompLabel extends BorderedComponent {
    public BCompLabel(int x, int y, int width, String... text) {
        super(text.length * 25 + 2);
        this.setBounds(x, y, width);

        for(int i = 0; i < text.length; ++i) {
            JLabel title = this.addLabel(text[i], DisplayStyles.FONT_COURIER_BOLD_21, DisplayStyles.COLOR_INPUT_TITLE);
            title.setForeground(DisplayStyles.MAIN_TEXT_COLOR);
            title.setBounds(1, 1 + i * 25, width - 2, 25);
        }

    }
}