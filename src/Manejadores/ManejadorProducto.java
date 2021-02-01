package Manejadores;

import cafeteriafis.Conector;
import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vmpor
 */
public class ManejadorProducto {

    private Conector cc = new Conector();
    private Connection con = (Connection) cc.conexion();

    public ResultSet consultaProductos() {
        try {
            Statement stmt = con.createStatement();
            String query = "call procProductos();";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en consultaProductos(): " + e.getMessage());
        }
        return null;
    }
    public ResultSet consultaTodosProductos() {
        try {
            Statement stmt = con.createStatement();
            String query = "select * from bd_productos;";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en consultaTodosProductos(): " + e.getMessage());
        }
        return null;
    }
    
    public ResultSet consultaAlimentos() {
        try {
            Statement stmt = con.createStatement();
            String query = "select idProducto, nombre from bd_productos where tipo=2;";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en consultaAlimentos(): " + e.getMessage());
        }
        return null;
    }

    public void cerrarConexion() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error cerrarConexion(): " + ex.getMessage());
        }
    }

    public boolean agregarProducto(String nombre, int costo, int tipo) {
        try {
            CallableStatement cst = con.prepareCall("{call agregarProducto(?,?,?)}");
            cst.setString(1, nombre);
            cst.setInt(2, costo);
            cst.setInt(3, tipo);
            if (!cst.execute()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error en AgregarProducto(): " + ex.getMessage());
        }
        return false;
    }

    public ResultSet alimentoSeleccionado(String seleccionado) {
        try {
            Statement stmt = con.createStatement();
            String query = "select idProducto, nombre from bd_productos where nombre='" + seleccionado + "' and tipo=2;";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en Ventas consultaProductos(): " + e.getMessage());
        }
        return null;
    }

    public boolean agregarAMenu(int numMenu, int idProd, int anio, int mes, int dia) {
        try {

            CallableStatement cst = con.prepareCall("{call agregarAMenu(?,?,?)}");
            cst.setInt(1, numMenu);
            cst.setInt(2, idProd);            
            cst.setDate(3, new Date(anio-1900, mes-1, dia));
            if (!cst.execute()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error en AgregarAMenu(): " + ex.getMessage());
        }
        return false;
    }

    public ResultSet numMenu() {
        try {
            Statement stmt = con.createStatement();
            String query = "select menu from bd_menus;";
            ResultSet rs = stmt.executeQuery(query);
            if(rs!=null){
                return rs;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean modificarProducto(int id, String nombre, int costo, int tipo){
        try {
            CallableStatement cst = con.prepareCall("{call modificarProducto(?,?,?,?)}");
            cst.setInt(1, id);
            cst.setString(2, nombre);
            cst.setInt(3, costo);
            cst.setInt(4, tipo);
            
            if (!cst.execute()) {
                return true;
            }
        }catch (SQLException ex) {
            System.out.println("Error en ModificarProducto(): " + ex.getMessage());
        }
        return false;
    }
    public boolean eliminarProducto(int id){
        try {
            CallableStatement cst = con.prepareCall("{call eliminarProducto(?)}");
            cst.setInt(1, id);
            if (!cst.execute()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error en EliminarProducto(): "+ex.getMessage());
        }
        return false;
    }
    public ResultSet buscarProducto(String nombre){
        try {
            Statement stmt = con.createStatement();
            String query = "select * from bd_productos where nombre like '"+nombre+"%';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en buscarProducto(): " + e.getMessage());
        }
        return null;
    }
}
