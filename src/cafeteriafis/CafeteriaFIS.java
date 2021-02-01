package cafeteriafis;

import java.sql.SQLException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author vmpor
 */
public class CafeteriaFIS {
    public static void main(String[] args) throws SQLException {
        InicioSesion inicioSesion=new InicioSesion();
        inicioSesion.setVisible(true);
    }
    
}
