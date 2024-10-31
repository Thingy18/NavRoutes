public class Route {
    String start;
    String end;
    int travelTime;

    Route(String start, String end, int travelTime) {
        this.start = start;
        this.end = end;
        this.travelTime = travelTime;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getTravelTime() {
        return travelTime;
    }
    
}
