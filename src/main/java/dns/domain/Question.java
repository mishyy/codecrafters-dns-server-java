package dns.domain;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Represents a question in a DNS packet.
 *
 * @param name  The name of the question.
 * @param type  The type of the question.
 * @param clazz The class of the question.
 */
public record Question(String name, short type, short clazz) implements Writer {

    @Override
    public void write(final ByteBuffer buffer) {
        buffer.put(name.getBytes(StandardCharsets.UTF_8));
        buffer.putShort(type);
        buffer.putShort(clazz);
    }

}
