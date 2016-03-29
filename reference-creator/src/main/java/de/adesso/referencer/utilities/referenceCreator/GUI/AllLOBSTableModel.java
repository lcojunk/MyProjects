/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.GUI;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.LOB;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author odzhara-ongom
 */
public class AllLOBSTableModel extends AbstractTableModel {
    private Set <TableModelListener> listeners = new HashSet<TableModelListener>();
    private List <LOB> lobs;
    private int columnCount;
    private String [] columnNamen;
    private Class[] spaltenTypes;
    private boolean[] canEdit;

    public AllLOBSTableModel(List <LOB> lobs) {
        this.lobs = lobs;        
        columnNamen= new String [] {
                      "Id", "Name", "Description"
        };
        columnCount=columnNamen.length;
        spaltenTypes = new Class [] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class
        };
        canEdit = new boolean [] {
                false, false, false
         };
    }    
    
    
    
    @Override
    public int getRowCount() {
        return lobs.size();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        return columnCount;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNamen[columnIndex];
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
@Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        LOB aRow = lobs.get(rowIndex);
            switch (columnIndex) {
            case 0:
                aRow.setId((String)aValue); break;
            case 1:
                aRow.setName((String)aValue);  break;
            case 2:
                aRow.setDescription((String) aValue); break; 
            }       
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LOB aRow = lobs.get(rowIndex);
            switch (columnIndex) {
            case 0:
                return aRow.getId();
            case 1:
                return aRow.getName();
            case 2:
                return aRow.getDescription();
            }
            return "";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
