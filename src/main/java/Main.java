import dns.Parser;
import dns.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public final class Main {

    private static final Server SERVER = new Server();
    private static final Parser PARSER = new Parser();

    public static void main(final String[] args) {
        System.out.println("Logs from your program will appear here!");

        try (final var socket = new DatagramSocket(2053)) {
	        do {
		        final var inBuf = new byte[512];
		        final var packet = new DatagramPacket(inBuf, inBuf.length);
		        socket.receive(packet);

		        final var dnsPacket = PARSER.parse(packet);
		        final var dnsResponse = SERVER.handle(dnsPacket);
		        final var outBuf = PARSER.parse(dnsResponse);
		        final var response = new DatagramPacket(outBuf, outBuf.length, packet.getSocketAddress());
		        socket.send(response);
	        } while (true);
        } catch (final IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

}
