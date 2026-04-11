package myAdapter;

//modificare
import myCompatibilityLayer.MockHashTable;

public class MapAdapter implements HMap {

    public class Entry implements HMap.HEntry {
        private Object key;
        private Object value;

        private Entry(){
            this.key = null;
            this.value = null;
        }

        private Entry(Object key, Object value){
            this.key = key;
            this.value = value;
        }

        //la specifica dice che il controllo è cosi, anche se nel nostro caso
        //key e value non possono mai essere null
        public boolean equals(Object o) {
            if (!(o instanceof HEntry)) {
                return false;
            }

            HEntry e = (HEntry)o;
            
            return  (this.getKey() == null ? e.getKey() == null : this.getKey().equals(e.getKey()))  
                    &&
                    (this.getValue() == null ? e.getValue() == null : this.getValue().equals(e.getValue()));
        }

        public Object getKey(){
            return this.key;
        }

        public Object getValue(){
            return this.value;
        }
        //non può mai essere value
        public int hashCode(){
            return  (this.getKey() == null ? 0 : this.getKey().hashCode()) 
                    ^ 
                    (this.getValue() == null ? 0 : this.getValue().hashCode());
        }

        //lancia nullpointerexceptino se value null
        //da testare bene e provare anche la versione base se funziona
        public Object setValue(Object value){
            return hashTable.put(this.key, value);
        }
    }

    //modificare
    private MockHashTable hashTable;

    /*----------  CONSTRUCTORS ----------*/

    public MapAdapter(){
        //modificare
        this.hashTable = new MockHashTable();
    }
    
    public MapAdapter(HMap t){
        //modificare
        this.hashTable = new MockHashTable();
        this.putAll(t);
    }

    /*-----------------------------------*/

    /*-------- INTERFACE METHODS --------*/ 
    public void clear() {
        hashTable.clear();
    }

    //lancio nullpointerexception per mantenere
    //uniformità con containsValue
    public boolean containsKey(Object key) {
        if(key == null)
            throw new NullPointerException();
        return hashTable.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return hashTable.contains(value);
    }

    public HSet entrySet() {
        return null;
    }

    public boolean equals(Object o) {
        if (!(o instanceof HMap))
            return false;

        HMap m = (HMap)o;
        
        return this.entrySet().equals(m.entrySet());
    }

    //lancia eccezione sempre per mantenere uniformità
    public Object get(Object key) {
        if(key == null)
            throw new NullPointerException();
        return hashTable.get(key);
    }

    public int hashCode() {
        return 0;
    }

    public boolean isEmpty() {
        return hashTable.isEmpty();
    }

    public HSet keySet() {
        return null;
    }

    //commentare bene quell'illegalargumentexception
    public Object put(Object key, Object value) {
        if(key == this)
            throw new IllegalArgumentException();

        return hashTable.put(key, value);
    }

    //chiamo entrySet(), faccio put delle entry iterandole
    public void putAll(HMap t) {
        if(t == null)
            throw new NullPointerException();

        HSet set = t.entrySet();
        HIterator iterator = set.iterator();
        while(iterator.hasNext()){
            HEntry entry = (HEntry) iterator.next();
            this.put(entry.getKey(), entry.getValue());
        }
    }

    //lancia eccezione per uniformità
    public Object remove(Object key) {
        if(key == null)
            throw new NullPointerException();
        return hashTable.remove(key);
    }

    public int size() {
        return hashTable.size();
    }

    public HCollection values() {
        return null;
    }
}