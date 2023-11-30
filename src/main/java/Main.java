import dns.Server;
import dns.domain.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class Main {

    private static final int PACKET_SIZE = 512;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static void main(final String[] args) {
        final var server = new Server();

        System.out.println("Logs parse your program will appear here!");
        try (final var socket = new DatagramSocket(2053)) {
            while (true) {
                final var inBuf = new byte[PACKET_SIZE];
                final var inPacket = new DatagramPacket(inBuf, inBuf.length);
                socket.receive(inPacket);

                COUNTER.incrementAndGet();

//                System.out.printf("#%d(in) :: %s%n", COUNTER.get(), Arrays.toString(inBuf));
                final var request = readPacket(inBuf);
                System.out.printf("#%d(in) :: %s%n", COUNTER.get(), request);

                final var response = server.handle(request);

                final var outBuf = writePacket(response);
                final var outPacket = new DatagramPacket(outBuf, outBuf.length, inPacket.getSocketAddress());
                socket.send(outPacket);
                System.out.printf("#%d(out) :: %s%n", COUNTER.get(), response);
//                System.out.printf("#%d(out) :: %s%n", COUNTER.get(), Arrays.toString(outBuf));
            }
        } catch (final IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    private static Packet readPacket(final byte[] bytes) {
        return Packet.parse(ByteBuffer.wrap(bytes));
    }

    private static byte[] writePacket(final Packet packet) {
        final var buffer = ByteBuffer.allocate(PACKET_SIZE);
        packet.write(buffer);
        return buffer.rewind().array();
    }

}
