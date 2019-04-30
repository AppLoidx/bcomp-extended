package nightmaretest;

import fx.gui.EncodeApplication;
import javafx.scene.input.KeyCode;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;
import ru.ifmo.cs.elements.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


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
    private JButton encodeButton = new JButton("HEX конвертер");
    private TextField addr = new TextField();           // поле адреса
    private TextField output = new TextField();         // поле значения ячейки
    private final int SEARCH_BOX_X = 50;
    private final int SEARCH_BOX_Y = 20;
    private int memoryPaneCapacity = 21;
    private final int MEMORY_PANE_X = 300;
    private final int MEMORY_PANE_Y = 20;
    private final int MEMORY_PANE_DEFAULT_WIDTH = 130;
    private final int MEMORY_PANE_Y_DEFAULT_HEIGHT = 500;
    private final Color memoryAddrValueColor;

    private final Font monoFont = new Font("Courier New", Font.BOLD, 14);
    private JTextField memoryCapacityArea = new JTextField();

    MemoryView(GUI gui){
//        CPU cpu = gui.getCPU();
        memory = gui.getCPU().getMemory();
        memoryAddrValueColor = new JTextPane().getBackground();

        this.gui = gui;
        this.memoryInnerPane.setLayout(new BoxLayout(this.memoryInnerPane, BoxLayout.Y_AXIS));

        JLabel panel = new JLabel();


        JLabel labelAddr = new JLabel();
        labelAddr.setText("Адрес: ");

        JLabel labelVal = new JLabel();
        labelVal.setText("Значение: ");


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
        JLabel capacityAreaLabel = new JLabel();
        capacityAreaLabel.setText("Кол-во:");
        capacityAreaLabel.setBounds(memoryPane.getX(), memoryPane.getHeight() + 20,
                memoryPane.getWidth() - memoryCapacityArea.getWidth(), 18);
        this.add(memoryPane);
        this.add(memoryCapacityArea);
        this.add(capacityAreaLabel);

        encodeButton.setBounds(MEMORY_PANE_X+ memoryPane.getWidth() + 50, MEMORY_PANE_Y, 200, 30);
        this.add(encodeButton);
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

                            }

                            @Override
                            public void keyPressed(KeyEvent e) {

                                if (e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_UP)
                                textP.setBackground(memoryAddrValueColor);

                                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
                                    boolean focusAddrFound = false;
                                    boolean focusAddrIsNotSetted = true;

                                    JTextPane lastAddr = null;

                                    for (JTextPane text : memoryMap.values()) {
                                        if (focusAddrFound) {
                                            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                                                text.requestFocus();
                                                focusAddrIsNotSetted = false;
                                                break;
                                            } else {
                                                if (lastAddr != null) {
                                                    lastAddr.requestFocus();
                                                    focusAddrIsNotSetted = false;
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
                                    if (focusAddrIsNotSetted) {
                                        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
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
                        memoryMap.get(label).setBackground(Color.RED);
                    }

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

        encodeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(() -> {
                    EncodeApplication app = new EncodeApplication();
                    app.run();
                }).start();

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

}
