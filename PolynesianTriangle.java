import java.util.LinkedList;

public class PolynesianTriangle {
    Island[] islands;
    Route[] routes;
    LinkedList<Canoe> canoesOnRoutes = null;
    
    public PolynesianTriangle(Island[] islands, Route[] routes) {
        this.islands = islands;
        this.routes = routes;
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

    public void progress(){
        for (Canoe canoe : canoesOnRoutes) {
            canoe.getRoute().updateLocation();
            if(canoe.atSea()){
                islands[canoe.currentIsland()] = canoe.deliver(islands[canoe.currentIsland()]);
            }
        }
    }

    public void addCanoe(Canoe canoe){
        canoesOnRoutes.add(canoe);
    }
}