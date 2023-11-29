package dns.domain;

public record ResourceRecord(
        String name, // Resource Name; variable bytes
        short type, // Record Type; 2 bytes
        short clazz, // Class of Resource Data; 2 bytes
        int ttl, // Time to Live; 4 bytes
        short rdLength, // Resource Data Length; 2 bytes
        byte[] rData // Resource Data; variable bytes
) {

}
