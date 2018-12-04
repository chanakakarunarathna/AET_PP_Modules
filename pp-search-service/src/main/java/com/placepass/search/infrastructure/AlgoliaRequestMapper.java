package com.placepass.search.infrastructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.NotFoundException;
import com.placepass.search.application.search.request.Location;
import com.placepass.search.application.search.request.LocationIndex;
import com.placepass.search.application.search.request.ProductSearchRequest;
import com.placepass.search.domain.SearchRequest;
import com.placepass.search.domain.GeoSearch;

@Component
public class AlgoliaRequestMapper {

    @Autowired
    RestClient restClient;

    @Autowired
    AlgoliaConfig algoliaConfig;

    /* FACETS */
    private static String FormattedAddress = "FormattedAddress";

    private static String Group = "Group";

    private static String SubGroup = "SubGroup";

    private static String TriedAndTrueGuarantee = "TriedAndTrueGuarantee";

    private static String Vendor = "Vendor";

    private static String AvgRating = "AvgRating";

    private static String Reviews = "Reviews";

    private static String Tag = "_tags";

    private static String Classifications = "Classifications";

    private static String AvailableDates = "AvailableDays";
    

    /* OPERATORS */
    private static String ToOperator = " TO ";

    private static String CommaOperator = ", ";

    private static String ColonOperator = ":";

    /* FACET FILTERS */
    private static String CategoryFacetFilter = "Category";

    private static String ClassificationFacetFilter = "Classifications";

    private static String AvailableDatesFacetFilter = "AvailableDays";

    private static String GroupFilter = "Group";

    private static String SubGroupFilter = "SubGroup";

    private static String TriedAndTrueFacetFilter = "TriedAndTrueGuarantee";

    private static String FmtAddressFacetFilter = "FormattedAddress";

    private static String GeolocationIdsFacetFilter = "GeoLocationIds";

    private static String IsBookableFilter = "IsBookable";

    private static String PartnerIds = "PartnerIds";

    private static String VendorFacet = "Vendor";

    private static String TagFilter = "_tags";
    
    private static String ProductIdFilter="Id";

    /* NUMERIC FILTERS */
    private static String AvgRatingNumericFilter = "AvgRating";

    private static String ReviewsNumericFilter = "Reviews";

    private static String DurationInMinutesNumericFilter = "DurationInMinutes";

    private static String PriceNumericFilter = "Price";

    private static List<String> Facets = Arrays.asList(FormattedAddress, Group, SubGroup, TriedAndTrueGuarantee, Vendor,
            AvgRating, Reviews, Tag, Classifications, AvailableDates);

    public SearchRequest from(ProductSearchRequest psr, String partnerId, List<String> vendorFacetValues,
            List<String> tags) {
        SearchRequest asr = new SearchRequest();
        asr.setHitsPerPage(psr.getHitsPerPage());
        asr.setPageNumber(psr.getPageNumber());
        asr.setQuery(psr.getQuery());
        List<String> facetFilters = new ArrayList<>();
        asr.setFacets(Facets);
        facetFilters.add(partnerIdFilter(PartnerIds, partnerId));

        if (vendorFacetValues != null) {
            facetFilters.add(ListFilter(CommaOperator, VendorFacet, vendorFacetValues));
        }
        if (psr.getCategories() != null) {
            facetFilters.add(ListFilter(CommaOperator, CategoryFacetFilter, psr.getCategories()));
        }
        if (psr.getClassifications() != null) {
            facetFilters.add(ListFilter(CommaOperator, ClassificationFacetFilter, psr.getClassifications()));
        }
        if (psr.getAvailableDates() != null) {
            List<String> inputDates = GenerateDatesList(psr.getAvailableDates().getStartDate(),
                    psr.getAvailableDates().getEndDate());
            facetFilters.add(ListFilter(CommaOperator, AvailableDatesFacetFilter, inputDates));
        }
        if(psr.getProductIds()!=null){
            facetFilters.add(ListFilter(CommaOperator, ProductIdFilter, psr.getProductIds()));
        }
        if (psr.getGroup() != null) {
            facetFilters.add(ListFilter(CommaOperator, GroupFilter, psr.getGroup()));
        }
        if (psr.getSubGroup() != null) {
            facetFilters.add(ListFilter(CommaOperator, SubGroupFilter, psr.getSubGroup()));
        }

        if (psr.getIsBookable() != null) {
            facetFilters.add(booleanKeyValueFilter(IsBookableFilter, psr.getIsBookable()));
        }
        if (psr.getTriedAndTrue() != null) {
            facetFilters.add(booleanKeyValueFilter(TriedAndTrueFacetFilter, psr.getTriedAndTrue()));
        }
        // Combining tags from the header and the request body
        ArrayList<String> requestTags = new ArrayList<>();
        if (tags != null) {
            requestTags.addAll(tags);
        }
        if (psr.getTags() != null) {
            requestTags.addAll(Arrays.asList(psr.getTags().split(",")));
        }
        facetFilters.add(ListFilter(CommaOperator, TagFilter, requestTags));

        List<String> numericFilters = new ArrayList<>();
        if (psr.getRatings() != null) {
            List<Integer> ratings = psr.getRatings();
            List<String> ratingsList = new ArrayList<>();
            for (Integer rating : ratings) {
                ratingsList.add(AvgRatingNumericFilter + "=" + rating);
            }
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = null;
            try {
                jsonInString = mapper.writeValueAsString(ratingsList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            numericFilters.add(jsonInString);
        }
        if (psr.getReviews() != null) {
            String ratingString = rangeFilter(ReviewsNumericFilter, psr.getReviews().getMin(),
                    psr.getReviews().getMax());
            String ratingFilter = "\"" + ratingString + "\"";

            numericFilters.add(ratingFilter);
        }
        if (psr.getDurationInMinutes() != null) {
            String durationString = rangeFilter(DurationInMinutesNumericFilter, psr.getDurationInMinutes().getMin(),
                    psr.getDurationInMinutes().getMax());
            String durationFilter = "\"" + durationString + "\"";
            numericFilters.add(durationFilter);
        }
        if (psr.getPrice() != null) {
            String priceString = rangeFilter(PriceNumericFilter, psr.getPrice().getMin(), psr.getPrice().getMax());
            String priceFilter = "\"" + priceString + "\"";
            numericFilters.add(priceFilter);
        }
        String numericFilterString = "[" + ValueFilter(CommaOperator, numericFilters) + "]";
        asr.setNumericFilters(numericFilterString);
        if (psr.getLocation() != null) {
            if (psr.getLocation().getWebId() != null) {
                getLocationData(psr);
            }
            if (psr.getLocation().getGeoLocationId() != null) {
                String geoFilter = "\"" + StringFilter(GeolocationIdsFacetFilter, psr.getLocation().getGeoLocationId())
                        + "\"";
                facetFilters.add(geoFilter);
            }
            if (psr.getLocation().getFormattedAddress() != null) {
                String address = psr.getLocation().getFormattedAddress();
                String fmtFacetFilter = "\"" + FmtAddressFacetFilter + ":" + address + "\"";
                facetFilters.add(fmtFacetFilter);
            }
            if (psr.getLocation().getLatitude() != null && psr.getLocation().getLongitude() != null) {
                asr.setGeoSearchFilters(locationFacetFilter(psr.getLocation()));
            }

        }
        String facetFilterString = "[" + ValueFilter(CommaOperator, facetFilters) + "]";
        asr.setFacetFilters(facetFilterString);
        return asr;
    }

    public List<String> GenerateDatesList(String date1, String date2) {
        if(date1 == null || date2 == null) {
            throw new BadRequestException("DATES_NULL", "Dates cannot be null");
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMddyyyy");
        List<String> availableDates = null;
        try {
            Date startDate = inputFormat.parse(date1);
            Date endDate = inputFormat.parse(date2);
            List<Date> dates = getDaysBetweenDates(startDate, endDate);
            availableDates = getDaysAsStrings(dates);
        } catch (ParseException e) {
            throw new BadRequestException("INVALID_DATES_FORMAT", "Input dates are not in the correct format");
        }
        return availableDates;
    }

    private List<String> getDaysAsStrings(List<Date> dates) {
        List<String> availableDates = null;
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMddyyyy");
        if (dates != null) {
            availableDates = new ArrayList<>();
            for (Date date : dates) {
                availableDates.add(outputFormat.format(date));
            }
        }
        return availableDates;
    }

    public List<Date> getDaysBetweenDates(Date startdate, Date enddate) {        
        List<Date> dates = new ArrayList<Date>();
        if (startdate.equals(enddate)) {
            dates.add(startdate);
        }
        if (enddate.before(startdate)) {
            throw new BadRequestException("DATES_MISMATCH", "End date should be after Start date");
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        if (startdate.before(enddate)) {
            while (calendar.getTime().before(enddate)) {
                Date result = calendar.getTime();
                dates.add(result);
                calendar.add(Calendar.DATE, 1);
            }
            // Add enddate
            dates.add(enddate);
        }
        return dates;
    }

    private static String partnerIdFilter(String facetKey, String facetValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"" + facetKey + ":" + facetValue + "\"");
        return sb.toString();
    }

    public static Iterator<JsonNode> getHitsArray(String searchResultJsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;
        Iterator<JsonNode> hitsIterator = null;
        try {
            rootNode = mapper.readTree(searchResultJsonString);
            hitsIterator = rootNode.path("hits").elements();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hitsIterator;
    }

    public ProductSearchRequest getLocationData(ProductSearchRequest psr) {
        try {
            LocationIndex locationIndex = restClient.getLocationDetails(psr.getLocation().getWebId());
            if (locationIndex.getPlaceId() != null) {
                String geoLocationId = locationIndex.getPlaceId();
                Location location = new Location();
                location.setGeoLocationId(geoLocationId);
                psr.setLocation(location);
            }
        } catch (Exception e) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.name(), "WebId not found");
        }
        return psr;
    }

    public ProductSearchRequest createProductSearchRequest(String jsonString, Integer hitsPerPage, Integer pageNumber,
            String partnerId) {
        ProductSearchRequest psr = new ProductSearchRequest();
        psr.setHitsPerPage(hitsPerPage);
        psr.setPageNumber(pageNumber);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode productNode = mapper.readTree(jsonString);
            JsonNode formattedAddressArray = productNode.get("FormattedAddress");
            Location location = new Location();
            List<String> fmtAddress = listStringMap(formattedAddressArray);
            location.setFormattedAddress(fmtAddress.get(0));
            psr.setLocation(location);
            if (partnerId.equals(algoliaConfig.getPartnerIds().get(0))) {
                psr.setIsBookable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return psr;
    }

    private static String StringFilter(String facet, String webId) {
        StringBuilder sb = new StringBuilder();
        sb.append(facet + ":" + webId);
        return sb.toString();
    }

    private static String rangeFilter(String attributeName, Double min, Double max) {
        StringBuilder sb = new StringBuilder();
        if (min == null) {
            sb.append(attributeName + "<=" + max);
        } else if (max == null) {
            sb.append(attributeName + ">=" + min);
        } else {
            sb.append(attributeName + ":" + min + ToOperator + max);
        }
        return sb.toString();
    }

    private static String booleanKeyValueFilter(String type, Boolean triedAndTrue) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"" + type + ":" + triedAndTrue + "\"");
        return sb.toString();
    }

    private static GeoSearch locationFacetFilter(Location location) {
        GeoSearch gSearch = new GeoSearch();
        if (location.getLatitude() != null && location.getLongitude() != null) {
            gSearch.setLatitude(location.getLatitude());
            gSearch.setLongitude(location.getLongitude());
        }
        return gSearch;
    }

    private static String ListFilter(String seperator, String type, List<String> filterType) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String item : filterType) {
            sb.append(sep);
            sb.append("\"" + type + ColonOperator + item + "\"");
            sep = seperator;
        }
        return "[" + sb.toString() + "]";
    }

    private static String ValueFilter(String operator, List<String> filterType) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String item : filterType) {
            sb.append(sep);
            sb.append(item);
            sep = operator;
        }
        return sb.toString();
    }

    private static List<String> listStringMap(JsonNode typeArray) {
        List<String> typeList = new ArrayList<>();
        for (JsonNode item : typeArray) {
            typeList.add(item.asText());
        }
        return typeList;
    }

}
