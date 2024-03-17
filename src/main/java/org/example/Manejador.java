import java.io.*;
import java.net.*;

public class Manejador extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Manejador(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje del cliente: " + inputLine);

                // Lógica del servidor para procesar el mensaje recibido

                out.println("Respuesta del servidor: Mensaje recibido.");
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
