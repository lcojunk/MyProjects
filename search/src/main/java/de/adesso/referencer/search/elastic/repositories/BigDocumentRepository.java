/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.repositories;

import de.adesso.referencer.search.elastic.entities.BigDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author odzhara-ongom
 */
public interface BigDocumentRepository extends ElasticsearchRepository<BigDocument, String> {

    public List<BigDocument> findByRefId(String refId);

    public List<BigDocument> findByRefIdAndType(String refId, String type);

    public List<BigDocument> deleteByRefId(String refId);

    public List<BigDocument> removeByRefId(String refId);

    void delete(BigDocument entity);
}
