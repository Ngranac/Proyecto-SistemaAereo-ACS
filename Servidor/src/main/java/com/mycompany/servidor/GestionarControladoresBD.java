/*+/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidor;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author ngranado
 */
public class GestionarControladoresBD {
    connection conexion = new connection();
    ResultSet resultado = null;
    
    public boolean validarControlador(String bdcorreo, String bdnumeroidentificacion, String bdcontrasenna) {
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM ControladoresAereos WHERE (correo = ? OR numeroidentificacion = ?) AND contrasenna = ?");
            conexion.getConsulta().setString(1, bdcorreo);
            conexion.getConsulta().setString(2, bdnumeroidentificacion);
            conexion.getConsulta().setString(3, bdcontrasenna);
            resultado = conexion.getConsulta().executeQuery();
            return resultado.next();
        } catch (SQLException error) {
           error.printStackTrace();
           return false;
        }
    }
     public boolean validarIdentificacion(String bdnumeroIdentificacion) {
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM ControladoresAereos WHERE numeroidentificacion = ?");
            conexion.getConsulta().setString(1, bdnumeroIdentificacion);
            resultado = conexion.getConsulta().executeQuery();
            return !resultado.next(); 
        } catch (SQLException error) {
            error.printStackTrace();
            return false;
        } finally {
            conexion.cerrarConexion();
        }
    }

    public boolean validarCorreo(String bdcorreoElectronico) {
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM ControladoresAereos WHERE correo = ?");
            conexion.getConsulta().setString(1, bdcorreoElectronico);
            resultado = conexion.getConsulta().executeQuery();
            return !resultado.next(); 
        } catch (SQLException error) {
            error.printStackTrace();
            return false;
        } finally {
            conexion.cerrarConexion();
        }
    }
    
    public void insertarControladores(String bdnombre, String bdnumeroidentificacion ,String bdcorreo, String bdcontrasenna){
        try{
            if (!validarIdentificacion(bdnumeroidentificacion)) {
                System.out.println("Error: Este número de identificación ya está en uso.");
                return;
            }
            if (!validarCorreo(bdcorreo)) {
                System.out.println("Error: Este correo electrónico ya está en uso.");
                return;
            }
            conexion.setConexion();
            conexion.setConsulta("Insert into aviones (nombre , numeroidentificación ,correo , contrasenna ) values (? , ? , ? , ? )");
            conexion.getConsulta().setString(1, bdnombre);
            conexion.getConsulta().setString(2, bdnumeroidentificacion);
            conexion.getConsulta().setString(3, bdcorreo);
            conexion.getConsulta().setString(4, bdcontrasenna);

            
            if(conexion.getConsulta().executeUpdate() > 0){
                System.out.println("Contralador Insertado");
            }
            else{
                System.out.println("Error contralador no Insertado");

            }
            
            conexion.cerrarConexion();
        }
        catch(SQLException error){
            error.printStackTrace();
        }
    }
    
}

/*Querys Tabla ControladoresAereos

CREATE TABLE ControladoresAereos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    numeroidentificacion VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    contrasenna VARCHAR(100) NOT NULL
);


*/
