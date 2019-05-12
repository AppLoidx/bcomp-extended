package application;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Kupriyanov
 */
public class Debugger {
    public static List<Integer> markedAddrs = new ArrayList<>();

    public static boolean checkIsMarked(int addr){
        return markedAddrs.contains(addr);
    }
    public static void add(int addr){
        if (!markedAddrs.contains(addr)){
            markedAddrs.add(addr);
        }
    }
    public static void delete(int addr){
        markedAddrs.remove(new Integer(addr));
    }

}
