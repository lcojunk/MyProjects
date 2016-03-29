/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.dto;

import de.adesso.referencer.search.elastic.entities.*;

/**
 *
 * @author odzhara-ongom
 */
public class SaveReferenceRestRequest {

    private Reference reference;
    private BigDocument document;
    private BigDocument logo;

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public BigDocument getDocument() {
        return document;
    }

    public void setDocument(BigDocument document) {
        this.document = document;
    }

    public BigDocument getLogo() {
        return logo;
    }

    public void setLogo(BigDocument logo) {
        this.logo = logo;
    }

}
