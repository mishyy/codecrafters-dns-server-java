package dns;

import dns.domain.Header;
import dns.domain.Packet;

import java.util.List;

public final class Server {

    public Packet handle(final Packet packet) {
        final var header = new Header(
                packet.header().id(),
                true,
                (byte) 0,
                false,
                false,
                false,
                false,
                (byte) 0,
                Header.RCode.NO_ERROR,
                (short) 0,
                (short) 0,
                (short) 0,
                (short) 0
        );
        return new Packet(header, List.of(), List.of(), List.of(), List.of());
    }

}
