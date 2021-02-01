/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteriafis;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vmpor
 */
public class ModeloTablas extends DefaultTableModel {

    public ModeloTablas(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }
    

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;  //
    }

}
