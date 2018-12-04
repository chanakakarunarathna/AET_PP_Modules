package com.placepass.search.application.search.request;

public enum ShelfType {

    /*SortBy*/
    recommended("Recommended"), 
    topRated("TopRated"), 
    popularity("Popularity"), 
    priceLowHigh("PriceLowHigh"), 
    priceHighLow("PriceHighLow"),
    
    /*Categories*/
    MuseumTickets("Museum Tickets"),
    NatureAndAdventure("Nature & Adventure"),
    OnTheWater("On the Water"),
    HighRollers("High Rollers"),
    CulinaryTours("Culinary Tours"),
    Couples("Couples"),
    Sightseeing("Sightseeing"),
    Families("Families"),
    Transfers("Transfers"),
    WalkingAndBiking("Walking & Biking"),
    InTheAir("In the Air"),
    Shopping("Shopping"),
    ShowsAndConcerts("Shows & Concerts"),
    Cruisers("Cruisers"),
    Classes("Classes"),
    Wellness("Wellness"),
    AmusementParks("Amusement Parks"),
    VirtualReality("Virtual Reality"),
    
    /*Vendors*/
    Viator("Viator"),
    UrbanAdventures("UrbanAdventures"),
    CityDiscovery("CityDiscovery"),
    Gobe("Gobe"),
    Musement("Musement"),
    Isango("Isango"),
    IfOnly("IfOnly"),
    ProjectExpedition("ProjectExpeditions"),
    HeadOut("HeadOut"),
    Ticketmaster("Ticketmaster"),
    Tiqets("Tiqets"),
    
    
    /*ExperienceType*/
    
    Sightsee("Sightsee"),
    Immerse("Immerse"),
    Thrill("Thrill"),
    Relax("Relax")
    
    
    /* tags */
    

    
    ;
    
    private String label;

    ShelfType(String label) {
        this.label = label;
    }

    public static ShelfType getShelf(String name) {
        try {
            return ShelfType.valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid shelf name: " + name);
        }
    }

    public String getLabel() {
        return label;
    }
    
}
