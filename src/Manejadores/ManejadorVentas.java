package Manejadores;

import cafeteriafis.Conector;
import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vmpor
 */
public class ManejadorVentas {

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
            System.out.println("Error en Ventas consultaProductos(): " + e.getMessage());
        }
        return null;
    }

    public String costoProducto(String nombre) {

        try {
            Statement stmt = con.createStatement();
            String query = "select costo from bd_productos where nombre='" + nombre + "';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                if(rs.next())
                    return rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en Ventas consultaProductos(): " + e.getMessage());
        }
        return null;
    }
    
    public ResultSet registroSeleccionado(String seleccionado){
        try {
            Statement stmt = con.createStatement();
            String query = "select nombre, costo from bd_productos where nombre='" + seleccionado + "';";
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
    public ResultSet cargoEmpleado(String empleado){
        try {
            Statement stmt = con.createStatement();
            String query = "select e.cargo from bd_empleados e, bd_cuentas c where c.idEmpleado=e.idEmpleado and c.nickname='" + empleado + "';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en Ventas cargoEmpelado(): " + e.getMessage());
        }
        return null;
    }
    public ResultSet consultaUltimoMenu(){
        try {
            Statement stmt = con.createStatement();
            String query = "select menu from bd_menus;";
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.first()) {
                return null;
            }
            Date objDate = new Date();
            String strDateFormatanio = "yyyy-MM-dd";
            SimpleDateFormat objSDFanio = new SimpleDateFormat(strDateFormatanio); 
            String fechaAct = objSDFanio.format(objDate);
            rs.last();
            query = "select p.idProducto, p.nombre, p.costo from bd_productos p, bd_menus m where p.idProducto=m.idProd and m.menu="+rs.getObject(1).toString()+" AND m.fecha='"+fechaAct+"';";
            rs = stmt.executeQuery(query);
            if(rs!= null)
                return rs;
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en Ventas consultaUltimoMenu(): " + e.getMessage());
        }
        return null;
    }
    public boolean agregarVenta(String cuenta, double total){
        try {
            Statement stmt = con.createStatement();
            String query = "select idCuenta from bd_cuentas where nickname='"+cuenta+"';";
            ResultSet rs = stmt.executeQuery(query);
            rs.first();
            int id=Integer.parseInt(rs.getObject(1).toString());
            
            Date objDate = new Date();
            String strDateFormatanio = "yyyy";
            SimpleDateFormat objSDFanio = new SimpleDateFormat(strDateFormatanio); 
            int anio = Integer.parseInt(objSDFanio.format(objDate));
            String strDateFormatmes= "MM";
            SimpleDateFormat objSDFmes = new SimpleDateFormat(strDateFormatmes); 
            int mes = Integer.parseInt(objSDFmes.format(objDate));
            String strDateFormatdia = "dd";
            SimpleDateFormat objSDFdia = new SimpleDateFormat(strDateFormatdia); 
            int dia = Integer.parseInt(objSDFdia.format(objDate));
            CallableStatement cst = con.prepareCall("{call agregarVenta(?,?,?)}");
            
            cst.setInt(1, id);
            cst.setDate(2, new java.sql.Date(anio-1900, mes-1, dia));            
            cst.setDouble(3, total);
            
            if (!cst.execute()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error en AgregarVenta(): " + ex.getMessage());
        }
        return false;
    }
    public void agregarDetalleVenta(int idVenta, int idProducto, int cantidadProd){
         try {
            Statement stmt = con.createStatement();
            String query = "insert into bd_detallesVentas values(null,"+idVenta+","+idProducto+","+cantidadProd+");";
            stmt.executeUpdate(query);
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en Ventas agregarDetalleVenta(): " + e.getMessage());
        }
    }
    public int idUltimaVenta(){
        try {
            Statement stmt = con.createStatement();
            String query = "select idVenta from bd_ventas";
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            if(rs!= null)
                return Integer.parseInt(rs.getObject(1).toString());
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en Ventas consultaUltimoMenu(): " + e.getMessage());
        }
        return 1;
    }
    
    public int idProducto(String seleccionado){
        try {
            Statement stmt = con.createStatement();
            String query = "select idProducto from bd_productos where nombre='" + seleccionado + "';";
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            if (rs != null) {
                return Integer.parseInt(rs.getObject(1).toString());
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error en Ventas idProducto(): " + e.getMessage());
        }
        return 0;
    }
    public boolean isAlimento(String producto){
        try {
            Statement stmt = con.createStatement();
            String query = "select tipo from bd_productos where nombre='" + producto + "';";
            ResultSet rs = stmt.executeQuery(query);
            rs.first();
            if (rs == null) {
                return false;
            }
            if(rs.getObject(1).toString().equals("2")){
                return true;
            }else
                return false;            
        } catch (Exception e) {
            System.out.println("Error en Ventas idProducto(): " + e.getMessage());
        }
        return false;
    }
}
