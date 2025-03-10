package sc.player;

import com.thoughtworks.xstream.io.binary.Token;
import com.thoughtworks.xstream.mapper.Mapper;
import kotlin.collections.IntIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sc.api.plugins.IGameState;
import sc.plugin2025.Field;
import sc.plugin2025.Move;
import sc.plugin2025.GameState;
import sc.shared.GameResult;
import java.util.List;
import java.util.Objects;

import java.util.HashMap;

import sc.player.Calculations;



/**
 * Das Herz des Clients:
 * Eine simple Logik, die zufaellige gueltige Zuege macht.
 * <p>
 * Ausserdem werden zum Spielverlauf Konsolenausgaben gemacht.
 */
public class Logic implements IGameHandler {
  private static final Logger log = LoggerFactory.getLogger(Logic.class);



  /** Aktueller Spielstatus. */
  private GameState gameState;







  /** In dieser Methode habt ihr 2 Sekunden (berechnet etwas Puffer ein) Zeit,
   * um euren nächsten Zug zu planen. */
  @Override
  public Move calculateMove() {

    long startTime = System.currentTimeMillis();
    log.info("Es wurde ein Zug von {} angefordert.", gameState.getCurrentTeam());
    //System.out.println(gameState.getCurrentPlayer().getPosition());


    List<Move> possibleMoves = gameState.getSensibleMoves();
    // Hier intelligente Strategie zur Auswahl des Zuges einfügen
    //System.out.println(possibleMoves);
    Move move = possibleMoves.get((int) (Math.random() * possibleMoves.size()));
    //System.out.println(move);
    log.info("Sende {} nach {}ms.", move, System.currentTimeMillis() - startTime);
    System.out.println(Calculations.GetBestMove(gameState));
    return Calculations.GetBestMove(gameState) ;
  }

  /** Ein neuer Spielstatus ist verfügbar, d.h. ein Zug wurde erfolgreich ausgeführt. */
  @Override
  public void onUpdate(IGameState gameState) {
    this.gameState = (GameState) gameState;
    log.info("Zug: {} Dran: {}", gameState.getTurn(), gameState.getCurrentTeam());
  }

  /** Wird aufgerufen, wenn das Spiel beendet ist. */
  public void onGameOver(GameResult data) {
    System.out.println(data);
    log.info("Das Spiel ist beendet, Ergebnis: {}", data);

  }

  /** Wird aufgerufen, wenn der Server einen Fehler meldet.
   * Bedeutet auch den Abbruch des Spiels. */
  @Override
  public void onError(String error) {
    log.warn("Fehler: {}", error);
  }
}
