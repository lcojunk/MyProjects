/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Mapping.Types;

/**
 *
 * @author odzhara-ongom
 */
public class EDateType extends EFlatFieldType {

    public String format="dateOptionalTime";//date_optional_time";//basic_date_time";
    public EDateType(){
        type="date";
    }
}
