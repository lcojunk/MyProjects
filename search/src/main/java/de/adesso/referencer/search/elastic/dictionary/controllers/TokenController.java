/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers;

import de.adesso.referencer.search.elastic.dictionary.controllers.dto.SplitStringInTokenException;
import de.adesso.referencer.search.elastic.dictionary.entities.Token;
import de.adesso.referencer.search.elastic.dictionary.entities.TokenList;
import de.adesso.referencer.search.elastic.entities.Reference;
import de.adesso.referencer.search.helper.ElasticConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author odzhara-ongom
 */
public class TokenController {

    public static final String DEFAULT_TOKEN_ANALYZER = "standard";
    public static final String DEFAULT_INDEX_NAME = "";

    public static List<Token> splitStringInTokensWithElasticSearch(String string, String indexname, String analyzer) throws SplitStringInTokenException {
        if (string == null) {
            return null;
        }
        if (string.matches("")) {
            return new ArrayList<Token>();
        }
        String analyzerString = DEFAULT_TOKEN_ANALYZER;
        if (analyzer != null) {
            analyzerString = analyzer;
        }
        String index = DEFAULT_INDEX_NAME;
        if (indexname != null) {
            index = indexname;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = ElasticConfig.getURL() + "/" + index + "/_analyze?analyzer=" + analyzerString + "&&text=\"" + string;
        TokenList result = null;
        try {
            result = restTemplate.getForObject(url, TokenList.class);
        } catch (Exception e) {
            SplitStringInTokenException ex = (new SplitStringInTokenException(
                    "Exception by splitting string: " + string
                    + " with analyzer " + analyzerString
                    + " from index " + index))
                    .addParentException(e);
            throw ex;
        }
        if (result != null) {
            return result.getTokens();
        } else {
            return null;
        }
    }

    public static Map<String, Long> countWordsInTokens(List<Token> tokens) {
        Map<String, Long> result = new HashMap();
        if (tokens == null) {
            return result;
        }
        Long count = null;
        for (Token token : tokens) {
            if (token != null && token.getToken() != null) {
                count = result.get(token.getToken());
                if (count == null || count <= 0) {
                    result.put(token.getToken(), 1L);
                } else {
                    result.put(token.getToken(), count + 1L);
                }
            }

        }
        return result;
    }

    public static Map<String, Long> mapStringInTokens(String string, String indexname, String analyzer) throws SplitStringInTokenException {
        List<Token> tokens = splitStringInTokensWithElasticSearch(string, indexname, analyzer);
        Map<String, Long> result = countWordsInTokens(tokens);
        return result;
    }

    /**
     * ******************************************************************************************************
     */
    public static List<Token> splitStringInTokensWithElasticSearch(String string) throws SplitStringInTokenException {
        return splitStringInTokensWithElasticSearch(string, null, null);
    }

    public static List<Token> splitStringInTokensWithElasticSearch(String string, String analyzer) throws SplitStringInTokenException {
        return splitStringInTokensWithElasticSearch(string, null, analyzer);
    }

    public static List<Token> findTokensByToken(String token, List<Token> tokenList) {
        if (token == null || tokenList == null) {
            return null;
        }
        List<Token> result = new ArrayList<>();
        if (tokenList.isEmpty()) {
            return result;
        }
        for (Token currentToken : tokenList) {
            if (currentToken != null && currentToken.getToken() != null && currentToken.getToken().matches(token)) {
                result.add(currentToken);
            }
        }
        return result;
    }

    public static boolean existStringInTockenList(String string, List<Token> tokenList) {
        List<Token> tList = findTokensByToken(string, tokenList);
        if (tList != null && tList.size() > 0) {
            return true;
        }
        return false;
    }

    public static List<Token> addOnlyNewTokens(List<Token> oldList, List<Token> newTokens) {
        if (oldList == null && newTokens == null) {
            return null;
        }
        if (oldList == null && (newTokens != null)) {
            oldList = new ArrayList<>();
            for (Token token : newTokens) {
                oldList.add(token);
            }
            return oldList;
        }
        if (oldList != null && newTokens == null) {
            return oldList;
        }
        List<Token> result = new ArrayList<>();
        for (Token token : newTokens) {
            if (token.getToken() != null) {
                if (!existStringInTockenList(token.getToken(), oldList)) {
                    oldList.add(token);
                }
            }
        }
        return oldList;
    }

    public static List<Token> getTokensFromReference(Reference reference) throws SplitStringInTokenException {
        if (reference == null) {
            return null;
        }
        List<Token> result = new ArrayList<>();

        result = addOnlyNewTokens(result, splitStringInTokensWithElasticSearch(reference.getPreciseDescription()));
        result = addOnlyNewTokens(result, splitStringInTokensWithElasticSearch(reference.getProjectBackground()));
        return result;
    }

}
