public class Island {
    String name;
    int numPeople;
    Resource[] resources;
    Route[] routes;
    int id;
    Island(String name, int numPeople, Resource[] resources, Route[] routes, int id) {
        this.name = name;
        this.numPeople = numPeople;
        this.resources = resources;
        this.routes = routes;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public Resource[] getResources() {
        return resources;
    }

    public Route[] getRoutes() {
        return routes;
    }

    public int getId() {
        return id;
    }
    
    public void addPeople(int numPeople) {
        this.numPeople += numPeople;
    }

    public void subtractPeople(int numPeople) {
        this.numPeople -= numPeople;
    }

    public void addResource(Resource resource) {
        Resource[] newResources = new Resource[resources.length + 1];
        for (int i = 0; i < resources.length; i++) {
            newResources[i] = resources[i];
        }
        newResources[resources.length] = resource;
        resources = newResources;
    }

    public void removeResource(String resourceName) {
        Resource[] newResources = new Resource[resources.length - 1];
        int j = 0;
        for (int i = 0; i < resources.length; i++) {
            if (!resources[i].getType().equals(resourceName)) {
                newResources[j] = resources[i];
                j++;
            }
        }
        resources = newResources;
    }
}
