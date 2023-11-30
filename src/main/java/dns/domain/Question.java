package dns.domain;

import dns.domain.record.RecordClass;
import dns.domain.record.RecordType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a question in a DNS packet.
 *
 * @param name  The name of the question.
 * @param type  The type of the question.
 * @param clazz The class of the question.
 */
public record Question(List<String> name, RecordType type, RecordClass clazz) implements Writer {

    public static Question parse(final ByteBuffer buffer) {
        final var labels = new ArrayList<String>();
        for (var length = buffer.get(); length != 0x00; length = buffer.get()) {
            final var label = new byte[length];
            buffer.get(label);
            labels.add(new String(label));
        }
        final var type = RecordType.parse(buffer);
        final var clazz = RecordClass.parse(buffer);
        return new Question(labels, type, clazz);
    }

    @Override
    public void write(final ByteBuffer buffer) {
        for (final var label : name) {
            final var bytes = label.getBytes();
            buffer.put((byte) bytes.length);
            buffer.put(bytes);
        }
        buffer.put((byte) 0x00);
        type.write(buffer);
        clazz.write(buffer);
    }

}
