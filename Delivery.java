public class Delivery {
    Island destination; // The destination island
    Resource resource; // The resource to be delivered

    public Delivery(Island destination, Resource resource) {
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