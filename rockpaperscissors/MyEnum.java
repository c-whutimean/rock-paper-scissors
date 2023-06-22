package rockpaperscissors;

public class MyEnum {
    enum Moves {
        ROCK("rock", "paper"),
        PAPER("paper", "scissors"),
        SCISSORS("scissors", "rock");

        private final String move;
        private final String defeat;

        Moves(String move, String defeat) {
            this.move = move;
            this.defeat = defeat;
        }

        String getMove() {
            return move;
        }

        String getDefeat() {
            return defeat;
        }

    }
}
