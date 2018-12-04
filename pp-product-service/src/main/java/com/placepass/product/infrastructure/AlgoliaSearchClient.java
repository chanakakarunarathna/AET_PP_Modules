package com.placepass.product.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.product.application.hoteldetails.dto.GetHotelDetailsRS;
import com.placepass.product.application.hoteldetails.dto.HotelDetail;
import com.placepass.product.application.productdetails.dto.BookingOptionLangSvc;
import com.placepass.product.application.productdetails.dto.GetProductDetailsRS;
import com.placepass.product.application.productlanguageoptions.dto.GetProductLanguageOptions;
import com.placepass.product.application.productdetails.dto.ProductCatalog;
import com.placepass.product.application.productdetails.dto.ProductDetail;
import com.placepass.product.application.productdetails.dto.WebLocation;
import com.placepass.utils.vendorproduct.Vendor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class AlgoliaSearchClient {

    private AlgoliaExtensions algoliaExtensions;

    private PlacePassIndices placePassIndices;

    @Autowired
    RestClient restClient;

    @Autowired
    public AlgoliaSearchClient(AlgoliaExtensions algoliaExtensions, PlacePassIndices placePassIndices) {
        this.algoliaExtensions = algoliaExtensions;
        this.placePassIndices = placePassIndices;
    }

    public GetProductDetailsRS getProductDetails(String partnerId, String productId) {

        // Get info from ProductCatalog
        Index index = algoliaExtensions.initializeAlgolia(placePassIndices.getPpProductCatalog());
        JSONObject productCatalogJson = algoliaExtensions.getObjectById(index, productId);
        GetProductDetailsRS getProductDetailsRS = new GetProductDetailsRS();
        if (productCatalogJson == null) {
            throw new NotFoundException(PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.getDescription());
        } else {
            ProductCatalog productCatalog = AlgoliaSearchHelper.getProductCatalog(productCatalogJson.toString());
            ProductDetail detail = productCatalog.getDetails();
            if (detail == null || detail.getPartnerIds() == null) {
                throw new NotFoundException(PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.toString(),
                        PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.getDescription());
            } else {
                if(!detail.getPartnerIds().contains(partnerId)){
                    throw new NotFoundException(PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.toString(),
                            PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.getDescription());
                }
                getProductDetailsRS = AlgoliaSearchHelper.merge(detail, productCatalog, getProductDetailsRS, partnerId);

                List<String> locationIds = productCatalog.getGeoLocationIds();
                for (String locationId: locationIds) {
                    WebLocation webLocation = null;
                    try {
                        webLocation = restClient.getWebLocations(locationId);
                    } catch (HttpServerErrorException hse) {
                    } catch (HttpClientErrorException hce) {
                    } catch (ResourceAccessException rae) {
                    } catch (Exception e) {
                    }
                    if (webLocation != null) {
                        getProductDetailsRS.setWebLocation(webLocation);
                        break;
                    }
                }
            }
        }
        return getProductDetailsRS;
    }
    
    public GetProductLanguageOptions getProductLanguageOptions(String bookingOptionId, String productId) {
    	Index index = algoliaExtensions.initializeAlgolia(placePassIndices.getPpProductCatalog());
    	JSONObject productCatalogJson = algoliaExtensions.getObjectById(index, productId);
    	GetProductLanguageOptions getProductLanguageOptions = new GetProductLanguageOptions();
    	if (productCatalogJson == null) {
    		throw new NotFoundException(PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.toString(),
    				PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.getDescription());
    	} else { 
    		ProductCatalog productCatalog = AlgoliaSearchHelper.getProductCatalog(productCatalogJson.toString());
    		ProductDetail detail = productCatalog.getDetails();
    		List<BookingOptionLangSvc> bookingOptionLangSvc = detail.getBookingOptionLangSvcs();
    		
    			for (BookingOptionLangSvc b: bookingOptionLangSvc) {
    				if (b.getBookingOptionId().equals(bookingOptionId)) {
    					getProductLanguageOptions.setLanguageServices(b.getLanguageServices());
    				}
    			}
    			if (getProductLanguageOptions.getLanguageServices() == null) {
    				throw new NotFoundException(PlacePassExceptionCodes.BOOKING_OPTION_ID_NOT_FOUND.toString(),
    				PlacePassExceptionCodes.BOOKING_OPTION_ID_NOT_FOUND.getDescription());
    			}
    	}
    	return getProductLanguageOptions;    	
    }

    
    public GetProductLanguageOptions getProductLanguageOptionsHelper(GetProductLanguageOptions productLanguageOptions) {
		
    	return productLanguageOptions;
    }

    public GetHotelDetailsRS getHotelDetails(String partnerId, String vendorProductID, String productId,
            Vendor vendor) {
        GetHotelDetailsRS getHotelDetails = null;
        switch (vendor) {
            case URBANA:
                getHotelDetails = new GetHotelDetailsRS();
                GetProductDetailsRS productDetailsRS =null;
                try{
                    productDetailsRS = getProductDetails(partnerId, productId);
                }
                catch(Exception e){
                    throw new NotFoundException(PlacePassExceptionCodes.HOTEL_PICKUP_UNAVAILABLE.toString(),
                            PlacePassExceptionCodes.HOTEL_PICKUP_UNAVAILABLE.getDescription());
                }
                if (productDetailsRS.getIsHotelPickUpEligible()) {
                    List<HotelDetail> hotelDetails = new ArrayList<>();
                    getHotelDetails.setHotelDetails(hotelDetails);
                    getHotelDetails.setFreeText(true);
                } else {
                    throw new NotFoundException(PlacePassExceptionCodes.HOTEL_PICKUP_UNAVAILABLE.toString(),
                            PlacePassExceptionCodes.HOTEL_PICKUP_UNAVAILABLE.getDescription());
                }
                break;
            case VIATOR:
                Query query = queryFacetsAndFilters(vendorProductID, 2000);
                Index index = algoliaExtensions.initializeAlgolia(placePassIndices.getViatorHotelLocations());
                JSONObject response = algoliaExtensions.search(index, query, 2000);
                getHotelDetails = AlgoliaSearchHelper.getHotelDetails(response.toString());
                break;
            default:
                break;
        }
        return getHotelDetails;
    }

    private Query queryFacetsAndFilters(String vendorProductId, int hitsPerPage) {
        Query q = new Query();
        List<String> vendorIdFacet = new ArrayList<String>();
        String facetFilterString = "productCodes:" + vendorProductId;
        vendorIdFacet.add(facetFilterString);
        q.setFacetFilters(vendorIdFacet);
        q.setHitsPerPage(hitsPerPage);
        return q;
    }
}
