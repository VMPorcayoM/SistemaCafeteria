package Manejadores;

import cafeteriafis.Conector;
import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author vmpor
 */
public class ManejadorCuentas {
    private Conector cc = new Conector();
    private Connection con = (Connection) cc.conexion();
    public ResultSet consultaCuentas() {   
        try {
            Statement stmt = con.createStatement();
            String query = "call consultaCuentas();";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en Cuentas consultaCuentas(): " + e.getMessage());
        }
        return null;
    }
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
    public boolean agregarCuenta(String nombreEmp, String nickname, String contra) {
        try {
            CallableStatement cst = con.prepareCall("{call agregarCuenta(?,?,?)}");
            Statement stmt = con.createStatement();
            String query = "select idEmpleado from bd_empleados where nombre='"+nombreEmp+"';";
            ResultSet rs = stmt.executeQuery(query);
            rs.first();
            String idEmp=rs.getString(1);
            cst.setString(1, idEmp);
            cst.setString(2, nickname);
            cst.setString(3, contra);
            if (!cst.execute()) {
                return true;
            }
        }catch (SQLException ex) {
            System.out.println("Error en AgregarCuenta(): " + ex.getMessage());
        }
        return false;
    }
    public void cambiarContrasenia(int id, String nuevaContra){
        try {
            Statement stmt = con.createStatement();
            String query = "update bd_cuentas set contrasena='"+nuevaContra+"' where idCuenta="+id+";";
            stmt.executeUpdate(query);
            stmt.close();
        }catch (SQLException ex) {
            System.out.println("Error en CambiarContrasenia(): " + ex.getMessage());
        }
    }
    public ResultSet buscarCuenta(String nickname){
        try {
            Statement stmt = con.createStatement();
            String query = "select * from bd_cuentas where nickname like '"+nickname+"%';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en buscarCuenta(): " + e.getMessage());
        }
        return null;
    }
}
