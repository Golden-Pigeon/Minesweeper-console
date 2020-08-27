package io.github.golden_pigeon.game;

public enum BlockType {
    SPACE(0), HINT_ONE(1), HINT_TWO(2), HINT_THREE(3), HINT_FOUR(4), HINT_FIVE(5), HINT_SIX(6), HINT_SEVEN(7), HINT_EIGHT(8), MINE(9);

    private final int index;

    private BlockType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public BlockType after(){
        if(index < 8)
            for(BlockType bt : values()){
                if(bt.index == index + 1)
                    return bt;
            }
        return this;
    }

    public BlockType before(){
        if(index > 0 && index <= 8)
            for(BlockType bt : values()){
                if(bt.index == index - 1)
                    return bt;
            }
        return this;
    }

    public boolean isSpace(){
        return this == SPACE;
    }

    public boolean isMine(){
        return this == MINE;
    }

    public boolean isHint(){
        return !isSpace() && !isMine();
    }

}
