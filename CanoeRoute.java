import java.util.LinkedList;

public class CanoeRoute {
    LinkedList<Route> routes;
    LinkedList<Island> islands;
    LinkedList<Delivery> deliveries;
    boolean atSea = false;
    int timeIntoRoute = 0;
    Route currentRoute;
    Island currentIsland;

    public CanoeRoute(LinkedList<Route> routes, LinkedList<Island> islands, LinkedList<Delivery> deliveries) {
        this.routes = routes;
        this.islands = islands;
        this.deliveries = deliveries;
        this.currentIsland = islands.getFirst();
        this.currentRoute = routes.getFirst();
    }

    public String getLocation() {
        if (atSea) {
            return "The canoe is at sea. It is currently " + timeIntoRoute + " hours out from " + currentRoute.start
                    + " with " + (currentRoute.travelTime - timeIntoRoute)
                    + " hours left till it reaches " + currentRoute.end;
        } else {
            return "Currently in port at " + currentIsland.getName();
        }
    }

    public void updateLocation() {
        if (atSea) {
            timeIntoRoute++;
            if (timeIntoRoute >= currentRoute.travelTime) {
                // Arrived at the next island
                atSea = false;
                timeIntoRoute = 0;

                int islandIndex = islands.indexOf(currentIsland);
                if (islandIndex + 1 < islands.size()) {
                    currentIsland = islands.get(islandIndex + 1);
                    currentRoute = routes.get(islandIndex + 1);
                    System.out.println("Canoe has arrived at " + currentIsland.getName());
                }
            }
        } else {
            int routeIndex = routes.indexOf(currentRoute);
            if (routeIndex + 1 < routes.size()) {
                atSea = true;
                System.out.println("Canoe has left " + currentIsland.getName() + " and is heading to " + currentRoute.end);
            } else {
                System.out.println("No more routes to navigate.");
            }
        }
    }
}