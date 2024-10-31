public class Island {
    String name;
    int numPeople;
    Resource[] resources;
    Route[] routes;

    public Island(String name, int numPeople, Resource[] resources, Route[] routes) {
        this.name = name;
        this.numPeople = numPeople;
        this.resources = resources;
        this.routes = routes;
    }
    
}
