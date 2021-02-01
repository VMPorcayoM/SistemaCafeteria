package cafeteriafis;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * @author vmpor
 */
public class Agregar extends JFrame implements MouseListener {

    private int tipo;
    private JLabel fondo, lblNombre, lblAPaterno, lblAMaterno, lblProfesion, lblHonorarios, lblTelefono, lblDireccion, lblFechaNac, lblNoControl, lblSemestre, lblCarrera, lblPrecio, lblCombo, lblNickname, lblContrasenia, lblCargo;
    private JTextField txtNombre, txtAPaterno, txtAMaterno, txtProfesion, txtHonorarios, txtTelefono, txtDireccion, txtNoControl, txtPrecio, txtNickname, txtContrasenia;
    private UtilDateModel model = new UtilDateModel();
    private Properties p = new Properties();
    private JComboBox comboTipos, comboEmpleado, comboCargo, comboCarrera, comboSemestre;
    private JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
    private JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    private JButton btnGuardar, btnCancelar;
    private JFrame ventana;

    public Agregar(int tipo, JFrame ventana) {

        this.ventana = ventana;
        this.tipo = tipo;
        switch (tipo) {
            case 1:
                
                this.setTitle("Agregar Empleado");
                lblNombre = new JLabel();
                lblNombre.setText("Nombre:");
                lblNombre.setBounds(50, 50, 180, 20);
                lblNombre.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblNombre);
                txtNombre = new JTextField();
                txtNombre.setBounds(250, 50, 200, 20);
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

                this.add(txtNombre);
                lblAPaterno = new JLabel();
                lblAPaterno.setText("Apellido Paterno:");
                lblAPaterno.setBounds(50, 100, 180, 20);
                lblAPaterno.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblAPaterno);
                txtAPaterno = new JTextField();
                txtAPaterno.setBounds(250, 100, 200, 20);
                txtAPaterno.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtAPaterno.getText().length() == 20) {
                            e.consume();
                        }
                        if (!Character.isLetter(e.getKeyChar()) && !(e.getKeyChar() == KeyEvent.VK_SPACE) && !(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                            e.consume();
                        }
                    }
                });
                this.add(txtAPaterno);
                lblAMaterno = new JLabel();
                lblAMaterno.setText("Apellido Materno:");
                lblAMaterno.setBounds(50, 150, 180, 20);
                lblAMaterno.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblAMaterno);
                txtAMaterno = new JTextField();
                txtAMaterno.setBounds(250, 150, 200, 20);
                txtAMaterno.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtAMaterno.getText().length() == 20) {
                            e.consume();
                        }
                        if (!Character.isLetter(e.getKeyChar()) && !(e.getKeyChar() == KeyEvent.VK_SPACE) && !(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                            e.consume();
                        }
                    }
                });
                this.add(txtAMaterno);
                lblProfesion = new JLabel();
                lblProfesion.setText("Profesión/Estudios:");
                lblProfesion.setBounds(50, 200, 200, 20);
                lblProfesion.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblProfesion);
                txtProfesion = new JTextField();
                txtProfesion.setBounds(250, 200, 200, 20);
                this.add(txtProfesion);
                lblHonorarios = new JLabel();
                lblHonorarios.setText("Honorarios(Mensual):");
                lblHonorarios.setBounds(50, 250, 200, 20);
                lblHonorarios.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblHonorarios);
                txtHonorarios = new JTextField();
                txtHonorarios.setBounds(250, 250, 200, 20);
                txtHonorarios.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtHonorarios.getText().length() == 6) {
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
                this.add(txtHonorarios);
                lblTelefono = new JLabel();
                lblTelefono.setText("Telefono:");
                lblTelefono.setBounds(50, 300, 200, 20);
                lblTelefono.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblTelefono);
                txtTelefono = new JTextField();
                txtTelefono.setBounds(250, 300, 200, 20);
                txtTelefono.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtTelefono.getText().length() == 10) {
                            e.consume();
                        }
                        char caracter = e.getKeyChar();
                        // Verificar si la tecla pulsada no es un digito
                        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                            e.consume();  // ignorar el evento de teclado
                        }
                    }
                });
                this.add(txtTelefono);
                lblDireccion = new JLabel();
                lblDireccion.setText("Dirección:");
                lblDireccion.setBounds(50, 350, 200, 20);
                lblDireccion.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblDireccion);
                txtDireccion = new JTextField();
                txtDireccion.setBounds(250, 350, 200, 20);
                this.add(txtDireccion);
                lblFechaNac = new JLabel();
                lblFechaNac.setText("Fecha de nacimiento:");
                lblFechaNac.setBounds(50, 400, 200, 20);
                lblFechaNac.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblFechaNac);
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                datePicker.setBounds(250, 400, 200, 25);
                datePicker.getModel().addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent event) {
                        if (fechaMayoraActual()){
                            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                            Date fechaActual = new Date();
                            JOptionPane.showMessageDialog(null, "Fecha mayor a la actual");
                            datePicker.getModel().setDate(fechaActual.getYear()+1900, fechaActual.getMonth(), 1);
                        }
                    }
                });
                add(datePicker);
                lblCargo = new JLabel();
                lblCargo.setText("Cargo:");
                lblCargo.setBounds(50, 450, 200, 20);
                lblCargo.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblCargo);
                comboCargo = new JComboBox<Object>();
                comboCargo.setBounds(250, 450, 200, 20);
                comboCargo.addItem("Cajero");
                comboCargo.addItem("Gerente");
                this.add(comboCargo);
                break;
            case 2:
                this.setTitle("Agregar Cuenta");
                lblCombo = new JLabel();
                lblCombo.setText("Empleado:");
                lblCombo.setBounds(50, 150, 180, 20);
                lblCombo.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblCombo);
                comboEmpleado = new JComboBox<Object>();
                comboEmpleado.setBounds(250, 150, 200, 20);
                ResultSet rs = ((GestionCuentas) ventana).manejador.consultaEmpleados();

                try {
                    while (rs.next()) {
                        String nombre = rs.getObject(2).toString();
                        comboEmpleado.addItem(nombre);
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Agregar tipo 2: " + ex.getMessage());
                }

                comboEmpleado.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent itemEvent) {

                    }
                });
                add(comboEmpleado);
                lblNickname = new JLabel();
                lblNickname.setText("Nickname:");
                lblNickname.setBounds(50, 50, 180, 20);
                lblNickname.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblNickname);
                txtNickname = new JTextField();
                txtNickname.setBounds(250, 50, 200, 20);
                txtNickname.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtNickname.getText().length() == 20) {
                            e.consume();
                        }
                    }
                });
                this.add(txtNickname);
                lblContrasenia = new JLabel();
                lblContrasenia.setText("Contraseña:");
                lblContrasenia.setBounds(50, 100, 180, 20);
                lblContrasenia.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblContrasenia);
                txtContrasenia = new JTextField();
                txtContrasenia.setBounds(250, 100, 200, 20);
                txtContrasenia.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtContrasenia.getText().length() == 40) {
                            e.consume();
                        }
                    }
                });
                this.add(txtContrasenia);
                break;
            case 3:
                this.setTitle("Agregar Becario");
                lblNoControl = new JLabel();
                lblNoControl.setText("NoControl:");
                lblNoControl.setBounds(50, 50, 180, 20);
                lblNoControl.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblNoControl);
                txtNoControl = new JTextField();
                txtNoControl.setBounds(250, 50, 200, 20);
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
                lblNombre = new JLabel();
                lblNombre.setText("Nombre:");
                lblNombre.setBounds(50, 100, 180, 20);
                lblNombre.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblNombre);
                txtNombre = new JTextField();
                txtNombre.setBounds(250, 100, 200, 20);
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
                this.add(txtNombre);
                lblAPaterno = new JLabel();
                lblAPaterno.setText("Apellido Paterno:");
                lblAPaterno.setBounds(50, 150, 180, 20);
                lblAPaterno.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblAPaterno);
                txtAPaterno = new JTextField();
                txtAPaterno.setBounds(250, 150, 200, 20);
                txtAPaterno.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtAPaterno.getText().length() == 20) {
                            e.consume();
                        }
                        if (!Character.isLetter(e.getKeyChar()) && !(e.getKeyChar() == KeyEvent.VK_SPACE) && !(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                            e.consume();
                        }
                    }
                });
                this.add(txtAPaterno);
                lblAMaterno = new JLabel();
                lblAMaterno.setText("Apellido Materno:");
                lblAMaterno.setBounds(50, 200, 180, 20);
                lblAMaterno.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblAMaterno);
                txtAMaterno = new JTextField();
                txtAMaterno.setBounds(250, 200, 200, 20);
                txtAMaterno.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtAMaterno.getText().length() == 20) {
                            e.consume();
                        }
                        if (!Character.isLetter(e.getKeyChar()) && !(e.getKeyChar() == KeyEvent.VK_SPACE) && !(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                            e.consume();
                        }
                    }
                });
                this.add(txtAMaterno);
                lblSemestre = new JLabel();
                lblSemestre.setText("Semestre:");
                lblSemestre.setBounds(50, 250, 250, 20);
                lblSemestre.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblSemestre);
                comboSemestre = new JComboBox<Object>();
                comboSemestre.setBounds(250, 250, 200, 20);
                comboSemestre.addItem("1");
                comboSemestre.addItem("2");
                comboSemestre.addItem("3");
                comboSemestre.addItem("4");
                comboSemestre.addItem("5");
                comboSemestre.addItem("6");
                comboSemestre.addItem("7");
                comboSemestre.addItem("8");
                comboSemestre.addItem("9");
                comboSemestre.addItem("10");
                comboSemestre.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent itemEvent) {

                    }
                });
                this.add(comboSemestre);
                lblCarrera = new JLabel();
                lblCarrera.setText("Carrera:");
                lblCarrera.setBounds(50, 300, 300, 20);
                lblCarrera.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblCarrera);
                comboCarrera = new JComboBox<Object>();
                comboCarrera.setBounds(250, 300, 200, 20);
                comboCarrera.addItem("Ing.Sistemas Computacionales");
                comboCarrera.addItem("Ing.Gestion Empresarial");
                comboCarrera.addItem("Contador público");
                comboCarrera.addItem("Ing.Civil");
                comboCarrera.addItem("Ing.Informatica");
                comboCarrera.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent itemEvent) {
                    }
                });
                this.add(comboCarrera);

                break;
            case 4:
                this.setTitle("Agregar Producto");
                lblNombre = new JLabel();
                lblNombre.setText("Nombre:");
                lblNombre.setBounds(50, 50, 180, 20);
                lblNombre.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblNombre);
                txtNombre = new JTextField();
                txtNombre.setBounds(250, 50, 200, 20);
                txtNombre.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtNombre.getText().length() == 40) {
                            e.consume();
                        }
                    }
                });
                this.add(txtNombre);
                lblPrecio = new JLabel();
                lblPrecio.setText("Precio:");
                lblPrecio.setBounds(50, 100, 180, 20);
                lblPrecio.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblPrecio);
                txtPrecio = new JTextField();
                txtPrecio.setBounds(250, 100, 200, 20);
                txtPrecio.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (txtPrecio.getText().length() == 5) {
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
                this.add(txtPrecio);
                lblCombo = new JLabel();
                lblCombo.setText("Tipo:");
                lblCombo.setBounds(50, 150, 180, 20);
                lblCombo.setFont(new Font("Monospaced", Font.BOLD, 16));
                this.add(lblCombo);
                comboTipos = new JComboBox<Object>();
                comboTipos.setBounds(250, 150, 200, 20);
                comboTipos.addItem("Producto de mostrador");
                comboTipos.addItem("Alimento");
                comboTipos.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent itemEvent) {

                    }
                });
                this.add(comboTipos);
                break;
        }

        btnGuardar = new JButton();
        btnGuardar.setText("Guardar");
        btnGuardar.setBounds(200, 480, 100, 35);
        btnGuardar.addMouseListener(this);
        add(btnGuardar);
        btnCancelar = new JButton();
        btnCancelar.setText("Cancelar");
        btnCancelar.setBounds(350, 480, 100, 35);
        btnCancelar.addMouseListener(this);
        add(btnCancelar);

        fondo = new JLabel();
        fondo.setBounds(0, 0, 500, 600);
        fondo.setBackground(new Color(121, 133, 181));
        fondo.setOpaque(true);
        this.add(fondo);
        this.setBounds(0, 0, 500, 600);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public int getTipoProduto() {
        if ("Producto de mostrador".equals(comboTipos.getSelectedItem().toString())) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getComponent() == btnCancelar) {
            this.dispose();
        }
        if (e.getComponent() == btnGuardar) {
            switch (tipo) {
                case 1:

                    if (((GestionEmpleados) ventana).manejador.agregarEmpleado(txtNombre.getText(), txtAPaterno.getText(), txtAMaterno.getText(), txtProfesion.getText(), Double.parseDouble(txtHonorarios.getText()), txtTelefono.getText(), txtDireccion.getText(), datePicker.getModel().getDay(), datePicker.getModel().getMonth(), datePicker.getModel().getYear(), comboCargo.getSelectedItem().toString()) == false) {
                        JOptionPane.showMessageDialog(null, "Error al agregar");
                        this.dispose();
                    }
                    ((GestionEmpleados) ventana).empleadosEnTabla();
                    this.dispose();
                    break;
                case 2:
                    if (((GestionCuentas) ventana).manejador.agregarCuenta(comboEmpleado.getSelectedItem().toString(), txtNickname.getText(), txtContrasenia.getText()) == false) {
                        JOptionPane.showMessageDialog(null, "Error al agregar");
                        this.dispose();
                    }
                    ((GestionCuentas) ventana).cuentasEnTabla();
                    this.dispose();
                    break;
                case 3:
                    if (((GestionBeca) ventana).manejador.agregarBecario(Integer.parseInt(txtNoControl.getText()), txtNombre.getText(), txtAPaterno.getText(), txtAMaterno.getText(), Integer.parseInt(comboSemestre.getSelectedItem().toString()), comboCarrera.getSelectedItem().toString()) == false) {
                        JOptionPane.showMessageDialog(null, "Error al agregar");
                        this.dispose();
                    }
                    ((GestionBeca) ventana).becariosEnTabla();
                    this.dispose();
                    break;
                case 4:
                    if (((GestionProductos) ventana).manejador.agregarProducto(txtNombre.getText(), Integer.parseInt(txtPrecio.getText()), this.getTipoProduto()) == false) {
                        JOptionPane.showMessageDialog(null, "Error al agregar");
                        this.dispose();
                    }
                    ((GestionProductos) ventana).productosEnTabla();
                    this.dispose();
                    break;
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
        String mesP = (datePicker.getModel().getMonth())+1 + "";
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
            } else {
                if (fechaDate2.before(fechaDate1)) {
                    return true;
                } else {
                    return true;
                }
            }
        } catch (ParseException e) {
            System.out.println("Se Produjo un Error!!!  " + e.getMessage());
        }
        return false;
    }
}
