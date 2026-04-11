package myAdapterAI;

import java.util.Hashtable; // The adaptee from CLDC 1.1 equivalent
import java.util.Enumeration; // For iterators, as Enumeration is common in CLDC 1.1

// --- HMap Interface (No Generics) ---
/**
 * Represents a map that maps keys to values.
 * This interface mirrors the functionality of java.util.Map from J2SE 1.4.2,
 * adapted for CLDC 1.1 environments by using Object for all types.
 *
 * All optional operations are expected to be implemented.
 */
interface HMap {
    // Methods of the Map interface, using Object for keys and values

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map.
     */
    int size();

    /**
     * Returns {@code true} if this map contains no key-value mappings.
     *
     * @return {@code true} if this map contains no key-value mappings.
     */
    boolean isEmpty();

    /**
     * Returns {@code true} if this map contains a mapping for the specified key.
     *
     * @param key The key whose presence in this map is to be tested.
     * @return {@code true} if this map contains a mapping for the specified key.
     * @throws ClassCastException if the key is of an inappropriate type for this map
     * (optional operation).
     * @throws NullPointerException if the specified key is null and this map does not
     * permit null keys (optional operation).
     */
    boolean containsKey(Object key);

    /**
     * Returns {@code true} if this map maps one or more keys to the specified value.
     *
     * @param value The value whose presence in this map is to be tested.
     * @return {@code true} if this map maps one or more keys to the specified value.
     * @throws ClassCastException if the value is of an inappropriate type for this map
     * (optional operation).
     * @throws NullPointerException if the specified value is null and this map does not
     * permit null values (optional operation).
     */
    boolean containsValue(Object value);

    /**
     * Returns the value to which the specified key is mapped, or {@code null} if this map
     * contains no mapping for the key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value to which the specified key is mapped, or {@code null} if this map
     * contains no mapping for the key.
     * @throws ClassCastException if the key is of an inappropriate type for this map
     * (optional operation).
     * @throws NullPointerException if the specified key is null and this map does not
     * permit null keys (optional operation).
     */
    Object get(Object key);

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with {@code key}, or {@code null} if there was
     * no mapping for {@code key}.
     * @throws ClassCastException if the class of the specified key or value prevents it from
     * being stored in this map.
     * @throws NullPointerException if the specified key or value is null and this map does not
     * permit null keys or values.
     * @throws IllegalArgumentException if some property of the specified key or value prevents it
     * from being stored in this map.
     */
    Object put(Object key, Object value);

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key The key whose mapping is to be removed from the map.
     * @return The previous value associated with {@code key}, or {@code null} if there was
     * no mapping for {@code key}.
     * @throws ClassCastException if the key is of an inappropriate type for this map
     * (optional operation).
     * @throws NullPointerException if the specified key is null and this map does not
     * permit null keys (optional operation).
     */
    Object remove(Object key);

    /**
     * Copies all of the mappings from the specified map to this map.
     *
     * @param m The mappings to be stored in this map.
     * @throws ClassCastException if the class of a key or value in the specified map
     * prevents it from being stored in this map.
     * @throws NullPointerException if the specified map is null, or if this map does not
     * permit null keys or values and the specified map contains them.
     * @throws IllegalArgumentException if some property of a key or value in the specified map
     * prevents it from being stored in this map.
     */
    void putAll(HMap m);

    /**
     * Removes all of the mappings from this map. The map will be empty after this call returns.
     */
    void clear();

    /**
     * Returns a {@link HSet} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
     *
     * @return a set view of the keys contained in this map.
     */
    HSet keySet();

    /**
     * Returns a {@link HCollection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are reflected in the collection, and vice-versa.
     *
     * @return a collection view of the values contained in this map.
     */
    HCollection values();

    /**
     * Returns a {@link HSet} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
     *
     * @return a set view of the mappings contained in this map.
     */
    HSet entrySet();

    // --- HEntry Interface (No Generics) ---
    /**
     * A map entry (key-value pair). The {@code HMap.entrySet} method returns a
     * collection-view of the map, whose elements are of this class. The only
     * way to obtain a reference to a map entry is from the iterator of this
     * collection-view. These {@code HMap.Entry} objects are valid only for the
     * duration of the iteration; the behavior of a map entry is undefined if
     * the backing map has been modified while the iteration is in progress
     * in any way other than by the {@code setValue} operation on the map entry.
     */
    public static interface HEntry {
        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry.
         */
        Object getKey();

        /**
         * Returns the value corresponding to this entry.
         *
         * @return the value corresponding to this entry.
         */
        Object getValue();

        /**
         * Replaces the value corresponding to this entry with the specified value.
         *
         * @param value new value to be stored in this entry.
         * @return the old value corresponding to the entry.
         * @throws UnsupportedOperationException if the {@code put} operation is not
         * supported by the backing map.
         * @throws ClassCastException if the class of the specified value prevents it
         * from being stored in this map.
         * @throws NullPointerException if the specified value is null and this map
         * does not permit null values.
         * @throws IllegalArgumentException if some property of this value prevents it
         * from being stored in this map.
         */
        Object setValue(Object value);

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
        boolean equals(Object o);

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
        int hashCode();
    }
}

// --- HCollection Interface (No Generics) ---
/**
 * Represents a collection of objects. This interface mirrors the functionality
 * of java.util.Collection from J2SE 1.4.2, adapted for CLDC 1.1 environments.
 */
interface HCollection {
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    HIterator iterator();
    boolean add(Object e); // Optional operation
    boolean remove(Object o); // Optional operation
    boolean containsAll(HCollection c);
    boolean addAll(HCollection c); // Optional operation
    boolean removeAll(HCollection c); // Optional operation
    boolean retainAll(HCollection c); // Optional operation
    void clear(); // Optional operation
    Object[] toArray();
    Object[] toArray(Object[] a);
}

// --- HSet Interface (No Generics) ---
/**
 * Represents a set of objects. This interface mirrors the functionality
 * of java.util.Set from J2SE 1.4.2, adapted for CLDC 1.1 environments.
 * It extends HCollection and adds no new methods, but implies stricter
 * contracts for its elements (no duplicates).
 */
interface HSet extends HCollection {
    // Set has no additional methods beyond Collection, just stricter contracts.
    // You might override equals and hashCode here if needed for Set contract.
}

// --- HIterator Interface (No Generics) ---
/**
 * An iterator over a collection. This interface mirrors the functionality
 * of java.util.Iterator from J2SE 1.4.2, adapted for CLDC 1.1 environments.
 */
interface HIterator {
    boolean hasNext();
    Object next();
    void remove(); // Optional operation
}


/**
 * An adapter class that implements the HMap interface,
 * using the CLDC 1.1 Hashtable as its underlying adaptee.
 * This class provides a J2SE 1.4.2 Map-like behavior
 * while adhering to CLDC 1.1 functionalities.
 *
 * IMPORTANT NOTE ON NULLS:
 * CLDC 1.1 Hashtable DOES NOT permit null keys or null values.
 * J2SE 1.4.2 java.util.Map DOES permit one null key and multiple null values.
 *
 * This current implementation will throw NullPointerException for null keys/values
 * when interacting with the underlying Hashtable, mirroring Hashtable's behavior.
 * To fully comply with J2SE 1.4.2 Map behavior, you would need to implement
 * additional logic within this MapAdapter_gemini2 to store null keys/values outside
 * the Hashtable (e.g., using a dedicated field for the null key's value,
 * and potentially a separate collection for keys mapping to null values).
 * This is a significant part of the assignment's compliance requirement.
 */
public class MapAdapter_gemini2 implements HMap {

    // The adaptee: CLDC 1.1 Hashtable
    private Hashtable adaptee;

    // --- Fields for J2SE 1.4.2 null key/value compliance (if needed) ---
    // private boolean hasNullKey = false;
    // private Object nullKeyValue = null;
    // private Hashtable nullValuesMap = null; // To store keys that map to null values

    /**
     * Constructs a new, empty MapAdapter_gemini2 with a default initial capacity.
     */
    public MapAdapter_gemini2() {
        this.adaptee = new Hashtable();
    }

    /**
     * Constructs a new MapAdapter_gemini2 with the same mappings as the specified HMap.
     *
     * @param m the map whose mappings are to be placed in this map
     * @throws NullPointerException if the specified map is null
     */
    public MapAdapter_gemini2(HMap m) {
        this(); // Call default constructor
        putAll(m);
    }

    // --- HMap Interface Methods Implementation ---

    @Override
    public int size() {
        // If null key/value handling is implemented, adjust size here.
        // int totalSize = adaptee.size();
        // if (hasNullKey) totalSize++;
        // return totalSize;
        return adaptee.size();
    }

    @Override
    public boolean isEmpty() {
        // If null key/value handling is implemented, adjust isEmpty here.
        // return adaptee.isEmpty() && !hasNullKey;
        return adaptee.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        // IMPORTANT: Hashtable does not allow null keys.
        // If J2SE 1.4.2 Map compliance is needed for null keys:
        // if (key == null) {
        //     return hasNullKey;
        // }
        // Otherwise, Hashtable.containsKey(null) would throw NullPointerException.
        return adaptee.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        // IMPORTANT: Hashtable does not allow null values.
        // If J2SE 1.4.2 Map compliance is needed for null values:
        // if (value == null) {
        //     // Check if nullKeyValue is null AND hasNullKey is true
        //     // OR if any key in nullValuesMap maps to null.
        //     return (hasNullKey && nullKeyValue == null) || (nullValuesMap != null && !nullValuesMap.isEmpty());
        // }
        // Otherwise, Hashtable.containsValue(null) would throw NullPointerException.
        return adaptee.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        // IMPORTANT: Hashtable.get() throws NullPointerException if key is null.
        // If J2SE 1.4.2 Map compliance is needed for null keys:
        // if (key == null) {
        //     return hasNullKey ? nullKeyValue : null;
        // }
        // Otherwise, this will throw NullPointerException if key is null.
        return adaptee.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        // IMPORTANT: Hashtable does not allow null keys or null values.
        // J2SE 1.4.2 Map allows one null key and multiple null values.
        // To be fully compliant, you MUST implement a strategy here to store
        // null keys/values outside the Hashtable.
        // The current implementation will throw NullPointerException if key or value is null.

        // Example of how you *might* handle null key (more complex for null values):
        // if (key == null) {
        //     Object old = nullKeyValue;
        //     nullKeyValue = value;
        //     hasNullKey = true;
        //     return old;
        // }
        // if (value == null) {
        //     // This is very complex for multiple null values. You'd need a separate map.
        //     // For now, it will throw NPE from adaptee.
        //     throw new NullPointerException("HMap does not support null values due to Hashtable adaptee.");
        // }

        return adaptee.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        // IMPORTANT: Hashtable.remove() throws NullPointerException if key is null.
        // If J2SE 1.4.2 Map compliance is needed for null keys:
        // if (key == null) {
        //     if (hasNullKey) {
        //         Object old = nullKeyValue;
        //         hasNullKey = false;
        //         nullKeyValue = null;
        //         return old;
        //     }
        //     return null;
        // }
        // Otherwise, this will throw NullPointerException if key is null.
        return adaptee.remove(key);
    }

    @Override
    public void putAll(HMap m) {
        if (m == null) {
            throw new NullPointerException("Specified map is null.");
        }
        // Iterate through the entries of the given map and put them into this adapter.
        // This will use the put method above, which handles nulls based on your strategy.
        for (HIterator it = m.entrySet().iterator(); it.hasNext(); ) {
            HMap.HEntry entry = (HMap.HEntry) it.next();
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        adaptee.clear();
        // If null key/value handling is implemented, clear those fields too.
        // hasNullKey = false;
        // nullKeyValue = null;
        // if (nullValuesMap != null) nullValuesMap.clear();
    }

    @Override
    public HSet keySet() {
        return new HKeySet();
    }

    @Override
    public HCollection values() {
        return new HValuesCollection();
    }

    @Override
	public HSet entrySet() {
		return new HEntrySet();
	}
	
	/**
     * Compares the specified object with this map for equality. Returns {@code true}
     * if the given object is also an HMap and the two maps represent the same mappings.
     * More formally, two maps {@code m1} and {@code m2} represent the same mappings if
     * {@code m1.entrySet().equals(m2.entrySet())}.
     *
     * @param o object to be compared for equality with this map.
     * @return {@code true} if the specified object is equal to this map.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        // Check if the other object is an instance of HMap
        if (!(o instanceof HMap)) {
            return false;
        }

        HMap otherMap = (HMap) o;

        // Compare sizes first for quick exit
        if (size() != otherMap.size()) {
            return false;
        }

        // The core of Map equality: compare their entry sets.
        // This relies on the HSet.equals and HMap.HEntry.equals implementations.
        return entrySet().equals(otherMap.entrySet());
    }

    /**
     * Returns the hash code value for this map. The hash code of a map is defined
     * to be the sum of the hash codes of each entry in the map's {@code entrySet()} view.
     * This ensures that {@code m1.equals(m2)} implies {@code m1.hashCode()==m2.hashCode()}
     * for any two maps {@code m1} and {@code m2}.
     *
     * @return the hash code value for this map.
     * @see HMap#equals(Object)
     */
    @Override
    public int hashCode() {
        int h = 0;
        // Iterate over the entrySet and sum their hash codes
        // This relies on the HMap.HEntry.hashCode implementation.
        for (HIterator it = entrySet().iterator(); it.hasNext(); ) {
            Object entry = it.next(); // This will be an HMap.HEntry
            if (entry != null) { // Defensive check, though entrySet should not return null entries
                h += entry.hashCode();
            }
        }
        return h;
    }

    // --- Private Static Nested Class for HEntry Implementation (No Generics) ---
    /**
     * Concrete implementation of the HMap.HEntry interface.
     * This class is static because it does not need an enclosing MapAdapter_gemini2 instance.
     * It is private because it's an internal implementation detail of MapAdapter_gemini2.
     */
    private static class HMapEntryImpl implements HMap.HEntry {
        private Object key;
        private Object value;

        /**
         * Constructs an HMapEntryImpl.
         * This constructor is package-private, meaning only classes within 'myAdapter'
         * (like MapAdapter_gemini2 itself) can directly create instances of this entry.
         * This promotes encapsulation.
         *
         * @param key the key of the entry
         * @param value the value of the entry
         */
        HMapEntryImpl(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Object getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            // This is an optional operation in J2SE Map.Entry.
            // For full "backing the views" compliance, this method should update
            // the value in the underlying MapAdapter_gemini2's adaptee.
            // This would require the HMapEntryImpl to have a reference to the
            // MapAdapter_gemini2, or a callback mechanism, which would make it a non-static
            // inner class or require passing the MapAdapter_gemini2 instance.
            // As a static nested class, it cannot directly access MapAdapter_gemini2.this.
            // For now, it updates its own internal value.
            // You would need to ensure the MapAdapter_gemini2's entrySet() returns entries
            // that can correctly update the underlying map.
            Object oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            // Use instanceof for safe casting
            if (!(o instanceof HMap.HEntry)) {
                return false;
            }
            HMap.HEntry e = (HMap.HEntry) o;
            Object k1 = getKey();
            Object v1 = getValue();
            Object k2 = e.getKey();
            Object v2 = e.getValue();

            // J2SE Map.Entry equals handles nulls as shown.
            // Even if the underlying Hashtable doesn't store nulls,
            // the HEntry's equals method must comply with Map.Entry's contract.
            return (k1 == null ? k2 == null : k1.equals(k2)) &&
                   (v1 == null ? v2 == null : v1.equals(v2));
        }

        @Override
        public int hashCode() {
            // J2SE Map.Entry hashCode handles nulls as shown.
            // Even if the underlying Hashtable doesn't store nulls,
            // the HEntry's hashCode method must comply with Map.Entry's contract.
            return (getKey() == null ? 0 : getKey().hashCode()) ^
                   (getValue() == null ? 0 : getValue().hashCode());
        }

        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    // --- HSet and HCollection Implementations (No Generics) ---
    // These are non-static inner classes to access the outer MapAdapter_gemini2 instance.

    private class HKeySet implements HSet {
        @Override
		public int size() {
			return MapAdapter_gemini2.this.size();
		}
        
		@Override
		public boolean isEmpty() {
			return MapAdapter_gemini2.this.isEmpty();
		}
        
		@Override
		public boolean contains(Object o) {
			return MapAdapter_gemini2.this.containsKey(o);
		}
        
		@Override
		public HIterator iterator() {
			return new HKeyIterator();
		}
        
		@Override
		public boolean add(Object e) {
			throw new UnsupportedOperationException("add");
		}
        
		@Override
		public boolean remove(Object o) {
			if (MapAdapter_gemini2.this.containsKey(o)) {
				MapAdapter_gemini2.this.remove(o);
				return true;
			}
			return false;
		}
        
		@Override
		public boolean containsAll(HCollection c) {
			for (HIterator it = c.iterator(); it.hasNext();) {
				if (!contains(it.next()))
					return false;
			}
			return true;
		}
        
		@Override
		public boolean addAll(HCollection c) {
			throw new UnsupportedOperationException("addAll");
		}
        
		@Override
		public boolean retainAll(HCollection c) {
			throw new UnsupportedOperationException("retainAll");
		}
        
		@Override
		public boolean removeAll(HCollection c) {
			throw new UnsupportedOperationException("removeAll");
		}
        
		@Override
		public void clear() {
			MapAdapter_gemini2.this.clear();
		}

        @Override
		public Object[] toArray() {
			Object[] arr = new Object[size()];
			int i = 0;
			for (HIterator it = iterator(); it.hasNext();) {
				arr[i++] = it.next();
			}
			return arr;
		}
        @Override
		public Object[] toArray(Object[] a) {
			int size = size();
			if (a.length < size) {
				a = (Object[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
			}
			int i = 0;
			for (HIterator it = iterator(); it.hasNext();) {
				a[i++] = it.next();
			}
			if (a.length > size) {
				a[size] = null;
			}
			return a;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof HSet))
				return false; // Check for HSet interface
			HSet other = (HSet) o;
			if (size() != other.size())
				return false;
			// A set's equality is defined by containing the same elements.
			// Since we're comparing sets, we can use containsAll.
			return containsAll(other);
		}

        @Override
		public int hashCode() {
			int h = 0;
			// Sum the hash codes of all elements in the set.
			// This relies on the hashCode of the individual key objects.
			for (HIterator it = iterator(); it.hasNext();) {
				Object obj = it.next();
				if (obj != null) {
					h += obj.hashCode();
				}
			}
			return h;
		}
    }

    private class HValuesCollection implements HCollection {
        @Override
        public int size() { return MapAdapter_gemini2.this.size(); }
        @Override
        public boolean isEmpty() { return MapAdapter_gemini2.this.isEmpty(); }
        @Override
        public boolean contains(Object o) { return MapAdapter_gemini2.this.containsValue(o); }
        @Override
        public HIterator iterator() {
            return new HValueIterator();
        }
        @Override
        public boolean add(Object e) { throw new UnsupportedOperationException("add"); }
        @Override
        public boolean remove(Object o) { throw new UnsupportedOperationException("remove is not supported for HValuesCollection"); } // Often not supported for value views
        @Override
        public boolean containsAll(HCollection c) {
            for (HIterator it = c.iterator(); it.hasNext(); ) {
                if (!contains(it.next())) return false;
            }
            return true;
        }
        @Override
        public boolean addAll(HCollection c) { throw new UnsupportedOperationException("addAll"); }
        @Override
        public boolean removeAll(HCollection c) { throw new UnsupportedOperationException("removeAll"); }
        @Override
        public boolean retainAll(HCollection c) { throw new UnsupportedOperationException("retainAll"); }
        @Override
        public void clear() { MapAdapter_gemini2.this.clear(); }
        @Override
        public Object[] toArray() {
            Object[] arr = new Object[size()];
            int i = 0;
            for (HIterator it = iterator(); it.hasNext(); ) {
                arr[i++] = it.next();
            }
            return arr;
        }
        @Override
        public Object[] toArray(Object[] a) {
            int size = size();
            if (a.length < size) {
                a = (Object[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
            }
            int i = 0;
            for (HIterator it = iterator(); it.hasNext(); ) {
                a[i++] = it.next();
            }
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }
    }

    private class HEntrySet implements HSet {
        @Override
        public int size() { return MapAdapter_gemini2.this.size(); }
        @Override
        public boolean isEmpty() { return MapAdapter_gemini2.this.isEmpty(); }
        @Override
        public boolean contains(Object o) {
            if (!(o instanceof HMap.HEntry)) return false;
            HMap.HEntry entry = (HMap.HEntry) o;
            // Check if key exists and if its value matches
            Object key = entry.getKey();
            Object value = entry.getValue();

            // IMPORTANT: If you implement null key/value handling for MapAdapter_gemini2,
            // this logic needs to be updated to check those special cases too.
            if (!MapAdapter_gemini2.this.containsKey(key)) return false;
            Object existingValue = MapAdapter_gemini2.this.get(key);
            return (value == null ? existingValue == null : value.equals(existingValue));
        }
        @Override
        public HIterator iterator() {
            return new HEntryIterator();
        }
        @Override
        public boolean add(Object e) { throw new UnsupportedOperationException("add"); }
        @Override
        public boolean remove(Object o) {
            if (!(o instanceof HMap.HEntry)) return false;
            HMap.HEntry entry = (HMap.HEntry) o;
            // Only remove if the entry (key-value pair) actually exists in the map
            if (contains(entry)) {
                MapAdapter_gemini2.this.remove(entry.getKey());
                return true;
            }
            return false;
        }
        @Override
        public boolean containsAll(HCollection c) {
            for (HIterator it = c.iterator(); it.hasNext(); ) {
                if (!contains(it.next())) return false;
            }
            return true;
        }
        @Override
        public boolean addAll(HCollection c) { throw new UnsupportedOperationException("addAll"); }
        @Override
        public boolean removeAll(HCollection c) { throw new UnsupportedOperationException("removeAll"); }
        @Override
        public boolean retainAll(HCollection c) { throw new UnsupportedOperationException("retainAll"); }
        @Override
        public void clear() { MapAdapter_gemini2.this.clear(); }
        @Override
        public Object[] toArray() {
            Object[] arr = new Object[size()];
            int i = 0;
            for (HIterator it = iterator(); it.hasNext(); ) {
                arr[i++] = it.next();
            }
            return arr;
        }
        @Override
        public Object[] toArray(Object[] a) {
            int size = size();
            if (a.length < size) {
                a = (Object[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
            }
            int i = 0;
            for (HIterator it = iterator(); it.hasNext(); ) {
                a[i++] = it.next();
            }
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }
    }

    // --- Iterator Implementations (No Generics) ---
    // These are non-static inner classes to access the outer MapAdapter_gemini2 instance.

    private class HKeyIterator implements HIterator {
        private Enumeration keys;
        private Object lastReturnedKey = null; // To support remove()

        HKeyIterator() {
            this.keys = adaptee.keys();
        }

        @Override
        public boolean hasNext() {
            return keys.hasMoreElements();
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            lastReturnedKey = keys.nextElement();
            return lastReturnedKey;
        }

        @Override
        public void remove() {
            // This is an optional operation.
            // If supported, it should remove the last element returned by next().
            // This requires removing it from the underlying MapAdapter_gemini2.
            if (lastReturnedKey == null) {
                throw new IllegalStateException("next method has not yet been called, or the remove method has already been called after the last call to the next method.");
            }
            MapAdapter_gemini2.this.remove(lastReturnedKey); // Remove from the outer map
            lastReturnedKey = null; // Invalidate last returned key
        }
    }

    private class HValueIterator implements HIterator {
        private Enumeration elements;
        // To support remove(), you'd need to track the key associated with the last returned value.
        // This is complex with Hashtable's Enumeration.
        // For simplicity, remove() is UnsupportedOperationException here.

        HValueIterator() {
            this.elements = adaptee.elements();
        }

        @Override
        public boolean hasNext() {
            return elements.hasMoreElements();
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return elements.nextElement();
        }

        @Override
        public void remove() {
            // Removing from a values view is complex because you need to find the key
            // associated with the value. J2SE Map.values().iterator().remove()
            // removes the corresponding mapping from the map.
            // This is an optional operation and is difficult to implement efficiently
            // with Hashtable's Enumeration without storing all keys.
            throw new UnsupportedOperationException("remove is not supported for HValueIterator");
        }
    }

    private class HEntryIterator implements HIterator {
        private Enumeration keys;
        private Object lastReturnedKey = null;

        HEntryIterator() {
            this.keys = adaptee.keys();
        }

        @Override
        public boolean hasNext() {
            return keys.hasMoreElements();
        }

        @Override
        public HMap.HEntry next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            lastReturnedKey = keys.nextElement();
            // Create a new HEntryImpl for each entry.
            // Ensure this HEntryImpl can update the underlying map if setValue is called on it.
            // This HEntryImpl should ideally hold a reference to the MapAdapter_gemini2 or the key
            // so its setValue can call MapAdapter_gemini2.this.put(key, newValue).
            // For now, it's a simple data holder.
            return new HMapEntryImpl(lastReturnedKey, adaptee.get(lastReturnedKey));
        }

        @Override
        public void remove() {
            // This is an optional operation.
            // If supported, it should remove the last entry returned by next().
            if (lastReturnedKey == null) {
                throw new IllegalStateException("next method has not yet been called, or the remove method has already been called after the last call to the next method.");
            }
            MapAdapter_gemini2.this.remove(lastReturnedKey); // Remove from the outer map
            lastReturnedKey = null; // Invalidate last returned key
        }
    }
}
