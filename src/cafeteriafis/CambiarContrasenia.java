package cafeteriafis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

/**
 *
 * @author vmpor
 */
public class CambiarContrasenia extends JFrame implements MouseListener{
    
    private JLabel fondo;
    private GestionCuentas gestionCuentas;
    private JLabel lblNuevaContra, lblConfirmarContra, lblIconoCoincidencia, lblCoincidencia;
    private JPasswordField txtNuevaContra, txtConfirmarContra;
    private JButton btnGuardarCambios, btnCancelar;
    
    public CambiarContrasenia(GestionCuentas gestionCuentas) {
        
        this.gestionCuentas = gestionCuentas;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        btnGuardarCambios=new JButton();
        btnGuardarCambios.setBounds(110, 210, 150, 25);
        btnGuardarCambios.setText("Guardar cambios");
        btnGuardarCambios.setEnabled(false);
        btnGuardarCambios.addMouseListener(this);
        add(btnGuardarCambios);
        
        lblNuevaContra=new JLabel();
        lblNuevaContra.setBounds(40, 30, 250, 30);
        lblNuevaContra.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblNuevaContra.setText("Nueva contraseña:");
        add(lblNuevaContra);
        
        txtNuevaContra=new JPasswordField();
        txtNuevaContra.setBounds(280, 30, 200, 25);
        txtNuevaContra.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtNuevaContra.getText().length() == 40) {
                            e.consume();
                        }
                    }
                });
        add(txtNuevaContra);
        
        
        lblConfirmarContra=new JLabel();
        lblConfirmarContra.setBounds(40, 110, 250, 30);
        lblConfirmarContra.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblConfirmarContra.setText("Confirmar contraseña:");
        add(lblConfirmarContra);
        
        txtConfirmarContra=new JPasswordField();
        txtConfirmarContra.setBounds(280, 110, 200, 25);
        txtConfirmarContra.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        if (txtConfirmarContra.getText().equals(txtNuevaContra.getText())){
                            btnGuardarCambios.setEnabled(true);
                            lblIconoCoincidencia.setIcon(new ImageIcon("src\\imagenes\\conciden.png"));
                            lblCoincidencia.setText("Coinciden");
                        }else{
                            btnGuardarCambios.setEnabled(false);
                            lblIconoCoincidencia.setIcon(new ImageIcon("src\\imagenes\\nocoinciden.png"));
                            lblCoincidencia.setText("No coinciden");
                        }
                    }
                });
        add(txtConfirmarContra);
        
        btnCancelar=new JButton();
        btnCancelar.setBounds(320, 210, 100, 25);
        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(this);
        add(btnCancelar);
        
        lblIconoCoincidencia=new JLabel();
        lblIconoCoincidencia.setBounds(210,150,50,50);
        add(lblIconoCoincidencia);
        
        lblCoincidencia=new JLabel();
        lblCoincidencia.setBounds(270, 175,80,20);
        add(lblCoincidencia);
        
        fondo = new JLabel();
        fondo.setBounds(0, 0, screenSize.width / 4+200, screenSize.height / 4 + 100);
        fondo.setBackground(new Color(121, 133, 181));
        fondo.setOpaque(true);
        this.add(fondo);
        this.setBounds(0, 0, screenSize.width / 4+200, screenSize.height / 4 + 100);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Cambiar contraseña");
        this.setResizable(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getComponent()==btnCancelar){
            this.dispose();
        }
        if(e.getComponent()==btnGuardarCambios){
            gestionCuentas.manejador.cambiarContrasenia(Integer.parseInt(gestionCuentas.tablaCuentas.getValueAt(gestionCuentas.tablaCuentas.getSelectedRow(), 0).toString()), txtConfirmarContra.getText());
            gestionCuentas.cuentasEnTabla();
            this.dispose();
        }
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
    
}
