/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers;

import de.adesso.referencer.search.elastic.dictionary.controllers.SuggestWordsService;
import de.adesso.referencer.search.elastic.dictionary.entities.WordFromReference;
import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.elastic.entities.Technology;
import de.adesso.referencer.search.elastic.services.AdessoRoleService;
import de.adesso.referencer.search.elastic.services.BigDocumentService;
import de.adesso.referencer.search.elastic.services.BranchService;
import de.adesso.referencer.search.elastic.services.CharacteristicService;
import de.adesso.referencer.search.elastic.services.FocusService;
import de.adesso.referencer.search.elastic.services.InvolvedRoleService;
import de.adesso.referencer.search.elastic.services.LobService;
import de.adesso.referencer.search.elastic.services.ProjectRoleService;
import de.adesso.referencer.search.elastic.services.ReferenceService;
import de.adesso.referencer.search.elastic.services.TechnologyService;
import de.adesso.referencer.search.elastic.services.UserService;
import de.adesso.referencer.search.rest.dto.GetBigDocumentResponse;
import de.adesso.referencer.search.rest.dto.RestReply;
import de.adesso.referencer.search.rest.dto.SaveBigDocumentRestRequest;
import de.adesso.referencer.search.rest.dto.SaveReferenceRestRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class CRUDReferenceController {

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private AdessoRoleService adessoRoleService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private CharacteristicService characteristicService;

    @Autowired
    private FocusService focusService;

    @Autowired
    private InvolvedRoleService involvedRoleService;

    @Autowired
    private ProjectRoleService projectRoleService;

    @Autowired
    private TechnologyService technologyService;

    @Autowired
    private UserService userService;

    @Autowired
    private LobService lobService;

    @Autowired
    private BigDocumentService bigDocumentService;

    @Autowired
    private SuggestWordsService suggestWordsService;

    public RestReply save(SaveReferenceRestRequest request) {
        RestReply reply = new RestReply();
        try {
            if (request == null || request.getReference() == null) {
                reply.setError("Error! Request or Reference is leer");
                return reply;
            }
            SaveReferenceRestRequest result = new SaveReferenceRestRequest();
            Reference entity = request.getReference();
            if (entity != null && entity.getId() != null) {
                bigDocumentService.deleteByRefId(entity.getId());
            }
            result.setReference(request.getReference());
            result.setDocument(request.getDocument());
            result.setLogo(request.getLogo());
            reply.setResult(result);
            //TODO add validation for reference id in files
            result.setReference(referenceService.addEntity(entity));
            result.setDocument(bigDocumentService.addEntity(request.getDocument()));
            result.setLogo(bigDocumentService.addEntity(request.getLogo()));
            reply.setResult(result);
            if (result.getReference() != null) {
                List<WordFromReference> words = suggestWordsService.indexReference(entity);
                if (words == null || words.size() <= 0) {
                    reply.setError("Warning! No Tokens was found in reference");
                }
            }
            reply.setSuccess(true);
            return reply;
        } catch (Exception e) {
            e.printStackTrace();
            reply.setError(e.getMessage());
            return reply;
        }
    }

    public RestReply saveWithoutDictionary(Reference entity) {
        RestReply result = new RestReply();
        try {
            result.setResult(referenceService.addEntity(entity));
            if (result.getResult() != null) {
                result.setSuccess(true);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            return result;
        }
    }

    //depricated
    public RestReply saveAndCreateLinkedEntities(Reference entity) {
        RestReply result = new RestReply();
        try {
            if (entity.getBranches() != null && entity.getBranches().size() > 0) {
                for (Branch subEntity : entity.getBranches()) {
                    branchService.addEntity(subEntity);
                }
            }
            if (entity.getCharacteristics() != null && entity.getCharacteristics().size() > 0) {
                for (Characteristic subEntity : entity.getCharacteristics()) {
                    characteristicService.addEntity(subEntity);
                }
            }
            if (entity.getFocuses() != null && entity.getFocuses().size() > 0) {
                for (Focus subEntity : entity.getFocuses()) {
                    focusService.addEntity(subEntity);
                }
            }
            if (entity.getInvolvedRoles() != null && entity.getInvolvedRoles().size() > 0) {
                for (InvolvedRole subEntity : entity.getInvolvedRoles()) {
                    involvedRoleService.addEntity(subEntity);
                }
            }
            if (entity.getLob() != null) {
                lobService.addEntity(entity.getLob());
            }
            if (entity.getAdessoDeputyContactProjectRole() != null) {
                projectRoleService.addEntity(entity.getAdessoDeputyContactProjectRole());
            }
            if (entity.getAdessoContactProjectRole() != null) {
                projectRoleService.addEntity(entity.getAdessoContactProjectRole());
            }
            if (entity.getTechnologies() != null && entity.getTechnologies().size() > 0) {
                for (Technology subEntity : entity.getTechnologies()) {
                    technologyService.addEntity(subEntity);
                }
            }
            if (entity.getLob() != null) {
                lobService.addEntity(entity.getLob());
            }
            if (entity.getOwner() != null) {
                if (entity.getOwner().getRole() != null) {
                    adessoRoleService.addEntity(entity.getOwner().getRole());
                }
                userService.addEntity(entity.getOwner());
            }
            if (entity.getEditor() != null) {
                if (entity.getEditor().getRole() != null) {
                    adessoRoleService.addEntity(entity.getEditor().getRole());
                }
                userService.addEntity(entity.getEditor());
            }
            if (entity.getqAssurance() != null) {
                if (entity.getqAssurance().getRole() != null) {
                    adessoRoleService.addEntity(entity.getqAssurance().getRole());
                }
                userService.addEntity(entity.getqAssurance());
            }
            if (entity.getApprover() != null) {
                if (entity.getApprover().getRole() != null) {
                    adessoRoleService.addEntity(entity.getApprover().getRole());
                }
                userService.addEntity(entity.getApprover());
            }
            if (entity.getAdessoContactAdessoRole() != null) {
                adessoRoleService.addEntity(entity.getAdessoContactAdessoRole());
            }
//            if (entity.getDeputy() != null) {
//                if (entity.getDeputy().getRole() != null) {
//                    adessoRoleService.addEntity(entity.getDeputy().getRole());
//                }
//                userService.addEntity(entity.getDeputy());
//            }
//            if (entity.getAdessoPartner() != null) {
//                if (entity.getAdessoPartner().getRole() != null) {
//                    adessoRoleService.addEntity(entity.getAdessoPartner().getRole());
//                }
//                userService.addEntity(entity.getAdessoPartner());
//            }

            if (entity.getTeammembers() != null && entity.getTeammembers().size() > 0) {
                for (User subEntity : entity.getTeammembers()) {
                    if (subEntity.getRole() != null) {
                        adessoRoleService.addEntity(subEntity.getRole());
                    }
                    userService.addEntity(subEntity);
                }
            }
            result.setResult(referenceService.addEntity(entity));
            if (result.getResult() != null) {
                result.setSuccess(true);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            return result;
        }
    }

    public RestReply getReferenceById(String id) {
        RestReply result = new RestReply();
        try {
            result.setResult(referenceService.getEntity(id));
            if (result.getResult() != null) {
                result.setSuccess(true);
                result.setCount(1);
                return result;
            } else {
                result.setError("Something happens, no enttity found!");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            return result;
        }
    }

    public RestReply getLobById(String id) {
        RestReply result = new RestReply();
        try {
            result.setResult(lobService.getEntity(id));
            if (result.getResult() != null) {
                result.setSuccess(true);
                result.setCount(1);
                return result;
            } else {
                result.setError("Something happens, no enttity found!");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            return result;
        }
    }

    public RestReply indexExists() {
        RestReply result = new RestReply();
        try {
            Boolean reply = new Boolean(referenceService.indexExists());
            result.setResult(reply);
            result.setSuccess(reply.booleanValue());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            result.setResult(new Boolean(false));
            return result;
        }
    }

    public RestReply deleteIndex() {
        RestReply result = new RestReply();
        try {
            if (referenceService.indexExists()) {
                Boolean reply = new Boolean(referenceService.deleteIndex());
                result.setResult(reply);
                result.setSuccess(true);
                return result;
            }
            result.setResult(new Boolean(false));
            result.setError("Index not existed");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            result.setResult(new Boolean(false));
            return result;
        }
    }

    public RestReply createIndex() {
        RestReply result = new RestReply();
        try {
            if (!referenceService.indexExists()) {
                if (!referenceService.createIndex()) {
                    result.setResult(new Boolean(false));
                    result.setError("Cannot create index");
                    return result;
                }
                if (!referenceService.putMapping()) {
                    result.setResult(new Boolean(false));
                    result.setError("Cannot map data in the index");
                    return result;
                }
                result.setResult(new Boolean(true));
                result.setSuccess(true);
                return result;
            }
            result.setResult(new Boolean(false));
            result.setError("Index already existed");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            result.setResult(new Boolean(false));
            return result;
        }
    }

    public RestReply createIfNotExistsIndex() {
        RestReply result = new RestReply();
        try {
            if (referenceService.indexExists()) {
                if (!referenceService.deleteIndex()) {
                    result.setResult(new Boolean(false));
                    result.setError("Index exists, but cannot be deleted");
                    return result;
                }
            }
            if (!referenceService.createIndex()) {
                result.setResult(new Boolean(false));
                result.setError("Cannot create index");
                return result;
            }
            if (!referenceService.putMapping()) {
                result.setResult(new Boolean(false));
                result.setError("Cannot map data in the index");
                return result;
            }
            result.setResult(new Boolean(true));
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            result.setResult(new Boolean(false));
            return result;
        }
    }

    private boolean isDataStringValid(String string) {
        if (string == null) {
            return false;
        }
        return true;
    }

    private BigDocMediaType mimeTypeStringToBigDocMediaType(String mimeType) {
        if (mimeType == null) {
            return null;
        }
        if (mimeType.matches(MimeTypeUtils.IMAGE_JPEG_VALUE)) {
            return BigDocMediaType.JPEG;
        }
        if (mimeType.matches(MimeTypeUtils.TEXT_PLAIN_VALUE)) {
            return BigDocMediaType.TEXT;
        }
        //return BigDocMediaType.OTHER;
        return BigDocMediaType.TEXT;
    }

    private String bigDocMediaTypeToMimeTypeString(BigDocMediaType elasticString) {
        if (elasticString == null) {
            return null;
        }
        if (elasticString == BigDocMediaType.JPEG) {
            return MimeTypeUtils.IMAGE_JPEG_VALUE;
        }
        if (elasticString == BigDocMediaType.TEXT) {
            return MimeTypeUtils.TEXT_PLAIN_VALUE;
        }
        return MimeTypeUtils.ALL_VALUE;
    }

    private List<BigDocument> getLogoByRefID(String refId) {
        if (refId == null) {
            return new ArrayList<>();
        }
        List<BigDocument> bigDocuments = bigDocumentService.getEntityByReferenceIdAndType(refId, BigDocumentType.LOGO);
        return bigDocuments;
    }

    private List<BigDocument> getDocumentByRefID(String refId) {
        if (refId == null) {
            return new ArrayList<>();
        }
        List<BigDocument> bigDocuments = bigDocumentService.getEntityByReferenceIdAndType(refId, BigDocumentType.DOCUMENT);
        return bigDocuments;
    }

    public ResponseEntity<ByteArrayResource> downloadDocumentByRefId(String id) {
        Reference reference = referenceService.getEntity(id);
        if (reference == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<BigDocument> bigDocuments = getDocumentByRefID(id);
        HttpHeaders headers = new HttpHeaders();

        if (bigDocuments == null || bigDocuments.size() <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        String filename;
        if (reference.getClientname() == null) {
            filename = "document" + ".docx";
        } else {
            filename = reference.getClientname().replace(" ", "_") + ".docx";
        }
        headers.add("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        BigDocument bigDocument = bigDocuments.get(0);
        try {
            byte[] data = Base64Utils.decodeFromString(bigDocument.getData());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(data.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new ByteArrayResource(data));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    public ResponseEntity<ByteArrayResource> downloadLogoByRefId(String id) {
        Reference reference = referenceService.getEntity(id);
        if (reference == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<BigDocument> bigDocuments = getLogoByRefID(id);
        HttpHeaders headers = new HttpHeaders();

        if (bigDocuments == null || bigDocuments.size() <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        BigDocument bigDocument = bigDocuments.get(0);
        String extension;
        if (bigDocument.getMediaType() == null) {
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        } else {
            extension = bigDocument.getMediaType().getFileExtension();
        }
        String filename = "logo" + extension;
        headers.add("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        try {
            byte[] data = Base64Utils.decodeFromString(bigDocument.getData());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(data.length)
                    .contentType(bigDocument.getMediaType().getMediaType())
                    .body(new ByteArrayResource(data));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    @Deprecated
    public RestReply<BigDocument> saveOrUpdateBigDocument(SaveBigDocumentRestRequest request, BigDocumentType type) {
        RestReply<BigDocument> result = new RestReply();
        try {
            if (request == null) {
                result.setCount(-1);
                result.setError("Cannot save empty entity");
                return result;
            }
            if (!isDataStringValid(request.getData())) {
                result.setCount(-2);
                result.setError("Data not valid");
                return result;
            }
            if (request.getRefId() == null
                    || referenceService.getEntity(request.getRefId()) == null) {
                result.setCount(-3);
                result.setError("Reference with id=" + request.getRefId() + " was not found");
                return result;
            }
            BigDocument bigDocument = new BigDocument();
            bigDocument.setId(request.getId());
            bigDocument.setData(request.getData());
            //bigDocument.setMediaType(mimeTypeStringToBigDocMediaType(request.getMediaType()));
            bigDocument.setMediaType(request.getMediaType());
            if (type == null) {
                bigDocument.setType(request.getType());
            } else {
                bigDocument.setType(request.getType());
            }
            bigDocument.setRefId(request.getRefId());
            bigDocument.setDescription(request.getDescription());
            bigDocumentService.addEntity(bigDocument);
            result.setResult(bigDocument);
            result.setCount(1);
            result.setSuccess(true);
            return result;
        } catch (Exception ex) {
            result.setCount(0);
            result.setError(ex.toString());
            return result;
        }
    }

    @Deprecated
    public RestReply<BigDocument> saveOrUpdateLogoDocument(SaveBigDocumentRestRequest request) {
        return saveOrUpdateBigDocument(request, BigDocumentType.LOGO);
    }

    @Deprecated
    public RestReply<BigDocument> saveOrUpdateWordDocument(SaveBigDocumentRestRequest request) {
        return saveOrUpdateBigDocument(request, BigDocumentType.DOCUMENT);
    }

    @Deprecated
    public RestReply<List<GetBigDocumentResponse>> getBigDocumentByReferenceID(String id, BigDocumentType type) {
        RestReply< List<GetBigDocumentResponse>> reply = new RestReply<>();
        try {
            if (id == null) {
                reply.setCount(-1);
                reply.setError("Cannot look for reference with null id");
                return reply;
            }
            List<BigDocument> bigDocuments = null;
            if (type == null) {
                bigDocuments = bigDocumentService.getEntityByReferenceId(id);
            } else {
                bigDocuments = bigDocumentService.getEntityByReferenceIdAndType(id, type);
            }
            if (bigDocuments == null) {
                reply.setCount(-2);
                reply.setError("Request returned null result");
                return reply;
            }
            GetBigDocumentResponse bigDocumentResponse;
            List<GetBigDocumentResponse> result = new ArrayList<>();
            for (BigDocument bigDocument : bigDocuments) {
                bigDocumentResponse = new GetBigDocumentResponse();
                bigDocumentResponse.setId(bigDocument.getId());
                bigDocumentResponse.setData(bigDocument.getData());
                bigDocumentResponse.setRefId(bigDocument.getRefId());
                //bigDocumentResponse.setMediaType(bigDocMediaTypeToMimeTypeString(bigDocument.getMediaType()));
                bigDocumentResponse.setMediaType(bigDocument.getMediaType());
                bigDocumentResponse.setType(bigDocument.getType());
                bigDocumentResponse.setDescription(bigDocument.getDescription());
                result.add(bigDocumentResponse);
            }
            reply.setResult(result);
            reply.setSuccess(true);
            return reply;
        } catch (Exception ex) {
            reply.setCount(0);
            reply.setError(ex.toString());
            return reply;
        }

    }

    @Deprecated
    public RestReply<List<GetBigDocumentResponse>> getWordDocumentByReferenceID(String id) {
        return getBigDocumentByReferenceID(id, BigDocumentType.DOCUMENT);
    }

    @Deprecated
    public RestReply<List<GetBigDocumentResponse>> getLogoByReferenceID(String id) {
        return getBigDocumentByReferenceID(id, BigDocumentType.LOGO);
    }
}
