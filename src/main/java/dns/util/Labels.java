package dns.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class Labels {

    public static List<String> parse(final ByteBuffer buffer) {
        final var labels = new ArrayList<String>();
        for (var length = buffer.get(); length != 0x00; length = buffer.get()) {
            final var label = new byte[length];
            buffer.get(label);
            labels.add(new String(label));
        }
        return labels;
    }

    public static void write(final ByteBuffer buffer, final List<String> labels) {
        for (final var label : labels) {
            final var bytes = label.getBytes();
            buffer.put((byte) bytes.length);
            buffer.put(bytes);
        }
        buffer.put((byte) 0x00);
    }

}
