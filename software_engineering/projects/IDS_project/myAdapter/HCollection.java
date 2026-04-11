package myAdapter;

// Collection Interface - Java SE 1.4.2
public interface HCollection {

	// Ensures that this collection contains the specified element (optional operation).
    public boolean add(Object o);
	
	// Adds all of the elements in the specified collection to this collection (optional operation).
    public boolean addAll(HCollection c);
	
	// Removes all of the elements from this collection (optional operation).
    public void clear();
	
	// Returns true if this collection contains the specified element.
    public boolean contains(Object o);
	
	// Returns true if this collection contains all of the elements in the specified collection.
    public boolean containsAll(HCollection c);
	
	// Compares the specified object with this collection for equality.
    public boolean equals(Object o);
	
	// Returns the hash code value for this collection.
    public int hashCode();
	
	// Returns true if this collection contains no elements.
    public boolean isEmpty();
	
	// Returns an iterator over the elements in this collection.
    public HIterator iterator();
	
	// Removes a single instance of the specified element from this collection, if it is present (optional operation).
    public boolean remove(Object o);
	
	// Removes all this collection's elements that are also contained in the specified collection (optional operation).
    public boolean removeAll(HCollection c);
	
	// Retains only the elements in this collection that are contained in the specified collection (optional operation).
    public boolean retainAll(HCollection c);
	
	// Returns the number of elements in this collection.
    public int size();
	
	// Returns an array containing all of the elements in this collection.
    public Object[] toArray();
	
	// Returns an array containing all of the elements in this collection; the runtime type of the returned array is that of the specified array.
    public Object[] toArray(Object[] a);
}
