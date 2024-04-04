/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class ClienteSocket {
    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;
    private static Scanner scanner = new Scanner(System.in);
    private static String mensajeRecibido;
    
    public static void iniciarConexion() {
        try {
            Socket socketCliente = new Socket(HOST, PUERTO);
            DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());
            DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
            
            String mensajeAEnviar = "";
            
            //Iniciamos la comunicación
            while(!mensajeAEnviar.equals("SALIR"))
            {
                System.out.println("Digite su mensaje para el servidor: ");
                System.out.println();
                mensajeAEnviar = scanner.nextLine();
                salida.writeUTF(mensajeAEnviar);
                mensajeRecibido = entrada.readUTF();
                System.out.println("Mensaje del servidor: " + mensajeRecibido);
            }
            /*
            System.out.print("Ingrese el usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Ingrese la contraseña: ");
            String contrasena = scanner.nextLine();

            salida.writeUTF(usuario);
            salida.writeUTF(contrasena);

            String respuesta = entrada.readUTF();
            System.out.println(respuesta);
            */
            salida.close();
            entrada.close();
            socketCliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
