/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.Raw;

import java.util.Date;

/**
 *
 * @author odzhara-ongom
 */
public class TimeInterval {
    public Date startTime;
    public Date endTime;
    
    public TimeInterval(){    }

    public TimeInterval(Date von, Date bis) {
        this.startTime = von;
        this.endTime = bis;
    }
    


}
