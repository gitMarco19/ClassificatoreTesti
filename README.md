# Obiettivo
Scrivere un programma che realizzi un classificatore binario di testi utilizzando tecniche di classificazione supervisionata ***"nearest neighbors"*** basate sul conteggio delle occorrenze delle singole parole in un testo.

Il programma si dovrà articolare in due fasi: <br>
Una fase di addestramento (training) in cui il programma riceve in input:
- un file contenente tutte le parole ammissibili e
- due insiemi di file, uno etichettato come “SPAM” e uno etichettato come “OK”.

In questa fase il programma analizza i file in ingresso e salva su un file di output i dati relativi alle occorrenze delle singole parole nei file “SPAM” e nei file “OK”. L’output di questa fase è un file contenente informazioni sulle occorrenze delle parole nei file.

Una fase di classificazione (testing) in cui il programma riceve in input:
- il file contenente i dati relativi alle occorrenze generato nella fase precedente e
- un insieme di file da classificare come “OK” e “SPAM”.

L’output di questa fase è la classificazione risultante.

# Specifiche funzionali
La prima fase del programma (fase di training) deve essere invocata tramite la linea di comando: <br>

	java Main TRAIN <file_dizionario> <directory_ok> <directory_spam> <file_occorrenze>

dove:
<file_dizionario> è il nome del file completo di percorso contenente l’elenco di parole lecite; <br>
<directory_ok> (<directory_spam>) è il nome della directory completa di percorso in cui sono contenuti i file "OK" ("SPAM") per l’addestramento; <br>
<file_occorrenze> è il nome del file completo di percorso su cui viene registrato il risultato della fase di training. <br>

La seconda fase del programma (fase di testing) deve essere invocata tramite la linea di comando: <br>

	java Main TEST <file_dizionario> <file_occorrenze> <directory_testi>

dove: <br>
<file_dizionario> è il nome del file completo di percorso contenente l’elenco di parole lecite; <br>
<file_occorrenze> è il nome del file completo di percorso contenente l’output della fase precedente; <br>
<directory_testi> è il nome della directory completa di percorso in cui sono contenuti i file da classificare.

# Formato di input
Il formato dei file di input nella fase di training è il seguente:
- Il dizionario (parametro <file_dizionario>) è un file di testo contenente un elenco di parole in ordine alfabetico, e tale per cui in ogni riga compare una sola parola scritta in caratteri minuscoli.
- La directory dei file "OK" (parametro <directory_ok>) contiene un insieme di file di testo in cui compaiono solamente le parole lecite del dizionario. I file sono privi di segni di interpunzione e altri caratteri non alfabetici. Le parole sono separate esclusivamente da uno o più spazi e dal ritorno a capo.
- I file della directory "SPAM" (parametro <directory_spam>) hanno lo stesso formato di quello dei file "OK".

Il formato dei file di input nella fase di testing è il seguente:
- Il file dizionario (parametro <file_dizionario>) ha lo stesso formato descritto sopra.
- Se n è il numero di file "OK", m è il numero di file "SPAM" e d è il numero di parole lecite acquisite nella fase di training, il file delle occorrenze (parametro <file_occorrenze>) contiene un numero di righe pari a n + m + 2 e un numero di colonne pari a d + 1. <br>

All’interno il file è organizzato come segue:

	OK <br>
	<file_OK_1> <occ_1_1> ... <occ_1_d> <br>
	...<br>
	<file_OK_n> <occ_n_1> ... <occ_n_d><br>
	SPAM <br>
	<file_SPAM_1> <occ_1_1> ... <occ_1_d> <br>
	... <br>
	<file_SPAM_m> <occ_m_1> ... <occ_m_d> <br>

dove: <br>
<file_OK_i> è il nome dello i-esimo file "OK" letto nella fase di training; <br>
<file_SPAM_i> è il nome dello i-esimo file "SPAM"; <br>
<occ_i_j> è il numero di occorrenze della j-esima parola del dizionario nello i-esimo file ("OK" o "SPAM").

Il file è suddiviso in due sezioni, la prima delimitata dalla parola chiave OK e la seconda delimitata dalla parola chiave SPAM. <br>
Nelle due fasi, il programma non prende nessun altro tipo di input se non quello specificato e nelle modalità indicate.

# Formato di output
Il formato del file di output della prima fase (training) è descritto sopra. <br>
L’output della seconda fase (testing) è una serie di p righe a console in cui ogni riga contiene le seguenti informazioni:

	<file_1> <etichetta>
	...
	<file_p> <etichetta>

dove <file_i> è il nome di uno dei p file da classificare contenuto nella direcotry passata in input alla seconda fase e "etichetta" è un valore a scelta fra OK oppure SPAM.
Nelle due fasi, il programma non emette nessun altro tipo di output se non quello specificato.
