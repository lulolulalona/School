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






  public int RateField(GameState game, int FieldPosition) {

    switch (Objects.requireNonNull(game.getBoard().getField(FieldPosition))) {
      case GOAL -> {
        return 200;
      }
      case HARE -> {
        return 1;
      }
      case SALAD -> {
        return 2;
      }
      case START -> {
        return 1;
      }
      case MARKET -> {
        return 1;
      }
      case CARROTS -> {
        return 1;
      }
      case HEDGEHOG -> {
        return 1;
      }
      case POSITION_1 -> {
        return 1;
      }
      case POSITION_2 -> {
        return 1;
      }
    }
    return 0;
  }

  public Object Simulate(HashMap move, int steps_in_the_future) {

    GameState clonedgames = (GameState) move.get("GameState");
    clonedgames.performMoveDirectly(clonedgames.getSensibleMoves().get(1));


    if (!clonedgames.getSensibleMoves().isEmpty()) {

    }
    return "Deine Mutter"; // We remember "Deine Mutter kann auch als Object returned werden"
  }


  public Move GetBestMove() {
    HashMap<Integer,HashMap> movelist = new HashMap<>();
    GameState cloned_game = gameState.clone();
    cloned_game.getSensibleMoves().forEach(move -> {
      cloned_game.performMoveDirectly(move);
      HashMap<String,Object> move_info = new HashMap<>();
      move_info.put("Value",RateField(gameState, gameState.getOtherPlayer().getPosition()));
      move_info.put("Total_Moves",1);
      move_info.put("Move",move);
      move_info.put("GameState",cloned_game);
      movelist.put(movelist.size()+1,move_info);
    }
    );

    int steps_in_the_future = 5;
    for (int i = 0; i < steps_in_the_future; i++) {

    }




    int best_rating = 0;
    int min_moves = 999;
    Move best_move = null;

    for (int i = 0; i < movelist.size(); i++) {
      if ((int) movelist.get(i).get("Total_Moves") < best_rating) {
        best_move   = (Move) movelist.get(i).get("Move");
        min_moves   = (int) movelist.get(i).get("Total_Moves");
        best_rating = (int) movelist.get(i).get("Value");
      } else if ((int) movelist.get(i).get("Total_Moves") == best_rating) {
        if ((int) movelist.get(i).get("Value") > best_rating) {
            best_move = (Move) movelist.get(i).get("Move");
            min_moves = (int) movelist.get(i).get("Total_Moves");
          best_rating = (int) movelist.get(i).get("Value");
        }
      }
    }



    return best_move;
  }

  /** In dieser Methode habt ihr 2 Sekunden (berechnet etwas Puffer ein) Zeit,
   * um euren nächsten Zug zu planen. */
  @Override
  public Move calculateMove() {
    String json = System.getenv("CONFIG_JSON");
    //System.out.println(json);
    long startTime = System.currentTimeMillis();
    log.info("Es wurde ein Zug von {} angefordert.", gameState.getCurrentTeam());
    //System.out.println(gameState.getCurrentPlayer().getPosition());
    for (int i = 0; i < 1000000000; i++) {
      int x = 5;
    }

    List<Move> possibleMoves = gameState.getSensibleMoves();
    // Hier intelligente Strategie zur Auswahl des Zuges einfügen
    //System.out.println(possibleMoves);
    Move move = possibleMoves.get((int) (Math.random() * possibleMoves.size()));
    //System.out.println(move);
    log.info("Sende {} nach {}ms.", move, System.currentTimeMillis() - startTime);
    return move;
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
