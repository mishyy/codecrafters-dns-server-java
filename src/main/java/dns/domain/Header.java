package dns.domain;

import dns.util.Bytes;

import java.nio.ByteBuffer;
import java.util.BitSet;

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
 * @param qdCount The number of question in the packet.
 * @param anCount The number of answer resource records in the packet.
 * @param nsCount The number of authority resource records in the packet.
 * @param arCount The number of additional resource records in the packet.
 */
public record Header(short id, boolean qr, byte opCode, boolean aa, boolean tc, boolean rd, boolean ra, byte z,
                     ResponseCode rCode, short qdCount, short anCount, short nsCount, short arCount) implements Writer {

    @Override
    public void write(final ByteBuffer buffer) {
        buffer.putShort(id);

        final var flags = new BitSet(16);
        flags.set(7, qr);
        final var opCode = BitSet.valueOf(new byte[]{this.opCode});
        flags.set(6, opCode.get(3));
        flags.set(5, opCode.get(2));
        flags.set(4, opCode.get(1));
        flags.set(3, opCode.get(0));
        flags.set(2, aa);
        flags.set(1, tc);
        flags.set(0, rd);
        buffer.put(Bytes.valueOrZero(flags));
        flags.set(7, ra);
        flags.clear(4, 7);
        final var rCode = BitSet.valueOf(new byte[]{this.rCode.value()});
        flags.set(3, rCode.get(3));
        flags.set(2, rCode.get(2));
        flags.set(1, rCode.get(1));
        flags.set(0, rCode.get(0));
        buffer.put(Bytes.valueOrZero(flags));

        buffer.putShort(qdCount);
        buffer.putShort(anCount);
        buffer.putShort(nsCount);
        buffer.putShort(arCount);
    }

}
