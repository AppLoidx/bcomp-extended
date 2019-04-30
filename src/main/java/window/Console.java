package window;

import core.cli.CustomCLI;
import core.cli.MnemonicInterpreter;
import core.cli.TraceGenerator;
import core.cli.interpretator.CLIInterpreter;
import ru.ifmo.cs.bcomp.MicroPrograms;
import util.TextDecorations;
import util.UserIOStream;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Arthur Kupriyanov
 */
public class Console {
    private final String pathToHelloMessageFile = "/hello-message.txt";

    public static void main(String[] args) {
        new Console().start();
    }

    public void start(){

        try {
            System.out.println(getHelloMessage());
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("bcomp extended version");
        }

        UserIOStream inputStream = new UserIOStream();
        UserIOStream outputStream = new UserIOStream();

        new Thread( () -> {
            try {
                CustomCLI cli = new CustomCLI(MicroPrograms.getMicroProgram("base"), outputStream);
                cli.cli(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        String request;
        while ((request=getUserInput())!=null){
            if (outputStream.available()){
                System.out.println(outputStream.readString());
            }

            // регистр срезки после команды HLT
            boolean cut = checkCut(request);

            // регистр для двойного ката (срезка после второго HLT)
            boolean doubleCut = doubleCutCheck(request);

            // регистр генерации xls-файла
            boolean generate = checkGenerate(request);

            // регистр печати мнемоники
            boolean mnemonic = checkMnemonic(request);

            request = cleanMsg(request);
            System.out.println(request);
            inputStream.writeln(CLIInterpreter.interpret(request).first);
            while(true){
                if (outputStream.available()){
                    String res = outputStream.readString();
                    StringBuilder response = new StringBuilder();
                    if (cut){
                        cut(response, res, doubleCut);
                    } else response.append(res);


                    if (generate){
                        if (mnemonic) generateDoc(insertMnemonic(response.toString()).toString(), "unnamed");
                        else generateDoc(response.toString(), "unnamed");
                    }
                    System.out.println(response.toString());
                    break;
                }

                try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }

    }

    private StringBuilder insertMnemonic(String res){
        StringBuilder response = new StringBuilder();
        for (String line : res.split("\n")){
            String[] regs = line.split(" ");

            if (regs.length > 1){
                if (regs[0].matches("[0-9a-fA-F]{3}")){
                    int emptyFieldsCount = 10 - regs.length;
                    StringBuilder lineBuilder = new StringBuilder(line);
                    for (int i = 0; i< emptyFieldsCount; i++){
                        lineBuilder.append(" -");
                    }
                    line = lineBuilder.toString();
                    response.append("\n").append(line).append(" ").append(MnemonicInterpreter.interpret(regs[1]));
                } else {
                    response.append("\n").append(line);
                }
            }
        }
        return response;
    }

    private String getHelloMessage() throws IOException {
        InputStream in = getClass().getResourceAsStream(pathToHelloMessageFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line=br.readLine())!=null){
            sb.append(line).append("\n");
        }

        return sb.toString();
    }

    private void generateDoc(String response, String name){
//        File file = new TraceGenerator().generate(response, name);
    }

    private String getUserInput(){
        int lines = 0;
        Scanner sc = new Scanner(System.in);
        String line;
        StringBuilder sb = new StringBuilder();
        do {
            line = sc.nextLine();
            sb.append(line.trim()).append(" ");
            lines++;
        } while(!line.matches(".*;") && !(line.matches("/.*") && lines==1));

        if (sb.toString().matches("e|exit]")){
            return null;
        }

        return cleanRequest(sb.toString());
    }

    private String cleanRequest(String request){
        request = request.trim();
        if (request.matches("/.*")){
            return request.substring(1);
        } else if (request.matches(".+;")){
            return request.substring(0, request.length() - 1);
        } else {
            return request;
        }
    }

    private boolean checkCut(String msg){
        return msg.matches(".*&cut.*");
    }
    private boolean doubleCutCheck(String msg) { return msg.matches(".*&cut2.*");}
    private boolean checkGenerate(String msg) { return msg.matches(".*&generate.*");}
    private boolean checkMnemonic(String msg){
        return msg.matches(".*&mnemonic.*");
    }

    private String cleanMsg(String msg){
        Pattern p = Pattern.compile("\\[.*]");
        Matcher m = p.matcher(msg);

        while (m.find()){
            msg = msg.replace(m.group(), "");
        }

        m = Pattern.compile("&cut[2]*").matcher(msg);
        while (m.find()){
            msg = msg.replace(m.group(),"");
        }
        m = Pattern.compile("&generate").matcher(msg);
        while (m.find()){
            msg = msg.replace(m.group(),"");
        }
        m = Pattern.compile("&mnemonic").matcher(msg);
        while (m.find()){
            msg = msg.replace(m.group(),"");
        }
        return msg;
    }

    private void cut(StringBuilder response, String res, boolean doubleCut){
        String lastLine = res.split("\n")[0];
        for (String line : res.split("\n")){
            String[] regs = line.split(" ");

            if (regs.length > 1){
                String[] lastLineS = lastLine.split(" ");


                if (lastLineS[0].equals(regs[0]) && lastLineS[1].equals("F200") && regs[1].equals("F200")){
                    response.append("\n").append(TextDecorations.longHR).append("\n");
                }

                if (regs[1].equals("F000") && !doubleCut){
                    response.append("\n").append(line);

                    break;
                } else if (regs[1].equals("F000")){
                    doubleCut = false;
                    response.append("\n").append(line);
                    lastLine = line;
                }
                else {

                    response.append("\n").append(line);
                    lastLine = line;
                }
            }
        }
    }
}
