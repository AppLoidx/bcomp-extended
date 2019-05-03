package application.entities;

import application.Settings;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Kupriyanov
 */
public class SettingsData implements Serializable {
    @SerializedName("background_path")
    public String backgroundPath;
    @SerializedName("bus_active_color")
    public Color busActiveColor;
    @SerializedName("bus_color")
    public Color busColor;
    @SerializedName("debug_marks")
    public List<Integer> markedAddrs;

    public void save(){
        File file = new File("bcomp_data/settings.json");
        if ((file.isFile() && file.canExecute() && file.canWrite()) || !file.exists()){
            if (!file.getParentFile().exists()){
                if (!file.getParentFile().mkdirs()) return;
            }
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(this);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(json);
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static SettingsData load(){
        File file = new File("bcomp_data/settings.json");
        if (file.exists() && file.canRead() && file.canExecute() && file.isFile()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) sb.append(line).append(" ");
                return new GsonBuilder().create().fromJson(sb.toString(), SettingsData.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new SettingsData();
    }

    public static SettingsData getDefault(){
        SettingsData data = new SettingsData();
        data.busColor = Color.GRAY;
        data.busActiveColor = Color.RED;
        data.backgroundPath = null;

        return data;
    }
}
