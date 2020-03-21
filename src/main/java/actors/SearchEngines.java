package actors;

public enum SearchEngines {
    YANDEX("yandex.ru"), GOOGLE("google.com"), BING("bing.com");

    String name;

    SearchEngines(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
