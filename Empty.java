class Empty extends Tile {
    @Override
    int getAscii() {
        return ' ';
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
    }

    @Override
    Tile clone(int posX, int posY, int sizeTile) {
        return new Empty(thisDirection,posX,posY,sizeTile);
    }

    @Override
    String getName() {
        return "Empty.png";
    }

    static Direction[] getAllowedDirections() {
        return new Direction[]{Direction.NORTHSOUTHEASTWEST};
    }
    Empty(Direction whichDirection, int posX, int posY, int sizeTile) {
        super(whichDirection, posX, posY, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }
}
