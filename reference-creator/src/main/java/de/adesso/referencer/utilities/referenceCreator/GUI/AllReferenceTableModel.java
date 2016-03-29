/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.GUI;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import de.adesso.referencer.utilities.referenceCreator.database.AllBranches;
import de.adesso.referencer.utilities.referenceCreator.database.AllLobs;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author odzhara-ongom
 */
public class AllReferenceTableModel extends AbstractTableModel {
    private Set <TableModelListener> listeners = new HashSet<TableModelListener>();
    private List <ReferenceEntity> entityList;
    private int columnCount;
    private String [] columnNamen;
    private Class[] spaltenTypes;
    private boolean[] canEdit;


    public AllReferenceTableModel(List <ReferenceEntity> entity) {
        this.entityList = entity;        
        columnNamen= new String [] {
                      "Nr", "Id", "Projectname", "Kundenname", "Branche","Lob","Zeitraum","Freigabestatus"
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
       // ReferenceEntity aRow = entityList.get(rowIndex);
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
        ReferenceEntity aRow = entityList.get(rowIndex);
        String result;
        switch (columnIndex) {
            case 0:
                return rowIndex+1+"";
            case 1:
                return aRow.getId();
            case 2:
                return aRow.getProjectname();
            case 3:
                return aRow.getClientname();
            case 4:
                if(aRow.getBranchList()!=null) {
                    if (aRow.getBranchList().size()>0){                       
                        Branch entity=AllBranches.getInstance().getEntityById(
                                                    aRow.getBranchList().get(0));
                        if(entity!=null) {
                            result=entity.getName();
                            return result;                            
                        }                        
                    }
                } else return null;
            case 5:
                if(aRow.getLob()!=null) {                                      
                    LOB entity=AllLobs.getInstance().getEntityById(
                                                aRow.getLob());
                    if(entity!=null) {
                        result=entity.getName();
                        return result;                            
                    }                                            
                } else return null;
            case 6:
                if (aRow.getTimeInterval()!=null){
                    TimeInterval entity = aRow.getTimeInterval();
                    if (entity.startTime==null) result="StartDatum unbekannt - ";
                    else result=MyHelpMethods.Date2String(entity.startTime)+" - ";
                    if (entity.startTime==null) result+="dauert an";
                    else result+=MyHelpMethods.Date2String(entity.endTime);
                    return result;                        
                } else return null;
            case 7: return ElasticConfig.referenceStatus2String(aRow.getFreigabestatus());
        }
        return "";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
