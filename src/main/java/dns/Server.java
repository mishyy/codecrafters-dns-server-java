package dns;

import dns.domain.Header;
import dns.domain.Packet;
import dns.domain.ResponseCode;

import java.util.List;

public final class Server {

    public Packet handle(final Packet packet) {
        final var header = packet.header();
        final var responseHeader = new Header(
                header.id(),
                false,
                (byte) 0,
                false,
                false,
                false,
                false,
                (byte) 0,
                ResponseCode.FORMAT_ERROR,
                (short) 256,
                (short) 1,
                (short) 0,
                (short) 0
        );
        return new Packet(responseHeader, List.of(), List.of(), List.of(), List.of());
    }

}
