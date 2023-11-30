package dns;

import dns.domain.Header;
import dns.domain.Packet;
import dns.domain.ResponseCode;

import java.util.List;

public final class Server {

    public Packet handle(final Packet packet) {
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
                (short) packet.answers().size(),
                (short) packet.authorities().size(),
                (short) packet.additional().size()
        );
        return new Packet(header, packet.questions(), List.of(), List.of(), List.of());
    }

}
