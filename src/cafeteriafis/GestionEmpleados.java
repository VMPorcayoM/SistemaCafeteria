package cafeteriafis;

import Manejadores.ManejadorEmpleados;
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
public class GestionEmpleados extends JFrame implements ActionListener, KeyListener, MouseListener {

    private JLabel fondo;
    public GestionVentas gestionVentas;
    private static JMenu m_opciones;
    private static JMenuBar mb_barra;
    private static JMenuItem mnSalir, mnGestionVentas, mnGestionEmpleados, mnGestionProductos, mnGestionCuentas, mnGestionCorte, mnGestionBeca;
    private JLabel lblNombre, iconoCafe, lblTitulo;
    private JTextField txtNombre;
    private JButton btnLupa, btnAgregar, btnModificar, btnEliminar, btnCancelar;
    private Agregar agregar;
    Object[][] dataEmpleados = new Object[0][0];
    static String[] datosEmpleado = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Profesión/Nivel de estudios", "Honorarios", "Telefono", "Dirección", "Fecha Nac.", "Cargo"};
    Object[][] dataMenu = new Object[0][0];
    private ModeloTablas modeloEmpleados = new ModeloTablas(dataEmpleados, datosEmpleado);
    public JTable tablaEmpleados;
    public ManejadorEmpleados manejador = new ManejadorEmpleados();
    private Modificar modificar;

    public GestionEmpleados(GestionVentas gestionVentas) {
        this.addKeyListener(this);
        this.gestionVentas = gestionVentas;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        lblTitulo = new JLabel();
        lblTitulo.setBounds(10, -5, 350, 50);
        lblTitulo.setText("Gestión de empleados");
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 26));
        add(lblTitulo);
        lblNombre = new JLabel("Nombre: ");
        lblNombre.setBounds(screenSize.width / 2 - 220, 30, 120, 30);
        lblNombre.setFont(new Font("Monospaced", Font.BOLD, 20));
        add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(screenSize.width / 2 - 120, 35, 280, 22);
        txtNombre.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtNombre.getText().length() == 30) {
                            e.consume();
                        }
                        if (!Character.isLetter(e.getKeyChar()) && !(e.getKeyChar() == KeyEvent.VK_SPACE) && !(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                            e.consume();
                        }
                    }
                });
        add(txtNombre);
        btnLupa = new JButton();
        btnLupa.setIcon(new ImageIcon("src\\imagenes\\lupa.png"));
        btnLupa.setBounds(screenSize.width / 2 + 200, 25, 50, 50);
        btnLupa.addMouseListener(this);
        add(btnLupa);
        tablaEmpleados = new JTable(modeloEmpleados);
        JScrollPane scrollMenu = new JScrollPane(tablaEmpleados);
        scrollMenu.setBounds(5, 100, screenSize.width - 10, 500);
        tablaEmpleados.getColumn("ID").setResizable(false);
        tablaEmpleados.getColumn("ID").setMinWidth(50);
        tablaEmpleados.getColumn("ID").setMaxWidth(50);
        tablaEmpleados.getColumn("Fecha Nac.").setResizable(false);
        tablaEmpleados.getColumn("Fecha Nac.").setMinWidth(80);
        tablaEmpleados.getColumn("Fecha Nac.").setMaxWidth(80);
        tablaEmpleados.getColumn("Cargo").setResizable(false);
        tablaEmpleados.getColumn("Cargo").setMinWidth(70);
        tablaEmpleados.getColumn("Cargo").setMaxWidth(70);
        tablaEmpleados.getColumn("Honorarios").setResizable(false);
        tablaEmpleados.getColumn("Honorarios").setMinWidth(100);
        tablaEmpleados.getColumn("Honorarios").setMaxWidth(100);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(scrollMenu);
        btnAgregar = new JButton();
        btnAgregar.setText("Agregar");
        btnAgregar.setBounds(screenSize.width / 4 - 100, screenSize.height - 100, 100, 30);
        btnAgregar.addMouseListener(this);
        add(btnAgregar);
        btnModificar = new JButton();
        btnModificar.setText("Modificar");
        btnModificar.setBounds(screenSize.width / 4 + 150, screenSize.height - 100, 100, 30);
        btnModificar.addMouseListener(this);
        add(btnModificar);
        btnEliminar = new JButton();
        btnEliminar.setText("Eliminar");
        btnEliminar.setBounds(screenSize.width / 2 + 50, screenSize.height - 100, 100, 30);
        btnEliminar.addMouseListener(this);
        add(btnEliminar);
        btnCancelar = new JButton();
        btnCancelar.setText("Cancelar");
        btnCancelar.setBounds(screenSize.width / 2 + 300, screenSize.height - 100, 100, 30);
        btnCancelar.addMouseListener(this);
        add(btnCancelar);

        m_opciones = new JMenu("   Gestión de Empleados   ");
        mnSalir = new JMenuItem("   Cerrar sesión     ");
        mnSalir.addActionListener(this);
        m_opciones.add(mnSalir);

        mnGestionVentas = new JMenuItem("   Gestión de Ventas   ");
        mnGestionVentas.addActionListener(this);
        m_opciones.add(mnGestionVentas);

        mnGestionEmpleados = new JMenuItem("   Gestión de Empleados   ");
        mnGestionEmpleados.setEnabled(false);
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
        mnGestionBeca.addActionListener(this);
        m_opciones.add(mnGestionBeca);

        mb_barra = new JMenuBar();
        mb_barra.add(m_opciones);
        this.setJMenuBar(mb_barra);
        iconoCafe = new JLabel();
        iconoCafe.setBounds(5, screenSize.height - 150, 100, 100);
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
        this.setTitle("Gestión de empleados");
        this.setResizable(false);
        empleadosEnTabla();
    }

    public void empleadosEnTabla() {
        DefaultTableModel tb = (DefaultTableModel) tablaEmpleados.getModel();
        int a = tablaEmpleados.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
        try {
            if (manejador.consultaEmpleados() != null) {
                ResultSet rs = manejador.consultaEmpleados();
                while (rs.next()) {
                    Object[] fila = new Object[10];
                    for (int i = 0; i < 10; i++) {
                        if (i == 8) {
                            fila[i] = rs.getDate(i + 1).toString();
                        }
                        fila[i] = rs.getObject(i + 1);
                    }
                    modeloEmpleados.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en productosEntabla(): " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mnSalir) {
            gestionVentas.inicio.setVisible(true);
            this.dispose();
            gestionVentas.dispose();
        }
        if (e.getSource() == mnGestionVentas) {
            gestionVentas.setVisible(true);
            this.setVisible(false);
        }
        if (e.getSource() == mnGestionProductos) {
            gestionVentas.gestionProductos.setVisible(true);
            this.setVisible(false);
        }
        if (e.getSource() == mnGestionCuentas) {
            gestionVentas.gestionCuentas.setVisible(true);
            this.setVisible(false);
        }
        if (e.getSource() == mnGestionCorte) {
            gestionVentas.gestionCorte.setVisible(true);
            this.setVisible(false);
        }
        if (e.getSource() == mnGestionBeca) {
            gestionVentas.gestionBeca.setVisible(true);
            this.setVisible(false);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setExtendedState(ICONIFIED);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getComponent() == btnCancelar) {
            tablaEmpleados.clearSelection();
            txtNombre.setText("");
            empleadosEnTabla();
        }
        if (e.getComponent() == btnAgregar) {
            agregar = new Agregar(1, this);
            agregar.setVisible(true);
        }
        if (e.getComponent() == btnModificar) {
            if (tablaEmpleados.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Registro no seleccionado");
                return;
            }
            modificar = new Modificar(1, this);
            modificar.setVisible(true);
        }
        if (e.getComponent() == btnEliminar) {
            if (tablaEmpleados.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Registro no seleccionado");
                return;
            }
            if (manejador.empleadoDentro(gestionVentas.usuarioActual, tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 1).toString())) {
                JOptionPane.showMessageDialog(null, "Empleado dentro del sistema");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(null, "Estás por eliminar un registro,\n¿Estás seguro?", "¿Estás seguro?", JOptionPane.YES_NO_OPTION);
            if (respuesta == 0) {
                manejador.eliminarEmpleado(Integer.parseInt(tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0).toString()));
                empleadosEnTabla();
            }
        }
        if (e.getComponent() == btnLupa) {
            if (txtNombre.getText().isEmpty()) {
                return;
            }
            try {
                if (manejador.buscarEmpleado(txtNombre.getText())!= null) {
                    DefaultTableModel tb = (DefaultTableModel) tablaEmpleados.getModel();
                    int a = tablaEmpleados.getRowCount() - 1;
                    for (int i = a; i >= 0; i--) {
                        tb.removeRow(tb.getRowCount() - 1);
                    }
                    
                    ResultSet rs = manejador.buscarEmpleado(txtNombre.getText());
                    while (rs.next()) {
                        Object[] fila = new Object[10];
                        for (int i = 0; i < 10; i++) {
                            if (i == 8) {
                                fila[i] = rs.getDate(i + 1).toString();
                            }
                            fila[i] = rs.getObject(i + 1);
                        }
                        modeloEmpleados.addRow(fila);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error en productosEntabla(): " + ex.getLocalizedMessage());
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
