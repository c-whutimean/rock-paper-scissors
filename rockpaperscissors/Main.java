package rockpaperscissors;

import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    static Random r = new Random();
    static String[] movesToPlay;
    static boolean original;
    static List<String> compLoses = new ArrayList<>();
    static List<String> compWins = new ArrayList<>();

    static boolean containsPlay(String userPlay) {
        for (String m : movesToPlay) {
            if (userPlay.equals(m)) {
                return true;
            }
        }
        return false;
    }

    static List convertArrtoList() {
        return Arrays.asList(movesToPlay);
    }

    static void createWinLoseTeams(String play) {
        List originalMoves = convertArrtoList();
        List<String> copyofMoves = new ArrayList<>(originalMoves);

        int len = originalMoves.size();
        int indexofPlay = originalMoves.indexOf(play);
        int leftSide;
        int rightSide;
        int half = len / 2;
        int end = len - 1;

        if (indexofPlay == 0) { // first word in input
            leftSide = 1;
            while (leftSide <= half) {
                compWins.add(movesToPlay[leftSide]);
                leftSide++;
            }
            rightSide = half + 1;
            while (rightSide <= end) {
                compLoses.add(movesToPlay[rightSide]);
                rightSide++;
            }
        } else if (indexofPlay == end) { // last word in input
            leftSide = 0;
            while (leftSide <= half) {
                compWins.add(movesToPlay[leftSide]);
                leftSide++;
            }
            rightSide = half + 1;
            while (rightSide <= end) {
                compLoses.add(movesToPlay[rightSide]);
                rightSide++;
            }
        } else if (indexofPlay == half) {   // right in the middle
            leftSide = 0;
            while (leftSide < half) {
                compWins.add(movesToPlay[leftSide]);
                leftSide++;
            }
            rightSide = half + 1;
            while (rightSide <= end) {
                compLoses.add(movesToPlay[rightSide]);
                rightSide++;
            }
        } else {
            if (indexofPlay < half) {   // word is on the left side of input split
                String word;
                leftSide = 0;
                while (leftSide < indexofPlay) {    //goes up to the userPlay word
                    word = movesToPlay[leftSide];
                    compWins.add(word);
                    copyofMoves.remove(word);
                    leftSide++;
                }
                rightSide = indexofPlay + 1;
                int max = rightSide + half;
                while (rightSide < max) {
                    word = movesToPlay[rightSide];
                    compLoses.add(word);
                    copyofMoves.remove(word);
                    rightSide++;
                }
                // add the remaining elements into compWins List to make it same size as compLoses List
                max = copyofMoves.size() - 1;
                int i = 0;
                while (i < max) {
                    String element = copyofMoves.get(i);
                    compWins.add(element);
                    i++;
                }
            } else {    // word is in right side half of input
                String word;
                rightSide = indexofPlay + 1;
                while (rightSide <= end) {
                    word = movesToPlay[rightSide];
                    compLoses.add(word);
                    copyofMoves.remove(word);
                    rightSide++;
                }
                leftSide = indexofPlay - 1;
                int max = leftSide - half;
                while (leftSide > max) {
                    word = movesToPlay[leftSide];
                    compWins.add(word);
                    copyofMoves.remove(word);
                    leftSide--;
                }
                max = copyofMoves.size() - 1;
                int i = 0;
                while (i < max) {
                    String element = copyofMoves.get(i);
                    compLoses.add(element);
                    i++;
                }
            }
        }
    }

    static long whoWins(String compMove) {
        if (compWins.contains(compMove)) {
            System.out.printf("Sorry, but the computer chose %s\n", compMove);
            return 0;
        } else if (compLoses.contains(compMove)) {
            System.out.printf("Well done. The computer chose %s and failed\n", compMove);
            return 100;
        } else {
            System.out.printf("There is a draw (%s)\n", compMove);
            return 50;
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        boolean play = true;
        String name;
        String options;
        int i = 0;


        System.out.print("Enter your name: ");
        name = sc.nextLine();
        System.out.printf("Hello, %s\n", name);
        options = sc.nextLine();
        if (options.isEmpty()) {
            movesToPlay = new String[]{"rock", "paper", "scissors"};
        } else {
            movesToPlay = options.split(",");
            original = false;
        }
        Rating rating = new Rating(name);
        rating.getPlayerstats();
        if (!rating.playerStats.containsKey(name)) {
            rating.playerStats.put(name, 0L);
        }

        System.out.println("Okay, let's start");
        int turn = 0;

        while (play || turn < 1) {
            String userPlay = sc.nextLine();
            if (userPlay.equals("!exit")) {
                System.out.println("Bye!");
                turn++;
                play = false;
            } else if (userPlay.equals("!rating")) {
                System.out.println("Your rating: " + rating.getPoints());
            } else if (original && containsPlay(userPlay)) {
                MyEnum.Moves userMove = MyEnum.Moves.valueOf(userPlay.toUpperCase());
                Game game = new Game(userMove);
                rating.gainPoints = game.gameResult();
                rating.updateScore();
            } else if (!original && containsPlay(userPlay)) {
                createWinLoseTeams(userPlay);
                int compPlay = r.nextInt(movesToPlay.length);
                String compMove = movesToPlay[compPlay];
                rating.gainPoints = whoWins(compMove);
                rating.updateScore();
            } else {
                System.out.println("Invalid input");
            }
        }

    }
}

