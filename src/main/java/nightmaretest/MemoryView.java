package nightmaretest;

import ru.ifmo.cs.bcomp.CPU;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;
import ru.ifmo.cs.elements.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * @author Arthur Kupriyanov
 */
public class MemoryView extends ActivateblePanel {

    private final GUI gui;
    private Memory memory;
    MemoryView(GUI gui){
//        CPU cpu = gui.getCPU();
        memory = gui.getCPU().getMemory();

        this.gui = gui;
        JLabel panel = new JLabel();

        TextField addr = new TextField();
        TextField output = new TextField();
        JButton button = new JButton("Найти");

        JLabel labelAddr = new JLabel();
        labelAddr.setText("Адрес: ");
        JLabel labelVal = new JLabel();
        labelVal.setText("Значение: ");
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = addr.getText();
                if (!text.matches("[0-9a-fA-F]+")) return;

                int addr = Integer.parseInt(text, 16);
                System.out.println(addr);
                try {
                    output.setText("" + memory.getValue(addr));
                }catch (ArrayIndexOutOfBoundsException exp){
                    System.err.println("Недопустимое значение");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        button.setBounds(250, 200, 100, 30);
        addr.setBounds(250, 100, 100, 25);
        labelAddr.setBounds(150, 100, 100, 25);
        output.setBounds(250, 125, 100, 25);
        labelVal.setBounds(150, 125, 100, 25);
        output.setEditable(false);
        panel.add(addr);
        panel.add(labelAddr);
        panel.add(output);
        panel.add(labelVal);
        panel.add(button);

        panel.setBounds(0, 0, 544, 600);
        this.add(panel);


    }
    @Override
    public void panelActivate() {

    }

    @Override
    public void panelDeactivate() {

    }

    @Override
    public String getPanelName() {
        return "Memory";
    }


}
