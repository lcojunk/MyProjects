/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services.dto;

/**
 *
 * @author odzhara-ongom
 */
public class IndexGramFilter extends IndexFilter {

    private int min_gram, max_gram;

    public IndexGramFilter() {
    }

    public IndexGramFilter(String type) {
        super(type);
    }

    public IndexGramFilter(String type, int min_gram, int max_gram) {
        super.setType(type);
        this.min_gram = min_gram;
        this.max_gram = max_gram;
    }

    public int getMin_gram() {
        return min_gram;
    }

    public void setMin_gram(int min_gram) {
        this.min_gram = min_gram;
    }

    public int getMax_gram() {
        return max_gram;
    }

    public void setMax_gram(int max_gram) {
        this.max_gram = max_gram;
    }

}
