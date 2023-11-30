package dns;

import dns.domain.Packet;
import dns.domain.record.ResourceRecord;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public record Client(String host, int port) {

    private static final int DEFAULT_PORT = 53;
    private static final byte[] LOCALHOST = { 127, 0, 0, 1 };

    public static Client using(final String resolver) {
        final var parts = resolver.split(":");
        final var host = parts[0];
        final int port = parts.length == 2 ? Integer.parseInt(parts[1]) : DEFAULT_PORT;
        return new Client(host, port);
    }

    public List<ResourceRecord> resolve(final Packet packet) {
        final var answers = new ArrayList<ResourceRecord>();
        for (final var question : packet.questions()) {
            if (!packet.header().rd()) {
                return List.of(new ResourceRecord(question.name(), question.qType(), question.qClass(), 60, (short) 4, LOCALHOST));
            }

            final var buffer = ByteBuffer.allocate(512);
            try (final var socket = new DatagramSocket()) {
                final var outgoing = Packet.resolving(packet);
                outgoing.write(buffer);

                final var outgoingDatagram = new DatagramPacket(buffer.array(), buffer.capacity(), InetAddress.getByName(host), port);
                socket.send(outgoingDatagram);

                final var incomingDatagram = new DatagramPacket(buffer.clear().array(), buffer.capacity());
                socket.receive(incomingDatagram);

                final var incoming = Packet.parse(buffer);
                answers.addAll(incoming.answers());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return answers;
    }

}
