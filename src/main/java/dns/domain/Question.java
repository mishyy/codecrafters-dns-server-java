package dns.domain;

public record Question(
        String name, // Resource Name; variable bytes
        short type, // ResourceRecord Type; 2 bytes
        short clazz // Class; 2 bytes
) {
}
