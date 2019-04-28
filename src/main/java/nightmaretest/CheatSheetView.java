package nightmaretest;

import ru.ifmo.cs.bcomp.CPU;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Arthur Kupriyanov
 */
public class CheatSheetView extends ActivateblePanel {
    GUI gui;
    CPU cpu;
    Image img;
    CheatSheetView(GUI gui){
        this.gui = gui;
        try {
            String backgroundPath = "/command-sheet.png";
            InputStream in = getClass().getResourceAsStream(backgroundPath);
            img = ImageIO.read(in);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.drawImage(img, 150 ,0 ,Color.WHITE, this);

//        g.fillRect(0, 0, this.getWidth(), this.getHeight());

    }

    @Override
    public void panelActivate() {

    }

    @Override
    public void panelDeactivate() {

    }

    @Override
    public String getPanelName() {
        return "Cheat sheet";
    }
}
