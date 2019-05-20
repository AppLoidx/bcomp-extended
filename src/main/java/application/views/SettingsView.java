package application.views;

import application.DisplayStyles;
import application.GUI;
import application.Settings;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * @author Arthur Kupriyanov
 */
public class SettingsView extends ActivateblePanel {
    private final GUI gui;
    private final int MARGIN_X = 20;
    private final int MARGIN_Y = 30;
    private final int BUTTON1_WIDTH = 250;
    private final int BUTTON1_HEIGHT = 30;
    Image img ;

    public SettingsView(GUI aGui){
        this.gui = aGui;
        setSleepTimeSettings(5, 0);
        JButton activeBusColorChooserBtn = new JButton("Выбрать цвет для активной стрелки");
        JButton busColorChooserBtn = new JButton("Выбрать цвет стрелок");
        busColorChooserBtn.addActionListener(a-> createColorChooseWindow(Setting.BUS_COLOR));
        activeBusColorChooserBtn.addActionListener(a-> createColorChooseWindow(Setting.ACTIVE_BUS_COLOR));

        JButton backgroundSelectBtn = new JButton("Выбрать фоновое изображение");
        backgroundSelectBtn.addActionListener( a -> createFileChooseWindow());

        JButton mainTextColorSelectBtn = new JButton("Выбрать цвет основного текста");
        mainTextColorSelectBtn.addActionListener( a-> createColorChooseWindow(Setting.MAIN_TEXT_COLOR));
        JButton additionalColorSelectBtn = new JButton("Выбрать вспомогательный цвет");
        additionalColorSelectBtn.addActionListener( a-> createColorChooseWindow(Setting.INPUT_TITLE_COLOR));
        JButton mainColorSelectBtn = new JButton("Выбрать основной цвет");
        mainColorSelectBtn.addActionListener( a-> createColorChooseWindow(Setting.INPUT_BODY_COLOR));
        JButton backgroundColorSelectBtn = new JButton("Выбрать фоновый цвет");
        backgroundColorSelectBtn.addActionListener( a -> createColorChooseWindow(Setting.BACKGROUND_COLOR));
        JButton setDefaultBtn = new JButton("Установить настройки по умолчанию");
        setDefaultBtn.addActionListener(a -> Settings.setDefault());

        activeBusColorChooserBtn.setBounds(MARGIN_X, MARGIN_Y, BUTTON1_WIDTH, BUTTON1_HEIGHT);
        busColorChooserBtn.setBounds(MARGIN_X + BUTTON1_WIDTH, MARGIN_Y, BUTTON1_WIDTH, BUTTON1_HEIGHT);
        backgroundSelectBtn.setBounds(MARGIN_X, MARGIN_Y + BUTTON1_HEIGHT, BUTTON1_WIDTH * 2, BUTTON1_HEIGHT);
        setDefaultBtn.setBounds(MARGIN_X, MARGIN_Y + 70, BUTTON1_WIDTH * 2 , BUTTON1_HEIGHT);
        mainTextColorSelectBtn.setBounds(MARGIN_X, MARGIN_Y + 70 + BUTTON1_HEIGHT, BUTTON1_WIDTH , BUTTON1_HEIGHT);
        backgroundColorSelectBtn.setBounds(MARGIN_X + BUTTON1_WIDTH, MARGIN_Y + 70 + BUTTON1_HEIGHT, BUTTON1_WIDTH, BUTTON1_HEIGHT);
        additionalColorSelectBtn.setBounds(MARGIN_X, MARGIN_Y + 70 + BUTTON1_HEIGHT * 2, BUTTON1_WIDTH, BUTTON1_HEIGHT);
        mainColorSelectBtn.setBounds(MARGIN_X + BUTTON1_WIDTH, MARGIN_Y + 70 + BUTTON1_HEIGHT * 2, BUTTON1_WIDTH, BUTTON1_HEIGHT);

        JCheckBox checkBox = new JCheckBox("ВУ всегда активно"){
            @Override
            protected void paintComponent(Graphics g)
            {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        checkBox.setBounds(MARGIN_X, MARGIN_Y + 70 + BUTTON1_HEIGHT * 3, 200, 20);
        checkBox.addItemListener(e -> gui.IOAlwaysReady = e.getStateChange() == ItemEvent.SELECTED);
        checkBox.setOpaque(false);
        checkBox.setBackground(new Color(255, 255, 255, 0));
        checkBox.setFocusPainted(false);
        checkBox.setForeground(DisplayStyles.MAIN_TEXT_COLOR);

        this.add(checkBox);
        this.add(activeBusColorChooserBtn);
        this.add(busColorChooserBtn);
        this.add(backgroundSelectBtn);
        this.add(setDefaultBtn);

        this.add(mainColorSelectBtn);
        this.add(mainTextColorSelectBtn);
        this.add(backgroundColorSelectBtn);
        this.add(additionalColorSelectBtn);

        // TODO: Add border color choose
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

    private void createColorChooseWindow(Setting setting) {
        JFrame frame = new JFrame("Color chooser for BUS");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createColorChooseUI(frame, setting);
        frame.setSize(650, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createColorChooseUI(final JFrame frame, Setting setting){

        try {

            String backgroundPath = "/zero-two.jpg";
            InputStream in;
            if (Settings.getBackgroundPath() != null) {
                backgroundPath = Settings.getBackgroundPath();
                in = new FileInputStream(new File(backgroundPath));
            } else {
                in = getClass().getResourceAsStream(backgroundPath);
            }
            if (in != null) {
                img = ImageIO.read(in).getScaledInstance(650, 450, Image.SCALE_DEFAULT);
            }
        }catch (FileNotFoundException e){
            System.err.println("Файл не найден по пути " + Settings.getBackgroundPath());
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
        String text = null;
        switch (setting) {
            case BUS_COLOR:
                text = "ЦВЕТ СТРЕЛОК";
                break;
            case ACTIVE_BUS_COLOR:
                text = "ЦВЕТ АКТИВНЫХ СТРЕЛОК";
                break;
            case MAIN_TEXT_COLOR:
                text = "ЦВЕТ ОСНОВНОГО ТЕКСТА";
                break;
            case INPUT_TITLE_COLOR:
                text = "ВСПОМОГАТЕЛЬНЫЙ ЦВЕТ";
                break;
            case INPUT_BODY_COLOR:
                text = "ОСНОВНОЙ ЦВЕТ";
                break;
            case BACKGROUND_COLOR:
                text = "ФОНОВЫЙ ЦВЕТ";
        }

        final JLabel colorLabel = new JLabel(text);
        colorLabel.setFont(new Font("Courier New", Font.BOLD, 24));
        final JColorChooser colorChooser = new JColorChooser();
        JButton submit = new JButton("Применить");
        submit.addActionListener(a ->{
            switch (setting) {
                case BUS_COLOR:
                    Settings.setBusColor(colorChooser.getColor());
                    break;
                case ACTIVE_BUS_COLOR:
                    Settings.setActiveBusColor(colorChooser.getColor());
                    break;
                case MAIN_TEXT_COLOR:
                    Settings.setMainTextColor(colorChooser.getColor());
                    break;
                case INPUT_TITLE_COLOR:
                    Settings.setInputTitleColor(colorChooser.getColor());
                    break;
                case INPUT_BODY_COLOR:
                    Settings.setInputBodyColor(colorChooser.getColor());
                    break;
                case BACKGROUND_COLOR:
                    Settings.setBackgroundColor(colorChooser.getColor());
            }

            gui.reload();

            // JOptionPane.showMessageDialog(this, "Перезапустите приложение, чтобы изменения вступили в силу");
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });

        panel.add(colorLabel);
        panel.add(colorChooser);

        panel.add(submit);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void setSleepTimeSettings(int x, int y){
        JLabel label = new JLabel("Время в мс между тактами(по умолчанию ~6 мс): ");
        label.setForeground(DisplayStyles.MAIN_TEXT_COLOR);
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

            this.gui.setSettingsTickTime();
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

    @Override
    protected void paintComponent(Graphics g) {
        DisplayStyles.setGraphics(g, this);
    }

    private enum Setting{
        BUS_COLOR,
        ACTIVE_BUS_COLOR,
        MAIN_TEXT_COLOR,
        INPUT_BODY_COLOR,
        INPUT_TITLE_COLOR,
        BACKGROUND_COLOR
    }

}
