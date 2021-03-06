/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.GUI;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author odzhara-ongom
 */
public class AllStringTableModel extends AbstractTableModel {
    private Set <TableModelListener> listeners = new HashSet<TableModelListener>();
    private String name="";
    private List <String> entityList;
    private int columnCount;
    private String [] columnNamen;
    private Class[] spaltenTypes;
    private boolean[] canEdit;

    public AllStringTableModel(List <String> entity, String name) {
        if(name!=null) this.name=name;
        this.entityList = entity;        
        columnNamen= new String [] {
                      "Nr", this.name
        };
        columnCount=columnNamen.length;
        spaltenTypes = new Class [] {
                java.lang.String.class,java.lang.String.class
        };
        canEdit = new boolean [] {
                false, false
         };
    }    
    
    
    
    @Override
    public int getRowCount() {
        if (entityList==null) return 0;
        return entityList.size();
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
        String aRow = entityList.get(rowIndex);
            switch (columnIndex) {
            case 0:
               // aRow.setId((String)aValue); break;
            case 1:
                aRow=((String)aValue);  break;
            }       
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String aRow = entityList.get(rowIndex);
            switch (columnIndex) {
            case 0:
                return rowIndex+"";
            case 1:
                return aRow;
            }
            return "";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
