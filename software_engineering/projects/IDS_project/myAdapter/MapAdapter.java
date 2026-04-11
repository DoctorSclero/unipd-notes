package myAdapter;

import java.util.Enumeration;

// classe hashtable con metodi compliant con J2ME CLDC 1.1
import myCompatibilityLayer.Hashtable;

/**
 * Classe Adapter che implementa una mappa che soddisfa i requisiti di J2SE 1.4.2, utilizzando una hashtable di J2ME CLDC 1.1.
 */
public class MapAdapter implements HMap {
	// ******************************************************************************************************************************************************************
	// ------------------------------------------------------------------ VARIABILI DELLA CLASSE HMap -------------------------------------------------------------------

	// sottostruttura hashtable di CLDC 1.1
	private Hashtable hashtable;


	// ******************************************************************************************************************************************************************
	// -------------------------------------------------------------------------- COSTRUTTORI ---------------------------------------------------------------------------
	/**
	 * Costruttore vuoto, crea una nuova mappa vuota.
	 */
	public MapAdapter() {
		// creo ed inizializzo la sottostruttura hashtable di CLDC 1.1
		hashtable = new Hashtable();
	}

	/**
	 * Costruttore di copia, crea una nuova mappa ed effettua una deep copy delle mappature {@code key-value} della mappa passata come parametro.
	 * 
	 * @param m mappa da cui copiare le mappature {@code key-value}.
	 * @throws NullPointerException se la mappa passata come parametro è {@code null}, o se una delle entry nella mappa {@code m} ha chiave o valore {@code null}
	 */
	public MapAdapter(HMap m) {
		// lacio l'eccezione se la mappa passata è null, altrimenti avrei un'invocazione di metodi su oggetti null
		if (m == null)
			throw new NullPointerException();
		
		// creo ed inizializzo la sottostruttura hashtable di CLDC 1.1
		hashtable = new Hashtable();

		// copio tutti i valori dalla mappa passata come parametro alla mappa appena creata, se ci sono alcune entry nella mappa m con chiave o valore null,
		// viene lanciata una NullPointerException dal metodo put() contenuto in putAll().
		putAll(m);
	}


	// ******************************************************************************************************************************************************************
	// ------------------------------------------------------------------ METODI DELL'INTERFACCIA HMap ------------------------------------------------------------------
	/**
	 * Metodo che restituisce il numero di mappature {@code key-value} contenute nella mappa.
	 * Se il numero da restituire è maggiore di {@code Integer.MAX_VALUE}, il metodo restituisce {@code Integer.MAX_VALUE}.
	 * 
	 * @return numero di mappature {@code key-value} contentue nella mappa.
	 */
	public int size() {
		// richiamo il corrispettivo metodo della hashtable e ne restituisco il risultato
		return hashtable.size();
	}

	/**
	 * Metodo che restituisce {@code true} se la mappa non contiene nessuna mappatura {@code key-value}, {@code false} altrimenti.
	 * 
	 * @return {@code true} se la mappa non contiene nessuna mappatura {@code key-value}, {@code false} altrimenti.
	 */
	public boolean isEmpty() {
		// richiamo il corrispettivo metodo della hashtable e ne restituisco il risultato
		return hashtable.isEmpty();
	}

	/**
	 * Metodo che restituisce {@code true} se la mappa contiene una mappatura {@code key-value} che coinvolge la chiave passata come parametro.
	 * Si sceglie di non supportare chiavi con valore {@code null}, per cui se la chiave passata è {@code null}, il metodo lancia l'eccezione {@code NullPointerException}.
	 * 
	 * @param key chiave da cercare tra le mappature {@code key-value} contenute all'interno della mappa.
	 * @return {@code true} se la mappa contiene una mappatura {@code key-value} che coinvolge la chiave cercata, {@code false} altrimenti.
	 * @throws NullPointerException se la chiave passata è {@code null}.
	 */
	public boolean containsKey(Object key) {
		// NullPointerException non viene lanciato dal metodo containsKey() della hashtable per cui lo lancio manualmente
		if (key == null)
			throw new NullPointerException();
		
		// richiamo il corrispettivo metodo della hashtable e ne restituisco il risultato
		return hashtable.containsKey(key);
	}
	
	/**
	 * Metodo che restituisce {@code true} se la mappa contiene una o più chiavi che vengono mappate al valore passato come parametro. La ricerca impiega tempo lineare
	 * nella dimensione della mappa.
	 * Si sceglie di non supportare valori {@code null}, per cui se il valore passato è {@code null}, il metodo lancia l'eccezione {@code NullPointerException}.
	 * 
	 * @param value valore di cui si vuole verificare.
	 * @throws NullPointerException se il valore passato è {@code null}.
	 */
	public boolean containsValue(Object value) {
		// NullPointerException già lanciato dal metodo contains() della hashtable
		// richiamo il corrispettivo metodo della hashtable e ne restituisco il risultato
		return hashtable.contains(value);
	}

	/**
	 * Metodo che restituisce il valore associato alla chiave passata come parametro. Se la chiave non è presente all'interno della mappa, il metodo restituisce {@code null}.
	 * Si osserva che non è possible, per scelta implementativa, avere chiavi associate a valori {@code null} (valori {@code null} non supportati), per cui se il metodo
	 * restitusce {@code null}, significa che sicuramente la chiave passata non è presente all'interno della mappa.
	 * 
	 * @param key chiave di cui cercare il corrispettivo valore associato all'interno della mappa.
	 * @return valore associato alla chiave {@code key}, {@code null} altrimenti.
	 * @throws NullPointerException se la chiave passata è {@code null}.
	 */
	public Object get(Object key) {
		// NullPointerException non viene lanciato dal metodo get() della hashtable per cui lo lancio manualmente
		if (key == null)
			throw new NullPointerException();

		// richiamo il corrispettivo metodo della hashtable e ne restituisco il risultato
		return hashtable.get(key);
	}

	/**
	 * Metodo che inserisce una nuova mappatura {@code key-value} all'interno della mappa. Se la mappa contiene già la chiave specificata, il valore mappato viene sovrascritto
	 * e quello vecchio viene restituito dal metodo, altrimenti se la chiave non è presente il metodo restituisce {@code null}. Per scelta implementativa la mappa non supporta
	 * chiavi o valori {@code null}, per cui se la chiave o il valore sono {@code null}, il metodo lancia {@code NullPointerException}.
	 * 
	 * @param key chiave da associare al nuovo valore da inserire nella mappa.
	 * @param value nuovo valore da inserire nella lista, associato alla chiave specificata.
	 * @return valore precedentemente associato alla chiave specificata (se la chiave è già presente), altrimenti {@code null}.
	 * @throws NullPointerException se la chiave o il valore passati sono {@code null}.
	 * @throws IllegalArgumentException se la chiave corrisponde alla mappa.
	 */
	public Object put(Object key, Object value) {
		// per come è stata definita l'interfaccia, non si può avere la mappa stessa come chiave di qualche entry
		// se la chiave passata corrisponde alla mappa, lancio una IllegalArgumentException
		if(key == this)
			throw new IllegalArgumentException();
			
		// NullPointerException già lanciato dal metodo put() della hashtable
		// richiamo il corrispettivo metodo della hashtable e ne restituisco il risultato
		return hashtable.put(key, value);
	}

	/**
	 * Metodo che rimuove dalla mappa la mappatura {@code key-value} che coinvolge la chiave specificata e ne restituisce il valore associato alla chiave. Se la chiave non è
	 * presente nella mappa, non viene eseguita nessuna operazione e viene restituito {@code null}.
	 * Si osserva che non è possibile per scelta implementativa, avere chiavi associate a valori {@code null} (valori {@code null} non supportati), per cui se il metodo
	 * restitusce {@code null}, significa che sicuramente la chiave passara non è presente all'interno della mappa.
	 * 
	 * @param key chiave associata alla mappatura {@code key-value} da rimuovere.
	 * @return valore della mappatura rimossa, {@code null} se la chiave non è presente nella mappa.
	 * @throws NullPointerException se la chiave passata è {@code null}.
	 */
	public Object remove(Object key) {
		// NullPointerException non viene lanciato dalla remove della hashtable per cui lo lancio manualmente
		if (key == null)
			throw new NullPointerException();
		
		// richiamo il corrispettivo metodo della hashtable e ne restituisco il risultato
		return hashtable.remove(key);
	}

	/**
	 * Metodo che copia tutte le mappature {@code key-value} contenute nella mappa {@code m} passata come parametro all'interno della mappa su cui il metodo è invocato.
	 * Corrisponde alla chiamata del metodo {@code put(key,value)} per ogni mappatura {@code key-value} presente nella mappa {@code m}.
	 * 
	 * @param m mappa da cui importare le mappature {@code key-value}.
	 * @throws NullPointerException se la mappa passata è {@code null}, oppure se una delle entry da inserire possiede chiave o valore {@code null}.
	 */
	public void putAll(HMap m) {
		// lacio l'eccezione se la mappa passata è null, altrimenti avrei un'invocazione di metodi su oggetti null
		if (m == null)
			throw new NullPointerException();

		// creo l'iteratore per la mappa da cui copiare i valori
		HIterator i = m.entrySet().iterator();
		
		// scorro l'iteratore attraverso tutti gli elementi della mappa passata come parametro
		while (i.hasNext()) {
			// leggo la entry dall'iteratore ed effettuo un casting lecito (Object -> HEntry) perché l'iteratore itera su un HSet costituito da oggetti HEntry
			HEntry newEntry = (HEntry) i.next();

			// inserisco la nuova entry nella mappa
			put(newEntry.getKey(), newEntry.getValue());
		}
	}
	
	/**
	 * Rimuove tutte le entry contenute nella mappa.
	 */
	public void clear() {
		// richiamo il corrispettivo metodo della hashtable
		hashtable.clear();
	}

	/**
	 * Restituisce un set contenente tutte le chiavi delle mappature {@code key-value} presenti all'interno della mappa. Il set restituito e la mappa agiscono sulla stessa
	 * sottostruttura quindi le modifiche che avvengono sulla mappa si riflettono sul set e viceversa. Se la mappa viene modificata mentre viene visitata da un iteratore,
	 * il comportamento dell'iteratore non è definito.
	 * Il set restituito supporta la rimozione delle chiavi tramite {@code Iterator.remove()}, {@code HSet.remove()}, {@code HSet.removeAll()}, {@code HSet.retainAll()},
	 * ma non l'aggiunta di nuove chiavi, per cui i metodi {@code HSet.add()} e {@code Hset.addAll()} sono disabilitati.
	 * 
	 * @return oggetto {@code Hset} con le chiavi delle mappature {@code key-value} contenute nella mappa
	 */
	public HSet keySet() {
		return new KeySetAdapter();
	}

	/**
	 * Restituisce una collection contenente tutti i valori (eventualmente duplicati) delle mappature {@code key-value} presenti all'interno della mappa. La collection restituita
	 * e la mappa agiscono sulla stessa sottostruttura quindi le modifiche che avvengono sulla mappa si riflettono sulla collection e viceversa. Se la mappa viene modificata mentre
	 * viene visitata da un iteratore, il comportamento dell'iteratore non è definito.
	 * La collection restituita supporta la rimozione dei valori tramite {@code Iterator.remove()}, {@code HCollection.remove()}, {@code HCollection.removeAll()},
	 * {@code HCollection.retainAll()}, ma non l'aggiunta di nuove chiavi, per cui i metodi {@code HCollection.add()} e {@code HCollection.addAll()} sono disabilitati.
	 * 
	 * @return oggetto {@code HCollection} con i valori (eventualmente duplicati) delle mappature {@code key-value} contenute nella mappa
	 */
	public HCollection values() {
		return new ValuesCollectionAdapter();
	}

	/**
	 * Restituisce un set contenente tutte le mappature {@code key-value} presenti all'interno della mappa. Il set restituito e la mappa agiscono sulla stessa sottostruttura quindi
	 * le modifiche che avvengono sulla mappa si riflettono sul set e viceversa. Se la mappa viene modificata mentre viene visitata da un iteratore, il comportamento dell'iteratore
	 * non è definito.
	 * Il set restituito supporta la rimozione delle entry tramite {@code Iterator.remove()}, {@code HSet.remove()}, {@code HSet.removeAll()}, {@code HSet.retainAll()}, ma non
	 * l'aggiunta di nuove entry, per cui i metodi {@code HSet.add()} e {@code Hset.addAll()} sono disabilitati.
	 * 
	 * @return oggetto {@code Hset} con le mappature {@code key-value} contenute nella mappa.
	 */
	public HSet entrySet() {
		return new EntrySetAdapter();
	}

	/**
	 * Metodo che confronta la mappa da cui è invocato con l'oggetto {@code o} passato come parametro. Se l'oggetto {@code o} è una mappa (implementa {@code HMap}) e contiene
	 * le stesse mappature {@code key-value} della mappa da cui è invocato il metodo, allora il metodo restituisce {@code true}, altrimenti {@code false}.
	 * Per fare ciò si sfrutta il metodo {@code HSet.equals} invocato sul set restiutito dalla vista {@code entrySet()} ({@code this.entrySet().equals(o.entrySet())}). In questo modo
	 * l'implementazione del metodo è indipendente dal tipo di implementazione delle due mappe da confrontare.
	 * 
	 * @param o oggetto da confrontare con la mappa da cui è invocato il metodo.
	 * @return {@code true} se {@code o} è una mappa e contiene le stesse mappature {@code key-value} della mappa da cui è invocato il metodo, {@code false} altrimenti.
	 */
	public boolean equals(Object o) {
		// verifico che l'oggetto passato non sia null
		if (o == null)
			return false;
		
		// verifico che l'oggetto passato sia una mappa
		if (!(o instanceof HMap))
			return false;
		
		// confronto le mappature tra le due mappe sfruttando la vista entrySet
		// si effettua un casting lecito (Object -> HMap) perché ho effettuato sopra il controllo
		return this.entrySet().equals(((HMap)o).entrySet());
	}

	/**
	 * Metodo che resituisce il codice hash della mappa. Il codice hash è definito come la somma dei codici hash di tutte le entry (mappature {@code key-value}) nella mappa ottenute
	 * attraverso il set restituito da {@code entrySet()}.
	 * 
	 * @return il codice hash della mappa.
	 */
	public int hashCode() {
		// inizializzo l'accumulatore dei codici hash
		int hash = 0;

		// creo un iteratore per visitare tutte le entry
		HIterator i = entrySet().iterator();

		// per ogni entry aggiungo il codice hash all'accumulatore
		while (i.hasNext())
			hash += i.next().hashCode();
		
		// resitutisco il codice hash
		return hash;
	}

	// ******************************************************************************************************************************************************************
	// ------------------------------------------------------------------ CLASSE ANNIDATA EntryAdapter ------------------------------------------------------------------

	/**
	 * Classe annidata per la gestione delle mappature {@code key-value} (chiamate anche entry) all'interno della mappa.
	 */
	public class EntryAdapter implements HMap.HEntry {
		// variabili che memorizzano rispettivamente la chiave e il valore di una mappatura key-value
		private Object key, value;

		/**
		 * Costruttore di default, accessibile solo alle classi all'interno del package myAdapter. Costruisce un oggetto {@code EntryAdapter} inizializzando chiave
		 * e valore a {@code null}. Siccome la mappa non supporta chiavi o valori {@code null}, è stato scelto di supportare entry con chiave e valore {@code null}
		 * per mantenere la portabilità, la compatibilità e l'uniformità con altre implementazioni della mappa che supportano chiavi o valori {@code null}.
		 */
		protected EntryAdapter() {
			key = value = null;
		}

		/**
		 * Costruttore con due parametri, accessibile solo alle classi all'interno del package myAdapter. Costruisce un oggetto {@code EntryAdapter} inizializzando
		 * chiave e valore secondo i parametri passati al metodo.
		 * 
		 * @param key chiave.
		 * @param value valore.
		 * @throws NullPointerException 
		 */
		protected EntryAdapter(Object key, Object value) {

			this.key = key;
			this.value = value;
		}

		/**
		 * Metodo che restituisce la chiave contenuta nella entry.
		 * 
		 * @return la chiave contenuta nella entry.
		 */
		public Object getKey() {
			return key;
		}

		/**
		 * Metodo che restituisce il valore contenuto nella entry.
		 * 
		 * @return il valore contenuto nella entry.
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Metodo che sostituisce il valore contenuto nella entry con il nuovo valore passato come parametro. Il valore precedentemente memorizzato viene restituito.
		 * 
		 * @param value nuovo valore da memorizzare nella entry al posto di quello vecchio.
		 * @return valore precedentemente memorizzato nella entry.
		 * @throws NullPointerException ...
		 */
		public Object setValue(Object value) {
			// se la entry è ancora all'interno della mappa (anche se è nell'array)
			if (hashtable.get(key).equals(value)) {
				return hashtable.put(key, value);
			}

			// se la entry è stata rimossa dalla mappa e si trova nell'array
			else {
				Object oldValue = this.value;
				this.value = value;
				return oldValue;
			}
		}

		/**
		 * Metodo che confronta la entry da cui è invocato con l'oggetto {@code o} passato come parametro. Se l'oggetto {@code o} è una entry (implementa {@code HMap.Hentry})
		 * ed entrambe contengono la stessa mappatura {@code key-value}, allora il metodo restituisce {@code true}, altrimenti {@code false}. Più specificatamente:
		 * 
		 * <pre>
		 *     (e1.getKey() == null ? e2.getKey() == null : e1.getKey().equals(e2.getKey()))  &&
		 *  (e1.getValue() == null ? e2.getValue() == null : e1.getValue().equals(e2.getValue()))
		 * </pre>
		 * 
		 * In questo modo il metodo funziona correttamente con diverse implementazioni della mappa (che supportano o meno chiavi o valori {@code null}) e inoltre si
		 * delega il controllo sull'uguaglianza degli oggetti chiave e valore a chi ha programmato tali oggetti per una migliore trasparenza.
		 * 
		 * @param o oggetto da confrontare con la entry da cui è invocato il metodo.
		 * @return {@code true} se {@code o} è una entry e contiene la stessa mappatura {@code key-value} della entry da cui è invocato il metodo, {@code false} altrimenti.
		 */
		public boolean equals(Object o) {
			// verifico che l'oggetto passato non sia null
			if (o == null)
				return false;

			// verifico che l'oggetto passato sia una entry
			if (!(o instanceof HEntry))
				return false;

			// confronto la mappatura tra le due entry secondo la formula indicata nel commento javadoc del metodo
			// si effettua un casting lecito (Object -> HEntry) perché il controllo è stato effettuato appena sopra
			return this.getKey().equals(((HEntry) o).getKey()) && this.getValue().equals(((HEntry) o).getValue());
		}

		/**
		 * Metodo che resituisce il codice hash della entry. Il codice hash è definito come la xor dei codici hash della chiave e del valore contenuti nella entry.
		 * Più speficicatamente:
		 * 
		 * <pre>
		 *    (e.getKey() == null ? 0 : e.getKey().hashCode()) ^
		 * (e.getValue() == null ? 0 : e.getValue().hashCode())
		 * </pre>
		 * 
		 * In questo modo il metodo funziona correttamente con diverse implementazioni della mappa (che supportano o meno chiavi o valori {@code null}) e inoltre si
		 * delega il calcolo dei codici hash degli oggetti chiave e valore a chi ha programmato tali oggetti per una migliore trasparenza.
		 * 
		 * @return il codice hash della entry.
		 */
		public int hashCode() {
			return getKey().hashCode() ^ getValue().hashCode();
		}
	}
	
	// ******************************************************************************************************************************************************************
	// ---------------------------------------------------------- CLASSE ANNIDATA ASTRATTA AbstractViewAdapter ----------------------------------------------------------
	
	/**
	 * Classe astratta annidata per la vista attraverso collection per i metodi keySet, values, entrySet
	 */

	public abstract class AbstractViewAdapter implements HCollection {
		// Adds the specified element to this set if it is not already present (optional operation).
		public boolean add(Object o) {
			throw new UnsupportedOperationException();
		}

		// Adds all of the elements in the specified collection to this set if they're not already present (optional operation).
		public boolean addAll(HCollection c) {
			throw new UnsupportedOperationException();
		}

		// Removes all of the elements from this set (optional operation).
		public void clear() {
			hashtable.clear();
		}

		// Returns true if this set contains all of the elements of the specified collection.
		public boolean containsAll(HCollection c) {
			if (c == null)
				throw new NullPointerException();

			HIterator i = c.iterator();
			while (i.hasNext())
				if (!contains(i.hasNext()))
					return false;
			return true;
		}

		// Compares the specified object with this set for equality.
		public boolean equals(Object o) {
			if (o == null)
				return false;

			if (!(o instanceof HCollection))
				return false;

			if (o == this)
				return true;

			return this.size() == ((HCollection) o).size() && this.containsAll(((HCollection) o));
		}

		// Returns the hash code value for this set.
		public int hashCode() {
			int hash = 0;
			HIterator i = iterator();
			while (i.hasNext())
				hash += i.next().hashCode();
			return hash;
		}

		// Returns true if this set contains no elements.
		public boolean isEmpty() {
			return hashtable.isEmpty();
		}

		// Removes from this set all of its elements that are contained in the specified collection (optional operation).
		public boolean removeAll(HCollection c) {
			if (c == null)
				throw new NullPointerException();

			boolean change = false;
			HIterator i = c.iterator();
			while (i.hasNext())
				if (remove(i.hasNext()))
					change = true;
			return change;
		}

		// Retains only the elements in this set that are contained in the specified collection (optional operation).
		public boolean retainAll(HCollection c) {
			if (c == null)
				throw new NullPointerException();

			boolean change = false;
			HIterator i = iterator();
			while (i.hasNext()) {
				if (!c.contains(i.next())) {
					i.remove();
					change = true;
				}
			}
			return change;
		}

		// Returns the number of elements in this set (its cardinality).
		public int size() {
			return hashtable.size();
		}

		// Returns an array containing all of the elements in this set.
		public Object[] toArray() {
			Object[] array = new Object[size()];
			HIterator i = iterator();
			int index = 0;
			while (i.hasNext())
				array[index++] = i.next();
			return array;
		}

		// Returns an array containing all of the elements in this set; the runtime type of the returned array is that of the specified array.
		public Object[] toArray(Object[] a) {
			if (a == null)
				throw new NullPointerException();

			// CLDC 1.1 Limitation: Cannot dynamically create an array of the exact
			// runtime component type of 'a' if 'a' is too small and not Object[].
			// We must fall back to creating a new Object[] in this scenario.
			// The caller is responsible for casting elements if a specific type is needed.
			if (a.length < size())
				// Cannot use reflection (e.g., Array.newInstance) in CLDC 1.1
				// So, we create a new Object array.
				a = new Object[size()];

			int i = 0;
			HIterator it = iterator();
			while (it.hasNext())
				a[i++] = it.next();

			if (a.length > size())
				a[size()] = null;

			return a;
		}
	}
	
	// ******************************************************************************************************************************************************************
	// ------------------------------------------------------------------ CLASSE ANNIDATA KeySetAdapter -----------------------------------------------------------------

	/**
	 * Classe annidata per la vista attraverso set per il metodo keySet
	 */
	public class KeySetAdapter extends AbstractViewAdapter implements HSet {
		// Returns true if this set contains the specified element.
		public boolean contains(Object o) {
			if (o == null)
				throw new NullPointerException();
			return hashtable.containsKey(o);
		}

		// Returns an iterator over the elements in this set.
		public HIterator iterator() {
			return new KeySetIterator();
		}

		// Removes the specified element from this set if it is present (optional operation).
		public boolean remove(Object o) {
			if (o == null)
				throw new NullPointerException();
			return hashtable.remove(o) != null;
		}
	}
	
	// ******************************************************************************************************************************************************************
	// ------------------------------------------------------------- CLASSE ANNIDATA ValuesCollectionAdapter ------------------------------------------------------------

	/**
	 * Classe annidata per la vista attraverso collection per il metodo values
	 */
	public class ValuesCollectionAdapter extends AbstractViewAdapter /* implements HCollection */ {
		// Returns true if this set contains the specified element.
		public boolean contains(Object o) {
			if (o == null)
				throw new NullPointerException();
			return hashtable.contains(o);
		}

		// Returns an iterator over the elements in this set.
		public HIterator iterator() {
			return new ValuesCollectionIterator();
		}

		// Removes the specified element from this set if it is present (optional operation).
		public boolean remove(Object o) {
			if (o == null)
				throw new NullPointerException();

			HIterator it = iterator();
			while (it.hasNext()) {
				if (it.next().equals(o)) {
					it.remove();
					return true;
				}
			}

			return false;
		}
	}

	// ******************************************************************************************************************************************************************
	// ----------------------------------------------------------------- CLASSE ANNIDATA EntrySetAdapter ----------------------------------------------------------------

	/**
	 * Classe annidata per la vista attraverso set per il metodo entrySet
	 */
	public class EntrySetAdapter extends AbstractViewAdapter implements HSet {
		// Returns true if this set contains the specified element.
		public boolean contains(Object o) {
			if (o == null)
				throw new NullPointerException();

			if (!(o instanceof HEntry))
				throw new ClassCastException();

			return hashtable.containsKey(((HEntry) o).getKey())
					&& hashtable.get(((HEntry) o).getKey()).equals(((HEntry) o).getValue());
		}

		// Returns an iterator over the elements in this set.
		public HIterator iterator() {
			return new EntrySetIterator();
		}

		// Removes the specified element from this set if it is present (optional operation).
		public boolean remove(Object o) {
			if (contains(o))
				return hashtable.remove(((HEntry) o).getKey()) != null; // se la entry è contenuta, la condizione sarà sempre vera
			else
				return false;
		}
	}

	// ******************************************************************************************************************************************************************
	// ---------------------------------------------------------------- CLASSE ANNIDATA AbstractIterator ----------------------------------------------------------------

	public abstract class AbstractIterator implements HIterator {
		protected Enumeration elements;
		protected Object lastExtracted;

		protected AbstractIterator() {
			elements = hashtable.keys();
			lastExtracted = null;
		}

		public boolean hasNext() {
			return elements.hasMoreElements();
		}

		public void remove() {
			if (lastExtracted == null)
				throw new IllegalStateException();
			hashtable.remove(lastExtracted);
			lastExtracted = null;
		}
	}

	// ******************************************************************************************************************************************************************
	// ----------------------------------------------------------------- CLASSE ANNIDATA KeySetIterator -----------------------------------------------------------------


	public class KeySetIterator extends AbstractIterator {
		public Object next() {
			// lancia già NoSuchElementException
			lastExtracted = elements.nextElement();
			return lastExtracted;
		}
	}

	// ******************************************************************************************************************************************************************
	// ------------------------------------------------------------ CLASSE ANNIDATA ValuesCollectionIterator ------------------------------------------------------------
	
	public class ValuesCollectionIterator extends AbstractIterator {
		public Object next() {
			// lancia già NoSuchElementException
			lastExtracted = elements.nextElement();
			return hashtable.get(lastExtracted);
		}
	}
	
	// ******************************************************************************************************************************************************************
	// ---------------------------------------------------------------- CLASSE ANNIDATA EntrySetIterator ----------------------------------------------------------------

	public class EntrySetIterator extends AbstractIterator {
			public Object next() {
			// lancia già NoSuchElementException
			lastExtracted = elements.nextElement();
			return new EntryAdapter(lastExtracted, hashtable.get(lastExtracted));
		}
	}
}
