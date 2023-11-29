package dns.domain;

import dns.domain.records.ResourceRecord;

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
) {}
