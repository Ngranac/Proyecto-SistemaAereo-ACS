/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidor;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author User
 */
public class ServidorSocket {
    private static final int PUERTO = 5000;
    /*
    private static final Map<String, String> usuarios = new HashMap<>();
    static {
        usuarios.put("Piloto1", "Piloto123");
        usuarios.put("Piloto2", "Piloto123");
    }*/
    static ArrayList<ControladoresAereos> controladoresAereos = new ArrayList<ControladoresAereos>();
    static ArrayList<Aviones> aviones = new ArrayList<Aviones>();
    private static String mensajeRecibido = "";
    static Scanner scanner = new Scanner(System.in);

    public static void iniciarServidor() {
        if(controladoresAereos.isEmpty()){
            registrarControladorAereo();
        } else {
            System.out.println("1. Iniciar sesión\n2. Registrarse\nDigite una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            if(opcion == 1){
                iniciarSesion();
            } else {
                registrarControladorAereo();
            }
        }
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + socketCliente.getInetAddress().getHostAddress());

                new HiloCliente(socketCliente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class HiloCliente extends Thread {
        private Socket socketCliente;
        private DataInputStream entrada;
        private DataOutputStream salida;

        public HiloCliente(Socket socketCliente) {
            this.socketCliente = socketCliente;
        }

        public void run() {
            try {
                entrada = new DataInputStream(socketCliente.getInputStream());
                salida = new DataOutputStream(socketCliente.getOutputStream());
                
                String mensajeAEnviar = "";
                
                while(!mensajeAEnviar.equalsIgnoreCase("SALIR")){
                    mensajeRecibido = entrada.readUTF();

                    System.out.println("Mensaje del cliente: " + mensajeRecibido);
                    System.out.println();
                    if(mensajeRecibido.equalsIgnoreCase("HOLA")){
                        mensajeAEnviar = "Hola";
                        salida.writeUTF(mensajeAEnviar);
                    } else if(mensajeRecibido.contains("caracteristicas") && mensajeRecibido.contains("aviones")){
                        System.out.println("1. Agregar un avión\n2.Enviar información de aviones\n");
                        int opcion = Integer.parseInt(scanner.nextLine());
                        if(opcion == 1){
                            agregarAvion();
                        } else {
                        
                        }
                    } else {
                        System.out.println("Digite una respuesta para el cliente: ");
                        mensajeAEnviar = scanner.nextLine();
                        salida.writeUTF(mensajeAEnviar);
                    }
                }
                
                /*
                if (autenticarUsuario(usuario, contrasena)) {
                    salida.writeUTF("Acceso concedido. ¡Bienvenido!");
                } else {
                    salida.writeUTF("Acceso denegado");
                }*/

                entrada.close();
                salida.close();
                socketCliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        private void agregarAvion(){
            boolean seguir = true;
            
            while(seguir){
                System.out.println("Digite el origen del avión que desea agregar: ");
                String origen = scanner.nextLine();
                System.out.println("Digite el destino del avión que desea agregar: ");
                String destino = scanner.nextLine();
                System.out.println("Digite la gasolina disponible del avión que desea agregar: ");
                String gasolinaDisponible = scanner.nextLine();
                System.out.println("Digite la altitud del avión que desea agregar: ");
                String altitud = scanner.nextLine();
                System.out.println("Digite la cantidad de pasajeros del avión que desea agregar: ");
                String cantidadPasajeros = scanner.nextLine();
                System.out.println("Digite si el avión que desea agregar está en condición de despegue o aterrizaje: ");
                String condicion = scanner.nextLine();
                String fecha = LocalDate.now().toString();
                String hora = LocalTime.now().toString();
                GestionarAvionesBD gestionarAvionesBD = new GestionarAvionesBD();
                
                // Agregar la infromación a la base de datos
                gestionarAvionesBD.insertarAviones(origen, destino, gasolinaDisponible, altitud, cantidadPasajeros, condicion, fecha, hora);
                System.out.println("Avión agregado con exito.");
                aviones.add(new Aviones(origen, destino, gasolinaDisponible, altitud, cantidadPasajeros, condicion, fecha, hora));

                System.out.println("Desea agregar otro avión: ");
                String opcion = scanner.nextLine();
                if(!opcion.equalsIgnoreCase("Si")){
                    seguir = false;
                }
            }
        }
        
        private String enviarInformacionAviones(){
            String msg = "";
            int n = 1;
            
            for(Aviones i : aviones){
                
                msg += n + "- " + i.getOrigen() + ", " + i.getDestino() + ", " + i.getGasolinaDisponible()
                        + ", " + i.getAltitud() + ", " + i.getCantidadPasajeros() 
                        + ", " + i.getCondicion() + ", " + i.getFecha() + ", "
                        + i.getHora() + ".\n";
                n++;
            }
            
            return msg;
        }
        
        /*
        private boolean autenticarUsuario(String usuario, String contrasena) {
            String contrasenaCorrecta = usuarios.get(usuario);
            return contrasenaCorrecta != null && contrasenaCorrecta.equals(contrasena);
        }*/
    }
    
    private static void registrarControladorAereo(){
        GestionarControladoresBD gestionarControladoresBD = new GestionarControladoresBD();

        System.out.println("Digite el nombre con el que se desea registrar: ");
        String nombre = scanner.nextLine();

        String numeroIdentificacion;
        boolean numeroIdentificacionValido = false;
        do {
            System.out.println("Digite el número de identificación con el que se desea registrar: ");
            numeroIdentificacion = scanner.nextLine();
            numeroIdentificacionValido = gestionarControladoresBD.validarIdentificacion(numeroIdentificacion);
            if (!numeroIdentificacionValido) {
                System.out.println("Error: Este número de identificación ya está en uso.");
            }
        
        }while (!numeroIdentificacionValido);

        String correoElectronico;
        boolean correoElectronicoValido = false;
        
        do {
           System.out.println("Digite el correo electrónico con el que se desea registrar: ");
           correoElectronico = scanner.nextLine();
           correoElectronicoValido = gestionarControladoresBD.validarCorreo(correoElectronico);
           if (!correoElectronicoValido) {
              System.out.println("Error: Este correo electrónico ya está en uso.");
           }
        } while (!correoElectronicoValido);

        System.out.println("Digite la contraseña con la que se desea registrar: ");
        String contrasena = scanner.nextLine();

   
        if (numeroIdentificacionValido && correoElectronicoValido) {
            gestionarControladoresBD.insertarControladores(nombre, numeroIdentificacion, correoElectronico, contrasena);
            System.out.println("Controlador aéreo registrado correctamente.");
        }
      /*  System.out.println("Digite el nombre con el que se desea registrar: ");
        String nombre = scanner.nextLine();
        
        boolean error = true;
        String numeroIdentificacion = "";
        
        while(error){
            System.out.println("Digite el número de identificación con el que se desea registrar: ");
            numeroIdentificacion = scanner.nextLine();
            
            if(controladoresAereos.isEmpty()){
                error = false;
            } else {
                for(ControladoresAereos i : controladoresAereos){
                    if(i.getNumeroIdentificacion().equals(numeroIdentificacion)){
                        System.out.println("Este número de identificación ya existe.");
                    } else {
                        error = false;
                    }
                }
            }
            
        }
        
        error = true;
        String correoElectronico = "";
        
        while(error){
            System.out.println("Digite el correo electrónico con el que se desea registrar: ");
            correoElectronico = scanner.nextLine();
            
            if(controladoresAereos.isEmpty()){
                error = false;
            } else {
                for(ControladoresAereos i : controladoresAereos){
                    if(i.getCorreoElectronico().equals(correoElectronico)){
                        System.out.println("Este correo electrónico ya está en uso.");
                    } else {
                        error = false;
                    }
                }
            }
        }
        
        System.out.println("Digite la contraseña con la que se desea registrar: ");
        String contrasena = scanner.nextLine();

        controladoresAereos.add(new ControladoresAereos(nombre, numeroIdentificacion, correoElectronico, contrasena));*/
      
    }
    
    private static boolean iniciarSesion(){
        GestionarControladoresBD gestionarControladoresBD = new GestionarControladoresBD();

        boolean error = true;

        while (error) {
           System.out.println("Digite su correo electrónico o número de identificación: ");
           String usuario = scanner.nextLine();
           System.out.println("Digite su contraseña: ");
           String contrasena = scanner.nextLine();  

           if (gestionarControladoresBD.validarControlador(usuario,usuario, contrasena)) {
                System.out.println("¡Bienvenid@!");
                error = false;
            } else {
                System.out.println("Datos ingresados erroneamente. Por favor digite los datos nuevamente.\n");
            }
        }

    return error;
        /*boolean error = true;
        
        while(error){
            System.out.println("Digite su correo electrónico o número de identificación: ");
            String usuario = scanner.nextLine();
            System.out.println("Digite su contraseña: ");
            String contrasena = scanner.nextLine();  
            for(ControladoresAereos i : controladoresAereos){
                if((i.getCorreoElectronico().equals(usuario) || i.getNumeroIdentificacion().equals(usuario)) && i.getContrasena().equals(contrasena)){
                    System.out.println("¡Bienvenid@, " + i.getNombre() + "!");
                    error = false;
                } else {
                    System.out.println("Datos ingresados erroneamente. Por favor digite los datos nuevamente.\n");
                }
            }
        }
        
        return error;*/
    }
}
