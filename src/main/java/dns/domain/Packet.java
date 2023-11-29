package dns.domain;

import java.util.List;

public record Packet(
        Header header,
        List<Question> questions,
        List<ResourceRecord> answers,
        List<ResourceRecord> authorities,
        List<ResourceRecord> additions
) {
}
