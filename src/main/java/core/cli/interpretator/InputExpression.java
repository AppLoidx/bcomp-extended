package core.cli.interpretator;


import util.DoubleTuple;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Arthur Kupriyanov
 */
public class InputExpression extends CLIExpression {
    /**
     * "144->1111 ,2222, 3333, 4444   ;" ->
     * 144 first 1111 w 2222 w 3333 w 4444 w
     * @param specCommand addr-> addr1, addr2;
     * @return addr first addr1 w addr2 w
     */
    private static String compileToValuesInserting(String specCommand){
        StringBuilder cmd = new StringBuilder();
        specCommand = specCommand.substring(0, specCommand.length() -1);
        String[] commandParts = specCommand.split("->");
        String startMemoryAddr = commandParts[0];
        String[] values = commandParts[1].split(",");

        cmd.append(startMemoryAddr).append(" a ");
        for(String addr : values){
            cmd.append(addr.trim()).append(" w ");
        }

        return cmd.toString().trim();
    }

    @Override
    DoubleTuple<String, String> interpret(String context, DoubleTuple<String, String> dt) {
        StringBuilder logBuilder = new StringBuilder();

        if (context.matches(".*&[0-9a-fA-F]{3}->.*;.*")) {
            String[] strings = context.split("&");
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (String sample: strings) {
                if (sample.trim().equals("")){
                    first = false;
                    continue;
                }
                boolean added = false;
                Pattern p = Pattern.compile("[0-9a-fA-F]{3}->.*;");
                Matcher m = p.matcher(sample);

                while (m.find()) {
                    sb.append(replaceSub(sample, compileToValuesInserting(m.group()),m.start(), m.end()));
                    added = true;
                }
                if (!added){
                    if (first) sb.append(sample);
                    else sb.append("@").append(sample);
                }
                first = false;
            }
            context = sb.toString();
        }
        return new DoubleTuple<>(context, dt.second + logBuilder.toString());
    }

    @Override
    public int hashCode() {
        return this.getClass().getSimpleName().hashCode();
    }
}
