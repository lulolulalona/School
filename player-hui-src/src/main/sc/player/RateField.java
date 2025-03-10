package sc.player;

import sc.plugin2025.Card;
import sc.plugin2025.Field;
import sc.plugin2025.GameState;
import sc.plugin2025.Move;

import java.util.HashMap;
import java.util.Map;


public class RateField {

    public static int GetFieldRating(GameState gameState, Move move) {

        GameState cloned = gameState.clone();
        cloned.performMoveDirectly(move);
        cloned.performMoveDirectly(cloned.getSensibleMoves().getFirst());
        Field field = cloned.getBoard().getField(cloned.getOtherPlayer().getPosition());


        HashMap<String,Integer> Cards = new HashMap<>();
        Cards.put(Card.EAT_SALAD.getLabel(), 0);
        Cards.put(Card.FALL_BACK.getLabel(), 0);
        Cards.put(Card.HURRY_AHEAD.getLabel(), 0);
        Cards.put(Card.SWAP_CARROTS.getLabel(), 0);
        for (Card card : cloned.getCurrentPlayer().getCards()) {
            Cards.merge(card.getLabel(), 1, Integer::sum);
        }






        switch (field) {
            case POSITION_2 -> {
                if (cloned.getCurrentPlayer().getPosition()<cloned.getOtherPlayer().getPosition()) {
                    return 30;
                } else {
                    return 5;
                }

            }
            case POSITION_1 -> {
                if (cloned.getCurrentPlayer().getPosition()<cloned.getOtherPlayer().getPosition()) {
                    return 5;
                } else {
                    return 20;
                }
            }
            case HEDGEHOG -> {
                if (cloned.getCurrentPlayer().getCarrots() < 10 && cloned.getCurrentPlayer().getPosition() < 60) {
                    return 40;
                } else {
                    return 15;
                }
            }
            case CARROTS -> {
                return 10;
            }
            case MARKET -> {
                if (Cards.get(Card.EAT_SALAD.getLabel()) < cloned.getCurrentPlayer().getSalads()) {
                    return 50;
                } else {
                    return 0;
                }

            }
            case START -> {
                return 0;
            }
            case SALAD -> {
                if (Cards.get(Card.EAT_SALAD.getLabel()) < cloned.getCurrentPlayer().getSalads()) {
                    return 40*cloned.getCurrentPlayer().getSalads();
                } else {
                    return 20*cloned.getCurrentPlayer().getSalads();
                }
            }
            case HARE -> {
                if (Cards.get(Card.EAT_SALAD.getLabel()) > 0) {
                    return 40;
                }
                return 0;
            }
            case GOAL -> {
                return 999999;
            }
            case null -> {
                return 0;
            }
        }
    }
}
