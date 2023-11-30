package dns.domain;

import dns.domain.record.RecordClass;
import dns.domain.record.RecordType;
import dns.util.Labels;

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
        final var labels = Labels.parse(buffer);
        final var type = RecordType.parse(buffer);
        final var clazz = RecordClass.parse(buffer);
        return new Question(labels, type, clazz);
    }

    @Override
    public void write(final ByteBuffer buffer) {
        Labels.write(buffer, name);
        type.write(buffer);
        clazz.write(buffer);
    }

}
