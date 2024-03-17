import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private JFrame frame;
    private JTextArea logArea;
    private ServerSocket serverSocket;
    private boolean isRunning = false;

    public Servidor() {
        frame = new JFrame("Server GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void startServer() {
        final int PORT = 12345;

        try {
            serverSocket = new ServerSocket(PORT);
            log("Servidor en línea. Esperando conexiones...");

            isRunning = true;

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                log("Cliente conectado desde: " + clientSocket.getInetAddress().getHostAddress());

                // Manejo de cliente en un hilo separado
                Manejador clientHandler = new Manejador(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                logArea.append(message + "\n");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Servidor servidor = new Servidor();
                servidor.startServer();
            }
        });
    }
}
