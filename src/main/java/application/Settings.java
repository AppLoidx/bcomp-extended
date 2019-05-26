package application;

import application.entities.SettingsData;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * @author Arthur Kupriyanov
 */
public final class Settings {
    private static transient volatile int tickFinishSleepTime = 6;
    private static String backgroundPath;
    private static String assemblerText;
    private static Color busColor = DisplayStyles.COLOR_BUS;
    private static Color activeBusColor = DisplayStyles.COLOR_ACTIVE;
    private static Image backgroundImage;
    private static boolean saveDebugMarks = false;

    private static Color mainTextColor = DisplayStyles.MAIN_TEXT_COLOR;
    private static Color inputTitleColor = DisplayStyles.COLOR_INPUT_TITLE;
    private static Color inputBodyColor = DisplayStyles.COLOR_INPUT_BODY;
    private static Color borderColor = DisplayStyles.COLOR_BORDER;
    private static Color backgroundColor = DisplayStyles.COLOR_BACKGROUND_STYLE;

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
        downloadImage();    // перезагрузка фонового изображения
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
        data.backgroundColor = Settings.backgroundColor;
        data.borderColor = Settings.getBorderColor();
        data.mainTextColor = Settings.getMainTextColor();
        data.inputTitleColor = Settings.inputTitleColor;
        data.inputBodyColor = Settings.getInputBodyColor();
        data.assemblerText = Settings.getAssemblerText();
        if (saveDebugMarks) saveDebugMarks(data);
        data.save();
    }

    private static void saveDebugMarks(SettingsData data){
        data.markedAddrs = Debugger.markedAddrs;
    }
    private static void init(SettingsData data){
        Settings.setBackgroundPath(data.backgroundPath);
        Settings.setActiveBusColor(data.busActiveColor==null?DisplayStyles.COLOR_ACTIVE:data.busActiveColor);
        Settings.setBusColor(data.busColor==null?DisplayStyles.COLOR_BUS:data.busColor);
        Settings.setInputTitleColor(data.inputTitleColor);
        Settings.setInputBodyColor(data.inputBodyColor);
        Settings.setMainTextColor(data.mainTextColor);
        Settings.setBorderColor(data.borderColor);
        Settings.setBackgroundColor(data.backgroundColor);
        Settings.setAssemblerText(data.assemblerText);
        if (data.markedAddrs!=null) Debugger.markedAddrs = data.markedAddrs;
    }
    public static void init(){
        SettingsData data = SettingsData.load();
        init(data);
    }
    public static void setDefault(){
        SettingsData data = SettingsData.getDefault();
        init(data);
        backgroundImage = data.backgroundImage;
    }
    private static void downloadImage(){
        try {

            String backgroundPath;
            InputStream in = null;
            if (Settings.getBackgroundPath() !=null){
                backgroundPath = Settings.getBackgroundPath();
                in = new FileInputStream(new File(backgroundPath));
            }
            if (in!=null) backgroundImage = ImageIO.read(in).getScaledInstance(DisplayStyles.SCALED_BACKGROUND_IMG_WIDTH, DisplayStyles.SCALED_BACKGROUND_IMG_HEIGHT, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getBackgroundImage() {
        return backgroundImage;
    }

    public static Color getMainTextColor() {
        return mainTextColor;
    }

    public static void setMainTextColor(Color mainTextColor) {
        Settings.mainTextColor = mainTextColor;
        DisplayStyles.MAIN_TEXT_COLOR = mainTextColor;
    }

    public static Color getInputTitleColor() {
        return inputTitleColor;
    }

    public static void setInputTitleColor(Color inputTitleColor) {
        Settings.inputTitleColor = inputTitleColor;
        DisplayStyles.COLOR_INPUT_TITLE = inputTitleColor;
    }

    public static Color getInputBodyColor() {
        return inputBodyColor;
    }

    public static void setInputBodyColor(Color inputBodyColor) {
        Settings.inputBodyColor = inputBodyColor;
        DisplayStyles.COLOR_INPUT_BODY = inputBodyColor;
    }

    public static Color getBorderColor() {
        return borderColor;
    }

    public static void setBorderColor(Color borderColor) {
        Settings.borderColor = borderColor;
        DisplayStyles.COLOR_BORDER = borderColor;
    }

    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    public static void setBackgroundColor(Color backgroundColor) {
        Settings.backgroundColor = backgroundColor;
    }

    public static String getAssemblerText() {
        return assemblerText;
    }

    public static void setAssemblerText(String assemblerText) {
        Settings.assemblerText = assemblerText;
    }
}
