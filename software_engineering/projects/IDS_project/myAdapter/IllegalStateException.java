package myAdapter;

/**
 * Eccezione unchecked lanciata per indicare che l'operazione richiesta non è supportata
 * dall'implementazione corrente di un metodo o di una classe.
 * Questa è un'implementazione personalizzata per ambienti CLDC 1.1,
 * dato che l'equivalente {@code java.lang.IllegalStateException} non è disponibile.
 * Estende {@code java.lang.RuntimeException} rendendola un'eccezione unchecked.
 */
public class IllegalStateException extends RuntimeException {
	/**
	 * Costruisce una {@code IllegalStateException} senza un messaggio di dettaglio.
	 */
	public IllegalStateException() {
		super();
	}

	/**
	 * Costruisce una {@code IllegalStateException} con il messaggio di dettaglio specificato.
	 *
	 * @param message il messaggio di dettaglio.
	 */
	public IllegalStateException(String message) {
		super(message);
	}
}
