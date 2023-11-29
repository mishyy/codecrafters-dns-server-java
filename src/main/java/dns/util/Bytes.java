package dns.util;

import java.util.BitSet;

public final class Bytes {

    public static byte valueOrZero(final BitSet bitSet) {
        final var bytes = bitSet.toByteArray();
        return bytes.length != 0 ? bytes[0] : 0;
    }

    public static byte[] reverse(final BitSet bitSet) {
        final var bytes = bitSet.toByteArray();
        for (int i = 0, j = bytes.length - 1; i < j; i++, j--) {
            final var temp = bytes[i];
            bytes[i] = bytes[j];
            bytes[j] = temp;
        }
        return bytes;
    }

}
