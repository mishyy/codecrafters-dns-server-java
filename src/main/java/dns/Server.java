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
                true,
                header.opCode(),
                false,
                false,
                header.rd(),
                false,
                (byte) 0,
                ResponseCode.NO_ERROR,
                (short) 0,
                (short) 0,
                (short) 0,
                (short) 0
        );
        return new Packet(responseHeader, List.of(), List.of(), List.of(), List.of());
    }

}
