/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ngranado
 */
public class connection {
    Connection conexion = null;
    PreparedStatement consulta = null;
    ResultSet resultado = null;
    
    String url = "jdbc:mysql://localhost:3306/Aeropuerto";
    String username = "root";
    String password = "1234";
    
    public void setConexion(){
        try{
            this.conexion = DriverManager.getConnection(url, username, password);

        }
        catch(SQLException error){
            error.printStackTrace();
        }
        
        
    }
    
    public void setConsulta(String sql){
        try{
             this.consulta = conexion.prepareStatement(sql);
        }
        catch(SQLException error){
            error.printStackTrace();
        }
    }
    
    public PreparedStatement getConsulta(){
        return this.consulta;
    }
    
    public ResultSet getResultado(){
        try{
             return consulta.executeQuery();
        }
        catch(SQLException error){
            error.printStackTrace();
            return null;
        }
    }
    
    public void cerrarConexion(){
        if(resultado != null){
            try{
                 resultado.close();
            }
            catch(SQLException error){
                error.printStackTrace();
                
            }
        }
        if(consulta != null){
            try{
                 consulta.close();
            }
            catch(SQLException error){
                error.printStackTrace();
                
            }
        }
        if(conexion != null){
            try{
                 conexion.close();
            }
            catch(SQLException error){
                error.printStackTrace();
            }
        }
    }
}
