package dns.domain.records;

import dns.domain.Writer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Represents a DNS record.
 *
 * @param name     Resource name; variable bytes
 * @param type     Record type; two bytes
 * @param clazz    Class of resource data; two bytes
 * @param ttl      Time to live; four bytes
 * @param rdLength Resource data length; two bytes
 * @param rData    Resource data; variable bytes
 */
public record ResourceRecord(
        String name, short type, short clazz,
        int ttl, short rdLength, byte[] rData
) implements Writer {

    @Override
    public void write(final ByteBuffer buffer) {
        buffer.put(name.getBytes(StandardCharsets.UTF_8));
        buffer.putShort(type);
        buffer.putShort(clazz);
        buffer.putInt(ttl);
        buffer.putShort(rdLength);
        buffer.put(rData);
    }

}
