package core.cli.interpretator;

import java.util.HashSet;

/**
 * @author Arthur Kupriyanov
 */
class InterpretManager {
    private static HashSet<CLIExpression> interpreters = new HashSet<>();
    static {
        interpreters.add(new InputExpression());
        interpreters.add(new ContinueExpression());
    }
    static HashSet<CLIExpression> getInterpreters(){
        return interpreters;
    }
}
