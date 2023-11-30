package dns.domain.record;

import dns.domain.Writer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
        List<String> name, RecordType type, RecordClass clazz,
        int ttl, short rdLength, byte[] rData
) implements Writer {

    @Override
    public void write(final ByteBuffer buffer) {

    }

}
