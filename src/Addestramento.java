import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * La classe {@code Addestramento} è composta esclusivamente da un metodo
 * statico per la realizzazione della fase di addestramento del
 * classificatore.<br>
 * In questa fase il programma analizza i file in ingresso e salva su un file di
 * output i dati relativi alle occorrenze delle singole parole nei file “SPAM” e
 * nei file “OK”.<br>
 * Questa classe non può essere istanziata.
 * 
 * @author Marco Scanu
 */
public class Addestramento {
    
    /**
     * Questo valore {@code String} identifica la fase di <i>addestramento</i>
     */
    public static final String TRAIN = "TRAIN";

    /**
     * Non permette a nessuno di istanziare questa classe.
     */
    private Addestramento() { }

    /**
     * Esegue le operazioni richieste dalle specifiche nella fase 
     * di addestramento del classificatore.
     * 
     * @param args
     *            vettore {@code String} che indica i parametri passati 
     *            tramite la linea di comando.
     * @param tipo
     *            {@code String} che identifica il tipo di documento.
     * @param valore
     *            {@code boolean}, se è {@code false} il file verrà sovrascritto,
     *            se è {@code true} no.
     */
    public static void training(String[] args, String tipo, boolean valore) {
	String dirPath = null;
	FileWriter fileOccorrenze = null;
	try {
	    fileOccorrenze = new FileWriter(args[4], valore);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	PrintWriter aPrintWriter = new PrintWriter(fileOccorrenze);
	Documento aDocument = new Documento();
	aDocument.setVocabolario(new File(args[1]));

	if (tipo.equals("OK")) {
	    aDocument.setCategoria(CategoriaDocumento.OK);
	    dirPath = args[2];
	} else if (tipo.equals("SPAM")) {
	    aDocument.setCategoria(CategoriaDocumento.SPAM);
	    dirPath = args[3];
	} else {
	    aPrintWriter.close();
	    try {
		fileOccorrenze.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    try {
		throw new Exception("Categoria del documento non valida");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	File directory = new File(dirPath);
	File[] dirFiles = directory.listFiles();

	try {
	    if (dirFiles == null)
		throw new Exception("La directory " 
			+ directory.getAbsolutePath() + " non esiste");
	} catch (Exception e) {
	    e.printStackTrace();
	}

	if (dirFiles.length == 0) {
	    try {
		throw new Exception("La directory " 
			+ directory.getName() + " è vuota");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	aPrintWriter.println(aDocument.getCategoria());

	for (File aFile : dirFiles) {
	    aDocument.setaFile(aFile);
	    aDocument.contaOccorrenze();
	    aPrintWriter.println(String.format("%-20s %s", 
		    aDocument.getNomeDocumento(), 
		    aDocument.getOccorrenzeToString()));
	}

	aPrintWriter.close();
	try {
	    fileOccorrenze.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
