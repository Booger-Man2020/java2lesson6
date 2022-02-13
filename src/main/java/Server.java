
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started");
             Socket socket = serverSocket.accept();
            System.out.println("Client accepted");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true){
                String message = in.readUTF();
                if (message.equals(" /stop")){
                    break;
                }
                out.writeUTF("Server" + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
