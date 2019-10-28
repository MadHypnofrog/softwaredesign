package lru;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {

    private Map<K, Node<K, V>> store;
    private Node<K, V> head;
    private Node<K, V> tail;
    private int capacity;
    private int size;

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Cache capacity must be positive: " + capacity);
        }
        this.capacity = capacity;
        store = new HashMap<>();
        head = null;
        tail = null;
    }

    /**
     * Returns the value for the specified key or null if such a value does not exist.
     *
     * @param key key
     * @return value associated with that key, null if there is no such value
     */
    public V get(K key) {
        int oldSize = size();

        V result = doGet(key);

        int newSize = size();
        assert oldSize == newSize;
        assert !store.containsKey(key) || store.get(key).value == result;
        assert newSize > 0 || (isEmpty() && head == null && tail == null);
        assert result == null || (head.key == key && head.value == result && newSize >= 1);

        return result;
    }

    private V doGet(K key) {
        Node<K, V> value = store.get(key);
        if (value == null) return null;
        promoteToHead(value);
        return value.value;
    }

    /**
     * Puts the specified value into the cache associated with the specified key. If there already was a value
     * associated with the key, returns it, otherwise returns null.
     * @param key key
     * @param value value to associate with the key
     * @return previous value associated with that key or null if there was no value
     */
    public V put(K key, V value) {
        int oldSize = size();
        boolean hasKey = store.containsKey(key);
        boolean isFull = size() == capacity;

        V result = doPut(key, value);

        int newSize = size();
        assert (hasKey && oldSize == newSize) || (isFull && !hasKey && oldSize == newSize)
                || (!isFull && !hasKey && oldSize + 1 == newSize);
        assert newSize >= 1 && newSize <= capacity;
        assert head != null;
        assert head.key == key && head.value == value;
        assert store.get(key).value == value;

        return result;
    }

    private V doPut(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        pushNodeToList(newNode);
        assert head == newNode;
        Node<K, V> prevNode = store.put(key, newNode);
        if (prevNode != null) {
            removeNodeFromList(prevNode);
            increaseSize();
            return prevNode.value;
        }
        else {
            increaseSize();
            return null;
        }
    }

    /**
     * Deletes the value associated with the specified key from the cache and returns it.
     * @param key key
     * @return previous value associsated with that key or null if there was no value
     */
    public V delete(K key) {
        int oldSize = size();
        boolean hasKey = store.containsKey(key);

        V result = doDelete(key);

        int newSize = size();
        assert !store.containsKey(key);
        assert (hasKey && oldSize - 1 == newSize) || (!hasKey && oldSize == newSize);
        assert newSize >= 0 && newSize <= capacity;
        assert newSize > 0 || (isEmpty() && head == null && tail == null);

        return result;
    }

    private V doDelete(K key) {
        Node<K, V> toDelete = store.remove(key);
        if (toDelete == null) {
            return null;
        }
        removeNodeFromList(toDelete);
        return toDelete.value;
    }

    /**
     * Checks whether there is a value present associated with the specified key
     * @param key key
     * @return true if there is a value associated with the key, false otherwise
     */
    public boolean contains(K key) {
        int oldSize = size();

        boolean result = doContains(key);

        int newSize = size();
        assert oldSize == newSize;
        assert result == store.containsKey(key);
        assert newSize > 0 || (isEmpty() && head == null && tail == null);
        assert !result || head.key == key;

        return result;
    }

    private boolean doContains(K key) {
        promoteToHead(store.get(key));
        return store.containsKey(key);
    }

    /**
     * Returns the current size of the cache.
     * @return current size of the cache
     */
    public int size() {
        assert size >= 0 && size <= capacity;

        return size;
    }

    /**
     * Returns a list of all key-value pairs in this cache sorted by their priority.
     * @return a list of all key-value pairs in this cache sorted by their priority
     */
    public List<Pair<K, V>> getPriorityList() {
        Node<K, V> h = head;
        List<Pair<K, V>> result = new ArrayList<>();
        while (h != null) {
            result.add(new Pair<>(h.key, h.value));
            h = h.next;
        }
        Node<K, V> t = tail;
        List<Pair<K, V>> resultInverted = new ArrayList<>();
        while (t != null) {
            resultInverted.add(0, new Pair<>(t.key, t.value));
            t = t.prev;
        }
        assert result.toString().equals(resultInverted.toString());
        return result;
    }

    private void promoteToHead(Node<K, V> node) {
        if (node == null) return;
        while (node.prev != null) {
            Node<K, V> prev = node.prev;
            Node<K, V> next = node.next;
            node.next = prev;
            node.prev = prev.prev;
            prev.next = next;
            prev.prev = node;
            if (next != null) next.prev = prev;
            if (node == tail) tail = prev;
        }
        head = node;
    }

    private void removeNodeFromList(Node<K, V> node) {
        Node<K, V> prev = node.prev;
        Node<K, V> next = node.next;
        if (prev != null) {
            prev.next = next;
        }
        if (next != null) {
            next.prev = prev;
        }
        if (node == head) {
            head = next;
        }
        if (node == tail) {
            tail = prev;
        }
        decreaseSize();
    }

    private void pushNodeToList(Node<K, V> node) {
        node.next = head;
        if (head != null) {
            head.prev = node;
        } else {
            tail = node;
        }
        head = node;
    }

    private void increaseSize() {
        size++;
        while (size > capacity) {
            store.remove(tail.key);
            removeNodeFromList(tail);
        }
    }

    private void decreaseSize() {
        size--;
    }

    private class Node<T, U> {

        Node<T, U> next;
        Node<T, U> prev;
        T key;
        U value;

        Node(T key, U value) {
            this.key = key;
            this.value = value;
            next = null;
            prev = null;
        }

    }

}
