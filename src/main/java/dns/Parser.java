package dns;

import dns.domain.Header;
import dns.domain.Packet;
import dns.domain.Question;
import dns.domain.ResponseCode;
import dns.domain.records.ResourceRecord;
import dns.util.Bytes;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public final class Parser {

    public Packet readPacket(final byte[] bytes) {
        final var buffer = ByteBuffer.wrap(bytes);
        final var header = parseHeader(buffer);
//        final var questions = parseQuestions(buffer, header.qdCount());
//        final var answers = parseRecords(buffer, header.anCount());
//        final var authorities = parseRecords(buffer, header.nsCount());
//        final var additions = parseRecords(buffer, header.arCount());
        return new Packet(header, List.of(), List.of(), List.of(), List.of());
    }

    public byte[] writePacket(final Packet packet) {
        final var buffer = ByteBuffer.allocate(12);
        packet.header().write(buffer);
//        packet.questions().forEach(question -> question.write(buffer));
//        packet.answers().forEach(answer -> answer.write(buffer));
//        packet.authorities().forEach(authority -> authority.write(buffer));
//        packet.additional().forEach(addition -> addition.write(buffer));
        return buffer.rewind().array();
    }

    private Header parseHeader(final ByteBuffer buffer) {
        final short id = buffer.getShort();

        var flags = BitSet.valueOf(buffer.slice(3, 2));
        final var qr = flags.get(15);
        final var opCode = Bytes.valueOrZero(flags.get(11, 15));
        final var aa = flags.get(10);
        final var tc = flags.get(9);
        final var rd = flags.get(8);
        final var ra = flags.get(7);
        final var z = Bytes.valueOrZero(flags.get(4, 7));
        final var rCodeRaw = Bytes.valueOrZero(flags.get(0, 4));
        final var rCode = ResponseCode.from(rCodeRaw);

        final var qdCount = buffer.getShort();
        final var anCount = buffer.getShort();
        final var nsCount = buffer.getShort();
        final var arCount = buffer.getShort();
        return new Header(id, qr, opCode, aa, tc, rd, ra, z, rCode, qdCount, anCount, nsCount, arCount);
    }

    private List<Question> parseQuestions(final ByteBuffer buffer, final int count) {
        final var questions = new ArrayList<Question>();
        for (int i = 0; i < count; i++) {
            final String name = new String(buffer.slice().array()); // TODO: fix
            final var type = buffer.getShort();
            final var clazz = buffer.getShort();
            questions.add(new Question(name, type, clazz));
        }
        return questions;
    }

    private List<ResourceRecord> parseRecords(final ByteBuffer buffer, final int count) {
        final var records = new ArrayList<ResourceRecord>();
        for (int i = 0; i < count; i++) {
            final var name = new String(buffer.slice().array()); // TODO: fix
            final var type = buffer.getShort();
            final var clazz = buffer.getShort();
            final var ttl = buffer.getInt();
            final var rdLength = buffer.getShort();
            final var rData = new byte[rdLength];
            buffer.get(rData);
            records.add(new ResourceRecord(name, type, clazz, ttl, rdLength, rData));
        }
        return records;
    }

}
