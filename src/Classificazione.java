import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * La classe {@code Classificazione} è composta esclusivamente da metodi statici
 * utili per la realizzazione della fase di <i>testing</i> del
 * classificatore.<br>
 * In questa fase il programma classifica dei file tramite la tecnica <i>nearest
 * neighbors</i> basata sul conteggio delle occorrenze delle singole parole in
 * un testo. <br>
 * Questa classe non può essere istanziata.
 * 
 * @author Marco Scanu
 */
public class Classificazione {

    /**
     * Questo valore {@code String} identifica la fase di <i>classificazione</i>.
     */
    public static final String TEST = "TEST";

    /**
     * Questo valore {@code int} identifica il delta utile per identificare i
     * documenti nell'algoritmo di <i>Nearest Neighbor</i>.
     */
    private static int delta;

    /**
     * Non permette a nessuno di istanziare questa classe.
     */
    private Classificazione() { }

    /**
     * Imposta il valore {@code int} del delta uguale a quello dell'argomento.<br>
     * 
     * @param delta
     *            valore del delta.
     */
    public static void setDelta(int delta) {
	Classificazione.delta = delta;
    }

    /**
     * Restituisce il valore {@code int} di delta.
     * 
     * @return delta.
     */
    public static int getDelta() {
	return Classificazione.delta;
    }

    /**
     * Esegue le operazioni richieste dalle specifiche nella fase di
     * <i>classificazione</i>.
     * 
     * @param args
     *            vettore {@code String} che indica i parametri passati 
     *            tramite la linea di comando.
     */
    public static void testing(String[] args) {
	ArrayList<Documento> documenti = new ArrayList<Documento>(0);
	Classificazione.caricaFileOccorrenze(args, documenti);

	File directory = new File(args[3]);
	File[] dirFiles = directory.listFiles();

	try {
	    if (dirFiles == null)
		throw new Exception("La directory " 
			+ directory.getAbsolutePath() + " non esiste");
	} catch (Exception e) {
	    e.printStackTrace();
	}

	try {
	    if (dirFiles.length == 0)
		throw new Exception("La directory " 
			+ directory.getAbsolutePath() + " è vuota");
	} catch (Exception e) {
	    e.printStackTrace();
	}

	Classificazione.setDelta(Classificazione.calcolaDelta(args));

	for (File aFile : dirFiles) {
	    Documento fileTest = new Documento(aFile.toString(), null);
	    fileTest.setVocabolario(new File(args[1]));
	    fileTest.contaOccorrenze();

	    int[] num = Classificazione.nearestNeighbor(fileTest, documenti);

	    System.out.print(aFile.getName() + "\t");
	    if (num[0] >= num[1])
		System.out.println("OK");
	    else
		System.out.println("SPAM");
	}
    }

    /**
     * Tramite la tecnica della <i>validazione incrociata</i> a K blocchi
     * stratificati, vengono effettuate delle prove empiriche in modo tale da
     * trovare il delta più adatto per la <i>classificazione</i>.<br>
     * 
     * @param args
     *            vettore {@code String} che indica i parametri passati 
     *            tramite la linea di comando.
     * @return il delta migliore.
     */
    public static int calcolaDelta(String[] args) {
	ArrayList<Documento> documenti = new ArrayList<Documento>(0);
	Classificazione.caricaFileOccorrenze(args, documenti);

	ValidazioneIncrociata.crossValidation(documenti);

	return Classificazione.getDelta();
    }

    /**
     * Esegue l'algoritmo di <i>nearest neighbor</i> tra un documento e 
     * un insieme di documenti.<br>
     * Restituisce un vettore {@code int} contenente in ogni cella un numero per
     * ogni {@code CategoriaDocumento}; ognuno identifica il numero dei file 
     * compresi nel delta.
     * 
     * @param fileTest
     *            documento da classificare.
     * @param documenti
     *            insieme di documenti usati come training.
     * @return vettore {@code int} contenente il numero di file compresi
     * 	       nel delta.
     */
    public static int[] nearestNeighbor(Documento fileTest,
	    ArrayList<Documento> documenti) {
	CategoriaDocumento[] categorie = CategoriaDocumento.values();
	int[] numCat = new int[categorie.length];

	ArrayList<ArrayList<Integer>> distanze = 
		new ArrayList<ArrayList<Integer>>(0);

	for (int i = 0; i < numCat.length; i++)
	    distanze.add(new ArrayList<Integer>(0));

	for (int i = 0; i < documenti.size(); i++) {
	    for (int j = 0; j < categorie.length; j++)
		if (documenti.get(i).getCategoria() == categorie[j])
		    distanze.get(j).add(documenti.get(i)
			    .verosimiglianzaSemantica(fileTest));
	}

	for (int i = 0; i < distanze.size(); i++)
	    numCat[i] = Classificazione.numeroDistanze(distanze.get(i));

	return numCat;
    }

    /**
     * Carica dal file delle occorrenze, verso un {@code ArrayList<Documento>}, 
     * il nome e le occorrenze delle parole di ciascun documento.
     * 
     * @param args
     *            vettore di {@code String} dei parametri da linea di comando.
     * @param documenti
     *            {@code ArrayList<Documento>} per il training.
     */
    private static void caricaFileOccorrenze(String[] args, 
	    ArrayList<Documento> documenti) {
	Scanner in = null;
	try {
	    in = new Scanner(new File(args[2]));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	int index = 0;
	CategoriaDocumento sent = null;

	while (in.hasNextLine()) {
	    String line = in.nextLine();
	    if (line.equals("OK")) {
		sent = CategoriaDocumento.OK;
		continue;
	    } else if (line.equals("SPAM")) {
		sent = CategoriaDocumento.SPAM;
		continue;
	    }

	    Scanner in2 = null;
	    try {
		in2 = new Scanner(new File(args[1]));
	    } catch (FileNotFoundException e) {
		in.close();
		e.printStackTrace();
	    }

	    ArrayList<Occorrenza> occorrenze = new ArrayList<Occorrenza>(0);
	    while (in2.hasNext())
		occorrenze.add(new Occorrenza(in2.next(), 0));

	    String[] aString = line.split(" ");
	    documenti.add(new Documento(aString[0], sent));

	    int j = 0;
	    for (int i = 1; i < aString.length; i++)
		if (!aString[i].equals(""))
		    occorrenze.get(j++)
		    	.setNumOccorrenza(Integer.parseInt(aString[i]));

	    documenti.get(index).setOccorrenze(occorrenze);

	    index++;
	    in2.close();
	}
	in.close();
    }

    /**
     * Calcola e restituisce il numero totale delle verosimiglianze semantiche
     * valide, ovvero minori uguali a delta, tra i documenti.
     * 
     * @param distanze
     *            {@code ArrayList<Integer>} dei valori della verosimiglianza
     *            semantica tra documenti.
     * @return numero verosimiglianze valide.
     */
    private static int numeroDistanze(ArrayList<Integer> distanze) {
	int num = 0;
	for (int i = 0; i < distanze.size(); i++)
	    if (distanze.get(i) <= delta)
		num++;
	return num;
    }

}
