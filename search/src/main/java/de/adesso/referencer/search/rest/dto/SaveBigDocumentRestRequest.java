/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.dto;

import de.adesso.referencer.search.elastic.entities.BigDocMediaType;
import de.adesso.referencer.search.elastic.entities.BigDocumentType;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;

/**
 *
 * @author odzhara-ongom
 */
public class SaveBigDocumentRestRequest {

    private String id;
    private BigDocumentType type;
    private BigDocMediaType mediaType;
    private String data;
    private String refId;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDocumentType getType() {
        return type;
    }

    public void setType(BigDocumentType type) {
        this.type = type;
    }

    public BigDocMediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(BigDocMediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
