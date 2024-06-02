# 🌱 The Plant Based Hub 🌱: back-end di un'applicazione web per ricette veg in Spring Boot

Benvenuti nel repository back-end di _The Plant Based Hub_, un'applicazione web progettata per aiutarti a trovare e creare ricette vegane in base agli ingredienti disponibili in casa e alle tue esigenze nutrizionali. 
Utilizzando Spring Boot per il backend e PostgreSQL come database, The Plant Based Hub offre un'esperienza utente intuitiva e funzionalità avanzate per ridurre gli sprechi alimentari e risparmiare denaro.

## Descrizione del Progetto

The Plant Based Hub è un'applicazione web progettata per:

1. **Ricerca Ricette per Ingredienti Disponibili**: Inserisci gli ingredienti che hai a disposizione e trova ricette vegane elaborate che li utilizzano al meglio.
2. **Selezione di Macrogruppi Nutritivi**: Filtra le ricette in base ai macrogruppi nutritivi principali come proteine, carboidrati, fibre e vitamine per creare pasti completi e bilanciati da un punto di vista nutrizionale.
3. **Generazione di Liste della Spesa**: Scegli le ricette che desideri preparare e genera automaticamente una lista della spesa con gli ingredienti esatti da acquistare, evitando sprechi inutili di risorse e denaro.

## Funzionalità Principali

- **Ricerca Intelligente di Ricette**: Trova facilmente ricette che utilizzano gli ingredienti disponibili in casa.
- **Filtraggio Nutrizionale**: Seleziona le ricette in base ai tuoi bisogni nutrizionali specifici.
- **Lista della Spesa Automatizzata**: Crea una lista della spesa dettagliata e precisa per le ricette selezionate.
- **Riduzione degli Sprechi**: Ottimizza l'uso degli ingredienti e riduci gli sprechi alimentari.

## Tecnologie Utilizzate

- **Backend**: Spring Boot 🍃
- **Database**: PostgreSQL 🐘
- **Linguaggio**: Java ☕️

## Struttura del Progetto

- **src/main/java**: Contiene il codice sorgente dell'applicazione.
- **src/main/resources**: Contiene le risorse dell'applicazione, come i file di configurazione.
- **src/test/java**: Contiene i test unitari e di integrazione per l'applicazione.

## API e Documentazione Swagger

Questo repository contiene il backend che fornisce le API per l'applicazione front-end. 
Una volta completati gli endpoint idonei, sarà disponibile la documentazione Swagger per esplorare e testare le API.

## Installazione e Configurazione

### Prerequisiti

- JDK 11 o superiore
- Maven 3.6.3 o superiore
- PostgreSQL

### Passaggi per l'Installazione

1. Clona il repository:
   ```bash
   git clone https://github.com/tuo-username/plantbasedhub.git
   ```
2. Naviga nella directory del progetto:
   ```bash
   cd plantbasedhub
   ```
3. Configura il database PostgreSQL:
   - Crea un database denominato `plantbasedhub`.
   - Aggiorna le credenziali del database nel file `src/main/resources/application.properties`.

4. Compila e avvia l'applicazione:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. Accedi all'applicazione:
   Apri il browser e vai all'indirizzo `http://localhost:8080`.

## Contribuire

Contribuzioni e pull request sono benvenute! Sentiti libero di esplorare le issue aperte e contribuire con miglioramenti o correzioni di bug.

1. Fork il progetto
2. Crea il tuo branch di feature (`git checkout -b feature/AmazingFeature`)
3. Commit delle modifiche (`git commit -m 'Add some AmazingFeature'`)
4. Push al branch (`git push origin feature/AmazingFeature`)
5. Apri una Pull Request

## Contatti

Per domande o segnalazioni di problemi, puoi aprire un'issue su GitHub.

Grazie per aver utilizzato The Plant Based Hub! Speriamo che questa applicazione ti aiuti a creare pasti vegani deliziosi e nutrienti, riducendo gli sprechi alimentari.
