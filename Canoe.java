public class Canoe {
    Resource resource;
    CanoeRoute cRoute = null;

    public Canoe(Resource resource) {
        this.resource = resource;
    }

    public void setRoute(CanoeRoute cRoute) {
        this.cRoute = cRoute;
    }

    public Resource getResource() {
        return resource;
    }

    public CanoeRoute getRoute() {
        return cRoute;
    }

    public boolean atSea() {
        return cRoute.atSea;
    }

    public int currentIsland() {
        return cRoute.currentIsland.getId();
    }

    public Island deliver(Island isl){
        for(int i = 0; i < cRoute.deliveries.size(); i++){
            if(cRoute.deliveries.get(i).getDestination().getId() == isl.getId()){
                isl.addResource(cRoute.deliveries.get(i).getResource());
                cRoute.deliveries.remove(i);
                return isl;
            }
        }
        return isl;
    }
}
