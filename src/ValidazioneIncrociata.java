import java.util.ArrayList;
import java.util.Collections;

/**
 * La classe {@code ValidazioneIncrociata} contiene esclusivamente dei metodi
 * statici relativi all'esecuzione della tecnica della <i>validazione
 * incrociata</i> a K blocchi stratificati. In questo caso si ha K = 10. <br>
 * Questa classe non è istanziabile.
 * 
 * @author Marco Scanu
 */
public class ValidazioneIncrociata {

    /**
     * Questo valore {@code int} indica il numero di blocchi in cui viene diviso
     * l'insieme dei file.
     */
    public static final int K = 10;

    /**
     * Non permette a nessuno di istanziare questa classe.
     */
    private ValidazioneIncrociata() { }

    /**
     * Esegue la tecnica della <i>validazione incrociata</i> a K blocchi
     * stratificati sull'insieme di documenti passato come argomento.
     * 
     * @param documenti
     *            insieme di documenti.
     */
    public static void crossValidation(ArrayList<Documento> documenti) {
	ArrayList<Documento> docOk = new ArrayList<Documento>(0);
	ArrayList<Documento> docSpam = new ArrayList<Documento>(0);

	int numCategorie = CategoriaDocumento.values().length;
	int counter = documenti.size() / numCategorie;
	
	for (int i = 0; i < (documenti.size() / numCategorie); i++) {
	    docOk.add(documenti.get(i));
	    docSpam.add(documenti.get(i + counter));
	}

	Collections.shuffle(docOk);
	Collections.shuffle(docSpam);

	documenti.clear();
	documenti.addAll(docOk);
	documenti.addAll(docSpam);

	ArrayList<ArrayList<Documento>> blocchi =
		new ArrayList<ArrayList<Documento>>(0);
	for (int i = 0; i < K; i++)
	    blocchi.add(new ArrayList<Documento>(0));

	int num = (documenti.size() / K) / numCategorie;

	counter = 0;
	for (int index = 0; index < numCategorie; index++)
	    for (int i = 0; i < blocchi.size(); i++) {
		for (int j = counter; j < (counter + num); j++)
		    blocchi.get(i).add(documenti.get(j));
		counter += num;
	    }

	boolean sent = false;
	for (Classificazione.setDelta(0); true; 
		Classificazione.setDelta(Classificazione.getDelta() + 1)) {
	    float[] err = new float[blocchi.size()];
	    for (int i = 0; i < blocchi.size(); i++) {
		ArrayList<Documento> tmp = new ArrayList<>(0);
		for (int j = 0; j < blocchi.size(); j++)
		    if (i != j)
			tmp.addAll(blocchi.get(j));

		err[i] = ValidazioneIncrociata
				.calcolaErrore(blocchi.get(i), tmp);
	    }

	    float errTot = 0;
	    for (int i = 0; i < err.length; i++)
		errTot += err[i];

	    if (((errTot / err.length) == 0) && (!sent))
		sent = true;
	    else if (((errTot / err.length) != 0) && (sent))
		break;
	}

	Classificazione.setDelta(Classificazione.getDelta() - 1);
    }

    /**
     * Calcola e restiruisce l'<i>errore di classificazione</i> 
     * relativo al blocco di documenti passato come primo argomento 
     * rispetto all'insieme di documenti passato come secondo argomento.
     * 
     * @param blocco
     *            documenti di testing.
     * @param tmp
     *            documenti di training.
     * @return errore di classificazione del blocco.
     */
    private static float calcolaErrore(ArrayList<Documento> blocco, 
	    ArrayList<Documento> tmp) {
	float falsiPositivi = 0;
	float falsiNegativi = 0;

	for (int index = 0; index < blocco.size(); index++) {
	    int[] num = Classificazione.nearestNeighbor(blocco.get(index), tmp);

	    if (num[0] >= num[1]) {
		if (blocco.get(index).getCategoria() 
			!= CategoriaDocumento.OK)
		    falsiNegativi++;
	    } else if (num[0] < num[1]) {
		if (blocco.get(index).getCategoria() 
			!= CategoriaDocumento.SPAM)
		    falsiPositivi++;
	    }
	}

	float errore = (falsiPositivi + falsiNegativi) / (blocco.size());

	return errore;
    }
}
