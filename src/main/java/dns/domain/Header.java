package dns.domain;

import dns.util.Booleans;

import java.nio.ByteBuffer;

/**
 * Represents a DNS packet header.
 *
 * @param id      The identification number of the packet.
 * @param qr      The query/response flag.
 * @param opCode  The operation code.
 * @param aa      The authoritative answer flag.
 * @param tc      The truncation flag.
 * @param rd      The recursion desired flag.
 * @param ra      The recursion availability flag.
 * @param z       Reserved bits.
 * @param rCode   The response code.
 * @param qdCount The number of questions in the packet.
 * @param anCount The number of answer resource records in the packet.
 * @param nsCount The number of authority resource records in the packet.
 * @param arCount The number of additional resource records in the packet.
 */
public record Header(short id, boolean qr, OpCode opCode, boolean aa, boolean tc, boolean rd, boolean ra, byte z,
                     ResponseCode rCode, short qdCount, short anCount, short nsCount, short arCount) implements Writer {

    public static Header parse(final ByteBuffer buffer) {
        final short id = buffer.getShort();

        final var flags = buffer.getShort();
        final var qr = Booleans.fromInt(flags & 0x8000);
        final var opCode = OpCode.parse((byte) ((flags & 0x7800) >> 11));
        final var aa = Booleans.fromInt(flags & 0x0400);
        final var tc = Booleans.fromInt(flags & 0x0200);
        final var rd = Booleans.fromInt(flags & 0x0100);
        final var ra = Booleans.fromInt(flags & 0x0080);
        final var z = (byte) ((flags & 0x0070) >> 4);
        final var rCode = ResponseCode.parse((byte) (flags & 0x000F));

        final var qdCount = buffer.getShort();
        final var anCount = buffer.getShort();
        final var nsCount = buffer.getShort();
        final var arCount = buffer.getShort();
        return new Header(id, qr, opCode, aa, tc, rd, ra, z, rCode, qdCount, anCount, nsCount, arCount);
    }

    @Override
    public void write(final ByteBuffer buffer) {
        buffer.putShort(id);

        var flags = Booleans.toInt(qr) << 15;
        flags |= (opCode.value() & 0x0F) << 11;
        flags |= Booleans.toInt(aa) << 10;
        flags |= Booleans.toInt(tc) << 9;
        flags |= Booleans.toInt(rd) << 8;
        flags |= Booleans.toInt(ra) << 7;
        flags |= (z & 0x07) << 4;
        flags |= rCode.value() & 0x0F;
        buffer.putShort((short) flags);

        buffer.putShort(qdCount);
        buffer.putShort(anCount);
        buffer.putShort(nsCount);
        buffer.putShort(arCount);
    }

}
