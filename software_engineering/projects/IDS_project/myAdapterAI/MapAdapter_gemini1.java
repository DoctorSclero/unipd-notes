package myAdapterAI;

import java.util.Hashtable; // The adaptee from CLDC 1.1 equivalent

// Assuming HMap interface is defined as discussed previously:
// public interface HMap<K, V> {
//     // ... methods ...
//     public static interface HEntry<K, V> {
//         K getKey();
//         V getValue();
//         V setValue(V value);
//         boolean equals(Object o);
//         int hashCode();
//     }
// }

/**
 * An adapter class that implements the HMap interface,
 * using the CLDC 1.1 Hashtable as its underlying adaptee.
 * This class provides a J2SE 1.4.2 Map-like behavior
 * while adhering to CLDC 1.1 functionalities.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class MapAdapter_gemini1<K, V> implements HMap<K, V> {

    // The adaptee: CLDC 1.1 Hashtable
    private Hashtable adaptee;

    /**
     * Constructs a new, empty MapAdapter with a default initial capacity.
     */
    public MapAdapter() {
        this.adaptee = new Hashtable();
    }

    /**
     * Constructs a new MapAdapter with the same mappings as the specified HMap.
     *
     * @param m the map whose mappings are to be placed in this map
     * @throws NullPointerException if the specified map is null
     */
    public MapAdapter(HMap<? extends K, ? extends V> m) {
        this(); // Call default constructor
        putAll(m);
    }

    // --- HMap Interface Methods Implementation ---

    @Override
    public int size() {
        return adaptee.size();
    }

    @Override
    public boolean isEmpty() {
        return adaptee.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        // Hashtable does not allow null keys, so handle this explicitly
        if (key == null) {
            // J2SE Map.containsKey(null) would return true if null key exists.
            // Since Hashtable doesn't allow null keys, it will never contain one.
            return false;
        }
        return adaptee.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        // Hashtable does not allow null values, so handle this explicitly
        if (value == null) {
            // J2SE Map.containsValue(null) would return true if null value exists.
            // Since Hashtable doesn't allow null values, it will never contain one.
            return false;
        }
        return adaptee.containsValue(value);
    }

    @Override
    public V get(Object key) {
        // Hashtable.get() throws NullPointerException if key is null.
        // Map.get(null) should return null if key not found, or value if found.
        if (key == null) {
            return null; // Null keys are not supported by Hashtable
        }
        Object result = adaptee.get(key);
        // Hashtable.get returns null if key not found.
        // J2SE Map.get returns null if key not found or if value is null.
        // Since Hashtable doesn't store null values, if result is null, it means key was not found.
        return (V) result;
    }

    @Override
    public V put(K key, V value) {
        // Hashtable does not allow null keys or null values.
        // J2SE Map allows one null key and multiple null values.
        // You MUST handle this to be compliant with J2SE 1.4.2 Map behavior.
        if (key == null) {
            throw new NullPointerException("HMap does not support null keys.");
        }
        if (value == null) {
            throw new NullPointerException("HMap does not support null values.");
        }

        // Hashtable.put returns the previous value or null if no previous value.
        // It also throws NullPointerException if key or value is null.
        Object oldVal = adaptee.put(key, value);
        return (V) oldVal;
    }

    @Override
    public V remove(Object key) {
        // Hashtable.remove() throws NullPointerException if key is null.
        // J2SE Map.remove(null) should remove the null key if present.
        if (key == null) {
            return null; // Null keys are not supported by Hashtable
        }
        Object oldVal = adaptee.remove(key);
        return (V) oldVal;
    }

    @Override
    public void putAll(HMap<? extends K, ? extends V> m) {
        if (m == null) {
            throw new NullPointerException("Specified map is null.");
        }
        for (HMap.HEntry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue()); // Use this adapter's put method to ensure null checks
        }
    }

    @Override
    public void clear() {
        adaptee.clear();
    }

    @Override
    public HSet<K> keySet() {
        // This will require an HSet implementation that is backed by the adaptee's keys.
        // You'll need to create a new class for HSet and its iterator.
        // This is a complex part of the assignment due to "backing the views".
        return new HKeySet(); // Example: You'll implement HKeySet
    }

    @Override
    public HCollection<V> values() {
        // This will require an HCollection implementation that is backed by the adaptee's values.
        // You'll need to create a new class for HCollection and its iterator.
        return new HValuesCollection(); // Example: You'll implement HValuesCollection
    }

    @Override
    public HSet<HEntry<K, V>> entrySet() {
        // This will require an HSet implementation for entries, backed by the adaptee.
        // This is where your HMapEntryImpl will be used.
        return new HEntrySet(); // Example: You'll implement HEntrySet
    }

    // --- Private Static Nested Class for HEntry Implementation ---
    /**
     * Concrete implementation of the HMap.HEntry interface.
     * This class is static because it does not need an enclosing MapAdapter instance.
     * It is private because it's an internal implementation detail of MapAdapter.
     *
     * @param <K> the type of the key
     * @param <V> the type of the value
     */
    private static class HMapEntryImpl<K, V> implements HMap.HEntry<K, V> {
        private K key;
        private V value;

        /**
         * Constructs an HMapEntryImpl.
         * This constructor is package-private, meaning only classes within 'myAdapter'
         * (like MapAdapter itself) can directly create instances of this entry.
         * This promotes encapsulation.
         *
         * @param key the key of the entry
         * @param value the value of the entry
         */
        HMapEntryImpl(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            // This is an optional operation in J2SE Map.Entry.
            // If the underlying Hashtable allows modification via entry views,
            // you would update it here. For Hashtable, you'd likely need to
            // call adaptee.put(this.key, value) in the MapAdapter's context.
            // This implementation assumes the entry itself holds the value.
            // If it's a "live" view, you'd need a reference to the map.
            // For simplicity here, we'll just update the entry's value.
            // The backing of the view means this change should propagate to the map.
            // This is a complex part of the assignment.
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        /**
         * Compares the specified object with this entry for equality.
         * Returns {@code true} if the given object is also a map entry and the two entries
         * represent the same mapping.
         * More formally, two entries {@code e1} and {@code e2} represent the same mapping if
         * <pre>
         * (e1.getKey()==null ?
         * e2.getKey()==null : e1.getKey().equals(e2.getKey()))  &amp;&amp;
         * (e1.getValue()==null ?
         * e2.getValue()==null : e1.getValue().equals(e2.getValue()))
         * </pre>
         * This ensures that the {@code equals} method works properly across different
         * implementations of the {@code Map.Entry} interface.
         *
         * @param o object to be compared for equality with this map entry.
         * @return {@code true} if the specified object is equal to this map entry.
         * @see Object#hashCode()
         */
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof HMap.HEntry)) {
                return false;
            }
            HMap.HEntry<?, ?> e = (HMap.HEntry<?, ?>) o;
            Object k1 = getKey();
            Object v1 = getValue();
            Object k2 = e.getKey();
            Object v2 = e.getValue();

            // Note: Hashtable does not permit null keys or values.
            // If you were adapting a HashMap, you'd need to handle nulls here.
            // For this project, given Hashtable, you might simplify, but
            // the J2SE Map.Entry contract requires this null handling for equals.
            return (k1 == null ? k2 == null : k1.equals(k2)) &&
                   (v1 == null ? v2 == null : v1.equals(v2));
        }

        /**
         * Returns the hash code value for this map entry.
         * The hash code of a map entry {@code e} is defined as:
         * <pre>
         * (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         * (e.getValue()==null ? 0 : e.getValue().hashCode())
         * </pre>
         * This ensures that {@code e1.equals(e2)} implies {@code e1.hashCode()==e2.hashCode()}
         * for any two entries {@code e1} and {@code e2}.
         *
         * @return the hash code value for this map entry.
         * @see HMap.HEntry#equals(Object)
         */
        @Override
        public int hashCode() {
            // Note: Hashtable does not permit null keys or values.
            // If you were adapting a HashMap, you'd need to handle nulls here.
            // The J2SE Map.Entry contract requires this null handling for hashCode.
            return (getKey() == null ? 0 : getKey().hashCode()) ^
                   (getValue() == null ? 0 : getValue().hashCode());
        }

        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    // --- Placeholder for HSet and HCollection implementations ---
    // These would also be private static nested classes or separate top-level classes
    // and would need to be carefully implemented to provide "backing views".

    private class HKeySet implements HSet<K> {
        // Implementation of HSet methods (size, isEmpty, contains, iterator, etc.)
        // These methods will delegate to the underlying adaptee.keySet() or iterate over it.
        // Crucially, changes to HKeySet should reflect in MapAdapter and vice-versa.

        @Override
        public int size() { return MapAdapter.this.size(); } // Access outer class method
        @Override
        public boolean isEmpty() { return MapAdapter.this.isEmpty(); }
        @Override
        public boolean contains(Object o) { return MapAdapter.this.containsKey(o); }
        @Override
        public HIterator<K> iterator() {
            // This will return an HIterator that iterates over the Hashtable's keys.
            // This iterator must support remove() if it's an optional operation.
            return new HKeyIterator();
        }
        @Override
        public boolean add(K e) { throw new UnsupportedOperationException("add"); } // Optional operation not supported
        @Override
        public boolean remove(Object o) {
            if (MapAdapter.this.containsKey(o)) {
                MapAdapter.this.remove(o); // Remove from the underlying map
                return true;
            }
            return false;
        }
        @Override
        public boolean containsAll(HCollection<?> c) {
            for (Object element : c) {
                if (!contains(element)) return false;
            }
            return true;
        }
        @Override
        public boolean addAll(HCollection<? extends K> c) { throw new UnsupportedOperationException("addAll"); }
        @Override
        public boolean retainAll(HCollection<?> c) { throw new UnsupportedOperationException("retainAll"); }
        @Override
        public boolean removeAll(HCollection<?> c) { throw new UnsupportedOperationException("removeAll"); }
        @Override
        public void clear() { MapAdapter.this.clear(); }
        @Override
        public Object[] toArray() { throw new UnsupportedOperationException("toArray"); }
        @Override
        public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException("toArray"); }
    }

    private class HValuesCollection implements HCollection<V> {
        // Similar implementation to HKeySet, but for values
        @Override
        public int size() { return MapAdapter.this.size(); }
        @Override
        public boolean isEmpty() { return MapAdapter.this.isEmpty(); }
        @Override
        public boolean contains(Object o) { return MapAdapter.this.containsValue(o); }
        @Override
        public HIterator<V> iterator() {
            return new HValueIterator();
        }
        @Override
        public boolean add(V e) { throw new UnsupportedOperationException("add"); }
        @Override
        public boolean remove(Object o) { throw new UnsupportedOperationException("remove"); }
        @Override
        public boolean containsAll(HCollection<?> c) { throw new UnsupportedOperationException("containsAll"); }
        @Override
        public boolean addAll(HCollection<? extends V> c) { throw new UnsupportedOperationException("addAll"); }
        @Override
        public boolean removeAll(HCollection<?> c) { throw new UnsupportedOperationException("removeAll"); }
        @Override
        public boolean retainAll(HCollection<?> c) { throw new UnsupportedOperationException("retainAll"); }
        @Override
        public void clear() { MapAdapter.this.clear(); }
        @Override
        public Object[] toArray() { throw new UnsupportedOperationException("toArray"); }
        @Override
        public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException("toArray"); }
    }

    private class HEntrySet implements HSet<HMap.HEntry<K, V>> {
        // Similar implementation to HKeySet, but for entries.
        // This iterator will return HMapEntryImpl instances.
        @Override
        public int size() { return MapAdapter.this.size(); }
        @Override
        public boolean isEmpty() { return MapAdapter.this.isEmpty(); }
        @Override
        public boolean contains(Object o) {
            if (!(o instanceof HMap.HEntry)) return false;
            HMap.HEntry<?, ?> entry = (HMap.HEntry<?, ?>) o;
            if (!MapAdapter.this.containsKey(entry.getKey())) return false;
            return MapAdapter.this.get(entry.getKey()).equals(entry.getValue());
        }
        @Override
        public HIterator<HMap.HEntry<K, V>> iterator() {
            return new HEntryIterator();
        }
        @Override
        public boolean add(HMap.HEntry<K, V> e) { throw new UnsupportedOperationException("add"); }
        @Override
        public boolean remove(Object o) {
            if (!(o instanceof HMap.HEntry)) return false;
            HMap.HEntry<?, ?> entry = (HMap.HEntry<?, ?>) o;
            if (contains(entry)) {
                MapAdapter.this.remove(entry.getKey());
                return true;
            }
            return false;
        }
        @Override
        public boolean containsAll(HCollection<?> c) { throw new UnsupportedOperationException("containsAll"); }
        @Override
        public boolean addAll(HCollection<? extends HMap.HEntry<K, V>> c) { throw new UnsupportedOperationException("addAll"); }
        @Override
        public boolean removeAll(HCollection<?> c) { throw new UnsupportedOperationException("removeAll"); }
        @Override
        public boolean retainAll(HCollection<?> c) { throw new UnsupportedOperationException("retainAll"); }
        @Override
        public void clear() { MapAdapter.this.clear(); }
        @Override
        public Object[] toArray() { throw new UnsupportedOperationException("toArray"); }
        @Override
        public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException("toArray"); }
    }

    // --- Iterator Implementations ---
    // These will likely be non-static inner classes because they need to iterate
    // over the adaptee's internal structure and potentially call remove() on the outer map.

    private class HKeyIterator implements HIterator<K> {
        private java.util.Enumeration keys;
        private K lastReturnedKey = null; // To support remove()

        HKeyIterator() {
            this.keys = adaptee.keys();
        }

        @Override
        public boolean hasNext() {
            return keys.hasMoreElements();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            lastReturnedKey = (K) keys.nextElement();
            return lastReturnedKey;
        }

        @Override
        public void remove() {
            // This is an optional operation.
            // If supported, it should remove the last element returned by next().
            // This is tricky with Hashtable's Enumeration as it doesn't have a remove method.
            // You'd need to remove it from the underlying MapAdapter.
            if (lastReturnedKey == null) {
                throw new IllegalStateException();
            }
            MapAdapter.this.remove(lastReturnedKey); // Remove from the outer map
            lastReturnedKey = null; // Invalidate last returned key
        }
    }

    private class HValueIterator implements HIterator<V> {
        private java.util.Enumeration elements;
        private Object lastReturnedValue = null; // To support remove() - more complex for values

        HValueIterator() {
            this.elements = adaptee.elements();
        }

        @Override
        public boolean hasNext() {
            return elements.hasMoreElements();
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            lastReturnedValue = elements.nextElement();
            return (V) lastReturnedValue;
        }

        @Override
        public void remove() {
            // Removing from a values view is complex because you need to find the key
            // associated with the value. J2SE Map.values().iterator().remove()
            // removes the corresponding mapping from the map.
            // This is an optional operation and might be difficult to implement efficiently
            // with Hashtable's Enumeration. You might throw UnsupportedOperationException.
            throw new UnsupportedOperationException("remove is not supported for HValueIterator");
        }
    }

    private class HEntryIterator implements HIterator<HMap.HEntry<K, V>> {
        private java.util.Enumeration keys;
        private K lastReturnedKey = null;

        HEntryIterator() {
            this.keys = adaptee.keys();
        }

        @Override
        public boolean hasNext() {
            return keys.hasMoreElements();
        }

        @Override
        public HMap.HEntry<K, V> next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            lastReturnedKey = (K) keys.nextElement();
            // Create a new HEntryImpl for each entry.
            // Note: The setValue method of this HEntryImpl will need to update the underlying map.
            // This requires a reference to the outer MapAdapter or a mechanism to update it.
            // For now, we'll create a simple entry.
            return new HMapEntryImpl<>(lastReturnedKey, (V) adaptee.get(lastReturnedKey));
        }

        @Override
        public void remove() {
            // This is an optional operation.
            // If supported, it should remove the last entry returned by next().
            if (lastReturnedKey == null) {
                throw new IllegalStateException();
            }
            MapAdapter.this.remove(lastReturnedKey); // Remove from the outer map
            lastReturnedKey = null; // Invalidate last returned key
        }
    }

    // --- Placeholder for HSet and HCollection interfaces ---
    // You'll need to define these in myAdapter package as well.
    // They should mirror java.util.Set and java.util.Collection 1.4.2

    public interface HCollection<E> extends Iterable<E> {
        int size();
        boolean isEmpty();
        boolean contains(Object o);
        HIterator<E> iterator();
        boolean add(E e); // Optional operation
        boolean remove(Object o); // Optional operation
        boolean containsAll(HCollection<?> c);
        boolean addAll(HCollection<? extends E> c); // Optional operation
        boolean removeAll(HCollection<?> c); // Optional operation
        boolean retainAll(HCollection<?> c); // Optional operation
        void clear(); // Optional operation
        Object[] toArray();
        <T> T[] toArray(T[] a);
    }

    public interface HSet<E> extends HCollection<E> {
        // Set has no additional methods beyond Collection, just stricter contracts.
        // You might override equals and hashCode here if needed for Set contract.
    }

    public interface HIterator<E> {
        boolean hasNext();
        E next();
        void remove(); // Optional operation
    }
}
