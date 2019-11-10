package lru;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LRUCacheTest {

    @Test
    public void negativeCapacityTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LRUCache<Integer, Integer> cache = new LRUCache<>(-1);
        });
    }

    @Test
    public void basePutTest() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(5);

        cache.put(1, 1);
        cache.put(2, 2);

        assertEquals(cache.getPriorityList().toString(), "[2=2, 1=1]");
        assertEquals(cache.size(), 2);
        assertEquals(cache.get(1), new Integer(1));  // avoid ambiguous call here
        assertEquals(cache.getPriorityList().toString(), "[1=1, 2=2]");
        assertEquals(cache.get(2), new Integer(2));  // avoid ambiguous call here
        assertEquals(cache.getPriorityList().toString(), "[2=2, 1=1]");
        assertTrue(cache.contains(1));
        assertEquals(cache.getPriorityList().toString(), "[1=1, 2=2]");
        assertTrue(cache.contains(2));
        assertEquals(cache.getPriorityList().toString(), "[2=2, 1=1]");
        assertFalse(cache.contains(3));
        assertEquals(cache.getPriorityList().toString(), "[2=2, 1=1]");

        cache.put(1, 3);

        assertEquals(cache.getPriorityList().toString(), "[1=3, 2=2]");
        assertEquals(cache.size(), 2);
        assertEquals(cache.get(1), new Integer(3));  // avoid ambiguous call here
        assertEquals(cache.getPriorityList().toString(), "[1=3, 2=2]");
        assertEquals(cache.get(2), new Integer(2));  // avoid ambiguous call here
        assertEquals(cache.getPriorityList().toString(), "[2=2, 1=3]");
        assertTrue(cache.contains(1));
        assertEquals(cache.getPriorityList().toString(), "[1=3, 2=2]");
        assertTrue(cache.contains(2));
        assertEquals(cache.getPriorityList().toString(), "[2=2, 1=3]");
        assertFalse(cache.contains(3));
        assertEquals(cache.getPriorityList().toString(), "[2=2, 1=3]");
    }

    @Test
    public void baseDeleteTest() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(5);

        cache.put(1, 1);
        cache.delete(1);

        assertEquals(cache.getPriorityList(), new ArrayList<>());
        assertEquals(cache.size(), 0);
        assertTrue(cache.isEmpty());
        assertNull(cache.get(1));
        assertFalse(cache.contains(1));

        cache.put(2, 2);
        cache.put(1, 3);
        cache.put(3, 4);
        cache.put(4, 3);
        Integer old = cache.delete(3);

        assertEquals(cache.getPriorityList().toString(), "[4=3, 1=3, 2=2]");
        assertEquals(cache.size(), 3);
        assertEquals(cache.get(2), new Integer(2));  // avoid ambiguous call here
        assertEquals(cache.getPriorityList().toString(), "[2=2, 4=3, 1=3]");
        assertEquals(cache.get(1), new Integer(3));  // avoid ambiguous call here
        assertEquals(cache.getPriorityList().toString(), "[1=3, 2=2, 4=3]");
        assertEquals(cache.get(4), new Integer(3));  // avoid ambiguous call here
        assertEquals(cache.getPriorityList().toString(), "[4=3, 1=3, 2=2]");
        assertTrue(cache.contains(1));
        assertEquals(cache.getPriorityList().toString(), "[1=3, 4=3, 2=2]");
        assertTrue(cache.contains(2));
        assertEquals(cache.getPriorityList().toString(), "[2=2, 1=3, 4=3]");
        assertFalse(cache.contains(3));
        assertEquals(cache.getPriorityList().toString(), "[2=2, 1=3, 4=3]");
        assertTrue(cache.contains(4));
        assertEquals(cache.getPriorityList().toString(), "[4=3, 2=2, 1=3]");
        assertEquals(old, new Integer(4));  // avoid ambiguous call here

        old = cache.delete(3);
        assertNull(old);
        assertEquals(cache.getPriorityList().toString(), "[4=3, 2=2, 1=3]");
        assertEquals(cache.size(), 3);
        assertEquals(cache.get(1), new Integer(3));  // avoid ambiguous call here
        assertEquals(cache.get(2), new Integer(2));  // avoid ambiguous call here
        assertEquals(cache.get(4), new Integer(3));  // avoid ambiguous call here

    }

    @Test
    public void overflowTest() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(5);

        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);
        cache.get(3);
        cache.get(4);

        assertEquals(cache.getPriorityList().toString(), "[4=4, 3=3, 5=5, 2=2, 1=1]");
        assertEquals(cache.size(), 5);
        assertEquals(cache.get(2), new Integer(2));  // avoid ambiguous call here
        assertEquals(cache.getPriorityList().toString(), "[2=2, 4=4, 3=3, 5=5, 1=1]");

        cache.put(5, 3);
        assertEquals(cache.getPriorityList().toString(), "[5=3, 2=2, 4=4, 3=3, 1=1]");
        assertEquals(cache.size(), 5);
        assertEquals(cache.get(5), new Integer(3));  // avoid ambiguous call here

        cache.put(6, 6);
        assertEquals(cache.getPriorityList().toString(), "[6=6, 5=3, 2=2, 4=4, 3=3]");
        assertEquals(cache.size(), 5);
        assertEquals(cache.get(6), new Integer(6));  // avoid ambiguous call here
        assertEquals(cache.get(5), new Integer(3));  // avoid ambiguous call here
        assertFalse(cache.contains(1));
        assertNull(cache.get(1));
        assertEquals(cache.getPriorityList().toString(), "[5=3, 6=6, 2=2, 4=4, 3=3]");
        assertNull(cache.put(1, 1));
        assertEquals(cache.getPriorityList().toString(), "[1=1, 5=3, 6=6, 2=2, 4=4]");
        assertFalse(cache.contains(3));
    }

    @Test
    public void singletonTest() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);

        cache.put(1, 1);
        assertEquals(cache.getPriorityList().toString(), "[1=1]");
        cache.put(2, 2);
        assertEquals(cache.getPriorityList().toString(), "[2=2]");
        assertFalse(cache.contains(1));
        assertEquals(cache.delete(2), new Integer(2));  // avoid ambiguous call here
        assertTrue(cache.isEmpty());
        assertEquals(cache.getPriorityList().toString(), "[]");
        cache.put(1, 1);
        assertEquals(cache.getPriorityList().toString(), "[1=1]");
        assertFalse(cache.contains(2));
        assertTrue(cache.contains(1));
    }

}
