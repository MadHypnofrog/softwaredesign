package rx;

public enum Currency {

    RUR(1.0), USD(79.9), EUR(85.4);

    private double cur;

    Currency(double cur) {
        this.cur = cur;
    }

    public double getCur() {
        return cur;
    }

    public static double convert(Currency from, Currency to, double value) {
        return value * from.getCur() / to.getCur();
    }

}
