package myAdapter;

/**
 * Eccezione unchecked lanciata per indicare che l'operazione richiesta non è supportata
 * dall'implementazione corrente di un metodo o di una classe.
 * Questa è un'implementazione personalizzata per ambienti CLDC 1.1,
 * dato che l'equivalente {@code java.lang.UnsupportedOperationException} non è disponibile.
 * Estende {@code java.lang.RuntimeException} rendendola un'eccezione unchecked.
 */
public class UnsupportedOperationException extends RuntimeException {
	/**
	 * Costruisce una {@code UnsupportedOperationException} senza un messaggio di dettaglio.
	 */
	public UnsupportedOperationException() {
		super();
	}

	/**
	 * Costruisce una {@code UnsupportedOperationException} con il messaggio di dettaglio specificato.
	 *
	 * @param message il messaggio di dettaglio.
	 */
	public UnsupportedOperationException(String message) {
		super(message);
	}
}
