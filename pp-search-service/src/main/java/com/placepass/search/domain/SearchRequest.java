package com.placepass.search.domain;

import java.util.List;

public class SearchRequest {

    private String index;
    private String query;   
    private List<String> facets;
    private String facetFilters;
    private String numericFilters;
    private GeoSearch geoSearchFilters;   
    private Integer hitsPerPage;
    private Integer pageNumber;
    
   
    public Integer getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public List<String> getFacets() {
        return facets;
    }
    public void setFacets(List<String> facets) {
        this.facets = facets;
    }
    public String getFacetFilters() {
        return facetFilters;
    }
    public void setFacetFilters(String facetFilters) {
        this.facetFilters = facetFilters;
    }
    public String getNumericFilters() {
        return numericFilters;
    }
    public void setNumericFilters(String numericFilters) {
        this.numericFilters = numericFilters;
    }
    public Integer getHitsPerPage() {
        return hitsPerPage;
    }
    public void setHitsPerPage(Integer hitsPerPage) {
        this.hitsPerPage = hitsPerPage;
    }
    public GeoSearch getGeoSearchFilters() {
        return geoSearchFilters;
    }
    public void setGeoSearchFilters(GeoSearch geoSearchFilters) {
        this.geoSearchFilters = geoSearchFilters;
    }
}
