package io.github.golden_pigeon.game;

class GameMap {
    private final BlockType[][] map;
    private final MarkType[][] mark;
    private final int height;
    private final int width;
    private final int[] x_axis = {-1, 0, 1};
    private final int[] y_axis = {-1, 0, 1};
    private int mineCnt = 0;
    private int discoveredCnt = 0;
    private int exploredCnt = 0;
    private int flagCnt = 0;
    private boolean isWon = false;
    private boolean isLost = false;
    public GameMap(int height, int width){
        this.height = height;
        this.width = width;
        mark = new MarkType[height][width];
        map = new BlockType[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                map[i][j] = BlockType.SPACE;
                mark[i][j] = MarkType.UNEXPLORED;
            }
        }
    }

    private void addHint(int x, int y){
        if(outOfRange(x, y))
            return;
        map[x][y] = map[x][y].after();
    }

    private void minusHint(int x, int y){
        if(outOfRange(x, y))
            return;
        map[x][y] = map[x][y].before();
    }

    public boolean setMine(int x, int y){
        if(outOfRange(x, y))
            return false;
        if(map[x][y].isMine())
            return false;
        map[x][y] = BlockType.MINE;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                addHint(x_axis[i] + x, y_axis[j] + y);
            }
        }
        mineCnt++;
        return true;
    }

    /**
     * explore the map
     * @return if there is a mine in the block, return false AND if it is safe, return true.
     */
    public boolean explore(int x, int y){
        if(outOfRange(x, y))
            return true;
        if(map[x][y].isMine()) {
            mark[x][y] = MarkType.EXPLORED;
            isLost = true;
            return false;
        }
        if(mark[x][y] == MarkType.UNEXPLORED){
            mark[x][y] = MarkType.EXPLORED;
            exploredCnt++;
            if(!map[x][y].isHint()) {
//                explore(x + 1, y);
//                explore(x, y + 1);
//                explore(x - 1, y);
//                explore(x, y - 1);
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        explore(x + x_axis[i], y + y_axis[j]);
                    }
                }
            }
        }
        return true;
    }

    public boolean flag(int x, int y){
        if(outOfRange(x, y))
            return false;
        if(mark[x][y] != MarkType.UNEXPLORED)
            return false;
        mark[x][y] = MarkType.FLAG;
        if(map[x][y].isMine())
            discoveredCnt++;
        flagCnt++;
        return true;
    }

    public boolean danger(int x, int y){
        if(outOfRange(x, y))
            return false;
        if(mark[x][y] != MarkType.UNEXPLORED)
            return false;
        mark[x][y] = MarkType.DANGER;
        return true;
    }

    public boolean unexplore(int x, int y){
        if(outOfRange(x, y))
            return false;
        if(mark[x][y] == MarkType.EXPLORED || mark[x][y] == MarkType.UNEXPLORED)
            return false;
        if(mark[x][y] == MarkType.FLAG) {
            flagCnt--;
            if(map[x][y].isMine())
                discoveredCnt--;
        }

        mark[x][y] = MarkType.UNEXPLORED;
        return true;
    }

    public boolean won(){
        if(((flagCnt == mineCnt && mineCnt == discoveredCnt) || exploredCnt + mineCnt == height * width) && !isLost){
            isWon = true;
            return true;
        }
        return false;
    }

    private boolean outOfRange(int x, int y){
        return x < 0 || x >= height || y < 0 || y >= width;
    }

    public void printMap(){
        System.out.print("   ");
        for(int i = 0; i < width; i++)
            System.out.printf("%-3d", i);
        System.out.println();
        for(int i = 0; i < height; i++){
            System.out.printf("%-2d ", i);
            for(int j = 0; j < width; j++){
                switch (mark[i][j]){
                    case UNEXPLORED:
                        System.out.printf("%-2s ","\u25A0 ");
                        break;
                    case FLAG:
                        System.out.printf("%-2s ","\u2690 ");
                        break;
                    case DANGER:
                        System.out.printf("%-2s ","? ");
                        break;
                    default:
                        if(map[i][j].isSpace())
                            System.out.printf("%-2s ",". ");
                        else if(map[i][j].isHint())
                            System.out.printf("%-2s ",map[i][j].getIndex());
                        else
                            System.out.printf("\u263C ");
                }
            }
            System.out.println();
        }
    }

}
