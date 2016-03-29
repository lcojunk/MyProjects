/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.search;

import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class RestReplyReference {

    private boolean success;
    private List<Reference> result;
    private long count;
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Reference> getResult() {
        return result;
    }

    public void setResult(List<Reference> result) {
        this.result = result;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
