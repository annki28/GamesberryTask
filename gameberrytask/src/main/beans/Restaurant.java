package main.beans;

import java.util.Date;

public class Restaurant {
    private String restaurantId;
    private Cuisine cuisine;
    private int costBracket;
    private float rating;
    private boolean isRecommended;
    private Date onboardedTime;

    public Restaurant(String restaurantId, Cuisine cuisine, int costBracket, float rating, boolean isRecommended, Date onboardedTime) {
        this.restaurantId = restaurantId;
        this.cuisine = cuisine;
        this.costBracket = costBracket;
        this.rating = rating;
        this.isRecommended = isRecommended;
        this.onboardedTime = onboardedTime;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public int getCostBracket() {
        return costBracket;
    }

    public float getRating() {
        return rating;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public Date getOnboardedTime() {
        return onboardedTime;
    }
}
