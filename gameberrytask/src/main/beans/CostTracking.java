package main.beans;

public class CostTracking implements Comparable<CostTracking>{
    private int type;
    private int noOrders;

    public  CostTracking(int type, int noOrders){
        this.type = type;
        this.noOrders = noOrders;
    }

    public int getType() {
        return type;
    }

    public int getNoOrders() {
        return noOrders;
    }

    @Override
    public int compareTo(CostTracking o) {
        return o.getNoOrders() - this.noOrders;
    }
}
