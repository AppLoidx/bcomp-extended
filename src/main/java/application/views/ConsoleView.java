package application.views;

import application.GUI;
import application.Settings;
import core.cli.CustomCLI;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;
import util.UserIOStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Arthur Kupriyanov
 */
public class ConsoleView extends ActivateblePanel {
    private CustomCLI cli;
    private final UserIOStream inputStream;
    private final UserIOStream outputStream;
    private final JTextField consoleInputField;
    private TextArea console;
    private Thread cliThread;
    private boolean cliThreadIsActive = false;
    private final GUI gui;
    private boolean initializedOnce = false;
    private final JScrollPane pane;

    public ConsoleView(GUI aGui){
        this.gui = aGui;
        inputStream = new UserIOStream();
        outputStream = new UserIOStream();
        console = new TextArea();
        pane = new JScrollPane(console);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pane.setBounds(0, 0, 866, 524);


        this.consoleInputField = new JTextField();
        consoleInputField.setBounds(0, 524, 866, 30);
        setConsoleListener();

        this.add(consoleInputField);
        this.add(pane);

    }

    private void newCLISession(){
        cli = new CustomCLI(this.gui.getCPU().getMicroProgram(), outputStream, this.gui.getBasicComp());
        cliThread = new Thread(()-> cli.cli(inputStream));
    }

    private void setConsoleListener(){
        this.consoleInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    String cmd = consoleInputField.getText();
                    consoleInputField.setText("");
                    if (!cmd.isEmpty()){
                        console.setText(console.getText() + ">> " + cmd + "\n");
//                        console.revalidate();
                        updateConsole(cmd);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void updateConsole(String command){
        // clean old data
        if (outputStream.available()) console.setText(console.getText() + outputStream.readString() + "\n");

        while(inputStream.available()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        inputStream.writeln(command);

        while(!outputStream.available()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        console.setText(console.getText() + outputStream.readString() + "\n");

//        console.revalidate();
    }

    @Override
    public void panelActivate() {
        // может возникнуть java.lang.IllegalThreadStateException
        this.consoleInputField.requestFocus();
        if (!cliThreadIsActive) {
            newCLISession();
            cliThread.start();
            cliThreadIsActive = true;

            this.gui.getCPU().setTickFinishListener(() -> {
                if (cli.getSleep() > 0) {
                    try {
                        Thread.sleep((long) cli.getSleep());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });

            while (!outputStream.available()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!initializedOnce) {
                //newLine(outputStream.readString());
                initializedOnce = true;
            } else outputStream.readString();
        }
    }

    private void newLine(String newText){
        console.setText(console.getText() + newText + "\n");
        console.setCaretPosition(console.getText().length());
        this.console.revalidate();
    }

    @Override
    public void panelDeactivate() {
        cliThread.interrupt();
        cliThreadIsActive = false;

        this.gui.getCPU().setTickFinishListener(() -> {

                try {
                    Thread.sleep(Settings.getTickFinishSleepTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        });
    }

    @Override
    public String getPanelName() {
        return "CLI";
    }
}