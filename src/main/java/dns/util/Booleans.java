package dns.util;

public final class Booleans {

    public static int toInt(final boolean value) {
        return value ? 1 : 0;
    }

    public static boolean fromInt(final int value) {
        return value != 0;
    }

}
