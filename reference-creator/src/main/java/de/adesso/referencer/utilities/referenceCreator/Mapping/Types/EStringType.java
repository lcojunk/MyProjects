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
public class EStringType extends EFlatFieldType{
    
    
/*    public boolean store = false;
    
    public static enum Analyzed {analyzed, not_analyzed, no}
    public Analyzed index=Analyzed.analyzed;
    public double boost=1.0;
*/    
    public static enum TermVector {no, yes, with_offsets, with_positions, with_positions_offsets}
    public static enum IndexOptions {docs,freqs,positions,offsets};
    
    public TermVector term_vector=TermVector.no;
    public IndexOptions index_options=null;    
    public String analyzer;
    public String index_analyzer;
    public String search_analyzer;          
    public EStringType (){
        type="string";
        store=false;
        index=Analyzed.analyzed;
        /*
            Set to analyzed for the field to be indexed and searchable after 
            being broken down into token using an analyzer. not_analyzed means 
            that its still searchable, but does not go through any analysis 
            process or broken down into tokens. no means that it won’t be 
            searchable at all (as an individual field; it may still be included 
            in _all). Setting to no disables include_in_all. 
            Defaults to analyzed        
        */
        boost=1.0;
    }


    
}
