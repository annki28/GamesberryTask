package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import main.beans.CostTracking;
import main.beans.Cuisine;
import main.beans.CuisineTracking;
import main.beans.Restaurant;
import main.beans.User;
import main.helper.Constants;
import main.helper.Helper;

public class RecommendationEngine {
    private static final int MAX_LIMIT_RESTAURANTS = 100;

    public static void main(String[] args) throws ParseException {


        CostTracking costTracking1 = new CostTracking(3, 9);
        CostTracking costTracking2 = new CostTracking(1, 4);
        CostTracking costTracking3 = new CostTracking(2, 5);
        CostTracking costTracking4 = new CostTracking(4, 2);
        CostTracking costTracking5 = new CostTracking(5, 1);

        CuisineTracking cuisineTracking1 = new CuisineTracking(Cuisine.NorthIndian, 8);
        CuisineTracking cuisineTracking2 = new CuisineTracking(Cuisine.SouthIndian, 5);
        CuisineTracking cuisineTracking3 = new CuisineTracking(Cuisine.Chinese, 3);

        Restaurant r1 = new Restaurant("1", Cuisine.NorthIndian, 3, 4.8f, true, new SimpleDateFormat("dd-MM-yyyy").parse("21-03-2023"));
        Restaurant r2 = new Restaurant("2", Cuisine.Chinese, 3, 4.5f, true, new SimpleDateFormat("dd-MM-yyyy").parse("21-03-2023"));
        Restaurant r3 = new Restaurant("3", Cuisine.NorthIndian, 3, 4.0f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-04-2023"));
        Restaurant r4 = new Restaurant("4", Cuisine.SouthIndian, 4, 3.8f, true, new SimpleDateFormat("dd-MM-yyyy").parse("21-03-2023"));
        Restaurant r5 = new Restaurant("5", Cuisine.NorthIndian, 4, 2.8f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-04-2023"));
        Restaurant r6 = new Restaurant("6", Cuisine.NorthIndian, 1, 3.2f, true, new SimpleDateFormat("dd-MM-yyyy").parse("21-03-2023"));
        Restaurant r7 = new Restaurant("7", Cuisine.SouthIndian, 1, 4.5f, true, new SimpleDateFormat("dd-MM-yyyy").parse("21-03-2023"));
        Restaurant r8 = new Restaurant("8", Cuisine.SouthIndian, 2, 4.8f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-04-2023"));
        Restaurant r9 = new Restaurant("9", Cuisine.Chinese, 4, 4.8f, true, new SimpleDateFormat("dd-MM-yyyy").parse("21-03-2023"));
        Restaurant r10 = new Restaurant("10", Cuisine.NorthIndian, 5, 4.8f, true, new SimpleDateFormat("dd-MM-yyyy").parse("21-03-2023"));
        Restaurant r11 = new Restaurant("11", Cuisine.NorthIndian, 2, 4.5f, true, new SimpleDateFormat("dd-MM-yyyy").parse("21-03-2023"));
        Restaurant r12 = new Restaurant("12", Cuisine.Chinese, 2, 4.0f, false, new SimpleDateFormat("dd-MM-yyyy").parse("22-04-2023"));
        Restaurant r13 = new Restaurant("13", Cuisine.SouthIndian, 3, 4.5f, false, new SimpleDateFormat("dd-MM-yyyy").parse("21-03-2023"));

        Restaurant[] restaurants = new Restaurant[] {r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13};


        User user = new User(new CuisineTracking[] {cuisineTracking3, cuisineTracking2, cuisineTracking1},
                             new CostTracking[] {costTracking5, costTracking4, costTracking2, costTracking1, costTracking3});

        String[] top100 = getRestaurantRecommendations(user, restaurants);
        Arrays.stream(top100).forEach((a) -> System.out.print(a + " "));
    }

    /**
     * Get Recommended restaurant based on user
     *
     * @param user
     * @param restaurants
     * @return
     */

    public static String[] getRestaurantRecommendations(User user, Restaurant[] restaurants) {
        List<Cuisine> resCuisineSorted = Helper.getCuisineSortedRes(user.getCuisineTrackings());
        List<Integer> resCostSorted = Helper.getCostSortedRes(user.getCostTrackings());
        List<Restaurant> primaryCuisinePrimaryCostList = Helper.getPrimaryCuisinePrimaryCost(resCostSorted, resCuisineSorted, restaurants);
        List<Restaurant> primaryCuisineSecondaryCostList = Helper.getPrimaryCuisineSecondaryCost(resCostSorted, resCuisineSorted, restaurants);
        List<Restaurant> secondaryCuisinePrimaryCostList = Helper.getSecondaryCuisinePrimaryCost(resCostSorted, resCuisineSorted, restaurants);

        // Step 1.1: Find featured restaurants of primary cuisine and primary cost bracket
        List<Restaurant> featurePCPC = primaryCuisinePrimaryCostList.stream().filter(entity -> entity.isRecommended()).collect(Collectors.toList());
        List<Restaurant> featurePCSC = null;
        List<Restaurant> featureSCPC = null;

        // Step 1.2: Find featured restaurants of primary cuisine and secondary cost & secondary cuisine and primary cost bracket
        if (featurePCPC == null || featurePCPC.isEmpty()) {
            featurePCSC = primaryCuisineSecondaryCostList.stream().filter(entity -> entity.isRecommended()).collect(Collectors.toList());
            featureSCPC = secondaryCuisinePrimaryCostList.stream().filter(entity -> entity.isRecommended()).collect(Collectors.toList());
        }
        // Step 2: All restaurants of Primary cuisine, primary cost bracket with rating >= 4
        List<Restaurant> PCPCG4 = primaryCuisinePrimaryCostList.stream().filter(entity -> entity.getRating() >= Constants.PRIMARY_CUISINE_PRIMARY_COST_RATING).collect(Collectors.toList());

        // Step 3: All restaurants of Primary cuisine, secondary cost bracket with rating >= 4.5
        List<Restaurant> PCSCG4_5 = primaryCuisineSecondaryCostList.stream().filter(entity -> entity.getRating() >= Constants.PRIMARY_CUISINE_SECONDARY_COST_RATING).collect(Collectors.toList());

        // Step 4: All restaurants of secondary cuisine, primary cost bracket with rating >= 4.5
        List<Restaurant> SCPCG4_5 = secondaryCuisinePrimaryCostList.stream().filter(entity -> entity.getRating() >= Constants.SECONDARY_CUISINE_PRIMARY_COST_RATING).collect(Collectors.toList());

        // Step 5: Top 4 newly created restaurants by rating
        List<Restaurant> newRestaurants = Helper.newlyCreatedRestaurant(restaurants);

        // Step 6: All restaurants of Primary cuisine, primary cost bracket with rating < 4
        List<Restaurant> PCPCL4 = primaryCuisinePrimaryCostList.stream().filter(entity -> entity.getRating() < Constants.PRIMARY_CUISINE_PRIMARY_COST_RATING).collect(Collectors.toList());

        // Step 7: All restaurants of Primary cuisine, secondary cost bracket with rating < 4.5
        List<Restaurant> PCSCL4_5 = primaryCuisineSecondaryCostList.stream().filter(entity -> entity.getRating() < Constants.PRIMARY_CUISINE_SECONDARY_COST_RATING).collect(Collectors.toList());

        // Step 8: All restaurants of secondary cuisine, primary cost bracket with rating < 4.5
        List<Restaurant> SCPCL4_5 = secondaryCuisinePrimaryCostList.stream().filter(entity -> entity.getRating() < Constants.SECONDARY_CUISINE_PRIMARY_COST_RATING).collect(Collectors.toList());

        List<Restaurant> reComRes = Helper.safeCombineList(featurePCPC, featurePCSC, featureSCPC, PCPCG4, PCSCG4_5, SCPCG4_5, newRestaurants, PCPCL4, PCSCL4_5, SCPCL4_5);

        List<String> recommendedRes = reComRes.stream()
                                              .map(restaurant -> restaurant.getRestaurantId())
                                              .distinct()
                                              .limit(MAX_LIMIT_RESTAURANTS)
                                              .collect(Collectors.toList());

        // Step 9: All restaurants of any cuisine, any cost bracket
        Helper.addRemainingRes(restaurants, recommendedRes);

        // sending only MAX_LIMIT_RESTAURANTS (i.e. 100) restaurants
        return recommendedRes.stream().limit(MAX_LIMIT_RESTAURANTS).toArray(String[]::new);
    }


}