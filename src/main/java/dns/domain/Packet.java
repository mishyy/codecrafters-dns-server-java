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
        List<ResourceRecord> additionals
) implements Writer {

    public static Packet parse(final ByteBuffer buffer) {
        final var header = Header.parse(buffer);

        final var questions = new ArrayList<Question>();
        for (var i = 0; i < header.qdCount(); i++) {
            questions.add(Question.parse(buffer));
        }

        final var answers = new ArrayList<ResourceRecord>();
        for (var i = 0; i < header.anCount(); i++) {
            answers.add(ResourceRecord.parse(buffer));
        }

        final var authorities = new ArrayList<ResourceRecord>();
        for (var i = 0; i < header.nsCount(); i++) {
            authorities.add(ResourceRecord.parse(buffer));
        }

        final var additionals = new ArrayList<ResourceRecord>();
        for (var i = 0; i < header.arCount(); i++) {
            additionals.add(ResourceRecord.parse(buffer));
        }
        return new Packet(header, questions, answers, authorities, additionals);
    }

    @Override
    public void write(final ByteBuffer buffer) {
        header.write(buffer);
        questions.forEach(question -> question.write(buffer));
        answers.forEach(answer -> answer.write(buffer));
        authorities.forEach(authority -> authority.write(buffer));
        additionals.forEach(additional -> additional.write(buffer));
    }

}
