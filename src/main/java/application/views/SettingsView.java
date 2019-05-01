package application.views;

import application.GUI;
import application.Settings;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Arthur Kupriyanov
 */
public class SettingsView extends ActivateblePanel {
    private final GUI gui;
    Image img ;

    public SettingsView(GUI aGui){
        this.gui = aGui;
        setSleepTimeSettings(0, 0);
        JButton activeBusColorChooserBtn = new JButton("Выбрать цвет для активной стрелки");
        JButton busColorChooserBtn = new JButton("Выбрать цвет стрелок");
        busColorChooserBtn.addActionListener(a-> createColorChooseWindow(0));
        activeBusColorChooserBtn.addActionListener(a-> createColorChooseWindow(1));

        JButton backgroundSelectBtn = new JButton("Выбрать фоновое изображение");
        backgroundSelectBtn.addActionListener( a -> createFileChooseWindow());

        JButton setDefaultBtn = new JButton("Установить настройки по умолчанию");
        setDefaultBtn.addActionListener(a -> Settings.setDefault());

        activeBusColorChooserBtn.setBounds(20, 30, 250, 30);
        busColorChooserBtn.setBounds(20 + 250, 30, 250, 30);
        backgroundSelectBtn.setBounds(20, 60, 500, 30);
        setDefaultBtn.setBounds(20, 100, 500 , 30);

        this.add(activeBusColorChooserBtn);
        this.add(busColorChooserBtn);
        this.add(backgroundSelectBtn);
        this.add(setDefaultBtn);
    }

    private void createFileChooseWindow(){
        JFrame frame = new JFrame("Color chooser for BUS");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pane = new JPanel();

        JFileChooser fileChooser = new JFileChooser();
        pane.add(fileChooser);
        fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().matches(".+\\.((jpg)|(png)|(bmp))?") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "изображение";
            }
        });
        fileChooser.addActionListener(a -> {
            if (a.getID() == ActionEvent.ACTION_PERFORMED && fileChooser.getSelectedFile()!=null) {

                Settings.setBackgroundPath(fileChooser.getSelectedFile().getPath());
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        frame.getContentPane().add(pane, BorderLayout.CENTER);

        frame.setSize(650, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createColorChooseWindow(int bus) {
        JFrame frame = new JFrame("Color chooser for BUS");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createColorChooseUI(frame, bus);
        frame.setSize(650, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createColorChooseUI(final JFrame frame, int bus){

        try {

            String backgroundPath = "/zero-two.jpg";
            InputStream in;
            if (Settings.getBackgroundPath() !=null){
                backgroundPath = Settings.getBackgroundPath();
                in = new FileInputStream(new File(backgroundPath));
            } else {
                in = getClass().getResourceAsStream(backgroundPath);
            }
            if (in!=null){
                img = ImageIO.read(in).getScaledInstance(650, 450, Image.SCALE_DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        JPanel panel = new JPanel(){
            boolean imgDrawn = false;
            @Override
            public void paintComponent(Graphics g) {
                if (img!=null && !imgDrawn){
                    g.drawImage(img, 0, 0, this);
                }

            }
        };
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);
        String text;
        if (bus == 0) text = "ЦВЕТ СТРЕЛОК";
        else text = "ЦВЕТ АКТИВНЫХ СТРЕЛОК";

        final JLabel colorLabel = new JLabel(text);
        colorLabel.setFont(new Font("Courier New", Font.BOLD, 24));
        final JColorChooser colorChooser = new JColorChooser();
        JButton submit = new JButton("Применить");
        submit.addActionListener(a ->{
            if (bus==0) Settings.setBusColor(colorChooser.getColor());
            else Settings.setActiveBusColor(colorChooser.getColor());

            JOptionPane.showMessageDialog(this, "Перезапустите приложение, чтобы изменения вступили в силу");
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });

        panel.add(colorLabel);
        panel.add(colorChooser);

        panel.add(submit);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void setSleepTimeSettings(int x, int y){
        JLabel label = new JLabel("Время в мс между тактами(по умолчанию ~6 мс): ");
        JTextField value = new JTextField();
        JButton submit = new JButton("Применить");
        submit.addActionListener( (a) ->{
            String text = value.getText();
            if (!text.matches("[0-9]+")){
                showErrorMsg("Введите корректное (числовое) значение");
                return;
            }

            int sleepTime = Integer.parseInt(text);
            if (sleepTime < 0){
                showErrorMsg("Укажите неотрицательное число");
                return;
            }
            Settings.setTickFinishSleepTime(sleepTime);

            this.gui.getCPU().setTickFinishListener(() -> {
                this.gui.stepFinishViewElements();  // строго до вызова метода sleep
                try {
                    Thread.sleep(6);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        });

        label.setBounds(x , y , 300, 20);
        value.setBounds(x + label.getWidth(), y, 50, 20);
        submit.setBounds(x + label.getWidth() + value.getWidth() + 10, y, 100, 20);

        this.add(label);
        this.add(value);
        this.add(submit);
    }

    @Override
    public void panelActivate() {

    }

    @Override
    public void panelDeactivate() {

    }

    @Override
    public String getPanelName() {
        return "Настройки";
    }

    private void showErrorMsg(String msg){
        JOptionPane.showMessageDialog(this.gui,
                msg,
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }
}
