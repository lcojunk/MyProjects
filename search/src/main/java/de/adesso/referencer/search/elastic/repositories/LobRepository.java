/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.repositories;

import de.adesso.referencer.search.elastic.entities.Lob;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author odzhara-ongom
 */
public interface LobRepository extends ElasticsearchRepository <Lob, String> {

    public List<Lob> findByName(String name);
    
}
