import java.util.LinkedList;

import java.util.Iterator;

public class PolynesianTriangle {
    Island[] islands;
    Route[] routes;
    LinkedList<Canoe> canoesOnRoutes;
    
    public PolynesianTriangle(Island[] islands, Route[] routes) {
        this.islands = islands;
        this.routes = routes;
        canoesOnRoutes = new LinkedList<Canoe>();
    }

    public Island[] getIslands() {
        return islands;
    }

    public Route[] getRoutes() {
        return routes;
    }
    
    public LinkedList<Canoe> getCanoesOnRoutes() {
        return canoesOnRoutes;
    }

    public String toString(){
        String result = "";
        for (Island island : islands) {
            String res = "";
            for (Resource resource : island.getResources()) {
                res += resource.getType() + ":" + resource.getQuantity() + ",";
            }
            String rou = "";
            for (Route route : island.getRoutes()) {
                rou += route.getEnd() + ":" + route.getTravelTime() + ",";
            }
            result += island.getName() + " " + island.getNumPeople() + " Resources(" + res + ") " + " Routes(" + rou + ") " + island.getId() + "\n";
        }
        return result;
    }

    public void progress() {
        Iterator<Canoe> iterator = canoesOnRoutes.iterator();
    
        while (iterator.hasNext()) {
            Canoe canoe = iterator.next();
            canoe.getRoute().updateLocation();
            
            if (canoe.atSea()) {
                // Deliver resources and check if the canoe is done
                Island currentIsland = islands[canoe.currentIsland()];
                islands[canoe.currentIsland()] = canoe.deliver(currentIsland);
            }
            // If the canoe has finished delivering (i.e., no deliveries left), remove it
            if (canoe.finished()) {
                iterator.remove(); // Safe removal
            }
        }
    }

    public void addCanoe(Canoe canoe){
        canoesOnRoutes.add(canoe);
    }

    public boolean allFinished(){
        if(canoesOnRoutes.size() > 0){
            return false;
        }
        return true;
    }
}