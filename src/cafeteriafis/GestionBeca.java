package cafeteriafis;

import Manejadores.ManejadorBecas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
public class GestionBeca extends JFrame implements ActionListener, KeyListener, MouseListener{
    private JLabel fondo, iconoCafe, lblTitulo;
    public GestionVentas gestionVentas;
    private static JMenu m_opciones;
    private static JMenuBar mb_barra;
    private static JMenuItem mnSalir,mnGestionVentas,mnGestionEmpleados, mnGestionProductos, mnGestionCuentas, mnGestionCorte, mnGestionBeca;
    private JLabel lblNoControl;
    private JTextField txtNoControl;
    private JButton btnBuscar, btnAgregar, btnModificar, btnEliminar, btnCancelar;
    static String[] datosBeca= {"No. Control","Nombre","Apellido Paterno","Apellido Materno", "Semestre", "Carrera"};
    Object[][] dataBecas=new Object[0][0];
    private ModeloTablas modeloBecas = new ModeloTablas(dataBecas, datosBeca);  
    public JTable tablaBecas;
    public ManejadorBecas manejador=new ManejadorBecas();
    private Modificar modificar;
    public GestionBeca(GestionVentas gestionVentas) {
        this.addKeyListener(this);
        this.gestionVentas=gestionVentas;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        lblTitulo=new JLabel();
        lblTitulo.setBounds(10,-5,300,50);
        lblTitulo.setText("Gestión de beca");
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 26));
        add(lblTitulo);
        tablaBecas=new JTable(modeloBecas);
        tablaBecas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollBecas=new JScrollPane(tablaBecas);
        scrollBecas.setBounds(screenSize.width/2-350, 150, 900, 500);
        this.add(scrollBecas);
        lblNoControl=new JLabel("Número de control:");
        lblNoControl.setBounds(screenSize.width/2-350, 50,300,20);
        lblNoControl.setFont(new Font("Monospaced", Font.BOLD, 20));        
        this.add(lblNoControl);
        txtNoControl=new JTextField();
        txtNoControl.setBounds(screenSize.width/2-100, 50, 350,20);
        txtNoControl.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtNoControl.getText().length() == 8) {
                            e.consume();
                        }
                        char caracter = e.getKeyChar();
                        // Verificar si la tecla pulsada no es un digito
                        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                            e.consume();  // ignorar el evento de teclado
                        }
                    }
                });
        this.add(txtNoControl);
        btnBuscar=new JButton();
        btnBuscar.setIcon(new ImageIcon("src\\imagenes\\lupa.png"));
        btnBuscar.setBounds(screenSize.width/2+300,35,50,50);        
        btnBuscar.addMouseListener(this);
        this.add(btnBuscar);
        btnAgregar=new JButton();
        btnAgregar.setText("Agregar");
        btnAgregar.setBounds(100, 150, 150,30);       
        btnAgregar.addMouseListener(this);
        this.add(btnAgregar);   
        btnModificar=new JButton();
        btnModificar.setText("Modificar");
        btnModificar.setBounds(100, 280, 150,30);       
        btnModificar.addMouseListener(this);
        this.add(btnModificar);   
        btnEliminar=new JButton();
        btnEliminar.setText("Eliminar");
        btnEliminar.setBounds(100, 410, 150,30);       
        btnEliminar.addMouseListener(this);
        this.add(btnEliminar);
        btnCancelar=new JButton();
        btnCancelar.setBounds(100, 540, 150, 30);
        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(this);
        add(btnCancelar);
        
        m_opciones = new JMenu("   Gestión de Beca   ");
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
        mnGestionCuentas.addActionListener(this);
        m_opciones.add(mnGestionCuentas);
        
        mnGestionCorte = new JMenuItem("   Gestión de Corte de caja   ");
        mnGestionCorte.addActionListener(this);
        m_opciones.add(mnGestionCorte);
        
        mnGestionBeca = new JMenuItem("   Gestión de Beca   ");
        mnGestionBeca.setEnabled(false);
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
        this.setTitle("Gestión de beca");
        this.setResizable(false);
        becariosEnTabla();
    }
    
    public void becariosEnTabla(){
        DefaultTableModel tb = (DefaultTableModel) tablaBecas.getModel();
        int a = tablaBecas.getRowCount()-1;
        for (int i = a; i >= 0; i--) {          
            tb.removeRow(tb.getRowCount()-1);
        }
        try {
            if (manejador.consultaBecarios()!= null) {
                ResultSet rs = manejador.consultaBecarios();
                while (rs.next()) {
                    Object[] fila = new Object[6]; 
                    for (int i = 0; i < 6; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    modeloBecas.addRow(fila);
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
        if(e.getSource()==mnGestionCuentas){
            gestionVentas.gestionCuentas.setVisible(true);
            this.setVisible(false);            
        }
        if(e.getSource()==mnGestionCorte){
            gestionVentas.gestionCorte.setVisible(true);
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
        if(e.getComponent()==btnAgregar){
            Agregar agregar=new Agregar(3, this);
            agregar.setVisible(true);
        }
        if(e.getComponent()==btnModificar){
            if(tablaBecas.getSelectedRow()==-1){
                JOptionPane.showMessageDialog(null, "Registro no seleccionado");
                return;
            }
            modificar=new Modificar(3,this);
            modificar.setVisible(true);
        }
        if(e.getComponent()==btnEliminar){
            if(tablaBecas.getSelectedRow()==-1){
                JOptionPane.showMessageDialog(null, "Registro no seleccionado");
                return;
            }
            int respuesta=JOptionPane.showConfirmDialog(null, "Estás por eliminar un registro,\n¿Estás seguro?", "¿Estás seguro?", JOptionPane.YES_NO_OPTION);
            if(respuesta==0){
                manejador.eliminarBecario(Integer.parseInt(tablaBecas.getValueAt(tablaBecas.getSelectedRow(), 0).toString()));
                becariosEnTabla();
            }
        }
        if(e.getComponent()==btnBuscar){
            if (txtNoControl.getText().isEmpty()) {
                return;
            }
            try {
                if (manejador.buscarBecarios(txtNoControl.getText()) != null) {
                    DefaultTableModel tb = (DefaultTableModel) tablaBecas.getModel();
                    int a = tablaBecas.getRowCount() - 1;
                    for (int i = a; i >= 0; i--) {
                        tb.removeRow(tb.getRowCount() - 1);
                    }

                    ResultSet rs = manejador.buscarBecarios(txtNoControl.getText());
                    while (rs.next()) {
                        Object[] fila = new Object[6]; 
                        for (int i = 0; i < 6; i++) {
                            fila[i] = rs.getObject(i + 1);
                        }
                        modeloBecas.addRow(fila);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error en GestionBeca buscarBecario: " + ex.getLocalizedMessage());
            }
        }
        if(e.getComponent()==btnCancelar){
            tablaBecas.clearSelection();
            txtNoControl.setText("");
            becariosEnTabla();
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
