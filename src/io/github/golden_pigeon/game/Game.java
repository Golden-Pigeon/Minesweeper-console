package io.github.golden_pigeon.game;

import org.jetbrains.annotations.NotNull;

public class Game {
    private GameMap map;
    private final double factory = 0.1;
    public Game(int height, int width, int mines){
        if(mines < 1 || mines > factory * height * width)
            throw new IllegalArgumentException("地雷数目不恰当");
        map = new GameMap(height, width);
        int cnt = 0;
        while (cnt != mines) {
            int x = (int) Math.floor(Math.random() * (height - 1)) + 1;
            int y = (int) Math.floor(Math.random() * (width - 1)) + 1;
            if (map.setMine(x, y))
                cnt++;
        }
        map.printMap();
        System.out.println();
    }

    public GameStatus move(int x, int y, @NotNull String command){
        boolean lose = false;
        switch (command){
            case "explore":
                lose = !map.explore(x, y);
                break;
            case "flag":
                if(!map.flag(x, y)){
                    System.out.println("非法操作,请重试");
                    return GameStatus.NORMAL;
                }
                break;
            case "danger":
                if(!map.danger(x, y)){
                    System.out.println("非法操作,请重试");
                    return GameStatus.NORMAL;
                }
                break;
            case "unmark":
                if(!map.unexplore(x, y)){
                    System.out.println("非法操作,请重试");
                    return GameStatus.NORMAL;
                }
                break;
            default:
                System.out.println("未知命令,请检查输入");
                return GameStatus.NORMAL;
        }
        map.printMap();
        System.out.println();
        if(lose) {
            System.out.println("you lose");
            return GameStatus.LOSE;
        }
        if(map.won()){
            System.out.println("you win");
            return GameStatus.WIN;
        }
        return GameStatus.NORMAL;
    }
}
