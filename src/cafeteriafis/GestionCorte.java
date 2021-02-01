package cafeteriafis;

import Manejadores.ManejadorCorte;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
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
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/*
 * @author vmpor
 */
public class GestionCorte extends JFrame implements ActionListener, KeyListener, MouseListener {

    private JLabel fondo, iconoCafe, lblTitulo;
    public GestionVentas gestionVentas;
    private static JMenu m_opciones;
    private static JMenuBar mb_barra;
    private static JMenuItem mnSalir, mnGestionVentas, mnGestionEmpleados, mnGestionProductos, mnGestionCuentas, mnGestionCorte, mnGestionBeca;
    private JButton btnRealizarCorte, btnEliminar, btnImprimir, btnBuscar;
    private UtilDateModel model = new UtilDateModel();
    private Properties p = new Properties();
    private JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
    private JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    static String[] datosCorte = {"ID", "ID Cuenta", "Fecha", "Contado", "Calculado", "Diferencia", "Retiro"};
    Object[][] dataCortes = new Object[0][0];
    private ModeloTablas modeloCortes = new ModeloTablas(dataCortes, datosCorte);
    public JTable tablaCortes;
    public ManejadorCorte manejador = new ManejadorCorte();

    public GestionCorte(GestionVentas gestionVentas) {

        btnBuscar = new JButton();
        btnBuscar.setIcon(new ImageIcon("src\\imagenes\\lupa.png"));
        btnBuscar.setBounds(380, 40, 50, 50);
        btnBuscar.addMouseListener(this);
        add(btnBuscar);
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePicker.setBounds(150, 50, 200, 25);
        datePicker.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                if (fechaMayoraActual()) {
                    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                    Date fechaActual = new Date();
                    JOptionPane.showMessageDialog(null, "Fecha mayor a la actual");
                    datePicker.getModel().setDate(fechaActual.getYear() + 1900, fechaActual.getMonth() + 1, fechaActual.getDay() - 6);
                }
            }
        });
        add(datePicker);
        this.addKeyListener(this);
        this.gestionVentas = gestionVentas;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        lblTitulo = new JLabel();
        lblTitulo.setBounds(10, -5, 400, 50);
        lblTitulo.setText("Gestión de corte de caja");
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 26));
        add(lblTitulo);
        tablaCortes = new JTable(modeloCortes);
        tablaCortes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollCortes = new JScrollPane(tablaCortes);
        scrollCortes.setBounds(50, 150, screenSize.width - 100, 450);
        tablaCortes.getColumn("ID").setResizable(false);
        tablaCortes.getColumn("ID").setMinWidth(40);
        tablaCortes.getColumn("ID").setMaxWidth(40);
        tablaCortes.getColumn("ID Cuenta").setResizable(false);
        tablaCortes.getColumn("ID Cuenta").setMinWidth(80);
        tablaCortes.getColumn("ID Cuenta").setMaxWidth(80);
        this.add(scrollCortes);
        btnRealizarCorte = new JButton();
        btnRealizarCorte.setText("Realizar corte de caja");
        btnRealizarCorte.setBounds(screenSize.width / 2 - 50, 50, 200, 30);
        btnRealizarCorte.addMouseListener(this);
        this.add(btnRealizarCorte);
        btnEliminar = new JButton();
        btnEliminar.setText("Eliminar");
        btnEliminar.setBounds(screenSize.width / 2 + 250, 50, 80, 30);
        btnEliminar.addMouseListener(this);
        this.add(btnEliminar);
        btnImprimir = new JButton();
        btnImprimir.setIcon(new ImageIcon("src\\imagenes\\imprimir.png"));
        btnImprimir.setBounds(screenSize.width / 2 + 420, 40, 50, 50);
        btnImprimir.addMouseListener(this);
        this.add(btnImprimir);

        m_opciones = new JMenu("   Gestión de Corte de caja   ");
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
        mnGestionCorte.setEnabled(false);
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
        this.setTitle("Gestión de corte de caja");
        this.setResizable(false);
        if ("Cajero".equals(gestionVentas.cargoString)) {
            mnGestionBeca.setEnabled(false);
            mnGestionCuentas.setEnabled(false);
            mnGestionEmpleados.setEnabled(false);
            mnGestionProductos.setEnabled(false);
        }
        cortesEnTabla();
    }

    public void cortesEnTabla() {
        DefaultTableModel tb = (DefaultTableModel) tablaCortes.getModel();
        int a = tablaCortes.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
        try {
            if (manejador.consultaCortes() != null) {
                ResultSet rs = manejador.consultaCortes();
                while (rs.next()) {
                    Object[] fila = new Object[7];
                    for (int i = 0; i < 7; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    modeloCortes.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en cortesEnTabla(): " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Gerente".equals(gestionVentas.cargoString)) {
            if (e.getSource() == mnGestionEmpleados) {
                gestionVentas.gestionEmpleados.setVisible(true);
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
            if (e.getSource() == mnGestionBeca) {
                gestionVentas.gestionBeca.setVisible(true);
                this.setVisible(false);
            }
        }
        if (e.getSource() == mnSalir) {
            gestionVentas.inicio.setVisible(true);
            this.dispose();
            gestionVentas.dispose();
        }
        if (e.getSource() == mnGestionVentas) {
            gestionVentas.setVisible(true);
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
        if (e.getComponent() == btnBuscar) {
            try {
                if(!manejador.buscarCortes(datePicker.getModel().getYear(), datePicker.getModel().getMonth() + 1, datePicker.getModel().getDay()).first()){
                    JOptionPane.showMessageDialog(null, "Sin cortes en esa fecha");
                    return;
                }
                if (manejador.buscarCortes(datePicker.getModel().getYear(), datePicker.getModel().getMonth() + 1, datePicker.getModel().getDay()) != null) {
                    DefaultTableModel tb = (DefaultTableModel) tablaCortes.getModel();
                    int a = tablaCortes.getRowCount() - 1;
                    for (int i = a; i >= 0; i--) {
                        tb.removeRow(tb.getRowCount() - 1);
                    }

                    ResultSet rs = manejador.buscarCortes(datePicker.getModel().getYear(), datePicker.getModel().getMonth() + 1, datePicker.getModel().getDay());
                    while (rs.next()) {
                        Object[] fila = new Object[7];
                        for (int i = 0; i < 7; i++) {
                            fila[i] = rs.getObject(i + 1);
                        }
                        modeloCortes.addRow(fila);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error en GestionCorte buscarCortes: " + ex.getLocalizedMessage());
            }

        }
        if (e.getComponent() == btnRealizarCorte) {
            RealizarCorteCaja nuevoCorte = new RealizarCorteCaja(this);
            nuevoCorte.setVisible(true);
        }
        if (e.getComponent() == btnEliminar) {
            if (tablaCortes.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Registro no seleccionado");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(null, "Estás por eliminar un corte,\n¿Estás seguro?", "¿Estás seguro?", JOptionPane.YES_NO_OPTION);
            if (respuesta == 0) {
                manejador.eliminarCorte(Integer.parseInt(tablaCortes.getValueAt(tablaCortes.getSelectedRow(), 0).toString()));
                cortesEnTabla();
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

    public boolean fechaMayoraActual() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = new Date();
        String fechaSistema = formateador.format(fechaActual);
        String diaP = (datePicker.getModel().getDay()) + "";
        String mesP = (datePicker.getModel().getMonth()) + 1 + "";
        String anioP = (datePicker.getModel().getYear()) + "";
        String fechaPicker = diaP + "/" + mesP + "/" + anioP;
        try {
            /**
             * Obtenemos las fechas enviadas en el formato a comparar
             */
            Date fechaDate1 = formateador.parse(fechaPicker);
            Date fechaDate2 = formateador.parse(fechaSistema);
            if (fechaDate1.before(fechaDate2)) {
                return false;
            } else if (fechaDate2.before(fechaDate1)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            System.out.println("Se Produjo un Error!!!  " + e.getMessage());
        }
        return false;
    }
}
