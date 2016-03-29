/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class SuggestWordsPayLoad {

    private List<String> ref_id;

    public List<String> getRef_id() {
        return ref_id;
    }

    public void setRef_id(List<String> ref_id) {
        this.ref_id = ref_id;
    }

    public SuggestWordsPayLoad addRef(String ref) {
        if (ref == null) {
            return this;
        }
        if (this.ref_id == null) {
            ref_id = new ArrayList<>();
        }
        ref_id.add(ref);
        return this;
    }

}
