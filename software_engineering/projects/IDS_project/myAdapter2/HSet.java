package myAdapter;

public interface HSet extends HCollection {

    public boolean add(Object o);

    public boolean addAll(HCollection c);

    public void clear();

    public boolean contains(Object o);

    public boolean containsAll(HCollection c);

    public boolean equals(Object o);

    public int hashCode();

    public boolean isEmpty();

    public HIterator iterator();

    public boolean remove(Object o);

    public boolean removeAll(HCollection c);

    public boolean retainAll(HCollection c);

    public int size();

    public Object[] toArray();

    public Object[] toArray(Object[] a);
}