package com.placepass.search.application.search.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseCatalog {

    private List<ProductCatalog> hits;

    private Integer hitsPerPage;

    private Integer processingTimeMS;

    private String query;

    private Integer nbHits;

    private Integer page;

    private String params;

    private Integer nbPages;

    private Boolean exhaustiveNbHits;

    private Facets facets;

    private Boolean exhaustiveFacetsCount;

    public Integer getHitsPerPage() {
        return hitsPerPage;
    }

    public void setHitsPerPage(Integer hitsPerPage) {
        this.hitsPerPage = hitsPerPage;
    }

    public Integer getProcessingTimeMS() {
        return processingTimeMS;
    }

    public void setProcessingTimeMS(Integer processingTimeMS) {
        this.processingTimeMS = processingTimeMS;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getNbHits() {
        return nbHits;
    }

    public void setNbHits(Integer nbHits) {
        this.nbHits = nbHits;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getNbPages() {
        return nbPages;
    }

    public void setNbPages(Integer nbPages) {
        this.nbPages = nbPages;
    }

    public Boolean getExhaustiveNbHits() {
        return exhaustiveNbHits;
    }

    public void setExhaustiveNbHits(Boolean exhaustiveNbHits) {
        this.exhaustiveNbHits = exhaustiveNbHits;
    }

    public Facets getFacets() {
        return facets;
    }

    public void setFacets(Facets facets) {
        this.facets = facets;
    }

    public Boolean getExhaustiveFacetsCount() {
        return exhaustiveFacetsCount;
    }

    public void setExhaustiveFacetsCount(Boolean exhaustiveFacetsCount) {
        this.exhaustiveFacetsCount = exhaustiveFacetsCount;
    }

    public List<ProductCatalog> getHits() {
        return hits;
    }

    public void setHits(List<ProductCatalog> hits) {
        this.hits = hits;
    }

}
