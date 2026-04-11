package myAdapter;

// Map Interface - Java SE 1.4.2
public interface HMap {

	// Nested Map interface - Java SE 1.4.2
	public static interface HEntry {
		// Compares the specified object with this entry for equality.
		public boolean equals(Object o);
		
		// Returns the key corresponding to this entry.
		public Object getKey();
		
		// Returns the value corresponding to this entry.
		public Object getValue();
		
		// Returns the hash code value for this map entry.
		public int hashCode();
		
		// Replaces the value corresponding to this entry with the specified value (optional operation).
		public Object setValue(Object value);
	}
	
    // Removes all mappings from this map (optional operation).
    public void clear();

	// Returns true if this map contains a mapping for the specified key.
    public boolean containsKey(Object key);

	// Returns true if this map maps one or more keys to the specified value.
    public boolean containsValue(Object value);

	// Returns a set view of the mappings contained in this map.
    public HSet entrySet();

	// Compares the specified object with this map for equality.
    public boolean equals(Object o);

	// Returns the value to which this map maps the specified key.
    public Object get(Object key);

	// Returns the hash code value for this map.
    public int hashCode();

	// Returns true if this map contains no key-value mappings.
    public boolean isEmpty();

	// Returns a set view of the keys contained in this map.
    public HSet keySet();

	// Associates the specified value with the specified key in this map (optional operation).
    public Object put(Object key, Object value);

	// Copies all of the mappings from the specified map to this map (optional operation).
    public void putAll(HMap m);
	
	// Removes the mapping for this key from this map if it is present (optional operation).
    public Object remove(Object key);

	// Returns the number of key-value mappings in this map.
    public int size();

	// Returns a collection view of the values contained in this map.
    public HCollection values();
}
