package dns.util;

import java.math.BigInteger;
import java.util.BitSet;

public final class Bytes {

    public static byte valueOrZero(final BitSet bitSet) {
        final var bytes = bitSet.toByteArray();
        return bytes.length != 0 ? bytes[0] : 0;
    }

    public static String toBinaryString(final byte[] bytes) {
        final var bigInt = new BigInteger(bytes);
        return bigInt.toString(2);
    }

}
