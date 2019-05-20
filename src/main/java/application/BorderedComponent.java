package application;

import application.DisplayStyles;

import javax.swing.*;
import java.awt.*;

/**
 * @author Arthur Kupriyanov
 */
public class BorderedComponent extends JComponent {
    protected int width;
    protected final int height;

    protected BorderedComponent(int height) {
        this.height = height;
    }

    protected final JLabel addLabel(String value, Font font, Color color) {
        JLabel label = new JLabel(value, 0);
        label.setFont(font);
        label.setBackground(color);
        label.setOpaque(true);
        label.setForeground(DisplayStyles.MAIN_TEXT_COLOR);
        this.add(label);
        return label;
    }

    protected void setBounds(int x, int y, int width) {
        this.setBounds(x, y, this.width = width, this.height);
    }

    public void paintComponent(Graphics g) {
        if (Settings.getBorderColor()!=null) {
            g.setColor(Color.BLACK);// Settings.getBorderColor());
            g.drawRect(0, 0, this.width - 1, this.height - 1);
        }
    }
}

