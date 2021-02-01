package cafeteriafis;

import static cafeteriafis.GestionVentas.datosDetalleVenta;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vmpor
 */
public class CrearMenu extends JFrame implements MouseListener {

    private JLabel lblNombre, fondo;
    private JComboBox comboAlimentos;
    private JButton btnAgregar, btnEliminar, btnGuardar;
    static String[] datosNuevoMenu = {"ID", "Nombre de Alimento"};
    Object[][] dataNuevoMenu = new Object[0][0];
    private ModeloTablas modeloNuevoMenu = new ModeloTablas(dataNuevoMenu, datosNuevoMenu);
    private JTable tablaNuevoMenu;
    private GestionProductos gestionProductos;
    private String prodSeleccionado;

    public CrearMenu(GestionProductos gestionProductos) {
        this.gestionProductos = gestionProductos;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        lblNombre = new JLabel();
        lblNombre.setText("Nombre de alimento: ");
        lblNombre.setBounds(40, 20, 200, 30);
        lblNombre.setFont(new Font("Monospaced", Font.BOLD, 16));
        this.add(lblNombre);
        comboAlimentos = new JComboBox<Object>();
        comboAlimentos.setBounds(250, 20, 220, 25);
        comboAlimentos.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                prodSeleccionado = (String) comboAlimentos.getSelectedItem();
            }
        });
        this.add(comboAlimentos);
        btnAgregar = new JButton();
        btnAgregar.setBounds(510, 20, 100, 25);
        btnAgregar.setText("Agregar");
        btnAgregar.addMouseListener(this);
        add(btnAgregar);
        tablaNuevoMenu = new JTable(modeloNuevoMenu);
        tablaNuevoMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaNuevoMenu.getColumn("ID").setResizable(false);
        tablaNuevoMenu.getColumn("ID").setMinWidth(50);
        tablaNuevoMenu.getColumn("ID").setMaxWidth(50);
        JScrollPane scrollDetalleVenta = new JScrollPane(tablaNuevoMenu);
        scrollDetalleVenta.setBounds(40, 100, 430, 300);
        this.add(scrollDetalleVenta);
        btnEliminar = new JButton();
        btnEliminar.setBounds(500, 100, 120, 25);
        btnEliminar.setText("Eliminar Menú");
        btnEliminar.addMouseListener(this);
        add(btnEliminar);
        btnGuardar = new JButton();
        btnGuardar.setBounds(500, 180, 120, 25);
        btnGuardar.setText("Guardar Menú");
        btnGuardar.addMouseListener(this);
        add(btnGuardar);

        fondo = new JLabel();
        fondo.setBounds(0, 0, screenSize.width / 2, screenSize.height / 2 + 100);
        fondo.setBackground(new Color(121, 133, 181));
        fondo.setOpaque(true);
        this.add(fondo);
        this.setBounds(0, 0, screenSize.width / 2, screenSize.height / 2 + 100);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Crear Menú");
        this.setResizable(false);
        alimentosEnLista();
    }

    public void alimentosEnLista() {
        try {
            if (gestionProductos.manejador.consultaAlimentos() != null) {
                ResultSet rs = gestionProductos.manejador.consultaAlimentos();
                comboAlimentos.addItem("Seleccione una opción");
                while (rs.next()) {
                    comboAlimentos.addItem(rs.getString("nombre"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en productosEnLista(): " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent() == btnAgregar) {
            int total = 0;
            ResultSet rs;
            if (prodSeleccionado != null) {
                try {
                    String nombre = "";
                    rs = gestionProductos.manejador.alimentoSeleccionado(prodSeleccionado);
                    if (rs.next()) {
                        nombre = rs.getObject(1).toString();
                    } else {
                        return;
                    }
                    for (int i = 0; i < modeloNuevoMenu.getRowCount(); i++) {
                        if (nombre.equals(modeloNuevoMenu.getValueAt(i, 0).toString())) {
                            JOptionPane.showMessageDialog(null, "Ya agregó ese alimento");
                            return;
                        }
                    }
                    Object[] fila = new Object[2];
                    for (int i = 0; i < 2; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    modeloNuevoMenu.addRow(fila);

                } catch (SQLException ex) {
                    System.out.println("Error en CrearMenu: " + ex.getMessage());
                }
            } else {
                System.out.println("No seleccionado");
            }
        }
        if (e.getComponent() == btnEliminar) {
            DefaultTableModel tb = (DefaultTableModel) tablaNuevoMenu.getModel();
            int a = tablaNuevoMenu.getRowCount() - 1;
            for (int i = a; i >= 0; i--) {
                tb.removeRow(tb.getRowCount() - 1);
            }
        }
        if(e.getComponent()==btnGuardar){
            if(tablaNuevoMenu.getRowCount()!=0){            
                try {
                    Date objDate = new Date();
                    String strDateFormatanio = "yyyy";
                    SimpleDateFormat objSDFanio = new SimpleDateFormat(strDateFormatanio); 
                    String anio = objSDFanio.format(objDate);
                    
                    String strDateFormatmes = "MM";
                    SimpleDateFormat objSDFmes = new SimpleDateFormat(strDateFormatmes); 
                    String mes = objSDFmes.format(objDate);
                    
                    String strDateFormatdia = "dd";
                    SimpleDateFormat objSDFdia = new SimpleDateFormat(strDateFormatdia); 
                    String dia = objSDFdia.format(objDate);
                    int menu;
                    ResultSet rs=gestionProductos.manejador.numMenu();
                    if (rs.first() == false) {
                        menu = 1;
                    } else {
                        rs.last();
                        menu = Integer.parseInt(rs.getObject(1).toString()) + 1;
                    }
                    for (int i = 0; i < tablaNuevoMenu.getRowCount(); i++) {  
                        if(!gestionProductos.manejador.agregarAMenu(menu,Integer.parseInt(modeloNuevoMenu.getValueAt(i, 0).toString()), Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia))){
                            System.out.println("Error en agregarAMenu");
                        }
                        
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CrearMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "Menú guardado");
                gestionProductos.gestionVentas.menuEnTabla();
                this.dispose();
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

}
