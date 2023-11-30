package dns.domain;

import dns.domain.record.ResourceRecord;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a DNS packet.
 */
public record Packet(
        Header header,
        List<Question> questions,
        List<ResourceRecord> answers,
        List<ResourceRecord> authorities,
        List<ResourceRecord> additional
) implements Writer {

    public static Packet parse(final ByteBuffer buffer) {
        final var header = Header.parse(buffer);

        final var questions = new ArrayList<Question>();
        for (var i = 0; i < header.qdCount(); i++) {
            questions.add(Question.parse(buffer));
        }

        final var answers = new ArrayList<ResourceRecord>();
//        for (var i = 0; i < header.anCount(); i++) {
//            answers.add(ResourceRecord.parseAnswer(buffer));
//        }

        final var authorities = new ArrayList<ResourceRecord>();
//        for (var i = 0; i < header.nsCount(); i++) {
//            authorities.add(Question.parseAuthority(buffer));
//        }

        final var additional = new ArrayList<ResourceRecord>();
//        for (var i = 0; i < header.arCount(); i++) {
//            additional.add(Question.parseAdditional(buffer));
//        }
        return new Packet(header, questions, answers, authorities, additional);
    }

    @Override
    public void write(final ByteBuffer buffer) {
        header.write(buffer);
        questions.forEach(question -> question.write(buffer));
//        answers.forEach(answer -> answer.write(buffer));
//        authorities.forEach(authority -> authority.write(buffer));
//        additional.forEach(addition -> addition.write(buffer));
    }

}
