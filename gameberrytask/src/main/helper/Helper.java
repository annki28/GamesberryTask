package main.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import main.beans.CostTracking;
import main.beans.Cuisine;
import main.beans.CuisineTracking;
import main.beans.Restaurant;

public class Helper {

    /**
     * Getting secondary cost
     *
     * @param resCostSorted
     * @return
     */
    static List<Integer> getSecondaryCost(List<Integer> resCostSorted) {
        return resCostSorted.stream().skip(1).limit(2).collect(Collectors.toList());
    }

    /**
     * getting secondary cuisine
     *
     * @param resCuisineSorted
     * @return
     */
    static List<Cuisine> getSecondaryCuisine(List<Cuisine> resCuisineSorted) {
        return resCuisineSorted.stream().skip(1).limit(2).collect(Collectors.toList());
    }

    /**
     * getting sorted cost based on no. of orders
     *
     * @param costTrackings
     * @return
     */
    public static List<Integer> getCostSortedRes(CostTracking[] costTrackings) {
        //can we make it generic;
        Arrays.sort(costTrackings);
        return Arrays.stream(costTrackings).map(entity -> entity.getType()).collect(Collectors.toList());
    }

    /**
     * getting sorted cuisine bases on no. of orders
     *
     * @param cuisineTrackings
     * @return
     */
    public static List<Cuisine> getCuisineSortedRes(CuisineTracking[] cuisineTrackings) {
        Arrays.sort(cuisineTrackings);
        return Arrays.stream(cuisineTrackings).map(entity -> entity.getType()).collect(Collectors.toList());
    }

    /**
     * get newly created restaurants based on rating
     *
     * @param restaurants
     * @return
     */
    public static List<Restaurant> newlyCreatedRestaurant(Restaurant[] restaurants) {
        return Arrays.stream(restaurants)
                     .filter(restaurant -> isNewRestaurant(restaurant))
                     .sorted(Comparator.comparing(Restaurant::getRating).reversed())
                     .limit(4)
                     .collect(Collectors.toList());
    }

    /**
     * checking if restaurant is new
     *
     * @param restaurant
     * @return
     */
    static boolean isNewRestaurant(Restaurant restaurant) {
        long current = System.currentTimeMillis() - restaurant.getOnboardedTime().getTime();
        long diff = TimeUnit.MILLISECONDS.toHours(current);
        return diff < Constants.NEWLY_CREATED_DIFF;
    }

    /**
     * get primary cuisine secondary cost restaurants list
     *
     * @param resCostSorted
     * @param resCuisineSorted
     * @param restaurants
     * @return
     */
    public static List<Restaurant> getPrimaryCuisineSecondaryCost(List<Integer> resCostSorted, List<Cuisine> resCuisineSorted, Restaurant[] restaurants) {
        Cuisine priCui = resCuisineSorted.get(0);
        List<Integer> secCos = Helper.getSecondaryCost(resCostSorted);
        return Arrays.stream(restaurants)
                     .filter(entity -> entity.getCuisine().equals(priCui))
                     .filter(entity -> secCos.contains(entity.getCostBracket()))
                     .collect(Collectors.toList());
    }

    /**
     * get secondary cuisine and primary cost restaurants list
     *
     * @param resCostSorted
     * @param resCuisineSorted
     * @param restaurants
     * @return
     */
    public static List<Restaurant> getSecondaryCuisinePrimaryCost(List<Integer> resCostSorted, List<Cuisine> resCuisineSorted, Restaurant[] restaurants) {
        List<Cuisine> secCui = Helper.getSecondaryCuisine(resCuisineSorted);
        int primaryCostRes = resCostSorted.get(0);
        return Arrays.stream(restaurants)
                     .filter(entity -> secCui.contains(entity.getCuisine()))
                     .filter(entity -> primaryCostRes == entity.getCostBracket())
                     .collect(Collectors.toList());
    }

    /**
     * get primary cuisine and primary cost restaurants list
     *
     * @param resCostSorted
     * @param resCuisineSorted
     * @param restaurants
     * @return
     */
    public static List<Restaurant> getPrimaryCuisinePrimaryCost(List<Integer> resCostSorted, List<Cuisine> resCuisineSorted, Restaurant[] restaurants) {
        int primaryCostRes = resCostSorted.get(0);
        Cuisine primaryCuiRes = resCuisineSorted.get(0);
        return Arrays.stream(restaurants)
                     .filter(entity -> entity.getCostBracket() == primaryCostRes)
                     .filter(entity -> entity.getCuisine().equals(primaryCuiRes))
                     .collect(Collectors.toList());
    }

    /**
     * adding remaining restaurant based on rating
     *
     * @param restaurants
     * @param recommendedRes
     */
    public static void addRemainingRes(Restaurant[] restaurants, List<String> recommendedRes) {
        if (recommendedRes.size() < Constants.MAX_LIMIT_RESTAURANTS) {
            List<String> totalList = Arrays.stream(restaurants)
                                           .sorted(Comparator.comparing(Restaurant::getRating).reversed())
                                           .map(restaurant -> restaurant.getRestaurantId())
                                           .filter(restaurantsId -> !recommendedRes.contains(restaurantsId))
                                           .collect(Collectors.toList());
            recommendedRes.addAll(totalList);
        }
    }

    /**
     * combining the lists
     *
     * @param listsOfRes
     * @return
     */
    public static List<Restaurant> safeCombineList(List<Restaurant>... listsOfRes) {
        List<Restaurant> reComRes = new ArrayList<>();
        Arrays.stream(listsOfRes).filter(Objects::nonNull).forEach(reComRes::addAll);
        return reComRes;
    }
}
