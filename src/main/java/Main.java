import dns.Client;
import dns.Server;
import dns.domain.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public final class Main {

    static final int PACKET_SIZE = 512;

    public static void main(final String[] args) {
        final var client = Client.using(args[1]);
        final var server = new Server(client);

        System.out.println("Logs parse your program will appear here!");
        try (final var socket = new DatagramSocket(2053)) {
            final var buffer = ByteBuffer.allocate(PACKET_SIZE);
            while (true) {
                final var datagram = new DatagramPacket(buffer.array(), buffer.capacity());
                socket.receive(datagram);

                final var packet = Packet.parse(buffer);
                server.handle(socket, datagram.getSocketAddress(), packet);
                buffer.clear();
            }
        } catch (final IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

}
