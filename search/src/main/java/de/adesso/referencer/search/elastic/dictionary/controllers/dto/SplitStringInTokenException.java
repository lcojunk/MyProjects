/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers.dto;

/**
 *
 * @author odzhara-ongom
 */
public class SplitStringInTokenException extends Exception {

    private static final long serialVersionUID = 1501201612412107009L;

    private Exception parentException;

    public Exception getParentException() {
        return parentException;
    }

    public void setParentException(Exception parentException) {
        this.parentException = parentException;
    }

    public SplitStringInTokenException addParentException(Exception parentException) {
        this.parentException = parentException;
        return this;
    }

    public SplitStringInTokenException() {
    }

    public SplitStringInTokenException(String message) {
        super(message);
    }

    public SplitStringInTokenException(Throwable cause) {
        super(cause);
    }

    public SplitStringInTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public SplitStringInTokenException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
