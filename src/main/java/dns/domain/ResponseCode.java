package dns.domain;

public enum ResponseCode {

    UNKNOWN((byte) -1),
    NO_ERROR((byte) 0),
    FORMAT_ERROR((byte) 1),
    SERVER_FAILURE((byte) 2),
    NAME_ERROR((byte) 3),
    NOT_IMPLEMENTED((byte) 4),
    REFUSED((byte) 5);

    private static final ResponseCode[] ALL = values();
    private final byte value;

    ResponseCode(final byte value) {
        this.value = value;
    }

    public static ResponseCode parse(final byte value) {
        for (final var rCode : ALL) {
            if (rCode.value == value) {
                return rCode;
            }
        }
        System.err.println("Unknown ResponseCode: " + value);
        return UNKNOWN;
    }

    public byte value() {
        return value;
    }

}
