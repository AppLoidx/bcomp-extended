package application;

import application.entities.SettingsData;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
/**
 * @author Arthur Kupriyanov
 */
public final class Settings {
    private static transient volatile int tickFinishSleepTime = 6;
    private static String backgroundPath;
    private static Color busColor = DisplayStyles.COLOR_BUS;
    private static Color activeBusColor = DisplayStyles.COLOR_ACTIVE;

    private static boolean saveDebugMarks = false;

    public static int getTickFinishSleepTime() {
        return tickFinishSleepTime;
    }

    public static void setTickFinishSleepTime(int tickFinishSleepTime) {
        Settings.tickFinishSleepTime = tickFinishSleepTime;
    }

    public static String getBackgroundPath() {
        return backgroundPath;
    }

    public static void setBackgroundPath(String backgroundPath) {
        Settings.backgroundPath = backgroundPath;
        try {
            if (backgroundPath==null){
                BCompPanel.img = null;
                return;
            }
            BCompPanel.img = ImageIO.read(
                    new FileInputStream(new File(backgroundPath)))
                    .getScaledInstance(
                            DisplayStyles.SCALED_BACKGROUND_IMG_WIDTH,
                            DisplayStyles.SCALED_BACKGROUND_IMG_HEIGHT,
                            Image.SCALE_DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setBusColor(@NotNull Color busColor) {
        Settings.busColor = busColor;
        DisplayStyles.COLOR_BUS = busColor;
    }
    public static void setActiveBusColor(@NotNull Color color){
        Settings.activeBusColor = color;
        DisplayStyles.COLOR_ACTIVE = color;
    }
    public static void save(){
        SettingsData data = new SettingsData();
        data.backgroundPath = Settings.backgroundPath;
        data.busActiveColor = Settings.activeBusColor;
        data.busColor = Settings.busColor;
        if (saveDebugMarks) saveDebugMarks(data);
        data.save();
    }

    private static void saveDebugMarks(SettingsData data){
        data.markedAddrs = Debugger.markedAddrs;
    }
    private static void init(SettingsData data){
        Settings.setBackgroundPath(data.backgroundPath);
        Settings.setActiveBusColor(data.busActiveColor==null?Color.RED:data.busActiveColor);
        Settings.setBusColor(data.busColor==null?Color.GRAY:data.busColor);
        if (data.markedAddrs!=null) Debugger.markedAddrs = data.markedAddrs;
    }
    public static void init(){
        SettingsData data = SettingsData.load();
        init(data);
    }
    public static void setDefault(){
        init(SettingsData.getDefault());
    }

    public static void main(String[] args) {
        save();
        init();
    }
}
