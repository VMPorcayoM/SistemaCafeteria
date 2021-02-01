package cafeteriafis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vmpor
 */
public class Conector {

    static Connection connect = null;
    private static String user="root";
    private static String pass="123";
    public static Connection conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //?autoReconnect=true&useSSL=false
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafeteria?autoReconnect=true&useSSL=false", user,pass);
//            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            System.out.println("Error en conexión: "+e.getMessage());
        }
        return connect;
    }
}
