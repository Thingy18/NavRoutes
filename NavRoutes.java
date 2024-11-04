import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class NavRoutes {

    public static void main(String[] args) throws FileNotFoundException{
        PolynesianTriangle pt = null;
        // Sets up the Polynesian Triangle based on a provided file
        File file = new File("polytri.txt");
        try (Scanner scan = new Scanner(file)) {
            ArrayList<Island> islands = new ArrayList<Island>();
            ArrayList<Route> Routes = new ArrayList<Route>();
            int id = 0;
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                String[] parts = line.split(" ");
                String name = parts[0];
                int numPeople = Integer.parseInt(parts[1]);
                ArrayList<Resource> resources = new ArrayList<Resource>();
                parts[2] = parts[2].substring(parts[2].indexOf('(') + 1, parts[2].indexOf(')'));
                String[] rParts = parts[2].split(",");
                for(int i = 0; i < rParts.length; i++){
                    String[] resourceParts = rParts[i].split(":");
                    resources.add(new Resource(resourceParts[0], Integer.parseInt(resourceParts[1])));
                }
                ArrayList<Route> routes = new ArrayList<Route>();
                parts[3] = parts[3].substring(parts[3].indexOf('(') + 1, parts[3].indexOf(')'));
                rParts = parts[3].split(",");
                for(int i = 0; i < rParts.length; i++){
                    String[] routeParts = rParts[i].split(":");
                    routes.add(new Route(name, routeParts[0], Integer.parseInt(routeParts[1])));
                    Routes.add(new Route(name, routeParts[0], Integer.parseInt(routeParts[1])));
                }
                islands.add(new Island(name, numPeople, resources.toArray(new Resource[resources.size()]), routes.toArray(new Route[routes.size()]), id));
                id++;
            }
            pt = new PolynesianTriangle(islands.toArray(new Island[islands.size()]), Routes.toArray(new Route[Routes.size()]));
        }
        // Prints the initial state of the Polynesian Triangle
        System.out.println(pt.toString());
        pt = distrubuteResources(pt);
        while(pt.allFinished() == false){
            pt.progress();
        }
        // Prints the final state of the Polynesian Triangle after resources have been distributed
        System.out.println(pt.toString());

    }

    public static PolynesianTriangle distrubuteResources(PolynesianTriangle pt){
        Island[] islands = pt.getIslands();
        int totalPeoples = 0;
        //Gets a count for how many islands each resource can be found on
        Map<String, Integer> resourceCount = new HashMap<String, Integer>();
        for (Island island : islands) {
            totalPeoples += island.getNumPeople();
            Resource[] resources = island.getResources();
            for (Resource resource : resources) {
                String resourceType = resource.getType();
                resourceCount.put(resourceType, resourceCount.getOrDefault(resourceType, 0) + 1);
            }
        }
        //Creates a list of resources that are unique to one island
        ArrayList<Resource> uniqueResources = new ArrayList<Resource>();
        for (Island island : islands) {
            Resource[] resources = island.getResources();
            for (Resource resource : resources) {
                if (resourceCount.get(resource.getType()) == 1) {
                    uniqueResources.add(resource);
                }
            }
        }
        //Sets up deliveries for each unique resource
        for (Island island : islands) {
            Map<Island, List<Integer>> paths = findShortestPaths(pt, island);
            Resource[] resources = island.getResources();
            for (Resource resource : resources) {
                if (uniqueResources.contains(resource)) {
                    for (Island otherIsland : islands) {
                        if (otherIsland != island) {
                            List<Integer> path = paths.get(otherIsland);
                            if (path == null || path.isEmpty()) {
                                continue; // Skip if no valid path found
                            }
                            
                            LinkedList<Island> pathIslands = new LinkedList<>();
                            LinkedList<Route> pathRoutes = new LinkedList<>();
                            
                            for (int i = 0; i < path.size(); i++) {
                                pathIslands.add(islands[path.get(i)]);
                                if (i != path.size() - 1) {
                                    for (Route route : islands[path.get(i)].getRoutes()) {
                                        if (route.getEnd().equals(islands[path.get(i + 1)].getName())) {
                                            pathRoutes.add(route);
                                        }
                                    }
                                }
                            }
                            
                            // Calculate the total needed quantity based on the resource and population
                            double temp1 = resource.getQuantity();
                            double temp2 = totalPeoples;
                            double temp3 = otherIsland.getNumPeople();
                            double neededQuantity = (temp1 / temp2) * temp3;
                            while (neededQuantity > 0 && resource.getQuantity() > 0) {
                                if (neededQuantity >= 10) {
                                    pt.addCanoe(new Canoe(new Resource(resource.getType(), 10), new CanoeRoute(pathRoutes, pathIslands, new Delivery(otherIsland, new Resource(resource.getType(), 10)), pt.getCanoesOnRoutes().size()), pt.getCanoesOnRoutes().size()));
                                    neededQuantity -= 10;
                                } else{
                                    pt.addCanoe(new Canoe(new Resource(resource.getType(), (int) neededQuantity), new CanoeRoute(pathRoutes, pathIslands, new Delivery(otherIsland, new Resource(resource.getType(), (int) neededQuantity)), pt.getCanoesOnRoutes().size()), pt.getCanoesOnRoutes().size()));
                                    neededQuantity = 0;
                                }
                            }
                        }
                    }
                }
                //Removes the quantity of the unique resource from the island it is on to account for the resources that are being delivered
                double temp1 = resource.getQuantity();
                double temp2 = totalPeoples;
                double temp3 = totalPeoples - island.getNumPeople();
                double temp4 = (temp1 / temp2) * (temp3);
                int temp5 = (int) temp4;
                island.removeResource(resource.getType(), temp5);
            }
        }
        
        return pt;
    }

    public static Map<Island, List<Integer>> findShortestPaths(PolynesianTriangle pt, Island startIsland) {
        Map<Island, Integer> distances = new HashMap<>();
        Map<Island, List<Integer>> paths = new HashMap<>();
        Set<Island> visited = new HashSet<>();

        // Initialize distances and paths
        for (Island island : pt.getIslands()) {
            distances.put(island, Integer.MAX_VALUE);
            paths.put(island, new ArrayList<>());
        }

        distances.put(startIsland, 0);
        paths.put(startIsland, new ArrayList<>(List.of(startIsland.getId()))); // Initialize path with starting island's ID

        PriorityQueue<Island> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        queue.add(startIsland);

        while (!queue.isEmpty()) {
            Island currentIsland = queue.poll();
            if (visited.contains(currentIsland)) continue;
            visited.add(currentIsland);

            for (Route route : currentIsland.getRoutes()) {
                Island neighbor = getIslandByName(pt.getIslands(), route.getEnd());
                if (neighbor == null || visited.contains(neighbor)) continue;

                int newDist = distances.get(currentIsland) + route.getTravelTime();
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    List<Integer> newPath = new ArrayList<>(paths.get(currentIsland));
                    newPath.add(neighbor.getId());
                    paths.put(neighbor, newPath);
                    queue.add(neighbor);
                }
            }
        }

        // Create a list of entries and sort by path length
        List<Map.Entry<Island, List<Integer>>> entryList = new ArrayList<>(paths.entrySet());
        entryList.sort(Comparator.comparingInt(entry -> entry.getValue().size()));

        // Create a new ordered map to maintain order
        Map<Island, List<Integer>> orderedPaths = new LinkedHashMap<>();
        for (Map.Entry<Island, List<Integer>> entry : entryList) {
            orderedPaths.put(entry.getKey(), entry.getValue());
        }

        return orderedPaths;
    }

    private static Island getIslandByName(Island[] islands, String name) {
        for (Island island : islands) {
            if (island.getName().equals(name)) {
                return island;
            }
        }
        return null;
    }
}
