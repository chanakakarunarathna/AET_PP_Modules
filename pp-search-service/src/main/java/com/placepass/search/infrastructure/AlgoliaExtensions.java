package com.placepass.search.infrastructure;

import java.util.ArrayList;
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
import com.placepass.utils.vendorproduct.Vendor;
import com.placepass.utils.vendorproduct.VendorProduct;

@Component
public class AlgoliaExtensions {

    @Autowired
    @Qualifier("algoliaAPIClient")
    private APIClient client;

    public Index initializeAlgolia(String indexName) {
        Index index = client.initIndex(indexName);
        return index;
    }

    public JSONObject search(Index index, Query query) {
        JSONObject searchResult = null;
        try {
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

    public List<String> getVendor(List<VendorProduct> vendorProducts) {
        List<String> vendorName = null;
        if (vendorProducts != null) {
            vendorName = new ArrayList<>();
            for (VendorProduct vendorProduct : vendorProducts) {
                Vendor vendor = vendorProduct.getVendor();
                vendorName.add(switchVendors(vendor));
            }
        }
        return vendorName;
    }

    private String switchVendors(Vendor vendor) {
        String vendorName = null;
        switch (vendor) {
            case VIATOR:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case URBANA:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case MUSEME:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case GETYGU:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case TKTMST:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case PROEXP:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case TIQETS:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case IFONLY:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case ISANGO:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case HADOUT:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case BEMYGT:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case CTYDSY:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            case GOBEEE:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;    
            case VIREAL:
                vendorName = vendor.getLabel();
                break;
            case MARRWD:
            		vendorName = vendor.getLabel().replaceAll(" ", "");
            		break;
            case SPGRWD:
            		vendorName = vendor.getLabel().replaceAll(" ", "");
            		break;
            case STBHUB:
            		vendorName = vendor.getLabel().replaceAll(" ", "");
            		break;
            case MARRTT:
                vendorName = vendor.getLabel().replaceAll(" ", "");
                break;
            default:
                break;
        }
        return vendorName;

    }
}
