package rockpaperscissors;

import java.util.Random;

public class Game {
    static Random r = new Random();
    String compMove;
    String userMove;
    String userDefeat;


    public Game(MyEnum.Moves userPlay) {
        this.compMove = findCompMove();
        this.userMove = userPlay.getMove();
        this.userDefeat = userDefeatMove();
    }

    String findCompMove() {
        int compPlay = r.nextInt(3);

        for (MyEnum.Moves m : MyEnum.Moves.values()) {
            if (m.ordinal() == compPlay) {
                compMove = m.getMove();
            }
        }
        return compMove;
    }

    String userDefeatMove() {
        switch (userMove) {
            case "rock" -> userDefeat = MyEnum.Moves.ROCK.getDefeat();
            case "scissors" -> userDefeat = MyEnum.Moves.SCISSORS.getDefeat();
            case "paper" -> userDefeat = MyEnum.Moves.PAPER.getDefeat();
        }
        return userDefeat;
    }

    long gameResult() {
        if (userMove.equals(compMove)) {
            System.out.printf("There is a draw (%s)\n", userMove);
            return 50;
        } else if (compMove.equals(userDefeat)) {
            System.out.printf("Sorry, but the computer chose %s\n", compMove);
            return 0;
        } else {
            System.out.printf("Well done. The computer chose %s and failed\n", compMove);
            return 100;
        }
    }
}
