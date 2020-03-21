package rx;

import io.netty.handler.codec.http.QueryStringDecoder;
import io.reactivex.netty.protocol.http.server.HttpServer;

import java.util.List;
import java.util.Map;

public class RxNettyHttpServer {

    public static void main(final String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {

                    String query = req.getDecodedPath().substring(1);
                    Map<String, List<String>> parameters = new QueryStringDecoder(req.getUri()).parameters();

                    Observable<String> response;

                    switch (query) {
                        case "register": {
                            response = RxMongoDriver
                                    .getUsers()
                                    .exists(user -> user.getLogin().equals(parameters.get("login").get(0)))
                                    .flatMap(exists -> {
                                                String login = parameters.get("login").get(0);
                                                if (exists) {
                                                    return Observable.just("User with login " + login
                                                            + " is already registered!");
                                                } else {
                                                    String currencyStr = parameters.get("currency").get(0);
                                                    try {
                                                        Currency currency = Currency.valueOf(currencyStr);
                                                        User user = new User(login, currency);
                                                        return RxMongoDriver
                                                                .addUser(user)
                                                                .map(success -> user.toString() +
                                                                        " successfully registered.");
                                                    } catch (IllegalArgumentException e) {
                                                        return Observable.just("Invalid currency: " + currencyStr);
                                                    }
                                                }
                                            }
                                    );
                            break;
                        }
                        case "add": {
                            response = RxMongoDriver
                                    .getProducts()
                                    .exists(product -> product.getName().equals(parameters.get("name").get(0)))
                                    .flatMap(exists -> {
                                                try {
                                                    String name = parameters.get("name").get(0);
                                                    if (exists) {
                                                        return Observable.just("Product with name " + name
                                                                + " is already added!");
                                                    } else {
                                                        String currencyStr = parameters.get("currency").get(0);
                                                        try {
                                                            Currency currency = Currency.valueOf(currencyStr);
                                                            double price = Double.valueOf(parameters.get("price").get(0));
                                                            if (price < 0) {
                                                                return Observable.just("Negative price is passed!");
                                                            }
                                                            Product product = new Product(name, price, currency);
                                                            return RxMongoDriver
                                                                    .addProduct(product)
                                                                    .map(success -> product.toString() +
                                                                            " successfully added.");
                                                        } catch (NumberFormatException e) {
                                                            return Observable.just("Invalid price is passed!");
                                                        } catch (IllegalArgumentException e) {
                                                            return Observable.just("Invalid currency: " + currencyStr);
                                                        }
                                                    }
                                                } catch (NumberFormatException e) {
                                                    return Observable.just("Invalid id is passed!");
                                                }
                                            }
                                    );
                            break;
                        }
                        case "show": {
                            response = RxMongoDriver
                                    .getUsers()
                                    .filter(user -> user.getLogin().equals(parameters.get("login").get(0)))
                                    .firstOrDefault(new User("", Currency.RUR))
                                    .flatMap(user -> {
                                                if (user.getLogin().isEmpty()) {
                                                    return Observable.just("Invalid login is passed!");
                                                } else {
                                                    Currency currency = user.getCurrency();
                                                    return RxMongoDriver
                                                            .getProducts()
                                                            .map(product -> Product.pretty(product, currency));
                                                }
                                            }
                                    );
                            break;
                        }
                        case "wipe": {
                            response = RxMongoDriver
                                    .wipe()
                                    .map(result -> "Successfully wiped");
                            break;
                        }
                        default: {
                            response = null;
                        }
                    }

                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

}
