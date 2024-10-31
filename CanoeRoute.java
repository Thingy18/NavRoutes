import java.util.LinkedList;

public class CanoeRoute {
    // Using LinkedLists for routes and islands
    LinkedList<Route> routes;
    LinkedList<Island> islands;
    boolean atSea = false;
    int timeIntoRoute = 0;

    // Pointers for the current position
    Route currentRoute;
    Island currentIsland;

    CanoeRoute(LinkedList<Route> routes, LinkedList<Island> islands) {
        this.routes = routes;
        this.islands = islands;
        this.currentIsland = islands.getFirst();
        this.currentRoute = routes.getFirst();
    }

    public LinkedList<Route> getRoutes() {
        return routes;
    }

    public LinkedList<Island> getIslands() {
        return islands;
    }

    public String getLocation() {
        if (atSea) {
            return "The canoe is at sea. It is " + timeIntoRoute + " hours out from " + currentRoute.start.name
                    + " with " + (currentRoute.travelTime - timeIntoRoute)
                    + " hours left till it reaches " + currentRoute.end.name;
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

                // Move to the next island and route if available
                int islandIndex = islands.indexOf(currentIsland);
                if (islandIndex + 1 < islands.size()) {
                    currentIsland = islands.get(islandIndex + 1);
                    currentRoute = routes.get(islandIndex + 1);
                    System.out.println("Canoe has arrived at " + currentIsland.getName());
                } else {
                    System.out.println("End of route. Canoe has reached the final island: " + currentIsland.getName());
                }
            }
        } else {
            // Start the next route if there is one
            int routeIndex = routes.indexOf(currentRoute);
            if (routeIndex + 1 < routes.size()) {
                atSea = true;
                System.out.println("Canoe has left " + currentIsland.getName() + " and is heading to " + currentRoute.end.name);
            } else {
                System.out.println("No more routes to navigate.");
            }
        }
    }
}
