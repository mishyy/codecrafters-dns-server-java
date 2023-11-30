package dns.domain;

public enum OpCode {

    QUERY((byte) 0), INVERSE_QUERY((byte) 1), STATUS((byte) 2), RESERVED((byte) 3);

    private static final OpCode[] ALL = values();
    private final byte value;

    OpCode(final byte value) {
        this.value = value;
    }

    public static OpCode parse(final byte value) {
        for (final var opCode : ALL) {
            if (opCode.value == value) {
                return opCode;
            }
        }
        if (value < 0 || value > 15) {
            System.err.println("Unknown OpCode: " + value);
        }
        return RESERVED;
    }

    public byte value() {
        return value;
    }

}
