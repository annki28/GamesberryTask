package test;

import static org.junit.Assert.assertArrayEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import main.RecommendationEngine;
import main.beans.CostTracking;
import main.beans.Cuisine;
import main.beans.CuisineTracking;
import main.beans.Restaurant;
import main.beans.User;

public class TestRecommendations {
    @Test
    public void testGetRestaurantRecommendations() throws ParseException {
        // Create a user with a primary cuisine of North Indian and a primary cost bracket of 3
        CuisineTracking[] cuisines = {
                new CuisineTracking(Cuisine.NorthIndian, 5),
                new CuisineTracking(Cuisine.SouthIndian, 3),
                new CuisineTracking(Cuisine.Chinese, 2)
        };
        CostTracking[] costBrackets = {
                new CostTracking(3, 8),
                new CostTracking(4, 4),
                new CostTracking(2, 3)
        };
        User user = new User(cuisines, costBrackets);

        // Create some example restaurants
        Restaurant[] restaurants = {
                new Restaurant("r1", Cuisine.NorthIndian, 1, 3.5f, false, new Date()),
                new Restaurant("r2", Cuisine.NorthIndian, 2, 4.2f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r3", Cuisine.Chinese, 3, 4.5f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r4", Cuisine.NorthIndian, 3, 4.0f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r5", Cuisine.NorthIndian, 4, 4.8f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r6", Cuisine.SouthIndian, 3, 4.0f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r7", Cuisine.SouthIndian, 4, 4.2f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r8", Cuisine.Chinese, 3, 3.9f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r9", Cuisine.Chinese, 4, 4.4f, false, new Date()),
                new Restaurant("r10", Cuisine.NorthIndian, 3, 3.5f, true, new Date()),
        };

        // Call the getRestaurantRecommendations() method
        RecommendationEngine recommendationEngine = new RecommendationEngine();
        String[] result = recommendationEngine.getRestaurantRecommendations(user, restaurants);

        // Verify that the result contains the expected restaurants in the expected order
        String[] expected = {"r10","r4", "r5", "r3", "r9", "r1", "r2", "r6", "r8", "r7"};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testNotHavingSecondaryCuisine() throws ParseException {
        // Create a user with a primary cuisine of North Indian and a primary cost bracket of 3
        CuisineTracking[] cuisines = {
                new CuisineTracking(Cuisine.NorthIndian, 5)
        };
        CostTracking[] costBrackets = {
                new CostTracking(3, 8),
                new CostTracking(4, 4),
                new CostTracking(2, 3)
        };
        User user = new User(cuisines, costBrackets);

        // Create some example restaurants
        Restaurant[] restaurants = {
                new Restaurant("r1", Cuisine.NorthIndian, 1, 3.5f, false, new Date()),
                new Restaurant("r2", Cuisine.NorthIndian, 2, 4.2f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r3", Cuisine.Chinese, 3, 4.5f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r4", Cuisine.NorthIndian, 3, 4.0f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r5", Cuisine.NorthIndian, 4, 4.8f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r6", Cuisine.SouthIndian, 3, 4.0f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r7", Cuisine.SouthIndian, 4, 4.2f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r8", Cuisine.Chinese, 3, 3.9f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r9", Cuisine.Chinese, 4, 4.4f, false, new Date()),
                new Restaurant("r10", Cuisine.NorthIndian, 4, 4.4f, true, new Date())
        };

        // Call the getRestaurantRecommendations() method
        RecommendationEngine recommendationEngine = new RecommendationEngine();
        String[] result = recommendationEngine.getRestaurantRecommendations(user, restaurants);
        String[] expected = {"r10","r4", "r5", "r9", "r1", "r2", "r3", "r7", "r6", "r8"};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testNotHavingSecondaryCost() throws ParseException {
        // Create a user with a primary cuisine of North Indian and a primary cost bracket of 3
        CuisineTracking[] cuisines = {
                new CuisineTracking(Cuisine.NorthIndian, 5),
                new CuisineTracking(Cuisine.SouthIndian, 3),
                new CuisineTracking(Cuisine.Chinese, 2)
        };
        CostTracking[] costBrackets = {
                new CostTracking(3, 8)
        };
        User user = new User(cuisines, costBrackets);

        // Create some example restaurants
        Restaurant[] restaurants = {
                new Restaurant("r1", Cuisine.NorthIndian, 1, 3.5f, false, new Date()),
                new Restaurant("r2", Cuisine.NorthIndian, 2, 4.2f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r3", Cuisine.Chinese, 3, 4.5f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r4", Cuisine.NorthIndian, 3, 4.0f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r5", Cuisine.NorthIndian, 4, 4.8f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r6", Cuisine.SouthIndian, 3, 4.0f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r7", Cuisine.SouthIndian, 4, 4.2f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r8", Cuisine.Chinese, 3, 3.9f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-03-2023")),
                new Restaurant("r9", Cuisine.Chinese, 4, 4.4f, false, new Date())
        };

        // Call the getRestaurantRecommendations() method
        RecommendationEngine recommendationEngine = new RecommendationEngine();
        String[] result = recommendationEngine.getRestaurantRecommendations(user, restaurants);
        String[] expected = {"r4", "r3", "r9", "r1", "r6", "r8", "r5", "r2", "r7"};
        assertArrayEquals(expected, result);
    }
}
