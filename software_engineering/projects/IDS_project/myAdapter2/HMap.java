package myAdapter;

public interface HMap {

    public static interface HEntry{
        public boolean equals(Object o);

        public Object getKey();

        public Object getValue();

        public int hashCode();

        public Object setValue(Object value);
    }
    
    public void clear();

    public boolean containsKey(Object key);

    public boolean containsValue(Object value);

    public HSet entrySet();

    public boolean equals(Object o);

    public Object get(Object key);

    public int hashCode();

    public boolean isEmpty();

    public HSet keySet();

    public Object put(Object key, Object value);

    public void putAll(HMap t);

    public Object remove(Object key);

    public int size();

    public HCollection values();

}
