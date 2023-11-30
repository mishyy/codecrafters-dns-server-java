package dns.domain.record;

import dns.domain.Writer;
import dns.util.Labels;

import java.nio.ByteBuffer;
import java.util.Arrays;
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
public record ResourceRecord(List<String> name, RecordType type, RecordClass clazz, int ttl, short rdLength,
                             byte[] rData) implements Writer {

    public static ResourceRecord parse(final ByteBuffer buffer) {
        final var labels = Labels.parse(buffer);
        final var type = RecordType.parse(buffer);
        final var clazz = RecordClass.parse(buffer);
        final var ttl = buffer.getInt();
        final var rdLength = buffer.getShort();
        final var rData = new byte[rdLength];
        buffer.get(rData);
        return new ResourceRecord(labels, type, clazz, ttl, rdLength, rData);
    }

    @Override
    public void write(final ByteBuffer buffer) {
        Labels.write(buffer, name);
        type.write(buffer);
        clazz.write(buffer);
        buffer.putInt(ttl);
        buffer.putShort(rdLength);
        buffer.put(rData);
    }

    @Override
    public String toString() {
        return "ResourceRecord{" + "name=" + name + ", qType=" + type + ", qClass=" + clazz + ", ttl=" + ttl + ", rdLength=" + rdLength + ", rData=" + Arrays.toString(rData) + '}';
    }
}
