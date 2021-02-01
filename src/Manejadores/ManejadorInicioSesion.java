package Manejadores;

import cafeteriafis.Conector;
import cafeteriafis.GestionVentas;
import cafeteriafis.InicioSesion;
import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * @author vmpor
 */
public class ManejadorInicioSesion {

    // Declaramos los datos de conexion a la bd
    Conector cc = new Conector();
    private Connection con=(Connection) cc.conexion();

    public boolean iniciar(String nombre, String contraseña) throws SQLException {
        Statement stmt = con.createStatement();
        try {
            String query ="SELECT * FROM bd_cuentas WHERE nickname='" +nombre+ "' AND contrasena='" + contraseña+"';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.first()) { // si es valido el primer reg. hay una fila, tons el usuario y su pw existen
                return true;
            }
            stmt.close();
            return false;
        } catch (Exception e) {
            System.out.println("Error "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión"); //usuario validado incorrectamente
        }
        return false;
    }
}
