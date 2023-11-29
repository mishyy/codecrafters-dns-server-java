package dns.util;

import java.util.BitSet;

public final class Bytes {

    public static byte valueOrZero(final BitSet bitSet) {
        final var bytes = bitSet.toByteArray();
        return bytes.length != 0 ? bytes[0] : 0;
    }

    public static String toHexDump(final byte[] bytes) {
        final var builder = new StringBuilder();
        for (final var b : bytes) {
            builder.append(String.format("%02x ", b)).append(" ");
        }
        return builder.toString();
    }

}
