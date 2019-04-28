package util;

/**
 * @author Arthur Kupriyanov
 */
public class DoubleTuple<A,B> {
    public final A first;
    public final B second;

    public DoubleTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "DoubleTuple{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
