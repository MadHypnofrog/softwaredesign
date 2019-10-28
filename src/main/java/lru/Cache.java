package lru;

public interface Cache<K, V> {

    V get(K key);

    V put(K key, V value);

    V delete(K key);

    boolean contains(K key);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

}
