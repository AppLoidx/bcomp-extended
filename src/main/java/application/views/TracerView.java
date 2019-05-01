package application.views;

import application.GUI;
import ru.ifmo.cs.bcomp.CPU;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;
import ru.ifmo.cs.elements.Memory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author Arthur Kupriyanov
 */
public class TracerView extends ActivateblePanel {
    private final GUI gui;

    private JTable table;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private CPU cpu;

    private Thread listenerThread;

    private int lastIPValue = -1;

    TracerView(GUI aGui){

        this.gui = aGui;
        this.cpu = aGui.getCPU();

        JButton checkBox = new JButton();
        checkBox.setText("Вести трассировку");
        checkBox.setBackground(this.getBackground());
        checkBox.setBounds(0 , 0, 200, 25);

        checkBox.addActionListener(e -> {
            cpu.startStart();
        });

        tableModel.addColumn("Адр");
        tableModel.addColumn("Знач");
        tableModel.addColumn("СК");
        tableModel.addColumn("РА");
        tableModel.addColumn("РК");
        tableModel.addColumn("РД");
        tableModel.addColumn("А");
        tableModel.addColumn("C");
        tableModel.addColumn("Адр");
        tableModel.addColumn("Знач");

        table = new JTable(tableModel);



        JScrollPane tracePane = new JScrollPane(table);

        tracePane.setBounds(200, 0, 640, 500 );
        this.add(checkBox);
        this.add(tracePane);

        listenerThread = new Thread(()-> {
            while(true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) { }

                int ip = this.cpu.getRegValue(CPU.Reg.IP);
                if (ip != lastIPValue){
                    lastIPValue = ip;
                    ipChanged();
                }
            }
        });
        listenerThread.setDaemon(true);

        setIPListener();
    }

    private void setIPListener(){

    }
    private void stopIPListener(){
        listenerThread.interrupt();
        lastIPValue = -1;
    }

    private void ipChanged(){

        Memory mem = cpu.getMemory();

    }

    @Override
    public void panelActivate() {
        setIPListener();
    }

    @Override
    public void panelDeactivate() {
        stopIPListener();
    }

    @Override
    public String getPanelName() {
        return "Трассировка";

    }
}
