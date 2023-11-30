package dns;

import dns.domain.Header;
import dns.domain.OpCode;
import dns.domain.Packet;
import dns.domain.ResponseCode;
import dns.domain.record.ResourceRecord;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.List;

public final class Server {

    private final Client client;

    public Server(final Client client) {
        this.client = client;
    }

    private static Header header(final Packet incoming, final List<ResourceRecord> answers, final List<ResourceRecord> authorities, final List<ResourceRecord> additionals) {
        final var incomingHeader = incoming.header();
        return new Header(
                incomingHeader.id(),
                true,
                incomingHeader.opCode(),
                false,
                false,
                incomingHeader.rd(),
                true,
                incomingHeader.z(),
                incomingHeader.opCode() == OpCode.QUERY ? ResponseCode.NO_ERROR : ResponseCode.NOT_IMPLEMENTED,
                incomingHeader.qdCount(),
                (short) answers.size(),
                (short) authorities.size(),
                (short) additionals.size()
        );
    }

    public void handle(final DatagramSocket socket, final SocketAddress requester, final Packet incoming) throws IOException {
        final var answers = client.resolve(incoming);
        final var authorities = List.<ResourceRecord>of();
        final var additionals = List.<ResourceRecord>of();
        final var header = header(incoming, answers, authorities, additionals);

        final var buffer = ByteBuffer.allocate(512);
        {
            final var packet = new Packet(header, incoming.questions(), answers, authorities, additionals);
            packet.write(buffer);

            final var datagram = new DatagramPacket(buffer.array(), buffer.capacity(), requester);
            socket.send(datagram);
        }
        buffer.clear();
    }

}
