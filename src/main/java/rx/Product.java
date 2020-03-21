package rx;

import org.bson.Document;

public class Product {

    private final String name;
    private final double priceRu;


    Product(String name, double price, Currency currency) {
        this.name = name;
        this.priceRu = Currency.convert(currency, Currency.RUR, price);
    }

    public Product(Document doc) {
        this(doc.getString("name"), doc.getDouble("priceRu"), Currency.RUR);
    }


    public String getName() {
        return name;
    }

    public double getPrice(Currency currency) {
        return Currency.convert(Currency.RUR, currency, priceRu);
    }

    public Document toDoc() {
        return new Document()
                .append("name", name)
                .append("priceRu", priceRu);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + "\'" +
                ", priceRu='" + priceRu + "\'" +
                "}";
    }

    public static String pretty(Product product, Currency currency) {
        return String.format("Product name: %s, price: %f %s\n",
                product.getName(), product.getPrice(currency), currency);
    }

}
