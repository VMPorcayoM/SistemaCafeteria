package cafeteriafis;

import Manejadores.ManejadorVentas;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class GestionVentas extends JFrame implements ActionListener, KeyListener, MouseListener {

    private int turno;
    String cargoString;
    private JLabel fondo;
    public InicioSesion inicio;
    public GestionEmpleados gestionEmpleados;
    public GestionProductos gestionProductos;
    public GestionCuentas gestionCuentas;
    public GestionCorte gestionCorte;
    public GestionBeca gestionBeca;
    private static JMenu m_opciones;
    private static JMenuBar mb_barra;
    private static JMenuItem mnSalir, mnGestionVentas, mnGestionEmpleados, mnGestionProductos, mnGestionCuentas, mnGestionCorte, mnGestionBeca;
    public String usuarioActual;
    private JLabel lblBuscar, lblNombrePro, lblCostoProducto, lblDetalleVenta, lblMenuDia, lblTotal, iconoCafe, lblTitulo;
    private JButton btnAgregar, btnCobrar, btnCancelar, btnAgregarDeMenu;
    private JComboBox comboProductos;
    private JTextField txtCostoProductos, txtTotal;
    static String[] datosDetalleVenta = {"Descripción", "Precio", "Cantidad", "Importe"};
    Object[][] dataDetalleVenta = new Object[0][0];
    static String[] datosMenu = {"ID", "Nombre del producto", "Precio"};
    Object[][] dataMenu = new Object[0][0];
    private ModeloTablas modeloDetalleVenta = new ModeloTablas(dataDetalleVenta, datosDetalleVenta);
    private ModeloTablas modeloMenuDia = new ModeloTablas(dataMenu, datosMenu);
    private JTable tablaDetalleVenta, tablaMenuDia;
    private ManejadorVentas manejador = new ManejadorVentas();
    private String prodSeleccionado;

    public GestionVentas(InicioSesion inicio, String usuario) {
        turno = 1;
        this.usuarioActual = usuario;
        this.inicio = inicio;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        lblTitulo = new JLabel();
        lblTitulo.setBounds(10, -5, 300, 50);
        lblTitulo.setText("Gestión de ventas");
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 26));
        add(lblTitulo);

        m_opciones = new JMenu("   Gestión de Ventas   ");
        mnSalir = new JMenuItem("   Cerrar sesión     ");
        mnSalir.addActionListener(this);
        m_opciones.add(mnSalir);

        mnGestionVentas = new JMenuItem("   Gestión de Ventas   ");
        mnGestionVentas.setEnabled(false);
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
        mnGestionBeca.addActionListener(this);
        m_opciones.add(mnGestionBeca);

        mb_barra = new JMenuBar();
        mb_barra.add(m_opciones);
        this.setJMenuBar(mb_barra);

        btnCancelar = new JButton();
        btnCancelar.setText("Cancelar");
        btnCancelar.setBounds(screenSize.width / 2 + 380, 650, 100, 30);
        btnCancelar.addMouseListener(this);
        this.add(btnCancelar);

        btnCobrar = new JButton();
        btnCobrar.setText("Cobrar");
        btnCobrar.setBounds(screenSize.width / 2 + 50, 650, 100, 35);
        btnCobrar.addMouseListener(this);
        this.add(btnCobrar);
        lblBuscar = new JLabel("Buscar");
        lblBuscar.setBounds(screenSize.width / 2 - 50, 30, 100, 20);
        lblBuscar.setFont(new Font("Monospaced", Font.BOLD, 18));
        this.add(lblBuscar);
        lblNombrePro = new JLabel("Nombre producto:");
        lblNombrePro.setBounds(130, 80, 200, 20);
        lblNombrePro.setFont(new Font("Monospaced", Font.BOLD, 20));
        this.add(lblNombrePro);
        lblCostoProducto = new JLabel("Costo: $");
        lblCostoProducto.setBounds(screenSize.width / 2 + 100, 80, 100, 20);
        lblCostoProducto.setFont(new Font("Monospaced", Font.BOLD, 20));
        this.add(lblCostoProducto);
        comboProductos = new JComboBox<Object>();
        comboProductos.setBounds(350, 80, 300, 20);
        comboProductos.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                prodSeleccionado = (String) comboProductos.getSelectedItem();
                txtCostoProductos.setText(manejador.costoProducto(prodSeleccionado));
            }
        });
        this.add(comboProductos);
        txtCostoProductos = new JTextField();
        txtCostoProductos.setBounds(screenSize.width / 2 + 200, 80, 80, 20);
        txtCostoProductos.setEditable(false);
        txtCostoProductos.setFont(new Font("Monospaced", Font.BOLD, 18));
        this.add(txtCostoProductos);
        btnAgregar = new JButton();
        btnAgregar.setIcon(new ImageIcon("src\\imagenes\\mas.png"));
        btnAgregar.setBounds(screenSize.width / 2 + 550, 60, 50, 50);
        btnAgregar.addMouseListener(this);
        this.add(btnAgregar);
        lblDetalleVenta = new JLabel("Detalle de venta");
        lblDetalleVenta.setBounds(190, 150, 500, 50);
        lblDetalleVenta.setFont(new Font("Monospaced", Font.BOLD, 30));
        this.add(lblDetalleVenta);
        lblMenuDia = new JLabel("Menú del día");
        lblMenuDia.setBounds(880, 150, 500, 50);
        lblMenuDia.setFont(new Font("Monospaced", Font.BOLD, 30));
        this.add(lblMenuDia);
        tablaDetalleVenta = new JTable(modeloDetalleVenta);
        tablaDetalleVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDetalleVenta.getColumn("Descripción").setResizable(false);
        tablaDetalleVenta.getColumn("Descripción").setMinWidth(250);
        tablaDetalleVenta.getColumn("Descripción").setMaxWidth(250);
        JScrollPane scrollDetalleVenta = new JScrollPane(tablaDetalleVenta);
        scrollDetalleVenta.setBounds(110, 250, 500, 300);
        this.add(scrollDetalleVenta);
        tablaMenuDia = new JTable(modeloMenuDia);
        JScrollPane scrollMenu = new JScrollPane(tablaMenuDia);
        scrollMenu.setBounds(750, 250, 500, 300);
        tablaMenuDia.getColumn("ID").setResizable(false);
        tablaMenuDia.getColumn("ID").setMinWidth(40);
        tablaMenuDia.getColumn("ID").setMaxWidth(40);
        tablaMenuDia.getColumn("Precio").setResizable(false);
        tablaMenuDia.getColumn("Precio").setMinWidth(50);
        tablaMenuDia.getColumn("Precio").setMaxWidth(50);
        tablaMenuDia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.add(scrollMenu);
        lblMenuDia = new JLabel("Menú del día");
        lblMenuDia.setBounds(880, 150, 500, 50);
        lblMenuDia.setFont(new Font("Monospaced", Font.BOLD, 30));
        this.add(lblMenuDia);
        lblTotal = new JLabel("Total: $");
        lblTotal.setBounds(220, 600, 120, 30);
        lblTotal.setFont(new Font("Monospaced", Font.BOLD, 24));
        this.add(lblTotal);
        txtTotal = new JTextField();
        txtTotal.setBounds(340, 600, 120, 25);
        txtTotal.setEditable(false);
        txtTotal.setFont(new Font("Monospaced", Font.BOLD, 20));
        this.add(txtTotal);
        btnAgregarDeMenu = new JButton();
        btnAgregarDeMenu.setIcon(new ImageIcon("src\\imagenes\\masChico.png"));
        btnAgregarDeMenu.setBounds(screenSize.width - 100, screenSize.height / 2 + 135, 25, 25);
        btnAgregarDeMenu.addMouseListener(this);
        this.add(btnAgregarDeMenu);
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
        this.setTitle("Gestión de ventas");
        this.setResizable(false);
        this.addKeyListener(this);
        productosEnLista();
        ResultSet cargo = manejador.cargoEmpleado(usuarioActual);
        try {
            cargo.first();
            cargoString = cargo.getObject(1).toString();
            if ("Cajero".equals(cargoString)) {
                mnGestionBeca.setEnabled(false);
                mnGestionCuentas.setEnabled(false);
                mnGestionEmpleados.setEnabled(false);
                mnGestionProductos.setEnabled(false);
                gestionCorte = new GestionCorte(this);
            } else {
                gestionEmpleados = new GestionEmpleados(this);
                gestionProductos = new GestionProductos(this);
                gestionCuentas = new GestionCuentas(this);
                gestionCorte = new GestionCorte(this);
                gestionBeca = new GestionBeca(this);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        menuEnTabla();
    }

    public void productosEnLista() {
        try {
            if (manejador.consultaProductos() != null) {
                ResultSet rs = manejador.consultaProductos();
                comboProductos.addItem("Seleccione una opción");
                while (rs.next()) {
                    comboProductos.addItem(rs.getString("nombre"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en productosEnLista(): " + ex.getLocalizedMessage());
        }
    }

    public void menuEnTabla() {
        DefaultTableModel tb = (DefaultTableModel) tablaMenuDia.getModel();
        int a = tablaMenuDia.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
        try {
            if (manejador.consultaUltimoMenu() != null) {
                ResultSet rs = manejador.consultaUltimoMenu();
                while (rs.next()) {
                    Object[] fila = new Object[3];
                    for (int i = 0; i < 3; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    modeloMenuDia.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en menuEntabla(): " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Gerente".equals(cargoString)) {
            if (e.getSource() == mnGestionEmpleados) {
                gestionEmpleados.setVisible(true);
                this.setVisible(false);
            }
            if (e.getSource() == mnGestionProductos) {
                gestionProductos.setVisible(true);
                this.setVisible(false);
            }
            if (e.getSource() == mnGestionCuentas) {
                gestionCuentas.setVisible(true);
                this.setVisible(false);
            }
            if (e.getSource() == mnGestionBeca) {
                gestionBeca.setVisible(true);
                this.setVisible(false);
            }
        }
        if (e.getSource() == mnSalir) {
            inicio.setVisible(true);
            this.dispose();
        }
        if (e.getSource() == mnGestionCorte) {
            gestionCorte.setVisible(true);
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
        if (e.getComponent() == btnAgregar) {
            int total = 0;
            ResultSet rs;
            if (prodSeleccionado != null) {
                try {
                    String nombre = "";
                    rs = manejador.registroSeleccionado(prodSeleccionado);
                    if (rs.next()) {
                        nombre = rs.getObject(1).toString();
                    } else {
                        return;
                    }
                    for (int i = 0; i < modeloDetalleVenta.getRowCount(); i++) {
                        if (nombre.equals(modeloDetalleVenta.getValueAt(i, 0).toString())) {
                            modeloDetalleVenta.setValueAt(Integer.parseInt(modeloDetalleVenta.getValueAt(i, 2).toString()) + 1, i, 2);
                            modeloDetalleVenta.setValueAt((Integer.parseInt(modeloDetalleVenta.getValueAt(i, 1).toString()) * Integer.parseInt(modeloDetalleVenta.getValueAt(i, 2).toString())), i, 3);
                            total = Integer.parseInt(txtTotal.getText());
                            total += Integer.parseInt(modeloDetalleVenta.getValueAt(i, 1).toString());

                            txtTotal.setText(String.valueOf(total));
                            return;
                        }
                    }
                    Object[] fila = new Object[4];
                    for (int i = 0; i < 4; i++) {
                        if (i == 2) {
                            fila[i] = 1;
                        } else if (i == 3) {
                            fila[i] = Integer.parseInt(fila[1].toString()) * Integer.parseInt(fila[2].toString());
                        } else {
                            fila[i] = rs.getObject(i + 1);
                        }
                    }
                    modeloDetalleVenta.addRow(fila);

                } catch (SQLException ex) {
                    System.out.println("Error en GVentas: " + ex.getMessage());
                }
            } else {
                System.out.println("No seleccionado");
            }

            for (int i = 0; i < modeloDetalleVenta.getRowCount(); i++) {
                total += Integer.parseInt(modeloDetalleVenta.getValueAt(i, 3).toString());
            }
            txtTotal.setText(String.valueOf(total));
        }
        if (e.getComponent() == btnCancelar) {
            DefaultTableModel tb = (DefaultTableModel) tablaDetalleVenta.getModel();
            int a = tablaDetalleVenta.getRowCount() - 1;
            for (int i = a; i >= 0; i--) {
                tb.removeRow(tb.getRowCount() - 1);
            }
            txtTotal.setText("");
        }
        if (e.getComponent() == btnAgregarDeMenu) {
            if (modeloMenuDia.getRowCount() == 0) {
                return;
            }
            try {
                String alimentoSelec = tablaMenuDia.getValueAt(tablaMenuDia.getSelectedRow(), 1).toString();
                ResultSet rs = manejador.registroSeleccionado(alimentoSelec);
                if (rs.first()) {
                    int total = 0;
                    for (int i = 0; i < modeloDetalleVenta.getRowCount(); i++) {
                        if (alimentoSelec.equals(modeloDetalleVenta.getValueAt(i, 0).toString())) {
                            modeloDetalleVenta.setValueAt(Integer.parseInt(modeloDetalleVenta.getValueAt(i, 2).toString()) + 1, i, 2);
                            modeloDetalleVenta.setValueAt((Integer.parseInt(modeloDetalleVenta.getValueAt(i, 1).toString()) * Integer.parseInt(modeloDetalleVenta.getValueAt(i, 2).toString())), i, 3);
                            total = Integer.parseInt(txtTotal.getText());
                            total += Integer.parseInt(modeloDetalleVenta.getValueAt(i, 1).toString());

                            txtTotal.setText(String.valueOf(total));
                            return;
                        }
                    }
                    Object[] fila = new Object[4];
                    for (int i = 0; i < 4; i++) {
                        switch (i) {
                            case 2:
                                fila[i] = 1;
                                break;
                            case 3:
                                fila[i] = Integer.parseInt(fila[1].toString()) * Integer.parseInt(fila[2].toString());
                                break;
                            default:
                                fila[i] = rs.getObject(i + 1);
                                break;
                        }
                    }
                    modeloDetalleVenta.addRow(fila);
                    for (int i = 0; i < modeloDetalleVenta.getRowCount(); i++) {
                        total += Integer.parseInt(modeloDetalleVenta.getValueAt(i, 3).toString());
                    }
                    txtTotal.setText(String.valueOf(total));
                }
            } catch (SQLException ex) {
                System.out.println("Error en botonAgregarDeMenu " + ex.getMessage());
            }
        }
        if (e.getComponent() == btnCobrar) {
            if (tablaDetalleVenta.getRowCount() == 0) {
                return;
            }
            manejador.agregarVenta(usuarioActual, Double.parseDouble(txtTotal.getText()));
            boolean imprimir=false;
            for (int i = 0; i < tablaDetalleVenta.getRowCount(); i++) {
                manejador.agregarDetalleVenta(manejador.idUltimaVenta(), manejador.idProducto(tablaDetalleVenta.getValueAt(i, 0).toString()), Integer.parseInt(tablaDetalleVenta.getValueAt(i, 2).toString()));
                if(manejador.isAlimento(tablaDetalleVenta.getValueAt(i, 0).toString())){
                    turno++;
                    imprimir=true;
                }
            }
            if(imprimir){
                imprimirTurno();
                imprimir=false;
            }
            DefaultTableModel tb = (DefaultTableModel) tablaDetalleVenta.getModel();
            int a = tablaDetalleVenta.getRowCount() - 1;
            for (int i = a; i >= 0; i--) {
                tb.removeRow(tb.getRowCount() - 1);
            }
            txtTotal.setText("");
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

    void imprimirTurno() {        
        PrinterMatrix printer = new PrinterMatrix();

        Extenso e = new Extenso();

        e.setNumber(101.85);

        //Definir el tamanho del papel para la impresion  aca 25 lineas y 80 columnas
        printer.setOutSize(11, 30);
        //Imprimir * de la 2da linea a 25 en la columna 1;
        // printer.printCharAtLin(2, 25, 1, "*");
        //Imprimir * 1ra linea de la columa de 1 a 30
        printer.printCharAtCol(1, 1, 30, "=");
        //Imprimir Encabezado nombre del La EMpresa
        printer.printTextWrap(1, 2, 4, 30, "TICKET DE TURNO - CAJA");
        //printer.printTextWrap(linI, linE, colI, colE, null);
        printer.printTextWrap(2, 3, 1, 30, "Num. Turno     : " + turno);
        printer.printTextWrap(3, 3, 1, 30, "Vendedor       : " + this.usuarioActual);
        printer.printTextWrap(4, 3, 1, 30, "TOTAL A PAGAR $: " + txtTotal.getText());
        printer.printTextWrap(5, 3, 1, 2, "✄");
        printer.printCharAtCol(6, 5, 30, "=");
        printer.printTextWrap(7, 2, 2, 30, "TICKET DE TURNO - CLIENTE");
        printer.printTextWrap(8, 3, 1, 30, "Num. Turno     : " + turno);
        printer.printTextWrap(9, 3, 1, 30, "TOTAL A PAGAR $: " + txtTotal.getText());
        printer.printCharAtCol(11, 1, 30, "=");
        printer.toFile("src\\impresion.txt");

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src\\impresion.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        if (inputStream == null) {
            return;
        }

        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            System.out.println(defaultPrintService.getName());
            try {
                printJob.print(document, attributeSet);
            } catch (Exception ex) {
                System.out.println("Error imprimir :"+ex.getMessage());
            }
        } else {
            System.err.println("No existen impresoras instaladas");
        }

        //inputStream.close();
    }

}
