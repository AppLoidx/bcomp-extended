package application.views;

import application.GUI;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Arthur Kupriyanov
 */
public class CheatSheetView extends ActivateblePanel {
    GUI gui;
    Image img;

    public CheatSheetView(GUI gui){
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

        g.drawImage(img, 150 ,0 ,new Color(30,39,45), this);

    }

    @Override
    public void panelActivate() {

    }

    @Override
    public void panelDeactivate() {

    }

    @Override
    public String getPanelName() {
        return "Список команд";
    }
}
