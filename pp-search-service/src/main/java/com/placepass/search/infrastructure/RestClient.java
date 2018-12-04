package com.placepass.search.infrastructure;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.placepass.search.application.search.request.LocationData;
import com.placepass.search.application.search.request.LocationIndex;

@Component
public class RestClient {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

    public LocationIndex getLocationDetails(String webId) {
        URI targetUrl = UriComponentsBuilder.fromUriString(restConfig.getLocationBaseUrl()).queryParam("q", webId)
                .queryParam("searchby", "webid").build().encode().toUri();
        LocationData response = restTemplate.getForObject(targetUrl, LocationData.class);
        LocationIndex result = response.getPlaceList().get(0);
        return result;
    }
}
