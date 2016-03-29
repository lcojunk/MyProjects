/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.controllers;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.elastic.controllers.FacetedSearchController;
import de.adesso.referencer.search.elastic.controllers.dto.FacetedSearchReferenceRequest;
import de.adesso.referencer.search.elastic.controllers.dto.FacetedSearchReferenceResponse;
import de.adesso.referencer.search.elastic.controllers.dto.FacetedSearchReferenceResponseWithEntitiesCount;
import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.elastic.services.BranchService;
import de.adesso.referencer.search.elastic.services.LobService;
import de.adesso.referencer.search.elastic.services.TechnologyService;
import de.adesso.referencer.search.helper.MyHelpMethods;
import de.adesso.referencer.search.rest.dto.EntitiesCount;
import de.adesso.referencer.search.rest.dto.FacetedSearchReferenceRESTRequest;
import de.adesso.referencer.search.rest.dto.FacetedSearchReferenceRESTResponse;
import de.adesso.referencer.search.rest.dto.LimitedReference;
import de.adesso.referencer.search.rest.dto.RestPageRequest;
import de.adesso.referencer.search.rest.dto.SortOptionsDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class FacetedSearchDtoFactory {

    @Autowired
    private BranchService branchService;
    @Autowired
    private LobService lobService;
    @Autowired
    private TechnologyService technologyService;
    @Autowired
    private FacetedSearchController facetedSearchController;
    private static final String ERROR_SEARCH_WITH_NULL_ID = "Error! Entity was searched with no id";
    private static final String ERROR_BY_SEARCH = "Error searching for entity";

    private static final int DATE_OPTIONS_DELAY_3_YEAR = 3;
    private static final int DATE_OPTIONS_DELAY_5_YEAR = 5;
    private static final int DATE_OPTIONS_DELAY_1_YEAR = 1;
    private static final int DATE_OPTIONS_DELAY_NOT_FINISHED = 0;
    private static final int[] dateOptionsNumbers = {
        DATE_OPTIONS_DELAY_NOT_FINISHED,
        DATE_OPTIONS_DELAY_1_YEAR,
        DATE_OPTIONS_DELAY_3_YEAR,
        DATE_OPTIONS_DELAY_5_YEAR
    };

    private String[] getDateOptionsNames() {
        return facetedSearchController.getDateRangeKeys();
    }

    private Integer[] checkAndGetPT(Integer[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Integer[] result = new Integer[2];
        if (values.length == 1) {
            result[0] = values[0];
            return result;
        }
        if (values[0] == null || values[1] == null) {
            result[0] = values[0];
            result[1] = values[1];
            return result;
        }
        if (values[0] > values[1]) {
            result[0] = values[1];
            result[1] = values[0];
        } else {
            result[0] = values[0];
            result[1] = values[1];
        }
        return result;
    }

    private Double[] checkAndGetCost(Double[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Double[] result = new Double[2];
        if (values.length == 1) {
            result[0] = values[0];
            return result;
        }
        if (values[0] == null || values[1] == null) {
            result[0] = values[0];
            result[1] = values[1];
            return result;
        }
        if (values[0] > values[1]) {
            result[0] = values[1];
            result[1] = values[0];
        } else {
            result[0] = values[0];
            result[1] = values[1];
        }
        return result;
    }

    private Date[] checkAndGetDate(Integer value) {
        if (value == null) {
            return null;
        }
        Date[] result = new Date[2];
        Date today = new Date();
        if (value == 0) {
            result[0] = today;
            return result;
        }
        if (value == 1 || value == 3 || value == 5) {

            result[0] = MyHelpMethods.addYearsToDate(today, -value);
            result[1] = today;
            return result;
        }
        return null;
    }

    private PageRequest checkAndGetPageRequest(RestPageRequest page) {
        if (page == null) {
            return new PageRequest(0, ElasticSearchConfig.MAXIMUM_PAGE_SIZE);
        }
        return new PageRequest(page.getPage(), page.getSize());
    }

    private List<EntitiesCount<Lob>> getLobsById(HashMap<String, Long> entities) {
        if (entities == null) {
            return null;
        }
        List<EntitiesCount<Lob>> result = new ArrayList<>();
        Set<String> ids = entities.keySet();
        if (ids == null) {
            return result;
        }
        Lob entity;
        EntitiesCount<Lob> eCount;
        for (String id : ids) {
            entity = lobService.getEntity(id);
            eCount = new EntitiesCount<>();
            eCount.setEntity(entity);
            eCount.setCount(entities.get(id));
            result.add(eCount);
        }
        return result;
    }

    private List<EntitiesCount<Branch>> getBranchById(HashMap<String, Long> entities) {
        if (entities == null) {
            return null;
        }
        List<EntitiesCount<Branch>> result = new ArrayList<>();
        Set<String> ids = entities.keySet();
        if (ids == null) {
            return result;
        }
        Branch entity;
        EntitiesCount<Branch> eCount;
        for (String id : ids) {
            entity = branchService.getEntity(id);
            eCount = new EntitiesCount<>();
            eCount.setEntity(entity);
            eCount.setCount(entities.get(id));
            result.add(eCount);
        }
        return result;
    }

    private List<EntitiesCount<Technology>> getTechById(HashMap<String, Long> entities) {
        if (entities == null) {
            return null;
        }
        List<EntitiesCount<Technology>> result = new ArrayList<>();
        Set<String> ids = entities.keySet();
        if (ids == null) {
            return result;
        }
        Technology entity;
        EntitiesCount<Technology> eCount;
        for (String id : ids) {
            entity = technologyService.getEntity(id);
            eCount = new EntitiesCount<>();
            eCount.setEntity(entity);
            eCount.setCount(entities.get(id));
            result.add(eCount);
        }
        return result;
    }

    public FacetedSearchReferenceRequest createFacetedSearchRequest(FacetedSearchReferenceRESTRequest userRequest) {
        FacetedSearchReferenceRequest result = new FacetedSearchReferenceRequest();
        if (userRequest == null) {
            return result;
        }
        result.setFreeText(userRequest.getFreeText());
        result.setLobsId(userRequest.getLobs());
        result.setBranchesId(userRequest.getBranches());
        result.setTechnologyId(userRequest.getTechnologies());
        result.setPtSpan(checkAndGetPT(userRequest.getPtSpan()));
        result.setCostSpan(checkAndGetCost(userRequest.getCostSpan()));
        result.setProjectEndSpan(checkAndGetDate(userRequest.getProjectEndOption()));
        result.setServiceEndSpan(checkAndGetDate(userRequest.getServiceEndOption()));
        result.setServiceContract(userRequest.getServiceContract());
        result.setPage(checkAndGetPageRequest(userRequest.getPage()));
        result.setSort(checkAndGetSort(userRequest.getSort()));

        return result;
    }

    private LimitedReference createLimitedReference(Reference reference) {
        if (reference == null) {
            return null;
        }
        LimitedReference result = new LimitedReference();
        result.setId(reference.getId());
        result.setClientname(reference.getClientname());
        result.setProjectname(reference.getProjectname());
        result.setPtAdesso(reference.getPtAdesso());
        result.setCostAdesso(reference.getCostAdesso());
        result.setProjectStart(reference.getProjectStart());
        result.setProjectEnd(reference.getProjectEnd());
        result.setServiceEnd(reference.getServiceEnd());
        result.setBranches(reference.getBranches());
        result.setLob(reference.getLob());
        result.setTechnologies(reference.getTechnologies());
        result.setReleaseStatus(reference.getReleaseStatus());
        result.setServiceContract((reference.getServiceStart() != null));
        result.setDocumentCreationTime(reference.getDocumentCreationTime());
        if (reference.getReleased() != null) {
            result.setReleaseDate(reference.getReleased().getDate());
        }
        return result;
    }

    private List<LimitedReference> createLimitedReferences(List<Reference> references) {
        if (references == null) {
            return null;
        }
        List<LimitedReference> result = new ArrayList<>();
        for (Reference reference : references) {
            result.add(createLimitedReference(reference));
        }
        return result;
    }

    private int getPTMin(FacetedSearchReferenceResponse serverResponse) {
        if (serverResponse.getPtStatistic() == null || serverResponse.getPtStatistic()[0] == null) {
            return 0;
        }
        return serverResponse.getPtStatistic()[0];
    }

    private int getPTMax(FacetedSearchReferenceResponse serverResponse) {
        if (serverResponse.getPtStatistic() == null || serverResponse.getPtStatistic().length < 2 || serverResponse.getPtStatistic()[1] == null) {
            return 0;
        }
        return serverResponse.getPtStatistic()[1];
    }

    private double getCostMin(FacetedSearchReferenceResponse serverResponse) {
        if (serverResponse.getCostStatistic() == null || serverResponse.getCostStatistic()[0] == null) {
            return 0;
        }
        return serverResponse.getCostStatistic()[0];
    }

    private double getCostMax(FacetedSearchReferenceResponse serverResponse) {
        if (serverResponse.getCostStatistic() == null || serverResponse.getCostStatistic().length < 2 || serverResponse.getCostStatistic()[1] == null) {
            return 0;
        }
        return serverResponse.getCostStatistic()[1];
    }

    private int getPTMin(FacetedSearchReferenceResponseWithEntitiesCount serverResponse) {
        if (serverResponse.getPtStatistic() == null || serverResponse.getPtStatistic()[0] == null) {
            return 0;
        }
        return serverResponse.getPtStatistic()[0];
    }

    private int getPTMax(FacetedSearchReferenceResponseWithEntitiesCount serverResponse) {
        if (serverResponse.getPtStatistic() == null || serverResponse.getPtStatistic().length < 2 || serverResponse.getPtStatistic()[1] == null) {
            return 0;
        }
        return serverResponse.getPtStatistic()[1];
    }

    private double getCostMin(FacetedSearchReferenceResponseWithEntitiesCount serverResponse) {
        if (serverResponse.getCostStatistic() == null || serverResponse.getCostStatistic()[0] == null) {
            return 0;
        }
        return serverResponse.getCostStatistic()[0];
    }

    private double getCostMax(FacetedSearchReferenceResponseWithEntitiesCount serverResponse) {
        if (serverResponse.getCostStatistic() == null || serverResponse.getCostStatistic().length < 2 || serverResponse.getCostStatistic()[1] == null) {
            return 0;
        }
        return serverResponse.getCostStatistic()[1];
    }

    private List<Integer> getDateOptions(HashMap<String, Long> map) {
        List<Integer> result = new ArrayList<>();
        if (map.get(getDateOptionsNames()[0]) != null
                && map.get(getDateOptionsNames()[0]).longValue() > 0) {
            result.add(DATE_OPTIONS_DELAY_NOT_FINISHED);
            result.add(DATE_OPTIONS_DELAY_1_YEAR);
            result.add(DATE_OPTIONS_DELAY_3_YEAR);
            result.add(DATE_OPTIONS_DELAY_5_YEAR);
            return result;
        }
        for (int i = 1; i < getDateOptionsNames().length; i++) {
            if (map.get(getDateOptionsNames()[i]) != null
                    && map.get(getDateOptionsNames()[i]).longValue() > 0) {
                result.add(dateOptionsNumbers[i - 1]);
            }
        }
        return result;
    }

    public FacetedSearchReferenceRESTResponse createFacetedSearchReferenceRESTResponse(FacetedSearchReferenceResponse serverResponse) {
        if (serverResponse == null) {
            return null;
        }
        FacetedSearchReferenceRESTResponse result = new FacetedSearchReferenceRESTResponse();
        result.setPtMin(getPTMin(serverResponse));
        result.setPtMax(getPTMax(serverResponse));
        result.setCostMin(getCostMin(serverResponse));
        result.setCostMax(getCostMax(serverResponse));
        result.setLobStatistic(getLobsById(serverResponse.getLobsStatistic()));
        result.setBranchStatistic(getBranchById(serverResponse.getBranchStatistic()));
        result.setTechnologyStatistic(getTechById(serverResponse.getTechStatistic()));
        result.setProjectEndOptions(getDateOptions(serverResponse.getProjectEndStatistic()));
        result.setServiceEndOptions(getDateOptions(serverResponse.getServiceEndStatistic()));
        result.setReferences(createLimitedReferences(serverResponse.getReferences()));
        result.setTotalPages(serverResponse.getTotalPages());
        result.setTotalObjects(serverResponse.getTotalObjects());
        return result;
    }

    private HashMap<FacetedSearchReferenceRESTResponse.ServiceContractOptions, Long> createServiceContractStatistic(
            HashMap<FacetedSearchReferenceResponseWithEntitiesCount.ServiceContractFields, Long> serviceContractStatistic) {
        HashMap<FacetedSearchReferenceRESTResponse.ServiceContractOptions, Long> result = new HashMap<>();
        if (serviceContractStatistic == null) {
            return null;
        }
        result.put(FacetedSearchReferenceRESTResponse.ServiceContractOptions.FIELD_EXISTS,
                serviceContractStatistic.get(FacetedSearchReferenceResponseWithEntitiesCount.ServiceContractFields.FIELD_EXISTS));
        result.put(FacetedSearchReferenceRESTResponse.ServiceContractOptions.FIELD_NOT_EXISTS,
                serviceContractStatistic.get(FacetedSearchReferenceResponseWithEntitiesCount.ServiceContractFields.FIELD_NOT_EXISTS));
        return result;
    }

    public FacetedSearchReferenceRESTResponse createFacetedSearchReferenceRESTResponse(FacetedSearchReferenceResponseWithEntitiesCount serverResponse) {
        if (serverResponse == null) {
            return null;
        }
        FacetedSearchReferenceRESTResponse result = new FacetedSearchReferenceRESTResponse();
        result.setPtMin(getPTMin(serverResponse));
        result.setPtMax(getPTMax(serverResponse));
        result.setCostMin(getCostMin(serverResponse));
        result.setCostMax(getCostMax(serverResponse));
        result.setLobStatistic(serverResponse.getLobsStatistic());
        result.setBranchStatistic(serverResponse.getBranchStatistic());
        result.setTechnologyStatistic(serverResponse.getTechStatistic());
        result.setProjectEndOptions(getDateOptions(serverResponse.getProjectEndStatistic()));
        result.setServiceEndOptions(getDateOptions(serverResponse.getServiceEndStatistic()));
        result.setReferences(createLimitedReferences(serverResponse.getReferences()));
        result.setTotalPages(serverResponse.getTotalPages());
        result.setTotalObjects(serverResponse.getTotalObjects());
        result.setServiceContractStatistic(createServiceContractStatistic(serverResponse.getServiceContractStatistic()));
        return result;
    }

    private SortBuilder checkAndGetSort(SortOptionsDTO sort) {
        if (sort == null || sort.getType() == null) {
            return null;
        }

        SortOrder sortOrder = SortOrder.DESC;
        String sortMode = "max";
        if (sort.getDescending() == null || sort.getDescending().booleanValue() == false) {
            sortOrder = SortOrder.ASC;
            sortMode = "min";
        }

        switch (sort.getType()) {
            case CLIENTNAME:
                return SortBuilders.fieldSort("clientname." + ElasticSearchConfig.SORT_FIELD).order(sortOrder);
            case PROJECTNAME:
                return SortBuilders.fieldSort("projectname." + ElasticSearchConfig.SORT_FIELD).order(sortOrder);
            case PROJECTSTART:
                return SortBuilders.fieldSort("projectStart").order(sortOrder);
            case PROJECTEND:
                return SortBuilders.fieldSort("projectEnd").order(sortOrder);
            case PT:
                return SortBuilders.fieldSort("ptAdesso").order(sortOrder);
            case VOLUMES:
                return SortBuilders.fieldSort("costAdesso").order(sortOrder);
            case STATUS:
                return SortBuilders.fieldSort("releaseStatus").order(sortOrder);
            case LOB:
                return SortBuilders.fieldSort("lob.name.name." + ElasticSearchConfig.SORT_FIELD).setNestedPath("lob").order(sortOrder);
            case BRANCH:
                return SortBuilders.fieldSort("branches.name.name." + ElasticSearchConfig.SORT_FIELD).order(sortOrder).sortMode(sortMode);
            case TECHNOLOGY:
                return SortBuilders.fieldSort("technologies.name.name." + ElasticSearchConfig.SORT_FIELD).order(sortOrder).sortMode(sortMode);
            case DOCUMENT_CREATION_TIME:
                return SortBuilders.fieldSort("documentCreationTime").order(sortOrder);
            case RELEASED_TIME:
                return SortBuilders.fieldSort("released.date").order(sortOrder);
            default:
                return null;
        }
    }
}
