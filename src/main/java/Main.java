import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public final class Main {

    public static void main(final String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        try (final var socket = new DatagramSocket(2053)) {
            final var buf = new byte[512];
            final var packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            System.out.println("Received data");

            final var responseBuf = new byte[512];
            final var responsePacket = new DatagramPacket(responseBuf, responseBuf.length, packet.getSocketAddress());
            socket.send(responsePacket);
        } catch (final IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

}
