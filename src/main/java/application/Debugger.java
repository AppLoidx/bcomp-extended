package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Arthur Kupriyanov
 */
public class Debugger {
    public static List<Integer> markedAddrs = new ArrayList<>();

    public static boolean checkIsMarked(int addr){
        int markedAddr = Collections.binarySearch(markedAddrs, addr);
        return markedAddr > 0;
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
