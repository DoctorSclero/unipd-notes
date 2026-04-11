# Adapter da HashTable J2ME CLDC 1.1 a Map J2SE 1.4.2

## To-Do List
- bloccare l'inserimento della mappa stessa come chiave di una entry
- completare la documentazione di tutte le interfacce
- completare la documentazione di tutte le classi e classi interne
- sistemare la setValue della classe MapAdapter.EntryAdapter
- correggere che la classe Entry non può avere null come chiavi o valori
- nel setvalue cambiare in modo che la entry non si memorizza il value, ma solo la chiave / correggere il setvalue:
  - l'unico modo per ottenere una entry è attraverso l'iteratore
  - se faccio il remove dall'iteratore -> la rimuovo anche dalla mappa
  - se faccio il setvalue dalla entry -> modifico il valore anche sulla mappa
  - se cambio il valore della entry dalla mappa -> undefined behaviour
  - se rimuovo la entry dalla mappa -> undefined behaviour
- gestire la corrispodenza tra entry e hashtable nel caso in cui la entry non fa più parte della mappa
- aggiungere override, final, static

## Scelte progettuali
- hashtable CLDC 1.1 non accetta null key o value, le mappe di J2SE1.4.2 supportano null key e value (eccetto la TreeMap che supporta solo null value) per semplicità di implementazione ho scelto di non includere la possibilità di avere null key o value (maggiore coerenza con la sottostruttura e meno overhead/bugs).
- siccome non si utilizzano i generics, bensì si utilizza il polimorfismo dato dalla gerarchia delle classi, nella la maggior parte delle funzioni non è gestito il controllo sul tipo dei dati passati e di conseguenza non lanciano le ``ClassCastException``.
- sebbene la MapAdapter non supporti chiavi e valori null, è stato deciso di implementare la MapEntry in modo da supportarli per una maggiore portabilità, compatibilità e uniformità verso altre implementazioni della mappa che supportano chiavi o valori null.
- per ottenere gli effetti delle viste backed, le classi dedicate (EntrySet, KeySet, ValuesCollection) sono state implementate basandosi sulla stessa sottostruttura hashtable della mappa

## Tester
- verificare che se the map contains more than Integer.MAX_VALUE elements, size() returns Integer.MAX_VALUE
- verificare il corretto lancio delle eccezioni
- ...

## Confronto Diego
- ``return hashTable.put(this.key, value);``


