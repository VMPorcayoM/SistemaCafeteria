package cafeteriafis;

import Manejadores.ManejadorCuentas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * @author vmpor
 */
public class GestionCuentas extends JFrame implements ActionListener, KeyListener, MouseListener{
    private  Agregar agregar;
    private JLabel fondo;
    public GestionVentas gestionVentas;
    private static JMenu m_opciones;
    private static JMenuBar mb_barra;
    private static JMenuItem mnSalir,mnGestionVentas,mnGestionEmpleados, mnGestionProductos, mnGestionCuentas, mnGestionCorte, mnGestionBeca;
    private JButton btnAnadir, btnCambiarContra, btnCancelar;
    private JLabel lblNickname, iconoCafe, lblTitulo;
    private JTextField txtNickname;
    private JButton btnBuscar;
    static String[] datosCuenta= {"ID","Empleado","Nickname","Contraseña"};
    Object[][] dataCuentas=new Object[0][0];
    private ModeloTablas modeloCuentas = new ModeloTablas(dataCuentas, datosCuenta);  
    public JTable tablaCuentas;
    public ManejadorCuentas manejador;
    private CambiarContrasenia cambiarContra;
    public GestionCuentas(GestionVentas gestionVentas) {
        this.addKeyListener(this);
        this.gestionVentas=gestionVentas;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        lblTitulo=new JLabel();
        lblTitulo.setBounds(10,-5,300,50);
        lblTitulo.setText("Gestión de cuentas");
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 26));
        add(lblTitulo);
        manejador=new ManejadorCuentas();        
        btnAnadir=new JButton();
        btnAnadir.setText("Añadir");
        btnAnadir.setBounds(100, 250, 150,30);       
        btnAnadir.addMouseListener(this);
        this.add(btnAnadir);   
        btnCambiarContra=new JButton();
        btnCambiarContra.setText("Cambiar contraseña");
        btnCambiarContra.setBounds(100, 330, 150,30);       
        btnCambiarContra.addMouseListener(this);
        this.add(btnCambiarContra);   
        btnCancelar=new JButton();
        btnCancelar.setText("Cancelar");
        btnCancelar.setBounds(100, 410, 150,30);       
        btnCancelar.addMouseListener(this);
        this.add(btnCancelar);   
        lblNickname=new JLabel("Nickname: ");
        lblNickname.setBounds(screenSize.width/2-120, 80,200,20);
        lblNickname.setFont(new Font("Monospaced", Font.BOLD, 20));        
        this.add(lblNickname);
        txtNickname=new JTextField();
        txtNickname.setBounds(screenSize.width/2, 80, 350,25);
        this.add(txtNickname);
        btnBuscar=new JButton();
        btnBuscar.setIcon(new ImageIcon("src\\imagenes\\lupa.png"));
        btnBuscar.setBounds(screenSize.width/2+380,65,50,50);
        btnBuscar.addMouseListener(this);
        this.add(btnBuscar);
        tablaCuentas=new JTable(modeloCuentas);
        JScrollPane scrollCuentas=new JScrollPane(tablaCuentas);
        scrollCuentas.setBounds(screenSize.width/2-250, 150, 800, 500);
        tablaCuentas.getColumn("ID").setResizable(false);       
        tablaCuentas.getColumn("Empleado").setResizable(false);          
        tablaCuentas.getColumn("ID").setMinWidth(80);
        tablaCuentas.getColumn("Empleado").setMinWidth(100);
        tablaCuentas.getColumn("ID").setMaxWidth(80);
        tablaCuentas.getColumn("Empleado").setMaxWidth(100);
        
        tablaCuentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.add(scrollCuentas);
        
        m_opciones = new JMenu("   Gestión de Cuentas   ");
        mnSalir = new JMenuItem("   Cerrar sesión     ");         
        mnSalir.addActionListener(this);
        m_opciones.add(mnSalir);
        
        mnGestionVentas = new JMenuItem("   Gestión de Ventas   ");
        mnGestionVentas.addActionListener(this);
        m_opciones.add(mnGestionVentas);
        
        mnGestionEmpleados = new JMenuItem("   Gestión de Empleados   ");
        mnGestionEmpleados.addActionListener(this);
        m_opciones.add(mnGestionEmpleados);
                
        mnGestionProductos = new JMenuItem("   Gestión de Productos   ");
        mnGestionProductos.addActionListener(this);
        m_opciones.add(mnGestionProductos);
        
        mnGestionCuentas = new JMenuItem("   Gestión de Cuentas   ");
        mnGestionCuentas.setEnabled(false);
        m_opciones.add(mnGestionCuentas);
        
        mnGestionCorte = new JMenuItem("   Gestión de Corte de caja   ");
        mnGestionCorte.addActionListener(this);
        m_opciones.add(mnGestionCorte);
        
        mnGestionBeca = new JMenuItem("   Gestión de Beca   ");
        mnGestionBeca.addActionListener(this);
        m_opciones.add(mnGestionBeca);
                        
        mb_barra = new JMenuBar();
        mb_barra.add(m_opciones);
        this.setJMenuBar(mb_barra);
        iconoCafe=new JLabel();
        iconoCafe.setBounds(5, screenSize.height-150, 100, 100);
        iconoCafe.setIcon(new ImageIcon("src\\imagenes\\cafe.png"));
        this.add(iconoCafe);
        
        fondo = new JLabel();
        fondo.setBounds(0, 0, screenSize.width, screenSize.height);
        fondo.setBackground(new Color(121, 133, 181));
        fondo.setOpaque(true);
        this.add(fondo);
        this.setUndecorated(true);
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Gestión de cuentas");
        this.setResizable(false);
        cuentasEnTabla();
    }
    
    public void cuentasEnTabla(){
        DefaultTableModel tb = (DefaultTableModel) tablaCuentas.getModel();
        int a = tablaCuentas.getRowCount()-1;
        for (int i = a; i >= 0; i--) {          
            tb.removeRow(tb.getRowCount()-1);
        }
        try {
            if (manejador.consultaCuentas()!= null) {
                ResultSet rs = manejador.consultaCuentas();
                while (rs.next()) {
                    Object[] fila = new Object[4]; 
                    for (int i = 0; i < 4; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    modeloCuentas.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en productosEntabla(): "+ex.getLocalizedMessage());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==mnSalir){
            gestionVentas.inicio.setVisible(true);
            this.dispose();
            gestionVentas.dispose();
        }
        if(e.getSource()==mnGestionVentas){
            gestionVentas.setVisible(true);
            this.setVisible(false);
        }
        if(e.getSource()==mnGestionEmpleados){
            gestionVentas.gestionEmpleados.setVisible(true);
            this.setVisible(false);
        }
        if(e.getSource()==mnGestionProductos){
            gestionVentas.gestionProductos.setVisible(true);
            this.setVisible(false);
        }
        if(e.getSource()==mnGestionCorte){
            gestionVentas.gestionCorte.setVisible(true);
            this.setVisible(false);            
        }
        if(e.getSource()==mnGestionBeca){
            gestionVentas.gestionBeca.setVisible(true);
            this.setVisible(false);                        
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)           
            this.setExtendedState(ICONIFIED);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getComponent()==btnCancelar){
            tablaCuentas.clearSelection();
            txtNickname.setText("");
            cuentasEnTabla();
        }
        if(e.getComponent()==btnAnadir){
            agregar=new Agregar(2, this);
            agregar.setVisible(true); 
        }
        if(e.getComponent()==btnCambiarContra){
             if(tablaCuentas.getSelectedRow()==-1){
                JOptionPane.showMessageDialog(null, "Registro no seleccionado");
                return;
            }
            cambiarContra=new CambiarContrasenia(this);
            cambiarContra.setVisible(true);
        }
        if(e.getComponent()==btnBuscar){
            if (txtNickname.getText().isEmpty()) {
                return;
            }
            try {
                if (manejador.buscarCuenta(txtNickname.getText()) != null) {
                    DefaultTableModel tb = (DefaultTableModel) tablaCuentas.getModel();
                    int a = tablaCuentas.getRowCount() - 1;
                    for (int i = a; i >= 0; i--) {
                        tb.removeRow(tb.getRowCount() - 1);
                    }

                    ResultSet rs = manejador.buscarCuenta(txtNickname.getText());
                    while (rs.next()) {
                        Object[] fila = new Object[4]; 
                        for (int i = 0; i < 4; i++) {
                            fila[i] = rs.getObject(i + 1);
                        }
                        modeloCuentas.addRow(fila);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error en GestionCuenta buscarCuenta: " + ex.getLocalizedMessage());
            }
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
