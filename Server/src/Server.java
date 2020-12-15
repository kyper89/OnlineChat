import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Server {

    public static final int SERVER_PORT = 8189;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Waiting for new connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client has been connected!");

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            Thread threadListen = new Thread(()-> {
                try {
                    printMessage(in);
                } catch (IOException e) {
                    System.err.println("Connection has been closed!");
                    e.printStackTrace();
                }
            });

            threadListen.start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                out.writeUTF(scanner.next());
            }

        } catch (SocketException e) {
            System.err.println("Server port is already opened!");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("Connection has been closed!");
            e.printStackTrace();
        }
    }

    private static void printMessage(DataInputStream in ) throws IOException {
        while (true) {
            String message = in.readUTF();
            System.out.println(message);
        }
    }

}
