package cafeteriafis;

import Manejadores.ManejadorInicioSesion;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author vmpor
 */
public class InicioSesion extends JFrame implements MouseListener, KeyListener {
    private JButton btnCancelar, btnAceptar;
    private JTextField txtnickname;
    private JPasswordField txtpassword;

    public InicioSesion() throws SQLException {               
        JLabel lblNickname = new JLabel();
        lblNickname.setBounds(80, 200, 100, 20);
        lblNickname.setText("NICKNAME");
        this.add(lblNickname);
        JLabel lblPassword = new JLabel();
        lblPassword.setBounds(80, 235, 100, 20);
        lblPassword.setText("PASSWORD");
        this.add(lblPassword);
        JLabel imagenInicioSesion = new JLabel();
        imagenInicioSesion.setBounds(225, 50, 100, 100);
        imagenInicioSesion.setIcon(new ImageIcon("src\\imagenes\\inicioSesion.png"));
        this.add(imagenInicioSesion);
        txtnickname = new JTextField();
        txtnickname.setBounds(175, 200, 200, 20);
        txtnickname.addKeyListener(this);
        this.add(txtnickname);
        txtpassword = new JPasswordField();
        txtpassword.setBounds(175, 235, 200, 20);
        txtpassword.addKeyListener(this);
        this.add(txtpassword);
        btnCancelar = new JButton();
        btnCancelar.addMouseListener(this);
        btnCancelar.setBounds(150, 285, 100, 30);
        btnCancelar.setText("Cancelar");
        this.add(btnCancelar);
        btnAceptar = new JButton();
        btnAceptar.addMouseListener(this);
        btnAceptar.setBounds(300, 285, 100, 30);
        btnAceptar.setText("Aceptar");
        btnAceptar.addKeyListener(this);
        this.add(btnAceptar);
        JLabel fondo = new JLabel();
        fondo.setBounds(0, 0, 550, 450);
        fondo.setBackground(new Color(121, 133, 181));
        fondo.setOpaque(true);
        this.addKeyListener(this);
        this.add(fondo);
        this.setBounds(0, 0, 550, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Inicio Sesión");
        this.setResizable(false);
    }

    private void entrar() throws SQLException {
        ManejadorInicioSesion mis=new ManejadorInicioSesion();
        if(txtnickname.getText().equals("") && txtpassword.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Campos vacíos");
            return;
        }
        if(mis.iniciar(txtnickname.getText(), txtpassword.getText())){
            System.out.println("Entra");
            GestionVentas c = new GestionVentas(this, txtnickname.getText());
            txtnickname.setText("");
            txtpassword.setText("");
            c.setVisible(true);
            this.setVisible(false);
        }else{            
            System.out.println("No entra");
            JOptionPane.showMessageDialog(null, "Usuario y/o contraseña inconrrectos"); //usuario validado incorrectamente
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent() == btnCancelar) {
            System.exit(0);
        }
        if (e.getComponent() == btnAceptar) {
            try {
                entrar();
            } catch (SQLException ex) {
                Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                entrar();
            } catch (SQLException ex) {
                Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
