package Manejadores;

import cafeteriafis.Conector;
import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * @author vmpor
 */
public class ManejadorCorte {
    private Conector cc = new Conector();
    private Connection con = (Connection) cc.conexion();
    
    public ResultSet consultaCortes(){
        try {
            Statement stmt = con.createStatement();
            String query = "call consultaCortes();";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en consultaCortes(): " + e.getMessage());
        }
        return null;
    }
    public boolean agregarCorte(int cuenta, double contado, double calculado, double diferencia, double retiro){
        try {
            java.util.Date objDate = new java.util.Date();
            String strDateFormatanio = "yyyy";
            SimpleDateFormat objSDFanio = new SimpleDateFormat(strDateFormatanio); 
            int anio = Integer.parseInt(objSDFanio.format(objDate));
            String strDateFormatmes= "MM";
            SimpleDateFormat objSDFmes = new SimpleDateFormat(strDateFormatmes); 
            int mes = Integer.parseInt(objSDFmes.format(objDate));
            String strDateFormatdia = "dd";
            SimpleDateFormat objSDFdia = new SimpleDateFormat(strDateFormatdia); 
            int dia = Integer.parseInt(objSDFdia.format(objDate));
            
            CallableStatement cst = con.prepareCall("{call agregarCorte(?,?,?,?,?,?)}");
            cst.setInt(1, cuenta);
            cst.setDate(2, new Date(anio-1900,mes-1,dia));
            cst.setDouble(3, contado);
            cst.setDouble(4, calculado);
            cst.setDouble(5, diferencia);
            cst.setDouble(6, retiro);
            if (!cst.execute()) {
                return true;
            }
            
            }catch (SQLException ex) {
            System.out.println("Error en agregarCorte(): " + ex.getMessage());
        }
        return false;
    }
    
    public int idCuenta(String nickname){
        try {
            Statement stmt = con.createStatement();
            String query = "select idCuenta from bd_cuentas where nickname='"+nickname+"';";    
            ResultSet rs = stmt.executeQuery(query);
            if (rs.first()) {
                return Integer.parseInt(rs.getObject(1).toString());
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error en idCuenta(): "+ex.getMessage());
        }
        return -1;
    }
    public int primerIdVentaSinCorte(){
        try {
            Statement stmt = con.createStatement();
            String query = "select min(idVenta) from bd_ventas where idCorte is null;";    
            ResultSet rs = stmt.executeQuery(query);
            if (rs.first()) {
                return Integer.parseInt(rs.getObject(1).toString());
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error en primerIdVentaSinCorte(): "+ex.getMessage());
        }
        return -1;
    }
    public ResultSet ventasSinCorte(){
        try {
            Statement stmt = con.createStatement();
            String query = "select * from bd_ventas where idCorte is null;";    
            ResultSet rs = stmt.executeQuery(query);
            if (rs!=null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error en ventasSinCorte(): "+ex.getMessage());
        }
        return null;
    }
    public boolean eliminarCorte(int id){
        try {
            CallableStatement cst = con.prepareCall("{call eliminarCorte(?)}");
            cst.setInt(1, id);
            if (!cst.execute()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error en EliminarCorte(): "+ex.getMessage());
        }
        return false;
    }
    public ResultSet buscarCortes(int anio, int mes, int dia){
        try {
            Statement stmt = con.createStatement();
            String query = "select * from bd_cortes where fecha='"+anio+"-"+mes+"-"+dia+"';";    
            ResultSet rs = stmt.executeQuery(query);
            if (rs!=null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error en idCuenta(): "+ex.getMessage());
        }
        return null;
    }
}
