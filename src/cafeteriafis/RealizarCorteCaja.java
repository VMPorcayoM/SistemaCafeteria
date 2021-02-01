package cafeteriafis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vmpor
 */
public class RealizarCorteCaja extends JFrame implements MouseListener{

    private JLabel fondo;
    private GestionCorte gestionCorte;
    private JLabel lblNickname, lblFecha, lblCantidadInicial, lblContado, lblDiferencia, lblRetiroCorte, lblEfectivo;
    private JTextField txtCantidadInicial, txtContado, txtDiferencia, txtRetiroCorte;
    private String fecha;
    private JButton btnCancelar, btnGuardar;
    private Double totalCalculado=0.0;
    
    public RealizarCorteCaja(GestionCorte gestionCorte) {
        ResultSet rs=gestionCorte.manejador.ventasSinCorte();
        try{
            while(rs.next()){
                totalCalculado+=Double.parseDouble(rs.getObject (4).toString());
            }            
        }catch(SQLException e){
            System.out.println("Error "+e.getMessage());
        }
        this.gestionCorte = gestionCorte;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        lblNickname=new JLabel();
        lblNickname.setBounds(50, 20, 350, 20);
        lblNickname.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblNickname.setText("Nickname: "+this.gestionCorte.gestionVentas.usuarioActual);
        add(lblNickname);
        
        lblFecha=new JLabel();
        lblFecha.setBounds(420, 20, 250, 20);
        lblFecha.setFont(new Font("Monospaced", Font.BOLD, 16));
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = new Date();
        fecha = formateador.format(fechaActual);
        lblFecha.setText("Fecha: "+this.fecha);
        add(lblFecha);
        lblCantidadInicial=new JLabel();
        lblCantidadInicial.setBounds(80, 100, 320, 20);
        lblCantidadInicial.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblCantidadInicial.setText("Cantidad Inicial/Fondo:    $");
        add(lblCantidadInicial);
        txtCantidadInicial=new JTextField();
        txtCantidadInicial.setBounds(400, 100,100,20);
        txtCantidadInicial.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtCantidadInicial.getText().length() == 6) {
                            e.consume();
                        }
                        char caracter = e.getKeyChar();
                        // Verificar si la tecla pulsada no es un digito
                        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                            if (caracter == '.') {
                                return;
                            }
                            e.consume();  // ignorar el evento de teclado
                        }
                    }
                });
        add(txtCantidadInicial);
        
        lblContado=new JLabel();
        lblContado.setBounds(80, 200, 300, 20);
        lblContado.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblContado.setText("Contado:                   $");
        add(lblContado);
        
        lblDiferencia=new JLabel();
        lblDiferencia.setBounds(80, 300, 350, 20);
        lblDiferencia.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblDiferencia.setText("Diferencia(Contado-Calculado):$");
        add(lblDiferencia);
        
        txtDiferencia=new JTextField();
        txtDiferencia.setBounds(400,300,100,20);
        txtDiferencia.setEditable(false);
        txtDiferencia.setFont(new Font("Monospaced", Font.BOLD, 16));      
        add(txtDiferencia);
        
        txtContado=new JTextField();
        txtContado.setBounds(400, 200,100,20);
        txtContado.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e){
                        if (txtContado.getText().length() == 6) {
                            e.consume();
                        }
                        char caracter = e.getKeyChar();
                        // Verificar si la tecla pulsada no es un digito
                        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                            if (caracter == '.') {
                                return;
                            }
                            e.consume();  // ignorar el evento de teclado
                        }
                    }
                    public void keyReleased(KeyEvent e) {                                                
                        if(txtContado.getText().length()>0 && txtContado.getText().length()<=6){                            
                            txtDiferencia.setText(String.valueOf(Double.parseDouble(txtContado.getText())-totalCalculado));
                        }
                        else
                            txtDiferencia.setText("");
                    }
                });
        add(txtContado);
        
        lblRetiroCorte=new JLabel();
        lblRetiroCorte.setBounds(760,20,200,20);
        lblRetiroCorte.setFont(new Font("Monospaced", Font.BOLD, 18));
        lblRetiroCorte.setText("Retiro por corte");
        add(lblRetiroCorte);    
        
        lblEfectivo=new JLabel();
        lblEfectivo.setBounds(720,100,150,20);
        lblEfectivo.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblEfectivo.setText("Efectivo:  $");
        add(lblEfectivo);    
        
        txtRetiroCorte=new JTextField();
        txtRetiroCorte.setBounds(880,100,100,20);
        txtRetiroCorte.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtRetiroCorte.getText().length() == 6) {
                            e.consume();
                        }
                        char caracter = e.getKeyChar();
                        // Verificar si la tecla pulsada no es un digito
                        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                            if (caracter == '.') {
                                return;
                            }
                            e.consume();  // ignorar el evento de teclado
                        }
                    }
                });
        add(txtRetiroCorte);
        
        btnCancelar=new JButton();
        btnCancelar.setBounds(400,380,100,25);
        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(this);
        add(btnCancelar);
        
        btnGuardar=new JButton();
        btnGuardar.setBounds(810,380,100,25);
        btnGuardar.setText("Guardar");
        btnGuardar.addMouseListener(this);
        add(btnGuardar);
        
        
        fondo = new JLabel();
        fondo.setBounds(0, 0, screenSize.width / 2, screenSize.height / 2 + 100);
        fondo.setBackground(new Color(121, 133, 181));
        fondo.setOpaque(true);      
        this.add(fondo);
        this.setBounds(0, 0, screenSize.width / 2+350, screenSize.height / 2 + 100);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Realizar Corte de caja");
        this.setResizable(false);
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getComponent()==btnCancelar){
            gestionCorte.cortesEnTabla();
            this.dispose();
        }
        if(e.getComponent()==btnGuardar){
                if(txtCantidadInicial.getText().isEmpty() || txtContado.getText().isEmpty() || txtRetiroCorte.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Campo(s) vacío(s)");
                    return;
                }   
                if(totalCalculado==0){
                    JOptionPane.showMessageDialog(null, "El total calculado es 0");
                    this.dispose();
                    return;
                }
                if(!gestionCorte.manejador.agregarCorte(gestionCorte.manejador.idCuenta(gestionCorte.gestionVentas.usuarioActual), Double.parseDouble(txtContado.getText()), totalCalculado, Double.parseDouble(txtDiferencia.getText()), Double.parseDouble(txtRetiroCorte.getText()))){
                    JOptionPane.showMessageDialog(null, "Error al agregar corte");
                }
                gestionCorte.cortesEnTabla();
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
