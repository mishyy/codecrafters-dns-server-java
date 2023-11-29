package dns.domain;

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
        // ID
        buffer.putShort(id);

        final var flags = new BitSet(16);
        flags.set(15, qr);
        final var opCode = BitSet.valueOf(new byte[]{this.opCode});
        flags.set(14, opCode.get(3));
        flags.set(13, opCode.get(2));
        flags.set(12, opCode.get(1));
        flags.set(11, opCode.get(0));
        flags.set(10, aa);
        flags.set(9, tc);
        flags.set(8, rd);
        flags.set(7, ra);
        final var z = BitSet.valueOf(new byte[]{this.z});
        flags.set(6, z.get(2));
        flags.set(5, z.get(1));
        flags.set(4, z.get(0));
        final var rCode = BitSet.valueOf(new byte[]{this.rCode.value()});
        flags.set(3, rCode.get(3));
        flags.set(2, rCode.get(2));
        flags.set(1, rCode.get(1));
        flags.set(0, rCode.get(0));
        buffer.put(flags.toByteArray());

        buffer.putShort(qdCount);
        buffer.putShort(anCount);
        buffer.putShort(nsCount);
        buffer.putShort(arCount);
    }

}
