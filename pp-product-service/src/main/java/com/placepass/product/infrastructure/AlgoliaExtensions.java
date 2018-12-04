package com.placepass.product.infrastructure;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.algolia.search.saas.APIClient;
import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.NotFoundException;

@Component
public class AlgoliaExtensions {
  
	@Autowired
	@Qualifier("algoliaAPIClient")
    private APIClient client;

    public Index initializeAlgolia(String indexName) {
    	Index index = client.initIndex(indexName);
        return index;
    }

    public JSONObject search(Index index, Query query, Integer pagination) {
        JSONObject searchResult = null;
        try {
            index.setSettings(new JSONObject().append("paginationLimitedTo", pagination));
            searchResult = index.search(query);
        } catch (AlgoliaException e) {
            if (e.getCode() == 400) {
                throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "Bad request");
            } else if (e.getCode() == 404) {
                throw new NotFoundException(HttpStatus.NOT_FOUND.name(), "Resource does not exist");
            } else {
                // TODO
                e.printStackTrace();
            }
        }
        return searchResult;
    }

    public JSONObject getObjectById(Index index, String id) {
        JSONObject productJson = null;
        try {
            productJson = index.getObject(id);
        } catch (AlgoliaException e) {
            if (e.getCode() == 400) {
                throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "Bad request");
            } else if (e.getCode() == 404) {
                throw new NotFoundException(HttpStatus.NOT_FOUND.name(), "Resource does not exist");
            } else {
                // TODO
                e.printStackTrace();
            }
        }
        return productJson;
    }

    public JSONObject getObjectsByIds(Index index, List<String> ids) {
        JSONObject productJson = null;
        try {
            productJson = index.getObjects(ids);
        } catch (AlgoliaException e) {
            if (e.getCode() == 400) {
                throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "Bad request");
            } else if (e.getCode() == 404) {
                throw new NotFoundException(HttpStatus.NOT_FOUND.name(), "Resource does not exist");
            } else {
                // TODO
                e.printStackTrace();
            }
        }
        return productJson;
    }

}
