package io.github.golden_pigeon;

import io.github.golden_pigeon.game.Game;
import io.github.golden_pigeon.game.GameStatus;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(10, 10, 5);
        Scanner sc = new Scanner(System.in);
        while(true){
            int x = sc.nextInt();
            int y = sc.nextInt();
            String command = sc.nextLine().replace(" ", "");
            GameStatus gs = game.move(x, y, command);
            switch (gs){
                case WIN:case LOSE:
                    sc.close();
                    System.exit(0);
                default:
            }
        }
    }
}
