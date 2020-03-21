package rx;

import org.bson.Document;

public class User {

    private final String login;
    private final Currency currency;

    public User(String login, String currency) {
        this(login, Currency.valueOf(currency));
    }

    public User(String login, Currency currency) {
        this.login = login;
        this.currency = currency;
    }

    public User(Document doc) {
        this(doc.getString("login"), doc.getString("currency"));
    }


    public String getLogin() {
        return login;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Document toDoc() {
        return new Document()
                .append("login", login)
                .append("currency", currency.toString());
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + "\'" +
                ", currency='" + currency + "\'" +
                "}";
    }

}
