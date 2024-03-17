import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        final String SERVER_IP = "127.0.0.1"; // Cambiar por la IP del servidor
        final int SERVER_PORT = 12345;

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviamos un mensaje al servidor
            out.println("Hola, servidor");

            // Esperamos la respuesta del servidor
            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

        } catch (UnknownHostException e) {
            System.err.println("Host desconocido: " + SERVER_IP);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("No se pudo establecer conexión con el servidor.");
            e.printStackTrace();
        }
    }
}
