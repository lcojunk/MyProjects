/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.repositories;

import de.adesso.referencer.search.elastic.entities.ProjectRole;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author odzhara-ongom
 */
public interface ProjectRoleRepository extends ElasticsearchRepository <ProjectRole, String> {

    public List<ProjectRole> findByName(String name);
    
}
