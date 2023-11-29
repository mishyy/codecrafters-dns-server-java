import dns.Parser;
import dns.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class Main {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static void main(final String[] args) {
        final var parser = new Parser();
        final var server = new Server();

        System.out.println("Logs from your program will appear here!");
        try (final var socket = new DatagramSocket(2053)) {
            while (true) {
                final var inBuf = new byte[12];
                final var inPacket = new DatagramPacket(inBuf, inBuf.length);
                socket.receive(inPacket);

                COUNTER.incrementAndGet();

                final var request = parser.readPacket(inBuf);
                System.out.printf("#%d(in) :: %s%n", COUNTER.get(), request);
                System.out.printf("#%d(in) :: %s%n", COUNTER.get(), Arrays.toString(inBuf));
                final var response = server.handle(request);

                final var outBuf = parser.writePacket(response);
                final var outPacket = new DatagramPacket(outBuf, outBuf.length, inPacket.getSocketAddress());
                socket.send(outPacket);
                System.out.printf("#%d(out) :: %s%n", COUNTER.get(), response);
                System.out.printf("#%d(out) :: %s%n", COUNTER.get(), Arrays.toString(outBuf));
            }
        } catch (final IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

}
