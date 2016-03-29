/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers;

import de.adesso.referencer.search.elastic.controllers.dto.FacetedSearchReferenceRequest;
import de.adesso.referencer.search.elastic.controllers.dto.FacetedSearchReferenceResponseWithEntitiesCount;
import de.adesso.referencer.search.rest.dto.RestReply;
import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.elastic.services.*;
import de.adesso.referencer.search.rest.controllers.FacetedSearchDtoFactory;
import de.adesso.referencer.search.rest.dto.FacetedSearchReferenceRESTRequest;
import de.adesso.referencer.search.rest.dto.FacetedSearchReferenceRESTResponse;
import de.adesso.referencer.search.rest.dto.ReferencesIds;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class RestSearchController {

    @Autowired
    private ReferenceService referenceService;
    @Autowired
    private QueriesController querriesControllers;
    @Autowired
    private BranchService branchService;
    @Autowired
    private LobService lobService;

    @Autowired
    private FacetedSearchDtoFactory facetedSearchDtoFactory;
    @Autowired
    private FacetedSearchController facetedSearchController;

    public RestReply<List<Reference>> getAllReferences() {
        RestReply<List<Reference>> result = new RestReply<>();
        try {
            List<Reference> refList = referenceService.getAll();
            if (refList == null) {
                result.setError("Server returned empty result for request for all reference entities");
                return result;
            }
            result.setCount(refList.size());
            result.setResult(refList);
            result.setSuccess(true);
            return result;
        } catch (Exception ex) {
            result.setError("Exception during request for all reference entities:" + ex.toString());
            return result;
        }
    }

    public RestReply<Reference> getReferencesById(String id) {
        RestReply<Reference> result = new RestReply<>();
        try {
            Reference entity = referenceService.getEntity(id);
            if (entity == null) {
                result.setError("Server returned empty result for request reference Nr." + id);
                return result;
            }
            result.setCount(1);
            result.setResult(entity);
            result.setSuccess(true);
            return result;
        } catch (Exception ex) {
            result.setError("Exception during request for reference Nr." + id + ": " + ex.toString());
            return result;
        }
    }

    public RestReply<List<Branch>> getAllBranches() {
        RestReply<List<Branch>> result = new RestReply<>();
        try {
            List<Branch> refList = branchService.getAll();
            if (refList == null) {
                result.setError("Server returned empty result for request for all branche entities");
                return result;
            }
            result.setCount(refList.size());
            result.setResult(refList);
            result.setSuccess(true);
            return result;
        } catch (Exception ex) {
            result.setError("Exception during request for all branches entities:" + ex.toString());
            return result;
        }
    }

    public RestReply<Branch> getBranchById(String id) {
        RestReply<Branch> result = new RestReply<>();
        try {
            Branch entity = branchService.getEntity(id);
            if (entity == null) {
                result.setError("Server returned empty result for request for branch Nr." + id);
                return result;
            }
            result.setCount(1);
            result.setResult(entity);
            result.setSuccess(true);
            return result;
        } catch (Exception ex) {
            result.setError("Exception during request for branch Nr." + id + ": " + ex.toString());
            return result;
        }
    }

    public RestReply<List<Lob>> getAllLob() {
        RestReply<List<Lob>> result = new RestReply<>();
        try {
            List<Lob> refList = lobService.getAll();
            if (refList == null) {
                result.setError("Server returned empty result for request for all lobs entities");
                return result;
            }
            result.setCount(refList.size());
            result.setResult(refList);
            result.setSuccess(true);
            return result;
        } catch (Exception ex) {
            result.setError("Exception during request for all lobs entities:" + ex.toString());
            return result;
        }
    }

    public RestReply<Lob> getLobById(String id) {
        RestReply<Lob> result = new RestReply<>();
        try {
            Lob entity = lobService.getEntity(id);
            if (entity == null) {
                result.setError("Server returned empty result for request for lob Nr." + id);
                return result;
            }
            result.setCount(1);
            result.setResult(entity);
            result.setSuccess(true);
            return result;
        } catch (Exception ex) {
            result.setError("Exception during request for lob Nr." + id + ": " + ex.toString());
            return result;
        }
    }

    public RestReply<Integer> getMaxPT() {
        RestReply<Integer> result = new RestReply<>();
        try {
            Aggregations aggregations = referenceService.queryForAggregations(
                    querriesControllers.buildQueryForMaxForReference("ptAdesso"));
            Max maxAggregation = (Max) aggregations.asList().get(0);
            int maxValue = (int) maxAggregation.getValue();
            result.setResult(new Integer(maxValue));
            result.setCount(1);
            result.setSuccess(true);

        } catch (Exception e) {
            result.setError("Error finding maximal PT. " + e.toString());
        }
        return result;
    }

    public RestReply<Double> getMaxVol() {
        RestReply<Double> result = new RestReply<>();
        try {
            Aggregations aggregations = referenceService.queryForAggregations(
                    querriesControllers.buildQueryForMaxForReference("costAdesso"));
            Max maxAggregation = (Max) aggregations.asList().get(0);
            double maxValue = maxAggregation.getValue();
            result.setResult(new Double(maxValue));
            result.setCount(1);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setError("Error finding maximal cost. " + e.toString());
        }
        return result;
    }

    public RestReply<FacetedSearchReferenceRESTResponse> facetedSearchForReferenceWithTopHits(FacetedSearchReferenceRESTRequest request) {
        RestReply<FacetedSearchReferenceRESTResponse> result = new RestReply<>();
        FacetedSearchReferenceRequest userRequest = facetedSearchDtoFactory.createFacetedSearchRequest(request);
        FacetedSearchReferenceRESTResponse response;
        FacetedSearchReferenceResponseWithEntitiesCount serverResponse;
        try {
            serverResponse = facetedSearchController.facetedSearchForReferencesWithTopHits(userRequest);
            response = facetedSearchDtoFactory.createFacetedSearchReferenceRESTResponse(serverResponse);
            response.setRequest(request);
            result.setResult(response);
            if (response.getReferences() != null) {
                result.setCount(response.getReferences().size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(result.getError() + " Error! Error searching for references. " + e.getMessage());
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    public RestReply<List<ReferencesIds>> getAllReferencesIds() {
        RestReply<List<ReferencesIds>> result = new RestReply<>();
        try {
            List<Reference> references = referenceService.getAll();
            if (references == null) {
                result.setError("Server returned empty result for request for all reference ids");
                result.setSuccess(false);
                return result;
            }
            List<ReferencesIds> ids = new ArrayList<>();
            ReferencesIds refId;
            for (Reference reference : references) {
                refId = new ReferencesIds();
                refId.setId(reference.getId());
                refId.setProjectName(reference.getProjectname());
                refId.setClientName(reference.getClientname());
                refId.setDescription(reference.getDescription());
                ids.add(refId);
            }
            result.setResult(ids);
            result.setCount(ids.size());
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(result.getError() + " Error! Error searching for references. " + e.getMessage());
            return result;
        }
    }

}
