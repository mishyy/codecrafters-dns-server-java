package dns;

import dns.domain.Header;
import dns.domain.Packet;
import dns.domain.Question;
import dns.domain.ResponseCode;
import dns.domain.record.ResourceRecord;

import java.util.ArrayList;
import java.util.List;

public final class Server {

    public Packet handle(final Packet packet) {
        final var answers = getAnswers(packet.questions());
        final var authorities = List.<ResourceRecord>of();
        final var additionals = List.<ResourceRecord>of();
        final var header = new Header(
                packet.header().id(),
                true,
                (byte) 0,
                false,
                false,
                packet.header().rd(),
                false,
                (byte) 0,
                ResponseCode.NO_ERROR,
                (short) packet.questions().size(),
                (short) answers.size(),
                (short) authorities.size(),
                (short) additionals.size()
        );
        return new Packet(header, packet.questions(), answers, authorities, additionals);
    }

    private List<ResourceRecord> getAnswers(final List<Question> questions) {
        final var answers = new ArrayList<ResourceRecord>();
        for (final var question : questions) {
            answers.add(new ResourceRecord(
                    question.name(),
                    question.type(),
                    question.clazz(),
                    60,
                    (short) 4,
                    new byte[]{127, 0, 0, 1}
            ));
        }
        return answers;
    }

}
