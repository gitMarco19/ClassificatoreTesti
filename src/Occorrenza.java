/**
 * La classe {@code Occorrenza} rappresenta una stringa e 
 * il relativo numero di occorrenze.
 * 
 * @author Marco Scanu
 */
public class Occorrenza {

    /**
     * Questo valore di tipo {@code String} è utilizzato per memorizzare
     * una stringa che rappresenta la parola.
     */
    private String parola;

    /**
     * Questo valore di tipo {@code int} serve per memorizzare 
     * le occorrenze di una stringa.
     */
    private int numOccorrenza;

    /**
     * Inizializza una nuova {@code Occorrenza} rappresentata 
     * da una stringa vuota e da un numero uguale a zero.
     */
    public Occorrenza() {
	this.setParola("");
	this.setNumOccorrenza(0);
    }

    /**
     * Inizializza una nuova {@code Occorrenza} rappresentata da una stringa
     * che contiene gli stessi caratteri di {@code parola} e da un numero
     * uguale a {@code numOccorrenza} .
     * 
     * @param parola
     *            {@code String} che identifica l'occorrenza.
     * @param numOccorrenza
     *            numero {@code int} delle occorrenze della stringa.
     */
    public Occorrenza(String parola, int numOccorrenza) {
	this.setParola(parola);
	this.setNumOccorrenza(numOccorrenza);
    }

    /**
     * Imposta il valore della stringa uguale a quello dell'argomento.
     * 
     * @param parola
     *            {@code String} che identifica l'occorrenza.
     */
    public void setParola(String parola) {
	this.parola = parola;
    }

    /**
     * Restituisce il valore {@code String} che rappresenta la parola.
     * 
     * @return contenuto della stringa.
     */
    public String getParola() {
	return parola;
    }

    /**
     * Imposta il valore del numero {@code int} di occorrenze della stringa
     * uguale a quello dell'argomento.
     * 
     * @param numOccorrenze
     *            numero delle occorrenze.
     */
    public void setNumOccorrenza(int numOccorrenze) {
	this.numOccorrenza = numOccorrenze;
    }

    /**
     * Restituisce un numero {@code int} che indica le occorrenze della stringa.
     * 
     * @return numero delle occorrenze.
     */
    public int getNumOccorrenza() {
	return numOccorrenza;
    }
}
