import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream output = new PrintStream(socket.getOutputStream(), true);

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String serverMessage = input.readLine();
            System.out.println(serverMessage);

            while (true) {
                String move = consoleInput.readLine();
                output.println(move);

                serverMessage = input.readLine();
                System.out.println(serverMessage);
            }
        } catch (UnknownHostException e) {
            System.out.println("Erro: Host desconhecido.");
        } catch (IOException e) {
            System.out.println("Erro de entrada/saída: " + e.getMessage());
        }
    }
}