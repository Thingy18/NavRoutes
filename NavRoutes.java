import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NavRoutes {

    public static void main(String[] args) throws FileNotFoundException{
        PolynesianTriangle pt = null;
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
        System.out.println(pt.toString());

    }
}
