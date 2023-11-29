package dns;

import dns.domain.Header;
import dns.domain.Packet;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.BitSet;
import java.util.List;

public final class Parser {

    public Packet parse(final DatagramPacket packet) {
        final var buffer = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
        final var header = parseHeader(buffer);
        return new Packet(header, List.of(), List.of(), List.of(), List.of());
    }

    public byte[] parse(final Packet response) {
        final var buffer = ByteBuffer.allocate(512).order(ByteOrder.BIG_ENDIAN);
        writeHeader(buffer, response.header());
        return buffer.rewind().array();
    }

    private void writeHeader(final ByteBuffer buffer, final Header header) {
        buffer.putShort(header.id());

        var flags = new BitSet(16);
        flags.set(0, header.qr());
        setByte(flags, 1, header.opCode());
        flags.set(5, header.aa());
        flags.set(6, header.tc());
        flags.set(7, header.rd());
        flags.set(8, header.ra());
        flags.clear(9, 12);
        setByte(flags, 12, header.rCode().value());
        buffer.put(flags.toByteArray());

        buffer.putShort(header.qdCount());
        buffer.putShort(header.anCount());
        buffer.putShort(header.nsCount());
        buffer.putShort(header.arCount());
    }

    private Header parseHeader(final ByteBuffer buffer) {
        final short id = buffer.getShort();

        final byte[] flagsBytes = new byte[2];
        buffer.get(flagsBytes);

        final var flags = BitSet.valueOf(buffer.alignedSlice(2));
        final var qr = flags.get(0);
        final var opCode = getByte(flags.get(1, 5));
        final var aa = flags.get(5);
        final var tc = flags.get(6);
        final var rd = flags.get(7);
        final var ra = flags.get(8);
        final var z = getByte(flags.get(9, 12));
        final var rCode = Header.RCode.from(getByte(flags.get(12, 16)));
        final var qdCount = buffer.getShort();
        final var anCount = buffer.getShort();
        final var nsCount = buffer.getShort();
        final var arCount = buffer.getShort();
        return new Header(
                id,
                qr,
                opCode,
                aa,
                tc,
                rd,
                ra,
                z,
                rCode,
                qdCount,
                anCount,
                nsCount,
                arCount
        );
    }

    private byte getByte(final BitSet bitSet) {
        final var bytes = bitSet.toByteArray();
        return bytes.length != 0 ? bytes[0] : 0;
    }

    private void setByte(final BitSet flags, final int startIndex, final byte value) {
        final var bitSet = BitSet.valueOf(new byte[]{value});
        for (int i = 0; i < 4; i++) {
            flags.set(startIndex + i, bitSet.get(i));
        }
    }

}
