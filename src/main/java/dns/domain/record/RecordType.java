package dns.domain.record;

import dns.domain.Writer;

import java.nio.ByteBuffer;
import java.util.NoSuchElementException;

public enum RecordType implements Writer {

    A((short) 1),
    NS((short) 2),
    MD((short) 3),
    MF((short) 4),
    CNAME((short) 5),
    SOA((short) 6),
    MB((short) 7),
    MG((short) 8),
    MR((short) 9),
    NULL((short) 10),
    WKS((short) 11),
    PTR((short) 12),
    H_INFO((short) 13),
    M_INFO((short) 14),
    MX((short) 15),
    TXT((short) 16),
    AXFR((short) 252),
    MAILB((short) 253),
    MAILA((short) 254),
    ANY((short) 255);


    private static final RecordType[] ALL = values();
    private final short value;

    RecordType(final short value) {
        this.value = value;
    }

    public static RecordType parse(final ByteBuffer buffer) {
        final var value = buffer.getShort();
        for (final var type : ALL) {
            if (type.value == value) {
                return type;
            }
        }
        throw new NoSuchElementException("Could not find RecordType with value " + value + ".");
    }

    @Override
    public void write(final ByteBuffer buffer) {
        buffer.putShort(value);
    }

}
