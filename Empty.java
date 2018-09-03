class Empty extends Tile {
    @Override
    char getAscii() {
        return ' ';
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
    }

    @Override
    public Empty clone(int sizeTile) {
        return new Empty(thisDirection,sizeTile);
    }

    @Override
    String getName() {
        return "Empty.png";
    }

    static Direction[] getAllowedDirections() {
        return new Direction[]{Direction.NORTHSOUTHEASTWEST};
    }
    Empty(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }
}
