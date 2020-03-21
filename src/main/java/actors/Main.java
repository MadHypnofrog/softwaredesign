package actors;

public class Main {

    public static void main(String[] args) {
        ResponseAggregator aggregator = new ResponseAggregator();
        aggregator.aggregate("test");
    }

}
