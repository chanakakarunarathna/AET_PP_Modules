package com.placepass.search.application.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.search.application.search.request.ProductSearchRequest;
import com.placepass.search.application.search.request.ProductsRequest;
import com.placepass.search.application.search.request.ShelvesRequest;
import com.placepass.search.application.search.response.Product;
import com.placepass.search.application.search.response.ProductsResponse;
import com.placepass.search.application.search.response.SearchResponse;
import com.placepass.search.application.search.response.ShelvesResponse;
import com.placepass.search.infrastructure.AlgoliaSearchClient;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.VendorProduct;

@Service
public class SearchService {

    @Autowired
    private AlgoliaSearchClient algoliaSearchClient;

    @Autowired
    @Qualifier("ProductHashGenerator")
    private ProductHashGenerator productHashGenerator;

    @Value("#{'${placepass.vendorids}'.split(',')}")
    private List<String> vendorIdList;

    public SearchResponse getSearchResults(String partnerId, String vendorId, String tags, ProductSearchRequest psr)
            throws JSONException {
        List<String> vendorIds = null;
        List<VendorProduct> vendorProducts = new ArrayList<>();
        if (vendorId != null) {
            if (vendorId.contains(",")) {
                vendorIds = new ArrayList<String>(Arrays.asList(vendorId.split(",")));
            } else {
                vendorIds = new ArrayList<>();
                vendorIds.add(vendorId);
            }
            try {
                if (vendorIdList.containsAll(vendorIds)) {
                    for (String vId : vendorIds) {
                        VendorProduct vc = VendorProduct.getInstance(vId, productHashGenerator);
                        vendorProducts.add(vc);
                    }
                } else {
                    throw new BadRequestException("INVALID_VENDOR_ID", "provided vendor-id is invalid");
                }

            } catch (Exception e) {

                throw new BadRequestException("INVALID_VENDOR_ID", "provided vendor-id is invalid");
            }
        }

        List<String> requestTags = new ArrayList<>();
        if (tags != null) {
            requestTags = new ArrayList<>(Arrays.asList(tags.split(",")));
        }

        SearchResponse res = algoliaSearchClient.searchProducts(psr, partnerId, vendorProducts, requestTags);
        return res;
    }

    @Deprecated
    public ShelvesResponse getShelfResults(String partnerId, ShelvesRequest shelvesRequest) {
        ShelvesResponse res = algoliaSearchClient.getShelves(shelvesRequest, partnerId);
        return res;
    }

    public SearchResponse getRelatedProducts(String partnerId, String productId, Integer hitsPerPage,
            Integer pageNumber) {
        VendorProduct vc = null;
        try {
            vc = VendorProduct.getInstance(productId, productHashGenerator);
        } catch (Exception e) {

            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_ID.toString(),
                    PlacePassExceptionCodes.INVALID_PRODUCT_ID.getDescription());
        }
        SearchResponse res = algoliaSearchClient.getRelatedProducts(productId, hitsPerPage, pageNumber, partnerId);
        return res;
    }

    public List<Product> getProducts(String partnerId, ProductsRequest productsRequest) {
        List<Product> res = algoliaSearchClient.getProducts(productsRequest, partnerId);
        return res;
    }

	public ProductsResponse getProducts(String partnerId, String productIds) {
		List<String> productIdList = null;
		if (productIds != null) {
	            if (productIds.contains(",")) {
	            		productIdList = new ArrayList<String>(Arrays.asList(productIds.split(",")));
	            } else {
	            		productIdList = new ArrayList<>();
	            		productIdList.add(productIds);
	            }
		 }
		 ProductsRequest productsRequest = new ProductsRequest();
		 productsRequest.setProductIds(productIdList);
		 List<Product> res = algoliaSearchClient.getProducts(productsRequest, partnerId);
		 ProductsResponse prodsResponse = new ProductsResponse();
		 if(res.size() > 0) {
			 prodsResponse.setProducts(res);
		 }		 
	     return prodsResponse;
	}

}
