package core.cli.interpretator;


import util.DoubleTuple;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Arthur Kupriyanov
 */
class ContinueExpression extends CLIExpression {
    private static String compileToContinueCmd(String specCommand){
        StringBuilder cmd = new StringBuilder();
        String[] commandParts = specCommand.split("[*]");
        int values = Integer.valueOf(commandParts[1]);
        for (int i = 0; i < values; i++){
            cmd.append(" c");
        }
        return cmd.toString().trim();
    }

    @Override
    DoubleTuple<String, String> interpret(String context, DoubleTuple<String, String> dt) {
        if (context.matches(".*&c[*][0-9]{1,4}.*")){
            String[] strings = context.split("&");
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (String sample: strings) {
                if (sample.trim().equals("")){
                    first = false;
                    continue;
                }
                boolean added = false;
                Pattern p = Pattern.compile("c[*][0-9]{1,4}");
                Matcher m = p.matcher(sample);

                while (m.find()) {
                    added = true;
                    sb.append(replaceSub(sample, compileToContinueCmd(m.group()),m.start(), m.end()));
                }

                if (!added){
                    if (first) sb.append(sample);
                    else sb.append("&").append(sample);
                }
                first = false;
            }
            context = sb.toString();
        }
        return new DoubleTuple<>(context, dt.second);
    }
}
