public class Canoe {
    Resource resource;
    CanoeRoute cRoute;
    int id;

    public Canoe(Resource resource, CanoeRoute cRoute, int id) {
        this.resource = resource;
        this.cRoute = cRoute;
        this.id = id;
    }

    public Resource getResource() {
        return resource;
    }

    public CanoeRoute getRoute() {
        return cRoute;
    }

    public int getId() {
        return id;
    }

    public boolean atSea() {
        return cRoute.atSea;
    }

    public boolean finished(){
        return cRoute.finished;
    }
    
    public int currentIsland() {
        return cRoute.currentIsland.getId();
    }

    public Island deliver(Island isl) {
        Delivery delivery = cRoute.getdelivery();
        // Check if the delivery destination matches the island
        if (delivery.getDestination().getId() == isl.getId()) {
            Resource deliveredResource = delivery.getResource();
            // Attempt to add or update the resource in the island
            isl.addResource(deliveredResource);
            cRoute.setFinished();
            return isl; // Return the updated island
        }
        return isl; // Return the island without changes if no delivery matched
    }
}
