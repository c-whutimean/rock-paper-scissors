package rockpaperscissors;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Rating {
    Map<String, Long> playerStats = new HashMap<>();
    long gainPoints = 0;
    long rating;
    String name;

    Rating(String name) {
        this.name = name;
    }

    void getPlayerstats() {
        String pathToFile = "rating.txt";
        File file = new File(pathToFile);

        try (Scanner scanner = new Scanner(file)) {
            String player;
            while (scanner.hasNext()) {
                player = scanner.nextLine();
                String[] stats = player.split(" ");
                long score = Long.parseLong(stats[1]);
                playerStats.put(stats[0], score);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file: " + pathToFile);
        }
    }

    void updateScore() {
        rating = playerStats.get(name);
        rating += gainPoints;
        playerStats.put(name, rating);
    }

    long getPoints() {
        return playerStats.get(name);
    }

}
