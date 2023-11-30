package dns.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class Labels {

    public static List<String> parse(final ByteBuffer buffer) {
        final var labels = new ArrayList<String>();
        byte length;
        while ((length = buffer.get()) != 0x00) {
            if ((length & 0xc0) > 0) {
                labels.addAll(parseCompressed(buffer, length));
                break;
            }

            final var label = new byte[length];
            buffer.get(label);
            labels.add(new String(label));
        }
        return labels;
    }

    private static List<String> parseCompressed(final ByteBuffer buffer, final byte length) {
        final var next = buffer.get();
        final var offset = ((length & 0x3f) << 8) | next;
        return parse(buffer.slice(0, buffer.capacity()).rewind().position(offset));
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
