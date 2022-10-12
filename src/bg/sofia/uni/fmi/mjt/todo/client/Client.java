package bg.sofia.uni.fmi.mjt.todo.client;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 6666;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = 512;

    private static ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public static void main(String[] args) {

        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

            System.out.println("Connected to the server");

            while (true) {
                System.out.println("Enter message: ...");
                String message = scanner.nextLine();

                if ("disconnect".equals(message)) {
                    break;
                }

                System.out.println("Sending message <" + message + "> to the server...");
                byteBuffer.clear();
                byteBuffer.put(message.getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);

                byteBuffer.clear();
                socketChannel.read(byteBuffer);
                byteBuffer.flip();

                byte[] byteArray = new byte[byteBuffer.remaining()];
                byteBuffer.get(byteArray);
                String reply = new String(byteArray, "UTF-8");


                System.out.println("The server replied <" + reply + ">");
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

}
