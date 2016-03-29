/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.entities;

import de.adesso.referencer.search.helper.MyHelpMethods;
import java.util.Date;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 *
 * @author odzhara-ongom
 */
public class AccessStamp {

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date date;
    @Field(type = FieldType.String)
    private String dateString;

    @Field(type = FieldType.Nested)
    private User user;

    @Field(type = FieldType.String)
    private String action;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        if (date != null) {
            this.dateString = MyHelpMethods.dateToSearchString(date);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

}
