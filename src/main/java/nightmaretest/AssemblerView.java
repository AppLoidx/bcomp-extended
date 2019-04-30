package nightmaretest;

/**
 * @author Arthur Kupriyanov
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rtextarea.RTextScrollPane;
import ru.ifmo.cs.bcomp.Assembler;
import ru.ifmo.cs.bcomp.CPU;
import ru.ifmo.cs.bcomp.ui.GUI;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;
import ru.ifmo.cs.bcomp.ui.components.ComponentManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssemblerView extends ActivateblePanel {
    private final GUI gui;
    private final CPU cpu;
    private final ComponentManager cmanager;
    private final Assembler asm;
    private final JTextArea text;
    private SyntaxScheme scheme;


    private Image img;

    {
        try {
            String backgroundPath = "/klimenkov2.png";
            InputStream in = getClass().getResourceAsStream(backgroundPath);
            img = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AssemblerView(GUI gui) {
        this.gui = gui;
        this.cpu = gui.getCPU();
        this.cmanager = gui.getComponentManager();
        this.asm = new Assembler(this.cpu.getInstructionSet());


        this.text = new RSyntaxTextArea();
        this.text.setFont(DisplayStyles.FONT_COURIER_BOLD_21);
        this.text.setBackground(Color.white);
        this.text.setForeground(Color.black);

//        ((RSyntaxTextArea) this.text).setSyntaxScheme(new SyntaxScheme(new Font("Blogger Sans", Font.PLAIN, 14)));

        ((RSyntaxTextArea) this.text).setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
        ((RSyntaxTextArea) this.text).setCurrentLineHighlightColor(new Color(50, 50 ,50, 50));

        RTextScrollPane scroll = new RTextScrollPane(this.text);
        scroll.setBounds(10, 10, 600, 540);
        scroll.setForeground(Color.WHITE);

        this.add(scroll);
        JButton button = new JButton("Компилировать");

//        button.setForeground(Color.WHITE);
//        button.setBackground(Color.darkGray);

        button.setBounds(640, 40, 200, 30);
        button.setFocusable(false);
        button.addActionListener(e -> {
            if (AssemblerView.this.cpu.isRunning()) {
                AssemblerView.this.showError("Для компиляции остановите выполняющуюся программу");
            } else {
                AssemblerView.this.cmanager.saveDelay();
                boolean clock = AssemblerView.this.cpu.getClockState();
                AssemblerView.this.cpu.setClockState(true);

                try {
                    AssemblerView.this.asm.compileProgram(AssemblerView.this.text.getText());
                    AssemblerView.this.asm.loadProgram(AssemblerView.this.cpu);
                } catch (Exception var4) {
                    AssemblerView.this.showError(var4.getMessage());
                }

                AssemblerView.this.cpu.setClockState(clock);
                AssemblerView.this.cmanager.clearActiveSignals();
                AssemblerView.this.cmanager.restoreDelay();
            }
        });

        this.add(button);

    }

    public void panelActivate() {
        this.text.requestFocus();
    }

    public void panelDeactivate() {
    }

    public String getPanelName() {
        return "Ассемблер";
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this.gui, msg, "Ошибка", 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
//        this.setBackground(Color.BLUE);
//        g.setColor(Color.DARK_GRAY);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (img !=null) g.drawImage(img, 0, 0, this);
        super.paintComponent(g);
    }
}

