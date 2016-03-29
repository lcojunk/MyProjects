/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.entities;

import org.springframework.http.MediaType;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

/**
 *
 * @author odzhara-ongom
 */
public enum BigDocMediaType {

    JPEG(MediaType.IMAGE_JPEG_VALUE),
    PNG(MediaType.IMAGE_PNG_VALUE),
    TEXT(MediaType.TEXT_PLAIN_VALUE);

    private String value;

    private BigDocMediaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getFileExtension() {
        switch (this) {
            case JPEG:
                return ".jpg";
            case PNG:
                return ".png";
            case TEXT:
                return ".txt";
            default:
                return "";
        }
    }

    public MediaType getMediaType() {
        switch (this) {
            case JPEG:
                return MediaType.IMAGE_JPEG;
            case PNG:
                return MediaType.IMAGE_PNG;
            case TEXT:
                return MediaType.TEXT_PLAIN;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @Override
    public String toString() {
        return getValue();
    }

}
