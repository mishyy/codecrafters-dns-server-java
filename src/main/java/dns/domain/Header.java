package dns.domain;

import java.util.NoSuchElementException;

public record Header(
        short id, // Packet Identifier; 16 bits
        boolean qr, // Query/Response Indicator; 1 bit
        byte opCode, // Operation Code; 4 bits
        boolean aa, // Authoritative Answer; 1 bit
        boolean tc, // Truncation Flag; 1 bit
        boolean rd, // Recursion Desired; 1 bit
        boolean ra, // Recursion Available; 1 bit
        byte z, // Reserved for future use; DNSSEC; 3 bits
        RCode rCode, // Response Code; 4 bits
        short qdCount, // Question Count; 16 bits
        short anCount, // Answer Record Count; 16 bits
        short nsCount, // Authority Record Count; 16 bits
        short arCount // Additional Record Count; 16 bits
) {
    public enum RCode {
        NO_ERROR((byte) 0),
        FORMAT_ERROR((byte) 1),
        SERVER_FAILURE((byte) 2),
        NAME_ERROR((byte) 3),
        NOT_IMPLEMENTED((byte) 4),
        REFUSED((byte) 5);

        private static final RCode[] ALL = values();
        private final byte value;

        RCode(final byte value) {
            this.value = value;
        }

        public static RCode from(final byte value) {
            for (final var rCode : ALL) {
                if (rCode.value == value) {
                    return rCode;
                }
            }
            throw new NoSuchElementException("Could not find RCode with value " + value + ".");
        }

        public byte value() {
            return value;
        }
    }
}
