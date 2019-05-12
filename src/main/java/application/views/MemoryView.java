package application.views;

import application.Debugger;
import application.DisplayStyles;
import application.GUI;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;
import ru.ifmo.cs.elements.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * @author Arthur Kupriyanov
 */
public class MemoryView extends ActivateblePanel {

    private final GUI gui;
    private Memory memory;
    private JLabel[] labels;
    private JTextPane[] texts;
    private LinkedHashMap<JLabel, JTextPane> memoryMap = new LinkedHashMap<>();
    private JPanel memoryInnerPane = new JPanel();
    private JScrollPane memoryPane = new JScrollPane(memoryInnerPane);
    private JButton searchButton = new JButton("Найти");
    private JButton acceptButton = new JButton("Применить");
    private char[] supportedChars = new char[]{'0','1','2','3','4','5','6','7','8','9','a', 'A', 'b', 'B', 'c', 'C', 'd', 'D','e','E','f','F'};

//    private JButton encodeButton = new JButton("HEX конвертер");

    private TextField addr = new TextField();           // поле адреса
    private TextField output = new TextField();         // поле значения ячейки
    private final int SEARCH_BOX_X = 10;
    private final int SEARCH_BOX_Y = 20;
    private int memoryPaneCapacity = 21;
    private final int MEMORY_PANE_X = 220;
    private final int MEMORY_PANE_Y = 20;
    private final int MEMORY_PANE_DEFAULT_WIDTH = 130;
    private final int MEMORY_PANE_Y_DEFAULT_HEIGHT = 500;
    private final Color memoryAddrValueColor;

    private final Font monoFont = new Font("Courier New", Font.BOLD, 14);
    private final Font markFont = new Font("Courier New", Font.PLAIN, 12);
    private JTextField memoryCapacityArea = new JTextField();
    private JPanel markedAddrPane = new JPanel();
    private JScrollPane markedAddrScroll = new JScrollPane(markedAddrPane);

    public MemoryView(GUI gui){

//        CPU cpu = gui.getCPU();
        memory = gui.getCPU().getMemory();
        memoryAddrValueColor = new JTextPane().getBackground();
        markedAddrScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        markedAddrScroll.setBackground(DisplayStyles.COLOR_INPUT_BODY);

        this.gui = gui;
        this.memoryInnerPane.setLayout(new BoxLayout(this.memoryInnerPane, BoxLayout.Y_AXIS));
        this.memoryInnerPane.setBackground(DisplayStyles.COLOR_INPUT_BODY);
        this.markedAddrPane.setLayout(new BoxLayout(this.markedAddrPane, BoxLayout.Y_AXIS));
        this.markedAddrPane.setBackground(DisplayStyles.COLOR_INPUT_BODY);

        JLabel panel = new JLabel();


        JLabel labelAddr = new JLabel();
        labelAddr.setText("Адрес: ");
        labelAddr.setForeground(DisplayStyles.MAIN_TEXT_COLOR);

        JLabel labelVal = new JLabel();
        labelVal.setText("Значение: ");
        labelVal.setForeground(DisplayStyles.MAIN_TEXT_COLOR);

        searchButton.setBounds(SEARCH_BOX_X  + 100, SEARCH_BOX_Y + 60, 100, 25);
        acceptButton.setBounds(SEARCH_BOX_X  + 100, SEARCH_BOX_Y + 90, 100, 25);
        addr.setBounds(SEARCH_BOX_X + 100, SEARCH_BOX_Y, 100, 25);
        labelAddr.setBounds(SEARCH_BOX_X, SEARCH_BOX_Y, 100, 25);
        output.setBounds(SEARCH_BOX_X + 100, SEARCH_BOX_Y + 25, 100, 25);
        labelVal.setBounds(SEARCH_BOX_X, SEARCH_BOX_Y + 25, 100, 25);
        output.setEditable(false);

        panel.add(addr);
        panel.add(labelAddr);
        panel.add(output);
        panel.add(labelVal);
        panel.add(searchButton);
        panel.add(acceptButton);



        setButtonsListeners();

        panel.setBounds(0, 0, 544, 600);
        this.add(panel);



        memoryPane.setBounds(MEMORY_PANE_X, MEMORY_PANE_Y, MEMORY_PANE_DEFAULT_WIDTH, MEMORY_PANE_Y_DEFAULT_HEIGHT);
        memoryCapacityArea.setBounds(memoryPane.getX() + 50, memoryPane.getHeight()+ 20, 80, 18);
        memoryCapacityArea.setBackground(DisplayStyles.COLOR_INPUT_BODY);
        memoryCapacityArea.setForeground(DisplayStyles.MAIN_TEXT_COLOR);
        memoryCapacityAreaListenersSet();
        JLabel capacityAreaLabel = new JLabel();
        capacityAreaLabel.setText("Кол-во:");
        capacityAreaLabel.setForeground(DisplayStyles.MAIN_TEXT_COLOR);
        capacityAreaLabel.setBounds(memoryPane.getX(), memoryPane.getHeight() + 20,
                memoryPane.getWidth() - memoryCapacityArea.getWidth(), 18);
        this.add(memoryPane);
        this.add(memoryCapacityArea);
        this.add(capacityAreaLabel);

        drawDebugger();
        showMarkedAddresses();

//        encodeButton.setBounds(MEMORY_PANE_X+ memoryPane.getWidth() + 50, MEMORY_PANE_Y, 200, 30);
//        this.add(encodeButton);
    }

    @Override
    public void panelActivate() {
        updateMarkedAddresses();
    }

    @Override
    public void panelDeactivate() {

    }
    private void memoryCapacityAreaListenersSet(){
        memoryCapacityArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    onSearchButtonClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
    @Override
    public String getPanelName() {
        return "Memory";
    }

    private String fieldValue(int field){
        return Integer.toHexString(memory.getValue(field));
    }

    private String memoryTextEdit(String text){
        StringBuilder textBuilder = new StringBuilder(text);
        while(textBuilder.length()<4){
            textBuilder.insert(0, "0");
        }
        text = textBuilder.toString();
        return text.toUpperCase();
    }

    private void cleanData(){
        this.memoryInnerPane.removeAll();
        this.memoryMap.clear();
    }

    private void setButtonsListeners(){
        searchButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onSearchButtonClick();
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

        acceptButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                for (JLabel label : memoryMap.keySet()){
                    int addr = Integer.parseInt(label.getText(), 16);
                    try {
                        int value = Integer.parseInt(memoryMap.get(label).getText(), 16);
                        memory.setValue(addr, value);
                    } catch (NumberFormatException formatE){
                        memoryMap.get(label).setBackground(new Color(227, 35, 35, 50));
                    }

                }
                updateMarkedAddresses();
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

    }
    private void onSearchButtonClick(){
        String text = addr.getText();
        if (!text.matches("[0-9a-fA-F]+")) return;

        int addr = Integer.parseInt(text, 16);
        int passed = 0;     // если будет exception - необходимо сделать кат для pane
        try {
            output.setText(fieldValue(addr));
            cleanData();

            if (memoryCapacityArea.getText().matches("[0-9]+")){
                memoryPaneCapacity = Integer.parseInt(memoryCapacityArea.getText());
            }

            for (int i = addr; i < addr + memoryPaneCapacity; i++) {
                JPanel pane = new JPanel();
                pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
                JLabel l = new JLabel();
                l.setText(memoryTextEdit(Integer.toHexString(i)));

                l.setFont(monoFont);

                JTextPane textP = new JTextPane();

                textP.setText(memoryTextEdit(fieldValue(i)));

                textP.setFont(monoFont);

                textP.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        textP.setBackground(Color.ORANGE);
                        textP.selectAll();
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        textP.setBackground(memoryAddrValueColor);
                    }
                });

                textP.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (textP.getText().length() > 3){
                            if (textP.getSelectedText() == null || textP.getSelectedText().isEmpty()) {
                                e.consume();
                                return;
                            }

                            if (!(textP.getText().length() - textP.getSelectedText().length() < 5)){
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

                        if (e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_UP)
                            textP.setBackground(memoryAddrValueColor);

                        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP) {
                            e.consume();
                            boolean focusAddrFound = false;
                            boolean focusAddrIsNotSet = true;

                            JTextPane lastAddr = null;

                            for (JTextPane text : memoryMap.values()) {
                                if (focusAddrFound) {
                                    if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
                                        text.requestFocus();
                                        focusAddrIsNotSet = false;
                                        break;
                                    } else {
                                        if (lastAddr != null) {
                                            lastAddr.requestFocus();
                                            focusAddrIsNotSet = false;
                                            break;
                                        } else {
                                            if (!memoryMap.isEmpty()) {
                                                Iterator<JTextPane> i = memoryMap.values().iterator();
                                                JTextPane last = text;
                                                while (i.hasNext()) last = i.next();
                                                if (last != null) last.requestFocus();
                                            }
                                            break;
                                        }
                                    }
                                }
                                if (text.isFocusOwner()) {
                                    focusAddrFound = true;
                                    continue;   // чтобы не заполнять lastAddr
                                }

                                lastAddr = text;
                            }
                            if (focusAddrIsNotSet) {
                                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    if (!memoryMap.isEmpty()) {
                                        memoryMap.values().iterator().next().requestFocus();
                                    }
                                } else {
                                        /*
                                            На последнем элементе, если не было обработки last item
                                            т.к. цикл foreach обрывается при окончании keySet()
                                         */
                                    if (lastAddr != null) {
                                        lastAddr.requestFocus();
                                    }
                                }
                            }

                            if (textP.getText().isEmpty()) textP.setText("0000");
                        }



                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });


                pane.add(l);
                pane.add(textP);

                memoryMap.put(l, textP);

                memoryInnerPane.add(pane);
                passed++;
            }
            updateMemoryPane();

        }catch (ArrayIndexOutOfBoundsException exp){
            updateMemoryPane(passed);
        }

    }
    private void updateMemoryPane(){
        setMemoryPaneBounds(memoryPaneCapacity);
    }

    private void updateMemoryPane(int tempCapacity){
        setMemoryPaneBounds(tempCapacity);
    }

    private void setMemoryPaneBounds(int tempCapacity) {
        int newHeight = tempCapacity * 26;
        if (newHeight > MEMORY_PANE_Y_DEFAULT_HEIGHT)
            memoryPane.setBounds(MEMORY_PANE_X, MEMORY_PANE_Y, MEMORY_PANE_DEFAULT_WIDTH, MEMORY_PANE_Y_DEFAULT_HEIGHT);
        else
            memoryPane.setBounds(MEMORY_PANE_X, MEMORY_PANE_Y, MEMORY_PANE_DEFAULT_WIDTH, newHeight);

        memoryPane.revalidate();
    }

    private void drawDebugger(){
        final int MARGIN_X = MEMORY_PANE_X + MEMORY_PANE_DEFAULT_WIDTH + 20;
        final int MARGIN_Y = SEARCH_BOX_Y;
        final int BUTTON_WIDTH = 180;
        final int DEFAULT_HEIGHT = 30;
        JLabel label = new JLabel("Debugger");
        label.setForeground(DisplayStyles.MAIN_TEXT_COLOR);
        label.setFont(new Font("Courier New", Font.BOLD, 24));
        JTextField addrField = new JTextField();
        JButton acceptBtn = new JButton("Поставить метку");
        JButton declineBtn = new JButton("Убрать метку");

        label.setBounds(MARGIN_X, MARGIN_Y, 150, 50);
        addrField.setBounds(MARGIN_X + 150, MARGIN_Y + 10, 100, 30);
        acceptBtn.setBounds(MARGIN_X, MARGIN_Y + 50, BUTTON_WIDTH, DEFAULT_HEIGHT);
        declineBtn.setBounds(MARGIN_X + BUTTON_WIDTH, MARGIN_Y + 50, BUTTON_WIDTH, DEFAULT_HEIGHT);

        this.add(label);
        this.add(addrField);
        this.add(acceptBtn);
        this.add(declineBtn);

        addrField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                addrField.selectAll();
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
        acceptBtn.addActionListener( a-> {
            String addr = addrField.getText();
            if (addr==null) return;
            if (addr.matches("[0-9a-fA-F]+")){
                int addrValue = Integer.parseInt(addr, 16);
                if (addrValue > 2047){
                    showErrorMsg("Число превышает значение 7FF");
                    return;
                }
                Debugger.add(addrValue);
                updateMarkedAddresses();
            } else {
                showErrorMsg( "Введите числовое значение в 16 radix");
            }
        });

        declineBtn.addActionListener( a->{
            String addr = addrField.getText();
            if (addr==null) return;
            if (addr.matches("[0-9a-fA-F]+")){
                int addrValue = Integer.parseInt(addr, 16);
                Debugger.delete(addrValue);
                updateMarkedAddresses();
            } else {
                JOptionPane.showMessageDialog(this, "Введите числовое значение в 16 radix");
            }
        });

    }

    private void showErrorMsg(String msg){
        JOptionPane.showMessageDialog(this, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    private void showMarkedAddresses() {
        final int MARGIN_X = MEMORY_PANE_X + MEMORY_PANE_DEFAULT_WIDTH + 20;


        List<Integer> mAddresses = Debugger.markedAddrs;
        for (int a : mAddresses) {
            JTextField field = new JTextField();
            field.setText( "   " +a + "\t------\t" + this.memory.getValue(a) + "   ");

            markedAddrPane.add(field);
        }
//        markedAddrPane.setBounds(MARGIN_X, SEARCH_BOX_Y + 100, 200, 400);
        markedAddrScroll.setBounds(MARGIN_X, SEARCH_BOX_Y + 100, 100, 400);
        this.add(markedAddrScroll);
    }

    private void updateMarkedAddresses(){
        List<Integer> mAddresses = Debugger.markedAddrs;
        markedAddrPane.removeAll();
        int count = 0;
        for (int a : mAddresses) {
            JTextField field = new JTextField();
            StringBuilder hex = new StringBuilder(Integer.toHexString(a));
            while (hex.length() < 4) hex.insert(0, "0");
            field.setText(hex + ": " + Integer.toHexString(this.memory.getValue(a)));
            field.setFont(markFont);
            field.setEditable(false);
            markedAddrPane.add(field);
            count++;
        }
        markedAddrScroll.revalidate();

    }

    @Override
    protected void paintComponent(Graphics g) {
        DisplayStyles.setGraphics(g, this);
    }
}
