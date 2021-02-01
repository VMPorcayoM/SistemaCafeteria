package Manejadores;

import cafeteriafis.Conector;
import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author vmpor
 */
public class ManejadorEmpleados {
    private Conector cc = new Conector();
    private Connection con = (Connection) cc.conexion();
    
    public ResultSet consultaEmpleados() {       
        try {
            Statement stmt = con.createStatement();
            String query = "call consultaEmpleados();";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en consultaEmpleados(): " + e.getMessage());
        }
        return null;
    }
    public boolean agregarEmpleado(String nombre, String aPaterno, String aMaterno, String profesionNivel, double honorarios, String tel, String direc, int diaFNac, int mesFNac, int anioFNac, String cargo) {
        try {
            CallableStatement cst = con.prepareCall("{call agregarEmpleado(?,?,?,?,?,?,?,?,?)}");
            cst.setString(1, nombre);
            cst.setString(2, aPaterno);
            cst.setString(3, aMaterno);
            cst.setString(4, profesionNivel);
            cst.setDouble(5, honorarios);
            cst.setString(6, tel);
            cst.setString(7, direc);
            cst.setDate(8, new Date(anioFNac-1900, mesFNac, diaFNac));
            cst.setString(9, cargo);
            if (!cst.execute()) {
                return true;
            }
            }catch (SQLException ex) {
            System.out.println("Error en AgregarEmpleado(): " + ex.getMessage());
        }
        return false;
    }
    public boolean modificarEmpleado(int id,String nombre, String aPaterno, String aMaterno, String profesionNivel, double honorarios, String tel, String direc, int diaFNac, int mesFNac, int anioFNac, String cargo) {
        try {
            CallableStatement cst = con.prepareCall("{call modificarEmpleado(?,?,?,?,?,?,?,?,?,?)}");
            cst.setInt(1, id);
            cst.setString(2, nombre);
            cst.setString(3, aPaterno);
            cst.setString(4, aMaterno);
            cst.setString(5, profesionNivel);
            cst.setDouble(6, honorarios);
            cst.setString(7, tel);
            cst.setString(8, direc);
            cst.setDate(9, new Date(anioFNac-1900, mesFNac, diaFNac));
            cst.setString(10, cargo);
            if (!cst.execute()) {
                return true;
            }
            }catch (SQLException ex) {
            System.out.println("Error en ModificarEmpleado(): " + ex.getMessage());
        }
        return false;
    }
    public boolean eliminarEmpleado(int id){
        try {
            CallableStatement cst = con.prepareCall("{call eliminarEmpleado(?)}");
            cst.setInt(1, id);
            if (!cst.execute()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error en EliminarEmpleado(): "+ex.getMessage());
        }
        return false;
    }
    public boolean empleadoDentro(String cuentaDentro, String nombreUsu){
        try {
            Statement stmt = con.createStatement();
            String query = "select e.idEmpleado from bd_empleados e, bd_cuentas c where c.idEmpleado=e.idEmpleado and c.nickname='"+cuentaDentro+"' and e.nombre='"+nombreUsu+"';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.first()) {
                return true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error en EliminarEmpleado(): "+ex.getMessage());
        }
        return false;
    }
    public ResultSet buscarEmpleado(String nombre){
        try {
            Statement stmt = con.createStatement();
            String query = "select * from bd_empleados where nombre like '"+nombre+"%';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en buscarEmpleados(): " + e.getMessage());
        }
        return null;
    }
    
}
