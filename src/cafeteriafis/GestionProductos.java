package cafeteriafis;

import Manejadores.ManejadorProducto;
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

/*
 * @author vmpor
 */
public class GestionProductos extends JFrame implements ActionListener, KeyListener, MouseListener {

    private JLabel fondo;
    public GestionVentas gestionVentas;
    private static JMenu m_opciones;
    private static JMenuBar mb_barra;
    private static JMenuItem mnSalir, mnGestionVentas, mnGestionEmpleados, mnGestionProductos, mnGestionCuentas, mnGestionCorte, mnGestionBeca;
    private JButton btnAgregar, btnModificar, btnEliminar, btnCrearMenu, btnBuscar, btnCancelar;
    private JLabel lblNombre, iconoCafe, lblTitulo;
    private JTextField txtNombre;
    static String[] datosProducto = {"Id", "Nombre Producto", "Precio", "Tipo"};
    Object[][] dataProductos = new Object[0][0];
    private ModeloTablas modeloProductos;
    public JTable tablaProductos;
    public ManejadorProducto manejador;
    private Modificar modificar;

    public GestionProductos(GestionVentas gestionVentas) {
        manejador = new ManejadorProducto();
        modeloProductos = new ModeloTablas(dataProductos, datosProducto);
        this.gestionVentas = gestionVentas;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        lblTitulo = new JLabel();
        lblTitulo.setBounds(10, -5, 350, 50);
        lblTitulo.setText("Gestión de productos");
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 26));
        add(lblTitulo);
        tablaProductos = new JTable(modeloProductos);
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);
        scrollProductos.setBounds(screenSize.width / 2 - 250, 150, 800, 500);
        tablaProductos.getColumn("Id").setResizable(false);
        tablaProductos.getColumn("Tipo").setResizable(false);
        tablaProductos.getColumn("Precio").setResizable(false);
        tablaProductos.getColumn("Id").setMinWidth(50);
        tablaProductos.getColumn("Tipo").setMinWidth(150);
        tablaProductos.getColumn("Precio").setMinWidth(150);
        tablaProductos.getColumn("Id").setMaxWidth(50);
        tablaProductos.getColumn("Tipo").setMaxWidth(150);
        tablaProductos.getColumn("Precio").setMaxWidth(150);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.add(scrollProductos);

        btnBuscar = new JButton();
        btnBuscar.setIcon(new ImageIcon("src\\imagenes\\lupa.png"));
        btnBuscar.setBounds(screenSize.width / 2 + 320, 40, 50, 50);
        btnBuscar.addMouseListener(this);
        this.add(btnBuscar);
        lblNombre = new JLabel();
        lblNombre.setText("Nombre");
        lblNombre.setBounds(screenSize.width / 2 - 150, 50, 150, 30);
        lblNombre.setFont(new Font("Monospaced", Font.BOLD, 20));
        this.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(screenSize.width / 2 - 50, 55, 350, 25);
        this.add(txtNombre);
        btnAgregar = new JButton();
        btnAgregar.setText("Agregar");
        btnAgregar.setBounds(100, 150, 100, 30);
        btnAgregar.addMouseListener(this);
        this.add(btnAgregar);
        btnModificar = new JButton();
        btnModificar.setText("Modificar");
        btnModificar.setBounds(100, 250, 100, 30);
        btnModificar.addMouseListener(this);
        this.add(btnModificar);
        btnEliminar = new JButton();
        btnEliminar.setText("Eliminar");
        btnEliminar.setBounds(100, 350, 100, 30);
        btnEliminar.addMouseListener(this);
        this.add(btnEliminar);
        btnCrearMenu = new JButton();
        btnCrearMenu.setText("Crear Menú");
        btnCrearMenu.setBounds(100, 450, 100, 30);
        btnCrearMenu.addMouseListener(this);
        this.add(btnCrearMenu);
        
        btnCancelar = new JButton();
        btnCancelar.setText("Cancear");
        btnCancelar.setBounds(100, 550, 100, 30);
        btnCancelar.addMouseListener(this);
        this.add(btnCancelar);

        this.addKeyListener(this);

        m_opciones = new JMenu("   Gestión de Productos   ");
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
        mnGestionProductos.setEnabled(false);
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
        this.setTitle("Gestión de productos");
        this.setResizable(false);
        productosEnTabla();
    }

    public void productosEnTabla() {
        DefaultTableModel tb = (DefaultTableModel) tablaProductos.getModel();
        int a = tablaProductos.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
        try {
            if (manejador.consultaTodosProductos() != null) {
                ResultSet rs = manejador.consultaTodosProductos();
                while (rs.next()) {
                    Object[] fila = new Object[4];
                    for (int i = 0; i < 4; i++) {
                        if (i == 3) {
                            if (((int) rs.getObject(i + 1)) == 1) {
                                fila[i] = "Producto de mostrador";
                            } else {
                                fila[i] = "Alimento";
                            }
                        } else {
                            fila[i] = rs.getObject(i + 1);
                        }
                    }
                    modeloProductos.addRow(fila);
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
        if (e.getSource() == mnGestionEmpleados) {
            gestionVentas.gestionEmpleados.setVisible(true);
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
        if (e.getComponent() == btnAgregar) {
            Agregar agregar = new Agregar(4, this);
            agregar.setVisible(true);
        }
        if (e.getComponent() == btnCrearMenu) {
            CrearMenu crearMenu = new CrearMenu(this);
            crearMenu.setVisible(true);
        }
        if (e.getComponent() == btnModificar) {
            if (tablaProductos.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Registro no seleccionado");
                return;
            }
            modificar = new Modificar(2, this);
            modificar.setVisible(true);
        }
        if (e.getComponent() == btnEliminar) {
            if (tablaProductos.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Registro no seleccionado");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(null, "Estás por eliminar un registro,\n¿Estás seguro?", "¿Estás seguro?", JOptionPane.YES_NO_OPTION);
            if (respuesta == 0) {
                if (!manejador.eliminarProducto(Integer.parseInt(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0).toString()))) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar");
                }
                productosEnTabla();
            }
        }
        if (e.getComponent() == btnBuscar) {
            if (txtNombre.getText().isEmpty()) {
                return;
            }
            try {
                if (manejador.buscarProducto(txtNombre.getText()) != null) {
                    DefaultTableModel tb = (DefaultTableModel) tablaProductos.getModel();
                    int a = tablaProductos.getRowCount() - 1;
                    for (int i = a; i >= 0; i--) {
                        tb.removeRow(tb.getRowCount() - 1);
                    }

                    ResultSet rs = manejador.buscarProducto(txtNombre.getText());
                    while (rs.next()) {
                        Object[] fila = new Object[4];
                        for (int i = 0; i < 4; i++) {
                            if (i == 3) {
                                if (((int) rs.getObject(i + 1)) == 1) {
                                    fila[i] = "Producto de mostrador";
                                } else {
                                    fila[i] = "Alimento";
                                }
                            } else {
                                fila[i] = rs.getObject(i + 1);
                            }
                        }
                        modeloProductos.addRow(fila);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error en productosEntabla(): " + ex.getLocalizedMessage());
            }
        }
        if(e.getComponent()==btnCancelar){
            tablaProductos.clearSelection();
            txtNombre.setText("");
            productosEnTabla();
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
}
