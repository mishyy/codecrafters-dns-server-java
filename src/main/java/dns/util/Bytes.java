package dns.util;

import java.util.BitSet;

public final class Bytes {

    public static byte valueOrZero(final BitSet bitSet) {
        final var bytes = bitSet.toByteArray();
        return bytes.length != 0 ? bytes[0] : 0;
    }

}
