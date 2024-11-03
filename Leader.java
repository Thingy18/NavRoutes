import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * The Leader class represents a skilled leader who travels between islands
 * to share knowledge. The leader prioritizes islands with larger populations
 * and those that have not been visited recently.
 */
public class Leader {
    private int currentIslandId;
    private PolynesianTriangle pt;
    private PriorityQueue<IslandVisit> visitQueue;

    /**
     * Constructs a Leader who starts at a specified island.
     *
     * @param startIslandId The ID of the island where the leader begins their journey.
     * @param pt            The PolynesianTriangle object representing the collection
     *                      of islands and routes.
     */
    public Leader(int startIslandId, PolynesianTriangle pt) {
        this.currentIslandId = startIslandId;
        this.pt = pt;
        this.visitQueue = new PriorityQueue<>(new VisitComparator());
        initializeVisitQueue();
    }

    /**
     * Initializes the priority queue with each island's visit information,
     * prioritizing based on population size and last visit time.
     */
    private void initializeVisitQueue() {
        for (Island island : pt.getIslands()) {
            visitQueue.add(new IslandVisit(island.getId(), island.getNumPeople(), 0));
        }
    }

    /**
     * Simulates the leader's journey by selecting islands based on priority.
     * Visits islands with higher populations or those that have not been visited
     * recently.
     */
    public void circulateKnowledge() {
        while (!visitQueue.isEmpty()) {
            IslandVisit nextVisit = visitQueue.poll(); // Get the next highest-priority island
            int islandId = nextVisit.getIslandId();
            Island island = pt.getIslands()[islandId];
            System.out.println("Visiting island: " + island.getName() + " with population: " + island.getNumPeople());

            // Update the last visit time to the current time
            nextVisit.setLastVisitTime(System.currentTimeMillis());

            // Recalculate the visit priority and re-add to queue
            visitQueue.add(new IslandVisit(islandId, island.getNumPeople(), nextVisit.getLastVisitTime()));
        }
    }

    /**
     * Inner class representing the information needed to manage island visits.
     * It tracks the island's ID, population, and last visit time for priority scheduling.
     */
    private static class IslandVisit {
        private int islandId;
        private int population;
        private long lastVisitTime;

        /**
         * Constructs an IslandVisit object with the specified attributes.
         *
         * @param islandId     The unique ID of the island.
         * @param population   The population size of the island.
         * @param lastVisitTime The timestamp of the last visit to this island.
         */
        public IslandVisit(int islandId, int population, long lastVisitTime) {
            this.islandId = islandId;
            this.population = population;
            this.lastVisitTime = lastVisitTime;
        }

        public int getIslandId() { return islandId; }
        public int getPopulation() { return population; }
        public long getLastVisitTime() { return lastVisitTime; }
        public void setLastVisitTime(long lastVisitTime) { this.lastVisitTime = lastVisitTime; }
    }

    /**
     * Comparator class to order IslandVisit objects. Prioritizes by population size
     * and then by last visit time, where higher population and least recent visits
     * have priority.
     */
    private static class VisitComparator implements Comparator<IslandVisit> {
        public int compare(IslandVisit a, IslandVisit b) {
            if (a.getPopulation() != b.getPopulation()) {
                return Integer.compare(b.getPopulation(), a.getPopulation()); // Higher population first
            }
            return Long.compare(a.getLastVisitTime(), b.getLastVisitTime()); // Least recent visit first
        }
    }
}
