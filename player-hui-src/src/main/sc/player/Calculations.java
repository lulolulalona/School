package sc.player;

import sc.plugin2025.GameState; import sc.plugin2025.Move;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Calculations {



    public static Move GetBestMove(GameState gameState2) {
        GameState gameState = gameState2.clone();
        Move move123 = gameState.getSensibleMoves().getFirst();
        System.out.println(move123);
        //Create Simulation State
        int cycle = 0;

        List<MoveList> State = new ArrayList<>();

        System.out.println(gameState.getSensibleMoves());

        for (Move move : gameState.getSensibleMoves()) {
            MoveList Move = new MoveList();
            Move.Rating = RateField.GetFieldRating(gameState.clone(),move);
            Move.Moves.add(move);
            Move.FirstMove = move;
            Move.State = gameState.clone();
            Move.State.performMoveDirectly(move);
            State.add(Move);
        }

        //Simulate

        for (int i = 0; i < 10; i++) {
            List<MoveList> NewState = new ArrayList<>();
            for (MoveList moveList:State) {
                for (Move SensibleMove : moveList.State.getSensibleMoves()) {
                    MoveList move = new MoveList();
                    move.Rating = moveList.Rating+ RateField.GetFieldRating(moveList.State,SensibleMove);
                    move.Moves.add(SensibleMove);
                    move.FirstMove = moveList.FirstMove;
                    move.State = moveList.State.clone();
                    move.State.performMoveDirectly(SensibleMove);
                    move.State.performMoveDirectly(move.State.getSensibleMoves().getFirst());
                    NewState.add(move);
                }
            }

            NewState.sort(Comparator.comparingInt(MoveList::GetRating));
            if (NewState.size() > 0) {
                State = NewState.subList(0,Math.max(Math.min(1000,NewState.size()-1),0));
            }
            NewState = null;
        }



        MoveList finalmove = (MoveList) State.getFirst();
        State = null;
        return finalmove.FirstMove;
    }

    private static class MoveList {
        public int Rating = 0;
        public List<Move> Moves = new ArrayList<>();
        public GameState State = null;
        public Move FirstMove = null;

        public MoveList clone() {
            MoveList cloned = new MoveList();
            cloned.Rating = Rating;
            cloned.Moves = Moves;
            cloned.State = State;
            return cloned;
        }

        public int GetRating() {
            return Rating;
        }
    }


}

