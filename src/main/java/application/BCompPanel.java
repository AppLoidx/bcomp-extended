package application;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import ru.ifmo.cs.bcomp.ControlSignal;
import ru.ifmo.cs.bcomp.SignalListener;
import ru.ifmo.cs.bcomp.ui.components.BusView;
import ru.ifmo.cs.bcomp.ui.components.ComponentManager;
import ru.ifmo.cs.bcomp.ui.components.RegisterProperties;
import ru.ifmo.cs.bcomp.ui.components.RegisterView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
//import ru.ifmo.cs.bcomp.ui.components.DisplayStyles;

public abstract class BCompPanel extends ru.ifmo.cs.bcomp.ui.components.BCompPanel {
    protected final ComponentManager cmanager;
    private final RegisterProperties[] regProps;
    private final BusView[] buses;
    private SignalListener[] listeners;

    public static Image img;

    {
        try {

            String backgroundPath;
            InputStream in = null;
            if (Settings.getBackgroundPath() !=null){
                backgroundPath = Settings.getBackgroundPath();
                in = new FileInputStream(new File(backgroundPath));
            }
            if (in!=null) img = ImageIO.read(in).getScaledInstance(DisplayStyles.SCALED_BACKGROUND_IMG_WIDTH, DisplayStyles.SCALED_BACKGROUND_IMG_HEIGHT, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public BCompPanel(ComponentManager cmanager, RegisterProperties[] regProps, BusView[] buses) {
        super(cmanager, regProps, buses);
        this.cmanager = cmanager;
        this.regProps = regProps;
        this.buses = buses;
    }

    protected void setSignalListeners(SignalListener[] listeners) {
        this.listeners = listeners;
    }

    protected SignalListener[] getSignalListeners() {
        return this.listeners;
    }

    public void drawBuses(Graphics g) {
        ArrayList<BusView> openbuses = new ArrayList();
        ArrayList<ControlSignal> signals = this.cmanager.getActiveSignals();
        BusView[] arr$ = this.buses;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            BusView bus = arr$[i$];
            ControlSignal[] arr = bus.getSignals();
            int len = arr.length;

            for(int i2 = 0; i2 < len; ++i2) {
                ControlSignal signal = arr[i2];
                if (signals.contains(signal)) {
                    openbuses.add(bus);
                }
            }

            bus.draw(g, DisplayStyles.COLOR_BUS);
        }

        Iterator i$ = openbuses.iterator();

        while(i$.hasNext()) {
            BusView bus = (BusView)i$.next();
            bus.draw(g, DisplayStyles.COLOR_ACTIVE);
        }

    }

    private void drawOpenBuses(Color color) {
        Graphics g = this.getGraphics();
        ArrayList<ControlSignal> signals = this.cmanager.getActiveSignals();
        BusView[] arr$ = this.buses;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            BusView bus = arr$[i$];
            ControlSignal[] arr = bus.getSignals();
            int len = arr.length;

            for(int i2 = 0; i2 < len; ++i2) {
                ControlSignal signal = arr[i2];
                if (signals.contains(signal)) {
                    bus.draw(g, color);
                }
            }
        }

    }

    public void stepStart() {
        this.drawOpenBuses(DisplayStyles.COLOR_BUS);
    }

    public void stepFinish() {
        this.drawOpenBuses(DisplayStyles.COLOR_ACTIVE);
    }

    public void panelActivate() {
        RegisterProperties[] arr$ = this.regProps;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            RegisterProperties prop = arr$[i$];
            RegisterView reg = this.cmanager.getRegisterView(prop.reg);

            reg.setProperties(prop.x, prop.y, prop.hex);
            this.add(reg);
        }

        this.cmanager.panelActivate(this);
    }

    public void panelDeactivate() {
        this.cmanager.panelDeactivate();
    }

    public void paintComponent(Graphics g) {
//        this.setBackground(DisplayStyles.COLOR_BACKGROUND_STYLE);
//        g.setColor(this.getBackground());
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (img !=null)  g.drawImage(img, 0, 0, this);

        this.drawBuses(g);

    }
}
