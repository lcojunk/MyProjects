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
public class AllPersonAddressTableModel extends AbstractTableModel {
    private Set <TableModelListener> listeners = new HashSet<TableModelListener>();
    private List <PersonAddress> entityList;
    private int columnCount;
    private String [] columnNamen;
    private Class[] spaltenTypes;
    private boolean[] canEdit;

    public AllPersonAddressTableModel(List <PersonAddress> entity) {
        this.entityList = entity;        
        columnNamen= new String [] {
                      "Art", "PLZ", "Land", "Stadt", "Straße", "Beschreibung"
        };
        columnCount=columnNamen.length;
        spaltenTypes = new Class [columnCount];
        canEdit = new boolean [columnCount];
        for (int i=0; i<columnCount; i++) {
            spaltenTypes[i]=java.lang.String.class;
            canEdit[i]=false;
        };
    }    
    
    
    
    @Override
    public int getRowCount() {
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
        PersonAddress aRow = entityList.get(rowIndex);
            switch (columnIndex) {
            case 0:
                aRow.setType((String)aValue); break;
            case 1:
                aRow.setPlz((String)aValue);  break;
            case 2:
                aRow.setCountry((String) aValue); break; 
            case 3:
                aRow.setCity((String) aValue); break; 
            case 4:
                aRow.setStreet((String) aValue); break; 
            case 5:
                aRow.setDescription((String) aValue); break; 
            }       
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PersonAddress aRow = entityList.get(rowIndex);
            switch (columnIndex) {
            case 0:
                return aRow.getType();
            case 1:
                return aRow.getPlz();
            case 2:
                return aRow.getCountry();
            case 3:
                return aRow.getCity();
            case 4:
                return aRow.getStreet();
            case 5:
                return aRow.getDescription();
            }
            return "";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
