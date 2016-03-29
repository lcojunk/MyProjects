/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.config;

import de.adesso.referencer.search.helper.MyHelpMethods;

import java.util.Date;

/**
 *
 * @author odzhara-ongom
 */
public class ElasticSearchConfig {

    public static final String TEST_ENDING = "";
    public static final boolean SKIP_ALL_TEST = (TEST_ENDING.length() == 0);
    public static final String INDEX_NAME = "reference_v4" + TEST_ENDING;

    public static final String TYPE_NAME_REFERENCE = "springdata_reference";

    public static final String TYPE_NAME_BIG_DOCUMENT = "springdata_big_document";

    public static final String DICTIONARY_INDEX_NAME = "references_dictionary" + TEST_ENDING;
    public static final String DICTIONARY_TYPE_NAME = "references_dictionary_word";
    public static final String DICTIONARY_SUGGEST_FIELD_NAME = "suggest";

    public static final String PROJECT_END_FIELD_NAME = "projectEnd";
    public static final String SERVICE_START_FIELD_NAME = "serviceStart";
    public static final String SERVICE_END_FIELD_NAME = "serviceEnd";
    public static final String PT_ADESSO_FIELD_NAME = "ptAdesso";
    public static final String COST_ADESSO_FIELD_NAME = "costAdesso";
    public static final String LOB_FIELD_NAME = "lob";
    public static final String BRANCH_FIELD_NAME = "branches";
    public static final String TECHNOLOGY_FIELD_NAME = "technologies";
    public static final String ID_FIELD_NAME = "id";

    public static final String ELASTIC_STANDARD_FILTER_NAME = "standard";
    public static final String ELASTIC_LOWERCASE_FILTER_NAME = "lowercase";

    public static final String SORT_FIELD = "sorted";
    public static final String RAW_FIELD = "raw";
    public static final String REFERENCE_INDEX_ANALYZER_NAME = "my_index_analyzer";
    public static final String REFERENCE_SEARCH_ANALYZER_NAME = ELASTIC_STANDARD_FILTER_NAME;
    public static final String ELASTIC_SEARCH_ANALYZER_NAME = "my_search_analyzer";
    public static final String EMAIL_ANALYZER_NAME = "emailAnalyzer";
    public static final String CONTACT_INDEX_ANALYZER_NAME = EMAIL_ANALYZER_NAME;
    public static final String CONTACT_SEARCH_ANALYZER_NAME = EMAIL_ANALYZER_NAME;

    public static final String SUGGESTWORD_ANALYZER_NAME = "suggest_words";
    public static final String ELASTIC_LOWERCASE_TOKENIZER = "lowercase";
    public static final String SUGGESTWORD_INDEX_ANALYZER_NAME = SUGGESTWORD_ANALYZER_NAME;
    public static final String SUGGESTWORD_SEARCH_ANALYZER_NAME = SUGGESTWORD_ANALYZER_NAME;

    public static final String GERMANY_LANGUAGE_FILTER = "germany_language_filter";
    public static final String GERMANY_PHONEBOOK_FILTER = "germany_phonebook_filter";
    public static final String GERMANY_LANGUAGE_ANALYZER = "germany_language_sorter";
    public static final String GERMANY_PHONEBOOK_ANALYZER = "germany_phonebook_sorter";

    public static final Date MINIMAL_DATE = MyHelpMethods.addYearsToDate(new Date(), -6000);
    public static final Date MAXIMAL_DATE = MyHelpMethods.addYearsToDate(new Date(), 6000);

    public static final int MAXIMUM_PAGE_SIZE = Integer.MAX_VALUE;

    public static final String REST_MAIN_PATH = "/referencer/search";
    public static final String REST_HELP_PATH_ENDING = "/help";
    //public static String EXCLUDED_WORDS_FILENAME = "ExcludedWordsList.txt";
    public static final String EXCLUDED_WORDS_FILENAME = "dictionary/ExcludedWordsList.txt";

}
