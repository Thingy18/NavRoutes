import java.util.LinkedList;

public class CanoeRoute {
    LinkedList<Route> routes; // The routes that the canoe will take
    LinkedList<Island> islands; // The islands that the canoe will visit
    Delivery delivery; // The instruction for the delivery
    boolean atSea = false; // Whether the canoe is at sea or at an island
    boolean finished = false; // Whether the canoe has finished its delivery
    int timeIntoRoute = 0; // The time into the current route
    Route currentRoute; // The current route that the canoe is on
    Island currentIsland; // The current island that the canoe is at
    int id; // The id of the canoe

    public CanoeRoute(LinkedList<Route> routes, LinkedList<Island> islands, Delivery delivery , int id) {
        this.routes = routes;
        this.islands = islands;
        this.delivery = delivery;
        this.currentIsland = islands.getFirst();
        this.currentRoute = routes.getFirst();
        this.id = id;
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

    public Delivery getdelivery() {
        return delivery;
    }

    public void setFinished(){
        finished = true;
    }
    
    public void updateLocation() {
        if (atSea) {
            timeIntoRoute++;
    
            if (timeIntoRoute >= currentRoute.getTravelTime()) {
                // Arrived at the next island
                atSea = false;
                timeIntoRoute = 0;
    
                int islandIndex = islands.indexOf(currentIsland);
                if (islandIndex + 1 < islands.size()) {
                    // Move to the next island
                    currentIsland = islands.get(islandIndex + 1);
                    // Set the current route for the next island
                    if(routes.size() > islandIndex){
                        
                    }else{
                        currentRoute = routes.get(islandIndex + 1);
                    }
                } else {
                    finished = true; // No more islands to navigate to
                }
            }
        } else {
            // This part executes when the canoe is at the current island
            int routeIndex = routes.indexOf(currentRoute);
            // Check if there is a next route available
            if (routeIndex < routes.size()) {
                // Start the next route
                atSea = true;
            } else {
                finished = true; // No more routes to navigate
            }
        }
    }
    
}