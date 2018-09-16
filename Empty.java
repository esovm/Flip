class Empty extends Tile {
    @Override
    char getAscii() {
        return ' ';
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
    }

    @Override
    public Empty clone() {
        return new Empty(thisDirection);
    }

    @Override
    String getName() {
        return "Empty.png";
    }
    Empty(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }
}
