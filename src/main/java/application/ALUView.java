package application;

import javax.swing.*;
import java.awt.*;

/**
 * @author Arthur Kupriyanov
 */
public class ALUView extends JComponent {
    private int[] xpoints;
    private int[] ypoints;

    public ALUView(int x, int y, int width, int height) {
        int half = width / 2;
        int offset = height / 3;
        int soffset = offset / 3;
        this.xpoints = new int[]{0, half - soffset, half, half + soffset, width - 1, width - 1 - offset, offset};
        this.ypoints = new int[]{0, 0, offset, 0, 0, height - 1, height - 1};
        JLabel title = new JLabel("АЛУ", 0);
        title.setFont(DisplayStyles.FONT_COURIER_BOLD_45);
        title.setBounds(offset, offset, width - 2 * offset, height - offset);
        title.setForeground(Settings.getMainTextColor());
        this.add(title);
        this.setBounds(x, y, width, height);
    }

    public void paintComponent(Graphics g) {
        if (Settings.getInputTitleColor()!=null) {
            g.setColor(Settings.getInputTitleColor());
            g.fillPolygon(this.xpoints, this.ypoints, this.xpoints.length);
        }
        if (Settings.getBorderColor()!=null) {
            g.setColor(Color.BLACK); // Settings.getBorderColor());
            g.drawPolygon(this.xpoints, this.ypoints, this.xpoints.length);
        }
    }
}