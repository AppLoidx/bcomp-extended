package application.views;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import application.DisplayStyles;
import application.GUI;
import application.RegisterView;
import application.MicroMemoryView;
import application.ALUView;


import ru.ifmo.cs.bcomp.CPU.Reg;
import ru.ifmo.cs.bcomp.ControlSignal;
import ru.ifmo.cs.bcomp.SignalListener;
import ru.ifmo.cs.bcomp.ui.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

//import ru.ifmo.cs.bcomp.ui.GUI;

/**
 * @author Arthur Kupriyanov
 */
public class MPView extends application.BCompPanel {
    private final MicroMemoryView mem;
    private final RegisterView regMIP;
    private final RegisterView regMInstr;
    private final RegisterView regBuf;
    private final RegisterView regState;
    private final JCheckBox cucheckbox;
    private char[] supportedChars = new char[]{'0','1','2','3','4','5','6','7','8','9','a', 'A', 'b', 'B', 'c', 'C', 'd', 'D','e','E','f','F'};
    private static final ControlSignal[] statesignals = new ControlSignal[0];

    public MPView(GUI gui) {
        super(gui.getComponentManager(), new RegisterProperties[]{new RegisterProperties(Reg.ADDR, application.DisplayStyles.CU_X_IO, 17, true), new RegisterProperties(Reg.IP, application.DisplayStyles.REG_IP_X_MP, 17, true), new RegisterProperties(Reg.DATA, application.DisplayStyles.CU_X_IO, 86, true), new RegisterProperties(Reg.INSTR, application.DisplayStyles.REG_INSTR_X_MP, 86, true), new RegisterProperties(Reg.ACCUM, application.DisplayStyles.CU_X_IO, 261, true), new RegisterProperties(Reg.STATE, application.DisplayStyles.REG_STATE_X, 330, true)}, new BusView[0]);
        this.add(this.mem = this.cmanager.getMicroMemory());
        this.regMIP = this.cmanager.getRegisterView(Reg.MIP);
        this.regMIP.setProperties(400, 1, false);
        this.add(this.regMIP);
        this.regMInstr = this.cmanager.getRegisterView(Reg.MINSTR);
        this.regMInstr.setProperties(400, 100, false);
        this.add(this.regMInstr);
        this.regBuf = this.cmanager.getRegisterView(Reg.BUF);
        this.regBuf.setProperties(application.DisplayStyles.REG_BUF_X_MP, 261, true);
        this.add(this.regBuf);
        this.regState = this.cmanager.getRegisterView(Reg.STATE);
        this.setSignalListeners(new SignalListener[]{new SignalListener(this.regState, ControlSignal.HALT, ControlSignal.BUF_TO_STATE_N, ControlSignal.BUF_TO_STATE_Z, ControlSignal.DISABLE_INTERRUPTS, ControlSignal.ENABLE_INTERRUPTS, ControlSignal.IO0_TSF, ControlSignal.IO1_TSF, ControlSignal.IO2_TSF, ControlSignal.IO3_TSF, ControlSignal.IO4_TSF, ControlSignal.IO5_TSF, ControlSignal.IO6_TSF, ControlSignal.IO7_TSF, ControlSignal.IO8_TSF, ControlSignal.IO9_TSF, ControlSignal.SET_RUN_STATE, ControlSignal.SET_PROGRAM, ControlSignal.SET_REQUEST_INTERRUPT), new SignalListener(this.regBuf, ControlSignal.ALU_AND, ControlSignal.SHIFT_RIGHT, ControlSignal.SHIFT_LEFT)});

        this.cucheckbox = this.cmanager.getMPCheckBox();
        cucheckbox.setBackground(new Color(219, 249, 235, 0));
        JPanel checkBoxPanel = new JPanel(){
            protected void paintComponent(Graphics g)
            {
                g.setColor( DisplayStyles.COLOR_INPUT_TITLE );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        checkBoxPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        checkBoxPanel.setBounds(400, 200, 300, 100);


        JTextPane regField = new JTextPane();
        regField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (regField.getText().length() > 3){
                    if (regField.getSelectedText() == null || regField.getSelectedText().isEmpty()) {
                        e.consume();
                        return;
                    }

                    if (!(regField.getText().length() - regField.getSelectedText().length() < 5)){
                        e.consume();
                    }
                }

                char keyChar = e.getKeyChar();
                for (char c : supportedChars){
                    if (keyChar == c){

                        return;
                    }
                }
                if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
//        regField.setSize(200, 100);
        regField.setMinimumSize(new Dimension(300, 50));
        regField.setPreferredSize(new Dimension(60, 25));
        regField.setFont(new Font("Courier New", Font.BOLD, 20));
        regField.setText("0000");
        regField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        regField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                regField.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
                regField.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                regField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        checkBoxPanel.add(regField, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton mpSubmitButton = new JButton("Ввести данные в УУ");
        mpSubmitButton.addActionListener( a->{
            if (regField.getText().isEmpty()) return;
            gui.getCPU().setRegKey(Integer.parseInt(regField.getText(), 16));
            if (!gui.getComponentManager().isCuswitch()) {
                gui.getComponentManager().setCuswitch(true);
                gui.getComponentManager().cmdWrite();
                gui.getComponentManager().setCuswitch(false);
            } else {
                gui.getComponentManager().cmdWrite();
            }
        });
        JButton mpAddButton = new JButton("Ввести значение в память");
        mpAddButton.addActionListener( a->{
            if (regField.getText().isEmpty()) return;
            gui.getCPU().setRegKey(Integer.parseInt(regField.getText(), 16));
            if (!gui.getComponentManager().isCuswitch()) {
                gui.getComponentManager().setCuswitch(true);
                gui.getComponentManager().cmdEnterAddr();
                gui.getComponentManager().setCuswitch(false);
            } else {
                gui.getComponentManager().cmdEnterAddr();
            }
        });
        buttonsPanel.add(mpAddButton);
        buttonsPanel.add(mpSubmitButton);

        mpSubmitButton.setPreferredSize(new Dimension(200, 25));
        mpAddButton.setPreferredSize(new Dimension(200, 25));

        buttonsPanel.setPreferredSize(new Dimension(200, 65));
        buttonsPanel.setBackground(new Color(255,255,255,0));
        checkBoxPanel.add(buttonsPanel);

        cucheckbox.setPreferredSize(new Dimension(210, 14));
        checkBoxPanel.add(cucheckbox);
        checkBoxPanel.setOpaque(false);
        checkBoxPanel.setBackground(new Color(219, 249, 235, 180));
        this.add(checkBoxPanel);

        this.add(new ALUView(DisplayStyles.ALU_X_MP, 155, 181, 90));
    }

    public void panelActivate() {
        this.mem.updateLastAddr();
        this.mem.updateMemory();
        this.regMIP.setValue();
        this.regMInstr.setValue();
        this.regBuf.setValue();
        this.cucheckbox.setSelected(false);
        super.panelActivate();
    }

    public String getPanelName() {
        return "Работа с МПУ";
    }

    public void stepStart() {
        this.mem.eventRead();
    }

    public void stepFinish() {
        ArrayList<ControlSignal> signals = this.cmanager.getActiveSignals();
        this.regMIP.setValue();
        this.regMInstr.setValue();
    }
}
