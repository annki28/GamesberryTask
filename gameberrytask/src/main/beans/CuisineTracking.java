package main.beans;

public class CuisineTracking implements Comparable<CuisineTracking>{
    private Cuisine type;
    private int noOrders;

    public  CuisineTracking(Cuisine type, int noOrders){
        this.type = type;
        this.noOrders = noOrders;
    }

    public Cuisine getType() {
        return type;
    }

    public int getNoOrders() {
        return noOrders;
    }

    @Override
    public int compareTo(CuisineTracking o) {
        return o.getNoOrders() - this.noOrders;
    }
}
