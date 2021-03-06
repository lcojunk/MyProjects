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
public class AllElasticUserTableModel extends AbstractTableModel {
    private Set <TableModelListener> listeners = new HashSet<TableModelListener>();
    private List <ElasticUser> entityList;
    private int columnCount;
    private String [] columnNamen;
    private Class[] spaltenTypes;
    private boolean[] canEdit;

    public AllElasticUserTableModel(List <ElasticUser> entity) {
        this.entityList = entity;        
        columnNamen= new String [] {
                      "Nr", "Id", "Username", "Group", "Name, Vorname"
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
        ElasticUser aRow = entityList.get(rowIndex);
            switch (columnIndex) {
/*                
            case 0:
                aRow.setId((String)aValue); break;
            case 1:
                aRow.setName((String)aValue);  break;
            case 2:
                aRow.setDescription((String) aValue); break; 
                
 */           }       
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ElasticUser aRow = entityList.get(rowIndex);
        String result;
        switch (columnIndex) {
            case 0:
                return rowIndex+1+"";
            case 1:
                return aRow.getId();
            case 2:
                return aRow.getUsername();
            case 3:
                return aRow.getGroup();
            case 4:
                if(aRow.getPerson()!=null) {
                    result=aRow.getPerson().getForename()+" "+aRow.getPerson().getName();
                    return result;
                }
        }
        return "";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
