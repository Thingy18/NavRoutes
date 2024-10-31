public class Delivery {
    Island destination;
    Resource resource;

    Delivery(Island destination, Resource resource) {
        this.destination = destination;
        this.resource = resource;
    }

    public Island getDestination() {
        return destination;
    }

    public Resource getResource() {
        return resource;
    }

}