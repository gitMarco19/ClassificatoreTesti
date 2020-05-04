import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * La classe {@code Documento} serve a rappresentare un qualsiasi file
 * appartenente alla {@code CategoriaDocumento}.<br>
 * Inoltre, contiene dei metodi per la manipolazione di questi file.
 * 
 * @author Marco Scanu
 */
public class Documento {

    /**
     * Questo valore di tipo {@code File} serve per riferirsi ad un qualsiasi file
     * appartenente alla {@code CategoriaDocumento}.
     */
    private File aFile;

    /**
     * Questo valore di tipo {@code File} serve per riferirsi al file vocabolario
     * contenente tutte le parole lecite.
     */
    private File vocabolario;

    /**
     * Questo valore di tipo {@code CategoriaDocumento} serve per memorizzare la
     * categoria a cui appartiene il file.
     */
    private CategoriaDocumento categoria;

    /**
     * Questo valore di tipo {@code ArrayList<Occorrenza>} serve per memorizzare
     * tutte le occorrenze delle parole contenute nel file.
     */
    private ArrayList<Occorrenza> occorrenze;

    /**
     * Inizializza un nuovo {@code Documento} rappresentato 
     * da tutti i suoi campi a {@code null}.
     */
    public Documento() {
	this.setaFile(null);
	this.setVocabolario(null);
	this.setCategoria(null);
	this.setOccorrenze(null);
    }

    /**
     * Inizializza un nuovo {@code Documento} rappresentato da un file e una
     * categoria uguali ai valori passati come argomenti e un vocabolario e le
     * occorrenze {@code null}.
     *
     * @param pathFile
     *            indica il percorso del file.
     * @param cat
     *            indica la categoria a cui appartiene il file.
     */
    public Documento(String pathFile, CategoriaDocumento cat) {
	this.setaFile(new File(pathFile));
	this.setVocabolario(null);
	this.setCategoria(cat);
	this.setOccorrenze(null);
    }

    /**
     * Inizializza il file che rappresenta il documento 
     * con quello passato come argomento.
     * 
     * @param aFile
     *            {@code File} a cui si deve riferire il documento.
     */
    public void setaFile(File aFile) {
	this.aFile = aFile;
    }

    /**
     * Restituisce il file che rappresenta il documento.
     * 
     * @return {@code File} a cui si riferisce il documento.
     */
    public File getaFile() {
	return aFile;
    }

    /**
     * Inizializza il vocabolario, di tipo {@code File}, del documento con quello
     * passato come argomento.
     * 
     * @param vocabolario
     *            contenente le parole lecite.
     */
    public void setVocabolario(File vocabolario) {
	this.vocabolario = vocabolario;
    }

    /**
     * Restituisce il vocabolario del documento.
     * 
     * @return vocabolario di tipo {@code File}
     */
    public File getVocabolario() {
	return vocabolario;
    }

    /**
     * Inizializza la categoria a cui appartiene il documento con il valore passato
     * come argomento.
     * 
     * @param categoria
     *            specifica la categoria del documento.
     */
    public void setCategoria(CategoriaDocumento categoria) {
	this.categoria = categoria;
    }

    /**
     * Restituisce il tipo di categoria a cui appartiene il documento.
     * 
     * @return categoria del documento.
     */
    public CategoriaDocumento getCategoria() {
	return categoria;
    }

    /**
     * Inizializza l'arraylist delle occorrenze delle parole del file, di tipo
     * {@code ArrayList<Occorrenza>}, con quello specificato come argomento.
     * 
     * @param occorrenze
     *            {@code ArrayList<Occorrenza>} delle occorrenze.
     */
    public void setOccorrenze(ArrayList<Occorrenza> occorrenze) {
	this.occorrenze = occorrenze;
    }

    /**
     * Restituisce l'arraylist contenente tutte le occorrenze delle parole del file.
     * 
     * @return {@code ArrayList<Occorrenza>} delle occorrenze.
     */
    public ArrayList<Occorrenza> getOccorrenze() {
	return occorrenze;
    }

    /**
     * Restituisce i valori delle occorrenze delle parole del file sottoforma di
     * {@code String} separati da uno spazio. Se il file è privo di occorrenze viene
     * restituita la stringa vuota.
     * 
     * @return {@code String} contenente i valori delle occorrenze.
     */
    public String getOccorrenzeToString() {
	String aString = "";
	for (Occorrenza i : occorrenze)
	    aString += i.getNumOccorrenza() + " ";
	return aString;
    }

    /**
     * Restituisce una {@code String} che contiene il nome 
     * del file che il documento rappresenta.
     * 
     * @return il nome del file.
     */
    public String getNomeDocumento() {
	return aFile.getName();
    }

    /**
     * Calcola il numero di occorrenze delle parole, appartenenti al vocabolario,
     * contenute in un file.
     */
    public void contaOccorrenze() {
	this.setOccorrenze(new ArrayList<Occorrenza>(0));

	Scanner scanVocabulary = null;
	try {
	    scanVocabulary = new Scanner(this.getVocabolario());
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}

	while (scanVocabulary.hasNext())
	    this.getOccorrenze().add(new Occorrenza(scanVocabulary.next(), 0));

	scanVocabulary.close();

	Scanner aScanner = null;
	try {
	    aScanner = new Scanner(aFile);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}

	while (aScanner.hasNext()) {
	    String aString = aScanner.next();
	    int index = -1;
	    for (int i = 0; i < this.getOccorrenze().size(); i++)
		if (this.getOccorrenze().get(i).getParola().equals(aString)) {
		    index = i;
		    break;
		}

	    if (index != -1)
		this.getOccorrenze().get(index)
			.setNumOccorrenza(this.getOccorrenze()
				.get(index).getNumOccorrenza() + 1);
	    else {
		aScanner.close();
		try {
		    throw new Exception("E' stata individuata " 
			    + "una parola non appartenente al vocabolario");
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
	aScanner.close();
    }

    /**
     * Calcola e restituisce la verosimiglianza semantica tra due documenti, ossia
     * il quadrato della distanza euclidea tra i vettori che contengono le
     * occorrenze delle parole dei documenti.
     * 
     * @param aDocument
     *            documento con cui si vuole calcolare la verosimiglianza semantica
     * @return la verosimiglianza semantica.
     */
    public int verosimiglianzaSemantica(Documento aDocument) {
	int somma = 0;
	for (int i = 0; i < this.getOccorrenze().size(); i++) {
	    int temp = this.getOccorrenze().get(i).getNumOccorrenza()
		    - aDocument.getOccorrenze().get(i).getNumOccorrenza();
	    temp *= temp;
	    somma += temp;
	}
	return somma;
    }
}