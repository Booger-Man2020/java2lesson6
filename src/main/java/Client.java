import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 8081;

    public static Socket socket;
    public static DataInputStream in;
    public static DataOutputStream out;


    public static void main(String[] args) throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        System.out.println("Сервер подключен");
        System.out.println("Введите сообщение");
        Scanner scanner = new Scanner(System.in);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        in = new DataInputStream(socket.getInputStream());
                        String message = in.readUTF();
                        System.out.println("Сервер: " + message);
                        if (message.equals("/stop")) {
                            Client.closeConection();
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (true) {
                        out = new DataOutputStream(new DataOutputStream(socket.getOutputStream()));
                        String mes = scanner.nextLine();
                        out.writeUTF(mes);
                        out.flush();
                        System.out.println("Клиент: " + mes);
                        if (mes.equals("/stop")) {
                            Client.closeConection();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private static void closeConection() {
        try {
            in.close();
            out.close();
            socket.close();
            System.exit(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
