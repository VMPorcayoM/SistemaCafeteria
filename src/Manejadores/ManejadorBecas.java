package Manejadores;

import cafeteriafis.Conector;
import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author vmpor
 */
public class ManejadorBecas {

    private Conector cc = new Conector();
    private Connection con = (Connection) cc.conexion();

    public ResultSet consultaBecarios() {
        try {
            Statement stmt = con.createStatement();
            String query = "call consultaBecarios();";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en consultaBecarios(): " + e.getMessage());
        }
        return null;
    }

    public boolean agregarBecario(int noControl, String nombre, String aPaterno, String aMaterno, int semestre, String carrera) {
        try {
            CallableStatement cst = con.prepareCall("{call agregarBecarios(?,?,?,?,?,?)}");
            cst.setInt(1, noControl);
            cst.setString(2, nombre);
            cst.setString(3, aPaterno);
            cst.setString(4, aMaterno);
            cst.setInt(5, semestre);
            cst.setString(6, carrera);
            if (!cst.execute()) {
                return true;
            }
            }catch (SQLException ex) {
            System.out.println("Error en AgregarBecario(): " + ex.getMessage());
        }
        return false;
    }
    
    public boolean modificarBecario(int noControl, String nombre, String aPaterno, String aMaterno, int semestre, String carrera) {
        try {
            CallableStatement cst = con.prepareCall("{call modificarBecario(?,?,?,?,?,?)}");
            cst.setInt(1, noControl);
            cst.setString(2, nombre);
            cst.setString(3, aPaterno);
            cst.setString(4, aMaterno);
            cst.setInt(5, semestre);
            cst.setString(6, carrera);
            if (!cst.execute()) {
                return true;
            }
            }catch (SQLException ex) {
            System.out.println("Error en ModificarBecario(): " + ex.getMessage());
        }
        return false;
    }
    public boolean eliminarBecario(int noControl){
        try {
            CallableStatement cst = con.prepareCall("{call eliminarBecario(?)}");
            cst.setInt(1, noControl);
            if (!cst.execute()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error en EliminarEmpleado(): "+ex.getMessage());
        }
        return false;
    }
    public ResultSet buscarBecarios(String noControl){
        try {
            Statement stmt = con.createStatement();
            String query = "select * from bd_becarios where noControl like '"+noControl+"%';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en buscarBecarios(): " + e.getMessage());
        }
        return null;
    }
    
    }
