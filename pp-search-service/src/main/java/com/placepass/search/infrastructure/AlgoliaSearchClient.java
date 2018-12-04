package com.placepass.search.infrastructure;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.search.application.search.request.ProductSearchRequest;
import com.placepass.search.application.search.request.ProductsRequest;
import com.placepass.search.application.search.request.ShelvesRequest;
import com.placepass.search.application.search.response.Product;
import com.placepass.search.application.search.response.ProductCatalog;
import com.placepass.search.application.search.response.SearchResponse;
import com.placepass.search.application.search.response.SearchResponseCatalog;
import com.placepass.search.application.search.response.ShelvesResponse;
import com.placepass.search.domain.SearchRequest;
import com.placepass.utils.vendorproduct.VendorProduct;

@Component
public class AlgoliaSearchClient {

    private AlgoliaExtensions algoliaExtensions;

    private PlacePassIndices placePassIndices;

    private AlgoliaRequestMapper requestMapper;

    @Autowired
    public AlgoliaSearchClient(AlgoliaExtensions algoliaExtensions, PlacePassIndices placePassIndices,
            AlgoliaRequestMapper requestMapper) {
        this.algoliaExtensions = algoliaExtensions;
        this.placePassIndices = placePassIndices;
        this.requestMapper = requestMapper;
    }

    public SearchResponse searchProducts(ProductSearchRequest psr, String partnerId, List<VendorProduct> vendorProducts,
            List<String> tags) {
        List<String> vendorFacetValues = algoliaExtensions.getVendor(vendorProducts);
        SearchRequest algoliaRequest = null;
        String indexName = AlgoliaConfig.getIndexName(placePassIndices, psr);
        Index index = algoliaExtensions.initializeAlgolia(indexName);
        algoliaRequest = requestMapper.from(psr, partnerId, vendorFacetValues, tags);
        Query queryWithSettings = queryFacetsAndFilters(algoliaRequest);
        JSONObject searchResultJson = algoliaExtensions.search(index, queryWithSettings);
        SearchResponseCatalog resCatalog = AlgoliaResponseMapper.getPaginatedResponse(searchResultJson.toString());
        SearchResponse res = AlgoliaResponseMapper.filterResponse(resCatalog, partnerId);
        res.setParams(res.getParams() + "&index=" + index.getIndexName());
        return res;
    }

    @Deprecated
    public ShelvesResponse getShelves(ShelvesRequest shelvesRequest, String partnerId) {
        Index index = algoliaExtensions.initializeAlgolia(placePassIndices.getPpShelvesIndex());
        ShelvesResponse res = null;
        if (shelvesRequest.getWebId() != null) {
            JSONObject productJson = algoliaExtensions.getObjectById(index, shelvesRequest.getWebId());
            if (productJson != null) {
                res = AlgoliaResponseMapper.toShelvesResponse(productJson.toString(), partnerId);
            }
        }
        return res;
    }

    public SearchResponse getRelatedProducts(String productId, Integer hitsPerPage, Integer pageNumber,
            String partnerId) {
        SearchResponse res = null;
        String indexName = placePassIndices.getPpSearchIndex();
        Index index = algoliaExtensions.initializeAlgolia(indexName);
        JSONObject productJson = algoliaExtensions.getObjectById(index, productId);
        if (productJson == null)
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_ID.toString(),
                    PlacePassExceptionCodes.INVALID_PRODUCT_ID.getDescription());
        else {
            ProductSearchRequest psr = requestMapper.createProductSearchRequest(productJson.toString(), hitsPerPage,
                    pageNumber, partnerId);
            res = searchProducts(psr, partnerId, null, null);
            AlgoliaResponseMapper.removeSearchedProduct(productId, res);
        }
        return res;
    }

    public List<Product> getProducts(ProductsRequest productsRequest, String partnerId) {
        String indexName = placePassIndices.getPpSearchIndex();
        List<Product> res = null;
        Index index = algoliaExtensions.initializeAlgolia(indexName);
        JSONObject productsJson = algoliaExtensions.getObjectsByIds(index, productsRequest.getProductIds());
        if (productsJson == null)
            throw new NotFoundException(HttpStatus.NOT_FOUND.name(), "Resource does not exist");
        else {
            List<ProductCatalog> resCatalog = AlgoliaResponseMapper.getProductsResponse(productsJson.toString());
            res = AlgoliaResponseMapper.filterLists(resCatalog, res, partnerId);
            AlgoliaResponseMapper.cleanProducts(res);
        }
        return res;
    }

    private Query queryFacetsAndFilters(SearchRequest algoliaRequest) {
        Query q = new Query(algoliaRequest.getQuery());
        if (algoliaRequest.getHitsPerPage() != null)
            q.setHitsPerPage(algoliaRequest.getHitsPerPage());
        if (algoliaRequest.getPageNumber() != null)
            q.setPage(algoliaRequest.getPageNumber());
        if (algoliaRequest.getFacets() != null)
            q.setFacets(algoliaRequest.getFacets());
        if (algoliaRequest.getFacetFilters() != null)
            q.setFacetFilters(algoliaRequest.getFacetFilters());
        if (algoliaRequest.getNumericFilters() != null)
            q.setNumericFilters(algoliaRequest.getNumericFilters());
        if (algoliaRequest.getGeoSearchFilters() != null) {
            q.aroundLatitudeLongitude(algoliaRequest.getGeoSearchFilters().getLatitude(),
                    algoliaRequest.getGeoSearchFilters().getLongitude());
            q.setAroundRadius(1000);
        }
        return q;
    }

}
