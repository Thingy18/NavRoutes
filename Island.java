import java.util.ArrayList;
import java.util.List;

public class Island {
    private String name; // Name of the island
    private int numPeople; // Number of people on the island
    private List<Resource> resources; // List of resources on the island
    private Route[] routes; // List of routes from the island to other islands
    private int id; // Unique identifier for the island
    
    public Island(String name, int numPeople, Resource[] resources, Route[] routes, int id) {
        this.name = name;
        this.numPeople = numPeople;
        this.resources = new ArrayList<>();
        for (Resource resource : resources) {
            this.resources.add(resource); // Add existing resources to the list
        }
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
        return resources.toArray(new Resource[0]);
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
        // Check if the resource already exists
        for (Resource existingResource : resources) {
            if (existingResource.getType().equals(resource.getType())) {
                // If it exists, just update the quantity
                existingResource.setQuantity(existingResource.getQuantity() + resource.getQuantity());
                return;
            }
        }
        // If the resource does not exist, add it to the list
        resources.add(resource);
    }

    public void removeResource(String resourceName, int quantity) {
        for (int i = 0; i < resources.size(); i++) {
            Resource existingResource = resources.get(i);
            if (existingResource.getType().equals(resourceName)) {
                if (existingResource.getQuantity() > quantity) {
                    // If the existing resource quantity is greater than the quantity to remove
                    existingResource.setQuantity(existingResource.getQuantity() - quantity);
                } else {
                    // If the existing resource quantity is less than or equal to the quantity to remove
                    resources.remove(i); // Remove the resource entirely
                }
                return;
            }
        }
    }
}
