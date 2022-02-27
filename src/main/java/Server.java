
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static DataInputStream in;
    public static DataOutputStream out;


    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            System.out.println("Server started");
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключен");

            new Thread(new Runnable() {  //чтение из потока от клиета
                @Override
                public void run() {
                    try {
                        while (true) {
                            in = new DataInputStream(socket.getInputStream());
                            String message = in.readUTF();
                            System.out.println("клиент: " + message);
                            if (in.equals("/stop")) {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        new Thread(new Runnable() { // отправка
            @Override
            public void run() {
                try {
                    while (true) {
                        out = new DataOutputStream(socket.getOutputStream());
                        Scanner s = new Scanner(System.in);
                        String text = s.nextLine();
                        System.out.println("Сервер: " + text);
                        out.writeUTF(text);
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }catch (IOException e) {
            e.printStackTrace();
        }
    }
}