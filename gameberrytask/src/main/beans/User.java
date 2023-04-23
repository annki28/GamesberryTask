package main.beans;

public class User {
    private CuisineTracking[] cuisineTrackings;
    private CostTracking[] costTrackings;
    public User(CuisineTracking[] cuisineTrackings, CostTracking[] costTrackings){
        this.costTrackings = costTrackings;
        this.cuisineTrackings = cuisineTrackings;
    }
    public CuisineTracking[] getCuisineTrackings() {
        return cuisineTrackings;
    }
    public CostTracking[] getCostTrackings() {
        return costTrackings;
    }
}
