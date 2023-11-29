import dns.Parser;
import dns.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public final class Main {

    private static final Server SERVER = new Server();
    private static final Parser PARSER = new Parser();

    public static void main(final String[] args) {
        System.out.println("Logs from your program will appear here!");

        try (final var socket = new DatagramSocket(2053)) {
	        while (true) {
                System.out.println("Receiving data...");
		        final var inBuf = new byte[512];
		        final var inPacket = new DatagramPacket(inBuf, inBuf.length);
		        socket.receive(inPacket);

                System.err.println("Received request: " + Arrays.toString(inBuf));
		        final var request = PARSER.readPacket(inBuf);
		        final var response = SERVER.handle(request);

		        final var outBuf = PARSER.writePacket(response);
		        final var outPacket = new DatagramPacket(outBuf, outBuf.length, inPacket.getSocketAddress());
		        socket.send(outPacket);
	        }
        } catch (final IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

}
