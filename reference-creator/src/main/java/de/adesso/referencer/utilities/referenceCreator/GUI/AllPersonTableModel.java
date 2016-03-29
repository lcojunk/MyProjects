/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.GUI;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import de.adesso.referencer.utilities.referenceCreator.database.AllAdessoRole;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author odzhara-ongom
 */
public class AllPersonTableModel extends AbstractTableModel {
    private Set <TableModelListener> listeners = new HashSet<TableModelListener>();
    private List <Person> entityList;
    private int columnCount;
    private String [] columnNamen;
    private Class[] spaltenTypes;
    private boolean[] canEdit;

    public AllPersonTableModel(List <Person> entity) {
        this.entityList = entity;        
        columnNamen= new String [] {
                      "Nr", "ID", "Name", "Tel", "Email", "Adresse","Rolle bei Adesso"
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
        Person aRow = entityList.get(rowIndex);
/*            switch (columnIndex) {
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
 */       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person aRow = entityList.get(rowIndex);
        String result;
        switch (columnIndex) {
            case 0:
                return rowIndex+1+"";
            case 1:
                return aRow.getId();
            case 2:
                result="";
                if (aRow.getTitel()!=null) result+=aRow.getTitel()+" ";
                if (aRow.getForename()!=null) result+=aRow.getForename()+" ";
                if (aRow.getName()!=null) result+=aRow.getName();                
                return result;
            case 3:
                result="";
                if (aRow.getTel()!=null) 
                    if(aRow.getTel().size()>0) result+=aRow.getTel().get(0)+"";
                return result;
            case 4:
                result="";
                if (aRow.getEmail()!=null) 
                    if(aRow.getEmail().size()>0) result+=aRow.getEmail().get(0)+"";
                return result;
            case 5:
                result="";
                if (aRow.getAddress()!=null) 
                    if(aRow.getAddress().size()>0) {
                        PersonAddress pa=aRow.getAddress().get(0);
                        if (pa!=null) {
                            if (pa.getStreet()!=null) result+=pa.getStreet()+", ";
                            if (pa.getCity()!=null) result+=pa.getCity()+" ";
                            if (pa.getPlz()!=null) result+=pa.getPlz()+"";                            
                        }
                    }
                return result;
            case 6: if (aRow.getRole()!=null){
                String s=AllAdessoRole.getInstance().getEntityById(aRow.getRole()).getName();
                if (s!=null) return s;
            }
            }
            return "";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
