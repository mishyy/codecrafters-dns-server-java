package dns.domain.record;

import dns.domain.Writer;

import java.nio.ByteBuffer;

public enum RecordClass implements Writer {

    IN((short) 1),
    CS((short) 2),
    CH((short) 3),
    HS((short) 4),
    ANY((short) 255);

    private static final RecordClass[] ALL = values();
    private final short value;

    RecordClass(final short value) {
        this.value = value;
    }

    public static RecordClass parse(final ByteBuffer buffer) {
        final var value = buffer.getShort();
        for (final var type : ALL) {
            if (type.value == value) {
                return type;
            }
        }
        return ANY;
    }

    @Override
    public void write(final ByteBuffer buffer) {
        buffer.putShort(value);
    }

}
