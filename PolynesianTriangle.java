public class PolynesianTriangle {
    Island[] islands;
    Route[] routes;
    Canoe[] canoesOnRoutes = null;
    
    public PolynesianTriangle(Island[] islands, Route[] routes) {
        this.islands = islands;
        this.routes = routes;
    }

    public String toString(){
        String result = "";
        for (Island island : islands) {
            result += island.id + "\n";
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
}