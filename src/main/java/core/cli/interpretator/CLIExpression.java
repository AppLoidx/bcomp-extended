package core.cli.interpretator;


import util.DoubleTuple;

/**
 * @author Arthur Kupriyanov
 */
public abstract class CLIExpression {
    static String replaceSub(String str, String newSub, int startPos, int endPos){
        String firstPart = str.substring(0, startPos);
        String lastPart = str.substring(endPos);
        return firstPart+newSub+lastPart;
    }

    /**
     * Возвращает кортеж из двух переменных:<br>
     *     1. Измененный(интерпретированный) контекст<br>
     *     2. Комментарий (ошибки в синтаксисе)<br>
     * @param context контекст предложения, которое нужно интерпретировать
     * @see DoubleTuple
     * @return кортеж состоящий из контекста и комментария
     */
    abstract DoubleTuple<String, String> interpret(String context, DoubleTuple<String, String> dt);

    @Override
    public int hashCode() {
        return this.getClass().getSimpleName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass();
    }
}
