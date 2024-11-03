import java.util.LinkedList;
import java.util.Iterator;

/**
 * The PolynesianTriangle class represents a network of islands connected by routes,
 * with canoes transporting resources between them. It manages the progress of canoes,
 * island data, and routes within the Polynesian Triangle.
 */
public class PolynesianTriangle {
    private Island[] islands;
    private Route[] routes;
    private LinkedList<Canoe> canoesOnRoutes;

    /**
     * Constructs a PolynesianTriangle with a collection of islands and routes.
     *
     * @param islands An array of Island objects representing each island in the triangle.
     * @param routes  An array of Route objects representing the travel routes between islands.
     */
    public PolynesianTriangle(Island[] islands, Route[] routes) {
        this.islands = islands;
        this.routes = routes;
        this.canoesOnRoutes = new LinkedList<>();
    }

    /**
     * Returns the array of islands within the Polynesian Triangle.
     *
     * @return An array of Island objects.
     */
    public Island[] getIslands() {
        return islands;
    }

    /**
     * Returns the array of routes within the Polynesian Triangle.
     *
     * @return An array of Route objects.
     */
    public Route[] getRoutes() {
        return routes;
    }

    /**
     * Returns the list of canoes currently active on routes.
     *
     * @return A LinkedList of Canoe objects.
     */
    public LinkedList<Canoe> getCanoesOnRoutes() {
        return canoesOnRoutes;
    }

    /**
     * Provides a detailed description of each island, including its name, population,
     * resources, and routes.
     *
     * @return A string representation of the PolynesianTriangle with details of each island.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Island island : islands) {
            StringBuilder res = new StringBuilder();
            for (Resource resource : island.getResources()) {
                res.append(resource.getType()).append(":").append(resource.getQuantity()).append(",");
            }
            StringBuilder rou = new StringBuilder();
            for (Route route : island.getRoutes()) {
                rou.append(route.getEnd()).append(":").append(route.getTravelTime()).append(",");
            }
            result.append(island.getName()).append(" ").append(island.getNumPeople())
                    .append(" Resources(").append(res).append(") Routes(").append(rou)
                    .append(") ").append(island.getId()).append("\n");
        }
        return result.toString();
    }

    /**
     * Advances each canoe's journey by updating its location. If a canoe reaches its
     * destination, it delivers resources to the island. Completed canoes are removed from
     * the active list.
     */
    public void progress() {
        Iterator<Canoe> iterator = canoesOnRoutes.iterator();

        while (iterator.hasNext()) {
            Canoe canoe = iterator.next();
            canoe.getRoute().updateLocation();

            if (canoe.atSea()) {
                // Deliver resources and check if the canoe has completed its journey
                Island currentIsland = islands[canoe.currentIsland()];
                islands[canoe.currentIsland()] = canoe.deliver(currentIsland);
            }
            // If the canoe has finished delivering resources, remove it from the list
            if (canoe.finished()) {
                iterator.remove(); // Safe removal
            }
        }
    }

    /**
     * Adds a new canoe to the list of active canoes in the Polynesian Triangle.
     *
     * @param canoe A Canoe object to be added to the active routes list.
     */
    public void addCanoe(Canoe canoe) {
        canoesOnRoutes.add(canoe);
    }

    /**
     * Checks if all canoes have completed their journeys and delivered resources.
     *
     * @return True if no active canoes remain, indicating all deliveries are complete; otherwise, false.
     */
    public boolean allFinished() {
        return canoesOnRoutes.isEmpty();
    }

    /**
     * Starts the leader's journey to circulate knowledge across the islands.
     * Initializes a Leader object and begins its circulation through the islands.
     *
     * @param startIslandId The ID of the starting island for the leader.
     */
    public void startLeaderJourney(int startIslandId) {
        Leader leader = new Leader(startIslandId, this);
        leader.circulateKnowledge();
    }
}
