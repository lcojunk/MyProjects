/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.entities;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;

/**
 *
 * @author odzhara-ongom
 */
@Document(indexName = ElasticSearchConfig.INDEX_NAME, type = ElasticSearchConfig.TYPE_NAME_BIG_DOCUMENT)
public class BigDocument {

    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private BigDocumentType type;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private BigDocMediaType mediaType;

    @Field(type = FieldType.String, index = FieldIndex.no)
    private String data;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String refId;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
