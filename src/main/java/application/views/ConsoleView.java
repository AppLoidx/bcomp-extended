package application.views;

import application.DisplayStyles;
import application.GUI;
import core.cli.CustomCLI;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;
import util.UserIOStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<String> outputLines = new ArrayList<>();

    public ConsoleView(GUI aGui){
        this.gui = aGui;
        inputStream = new UserIOStream();
        outputStream = new UserIOStream();
        console = new TextArea();
        console.setBackground(DisplayStyles.COLOR_INPUT_TITLE);
        console.setForeground(DisplayStyles.MAIN_TEXT_COLOR);
        console.setFont(new Font("Blogger Sans", Font.PLAIN, 12));
        pane = new JScrollPane(console);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pane.setBounds(0, 0, 866, 524);


        this.consoleInputField = new JTextField();
        consoleInputField.setBackground(DisplayStyles.COLOR_INPUT_BODY);
        consoleInputField.setForeground(DisplayStyles.MAIN_TEXT_COLOR);
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
                        if (cmd.equals("clear")){
                            console.setText("");
                            return;
                        }
                    }

                    updateConsole(cmd);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void updateConsole(String command){
        boolean cut = false;
        if (command.matches(".*&cut.*")){
            cut = true;
            command = command.replace("&cut", "");
        }
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
                if (command.matches(".*&c\\*[0-9]{3,4}.*")){
                    Thread.sleep(500);                      // additional sleep
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String consoleText;
        if (cut) {
            outputLines.clear();
            outputLines.addAll(Arrays.asList(outputStream.readString().split("\n")));
            StringBuilder text = new StringBuilder();
            for (String line : outputLines) {
                String[] rows = line.split("\\s");

                if (rows.length > 1 && rows[1].toLowerCase().equals("f000")) {
                    text.append(line).append("\n");
                    break;
                }
                text.append(line).append("\n");
            }
            consoleText = text.toString();
        } else {
            consoleText = outputStream.readString();
        }
        console.setText(console.getText() + consoleText + "\n");


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
                if (this.gui.IOAlwaysReady) this.gui.getIoView().getIoctrls()[3].setFlag();
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
            }
            inputStream.writeln("");
            outputStream.readString();
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

        this.gui.setSettingsTickTime();
    }

    @Override
    public String getPanelName() {
        return "CLI";
    }

    public void addConsoleText(String text) {
        console.setText(console.getText() + "\n" + text);
    }
}
