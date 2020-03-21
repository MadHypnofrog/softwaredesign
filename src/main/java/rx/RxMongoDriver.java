package rx;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import org.bson.Document;

public class RxMongoDriver {

    private static MongoClient client = MongoClients.create("mongodb://localhost:27017");
    private static MongoCollection<Document> userCollection = client.getDatabase("shop").getCollection("users");
    private static MongoCollection<Document> productCollection = client.getDatabase("shop").getCollection("products");

    public static void main(String[] args) {

    }

    public static Observable<Success> addUser(User user) {
        return userCollection.insertOne(user.toDoc());
    }

    public static Observable<Success> addProduct(Product product) {
        return productCollection.insertOne(product.toDoc());
    }

    public static Observable<User> getUsers() {
        return userCollection.find().toObservable().map(User::new);
    }

    public static Observable<Product> getProducts() {
        return productCollection.find().toObservable().map(Product::new);
    }

    public static Observable<DeleteResult> wipe() {
        return userCollection.deleteMany(new Document()).concatWith(productCollection.deleteMany(new Document()));
    }

}
