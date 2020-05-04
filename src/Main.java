/**
 * Questa classe contiene il metodo main.
 * 
 * @author Marco Scanu
 */
public class Main {
    
    /**
     * Metodo che esegue la fase di <i>addestramento</i>
     * e di <i>classificazione</i>.
     * 
     * @param args
     *            vettore {@code String} che indica i parametri passati 
     *            tramite la linea di comando.
     */
    public static void main(String[] args) {
	if ((args.length == 5) 
		&& (args[0].equals(Addestramento.TRAIN))) {
	    Addestramento.training(args, "OK", false);
	    Addestramento.training(args, "SPAM", true);
	    
	} else if ((args.length == 4) 
		&& (args[0].equals(Classificazione.TEST))) {
	    Classificazione.testing(args);
	    
	} else {
	    try {
		throw new Exception("Parametri da linea " 
			+ "di comando errati");
	    }
	    catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }
}
