/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.controllers;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.elastic.controllers.CRUDReferenceController;
import de.adesso.referencer.search.elastic.dictionary.controllers.ExcludedWordsService;
import de.adesso.referencer.search.elastic.dictionary.controllers.SuggestWordsController;
import de.adesso.referencer.search.elastic.dictionary.rest.dto.IndexReferenceResponse;
import de.adesso.referencer.search.elastic.dictionary.rest.dto.SuggestionRestReply;
import de.adesso.referencer.search.elastic.entities.BigDocMediaType;
import de.adesso.referencer.search.elastic.entities.BigDocument;
import de.adesso.referencer.search.elastic.entities.BigDocumentType;
import de.adesso.referencer.search.elastic.entities.Reference;
import de.adesso.referencer.search.elastic.services.BigDocumentService;
import de.adesso.referencer.search.elastic.services.ReferenceService;
import de.adesso.referencer.search.rest.dto.GetBigDocumentResponse;
import de.adesso.referencer.search.rest.dto.RestReply;
import de.adesso.referencer.search.rest.dto.SaveBigDocumentRestRequest;
import de.adesso.referencer.search.rest.dto.SaveReferenceRestRequest;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author odzhara-ongom
 */
@RestController
//@RequestMapping(value = {"/dev/api", "/${spring.application.name}" + "/dev/api"})
@RequestMapping(value = {"/dev/api"})
public class DevelopersRestController {

    @Autowired
    private CRUDReferenceController crudReferenceController;

    @Autowired
    private SuggestWordsController suggestWordsController;

    @Autowired
    private ReferenceService referenceService;
    @Autowired
    private BigDocumentService bigDocumentService;

    @RequestMapping(value = "/references", method = RequestMethod.POST)
    public RestReply createReference(@RequestBody Reference entity) {
        return crudReferenceController.saveWithoutDictionary(entity);
    }

    @RequestMapping(value = "/references_and_tokens", method = RequestMethod.POST)
    public RestReply createReferenceAndUpdateDictionary(@RequestBody Reference entity) {
        SaveReferenceRestRequest request = new SaveReferenceRestRequest();
        request.setReference(entity);
        return crudReferenceController.save(request);
    }

    @RequestMapping(value = "/references/_recreate_index", method = RequestMethod.GET)
    public RestReply reCreateReferenceIndex() {
        return crudReferenceController.createIfNotExistsIndex();
    }

    @Deprecated
    @RequestMapping(value = "/references/{id}/document", method = RequestMethod.GET)
    public RestReply<List<GetBigDocumentResponse>> getWordDocumentByReferenceID(@PathVariable String id) {
        return crudReferenceController.getWordDocumentByReferenceID(id);
    }

    @Deprecated
    @RequestMapping(value = "/references/{id}/logo", method = RequestMethod.GET)
    public RestReply<List<GetBigDocumentResponse>> getLogoByReferenceID(@PathVariable String id) {
        return crudReferenceController.getLogoByReferenceID(id);
    }

    @RequestMapping(value = "/dictionary/_recreate_index", method = RequestMethod.GET)
    public RestReply reCreateDictionaryIndex() {
        return suggestWordsController.createIfNotExistsIndex();
    }

    @RequestMapping(value = "/dictionary/reference/{id}", method = RequestMethod.GET)
    public SuggestionRestReply<IndexReferenceResponse> indexReferenceById(@PathVariable String id) {
        return suggestWordsController.indexReferenceById(id);
    }

    @RequestMapping(value = "/dictionary/reference", method = RequestMethod.GET)
    public SuggestionRestReply<String> indexReferenceByCount(@RequestParam("count") int count) {
        return suggestWordsController.indexReferenceByCount(count);
    }

    @RequestMapping(value = "/dictionary/excludedwords", method = RequestMethod.GET)
    public SuggestionRestReply<ExcludedWordsService> getExcludedWords() {
        return suggestWordsController.getExcludedWords();
    }

    //zu löschen
    @RequestMapping(value = "/references/{id}/demo_document", method = RequestMethod.POST)
    public RestReply<BigDocument> attachDemoDocument(@PathVariable String id) {
        RestReply<BigDocument> reply = new RestReply<>();
        try {
            Reference reference = referenceService.getEntity(id);
            if (id == null) {
                reply.setError("Reference with id=" + id + " not found");
                return reply;
            }
            bigDocumentService.deleteByRefId(id);
            reply.setResult(bigDocumentService.addEntity(createDemoDocument(id)));
            reply.getResult().setData("all_data_are_saved=");
            reply.setSuccess(true);
            return reply;
        } catch (Exception e) {
            reply.setError(e.getMessage());
            return reply;
        }
    }

    @RequestMapping(value = "/references/{id}/demo_logo_png", method = RequestMethod.POST)
    public RestReply<BigDocument> attachDemoLogoPNG(@PathVariable String id) {
        RestReply<BigDocument> reply = new RestReply<>();
        try {
            Reference reference = referenceService.getEntity(id);
            if (id == null) {
                reply.setError("Reference with id=" + id + " not found");
                return reply;
            }
            bigDocumentService.deleteByRefId(id);
            reply.setResult(bigDocumentService.addEntity(createDemoLogoPNG(id)));
            reply.getResult().setData("all_data_are_saved=");
            reply.setSuccess(true);
            return reply;
        } catch (Exception e) {
            reply.setError(e.getMessage());
            return reply;
        }
    }

    @RequestMapping(value = "/references/{id}/demo_logo_jpg", method = RequestMethod.POST)
    public RestReply<BigDocument> attachDemoLogoJPG(@PathVariable String id) {
        RestReply<BigDocument> reply = new RestReply<>();
        try {
            Reference reference = referenceService.getEntity(id);
            if (id == null) {
                reply.setError("Reference with id=" + id + " not found");
                return reply;
            }
            bigDocumentService.deleteByRefId(id);
            reply.setResult(bigDocumentService.addEntity(createDemoLogoJPG(id)));
            reply.getResult().setData("all_data_are_saved=");
            reply.setSuccess(true);
            return reply;
        } catch (Exception e) {
            reply.setError(e.getMessage());
            return reply;
        }
    }

    private BigDocument createDemoDocument(String refId) {
        if (refId == null) {
            return null;
        }
        BigDocument bigDocument = new BigDocument();
        bigDocument.setRefId(refId);
        bigDocument.setType(BigDocumentType.DOCUMENT);
        bigDocument.setMediaType(BigDocMediaType.TEXT);
        byte[] data = null;
        try {
            Path path = ResourceUtils.getFile("classpath:" + "testreference.docx").toPath();
            data = Files.readAllBytes(path);
            bigDocument.setData(Base64Utils.encodeToString(data));
            return bigDocument;
        } catch (Exception ex) {
            Logger.getLogger(DevelopersRestController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private BigDocument createDemoLogoPNG(String refId) {
        if (refId == null) {
            return null;
        }
        BigDocument bigDocument = new BigDocument();
        bigDocument.setRefId(refId);
        bigDocument.setType(BigDocumentType.LOGO);
        bigDocument.setMediaType(BigDocMediaType.PNG);
        byte[] data = null;
        try {
            Path path = ResourceUtils.getFile("classpath:" + "NoLogo.png").toPath();
            data = Files.readAllBytes(path);
            bigDocument.setData(Base64Utils.encodeToString(data));
            return bigDocument;
        } catch (Exception ex) {
            Logger.getLogger(DevelopersRestController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private BigDocument createDemoLogoJPG(String refId) {
        if (refId == null) {
            return null;
        }
        BigDocument bigDocument = new BigDocument();
        bigDocument.setRefId(refId);
        bigDocument.setType(BigDocumentType.LOGO);
        bigDocument.setMediaType(BigDocMediaType.JPEG);
        byte[] data = null;
        try {
            Path path = ResourceUtils.getFile("classpath:" + "NoLogo.jpg").toPath();
            data = Files.readAllBytes(path);
            bigDocument.setData(Base64Utils.encodeToString(data));
            return bigDocument;
        } catch (Exception ex) {
            Logger.getLogger(DevelopersRestController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
