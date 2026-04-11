package myCompatibilityLayer;

import java.util.Enumeration;

/**
 * Mock implementation of a HashTable for compatibility purposes.
 * This class is not intended for production use and does not implement
 * any real functionality. It serves as a placeholder to satisfy type requirements
 * in the codebase where a HashTable is expected.
 * 
 * It is used to limit the methods available in the MapAdapter class, ensuring
 * that only the methods defined 
 */
public class MockHashTable {
    public MockHashTable() {}
    public MockHashTable(int initialCapacity){}
    public void clear() { }
    public boolean contains(Object value) { return false; }
    public boolean containsKey(Object key) { return false; }
    public Enumeration<Object> elements() { return null; }
    public Object get(Object key) { return null; }
    public boolean isEmpty() { return true; }
    public Enumeration<Object> keys() { return null; }
    public Object put(Object key, Object value) { return null; }
    protected void rehash() { return; }
    public Object remove(Object key) { return null; }
    public int size() { return 0; }
    public String toString() { return "HashTable"; }
}