/*
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
public class GestionarAvionesBD {
    connection conexion = new connection();
    ResultSet resultado = null;
    
    public void insertarAviones(String bdOrigen, String bddestino ,String bdgasolina, String bdaltitud, String bdcantidadp , String bdcondicion , String bdfecha , String bdhora){
        try{
            conexion.setConexion();
            conexion.setConsulta("Insert into aviones (origen , destino ,gasolina , altitud , cantidadPasajeros , condicion , fecha , hora ) values (? , ? , ? , ? , ? ,? ,? ,?)");
            conexion.getConsulta().setString(1, bdOrigen);
            conexion.getConsulta().setString(2, bddestino);
            conexion.getConsulta().setString(3, bdgasolina);
            conexion.getConsulta().setString(4, bdaltitud);
            conexion.getConsulta().setString(5, bdcantidadp);
            conexion.getConsulta().setString(6, bdcondicion);
            conexion.getConsulta().setString(7, bdfecha);
            conexion.getConsulta().setString(8, bdhora);
            
            if(conexion.getConsulta().executeUpdate() > 0){
                System.out.println("Avión Insertado");
            }
            else{
                System.out.println("Error avión no Insertado");

            }
            
            conexion.cerrarConexion();
        }
        catch(SQLException error){
            error.printStackTrace();
        }
    }
    
    
    
}
/*Query para crear tabla Aviones
CREATE TABLE Aviones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    origen VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    gasolina VARCHAR(100) NOT NULL,
    altitud  VARCHAR(100) NOT NULL,
    cantidadPasajeros  VARCHAR(100) NOT NULL,
    condicion  VARCHAR(100) NOT NULL,
    fecha  VARCHAR(100) NOT NULL,
    hora  VARCHAR(100) NOT NULL,

);

*/