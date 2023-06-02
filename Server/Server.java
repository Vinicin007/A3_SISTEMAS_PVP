import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.ConnectException;

public class Server {
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Aguardando conexões de clientes...");

            while (true) {
                Socket player1Socket = serverSocket.accept();
                System.out.println("Jogador 1 conectado.");

                Socket player2Socket = serverSocket.accept();
                System.out.println("Jogador 2 conectado.");

                handleClients(player1Socket, player2Socket);

                player1Socket.close();
                player2Socket.close();
                System.out.println("Conexões com jogadores encerradas.");
            }
        } catch (ConnectException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void handleClients(Socket player1Socket, Socket player2Socket) throws IOException {
        BufferedReader player1Input = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
        PrintStream player1Output = new PrintStream(player1Socket.getOutputStream(), true);

        BufferedReader player2Input = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
        PrintStream player2Output = new PrintStream(player2Socket.getOutputStream(), true);

        // Envio das instruções para os jogadores
        player1Output.println("Você é o jogador 1. Digite 'pedra', 'papel' ou 'tesoura'.");
        player2Output.println("Você é o jogador 2. Digite 'pedra', 'papel' ou 'tesoura'.");

        // Lógica do jogo
        while (true) {
            String player1Move = player1Input.readLine();
            System.out.println("Jogador 1 jogou: " + player1Move);

            String player2Move = player2Input.readLine();
            System.out.println("Jogador 2 jogou: " + player2Move);

            String result = determineWinner(player1Move, player2Move);

            player1Output.println(result);
            player2Output.println(result);
        }
    }

    public static String determineWinner(String player1Move, String player2Move) {
        if (player1Move.equals(player2Move)) {
            return "Empate!";
        } else if ((player1Move.equals("pedra") && player2Move.equals("tesoura"))
                || (player1Move.equals("papel") && player2Move.equals("pedra"))
                || (player1Move.equals("tesoura") && player2Move.equals("papel"))) {
            return "Jogador 1 venceu!";
        } else {
            return "Jogador 2 venceu!";
        }
    }
}
