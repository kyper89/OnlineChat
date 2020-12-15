import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {

    public static final int SERVER_PORT = 8189;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Waiting for new connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client has been connected!");

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                String str = in.readUTF();
                if (str.equals("/end")) {
                    break;
                }
                out.writeUTF("Echo: " + str);
            }
            System.out.println("Server has been closed");
        } catch (SocketException e) {
            System.err.println("Server port is already opened!");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("Connection has been closed!");
            e.printStackTrace();
        }

    }
}
