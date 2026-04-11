package myAdapter;

// Set Interface - Java SE 1.4.2
public interface HSet extends HCollection {
	/* ---------------------- METODI GIÀ PRESENTI IN HCOLLECTION ---------------------- *\

	// Adds the specified element to this set if it is not already present (optional operation).
	public boolean add(Object o);
	
	// Adds all of the elements in the specified collection to this set if they're not already present (optional operation).
	public boolean addAll(HCollection c);
	
	// Removes all of the elements from this set (optional operation).
	public void clear();
	
	// Returns true if this set contains the specified element.
	public boolean contains(Object o);
	
	// Returns true if this set contains all of the elements of the specified collection.
	public boolean containsAll(HCollection c);
	
	// Compares the specified object with this set for equality.
	public boolean equals(Object o);
	
	// Returns the hash code value for this set.
	public int hashCode();
	
	// Returns true if this set contains no elements.
	public boolean isEmpty();
	
	// Returns an iterator over the elements in this set.
	public HIterator iterator();
	
	// Removes the specified element from this set if it is present (optional operation).
	public boolean remove(Object o);
	
	// Removes from this set all of its elements that are contained in the specified collection (optional operation).
	public boolean removeAll(HCollection c);
	
	// Retains only the elements in this set that are contained in the specified collection (optional operation).
	public boolean retainAll(HCollection c);
	
	// Returns the number of elements in this set (its cardinality).
	public int size();
	
	// Returns an array containing all of the elements in this set.
	public Object[] toArray();
	
	// Returns an array containing all of the elements in this set; the runtime type of the returned array is that of the specified array.
	public Object[] toArray(Object[] a);
	
	\* -------------------------------------------------------------------------------- */
}
