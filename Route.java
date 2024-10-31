public class Route {
    Island start;
    Island end;
    int travelTime;

    Route(Island start, Island end, int travelTime) {
        this.start = start;
        this.end = end;
        this.travelTime = travelTime;
    }

    public Island getStart() {
        return start;
    }

    public Island getEnd() {
        return end;
    }

    public int getTravelTime() {
        return travelTime;
    }
    
}
