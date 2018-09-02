abstract class Tile extends GraphicsObject{

    Tile(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    abstract boolean getModifier(Ball b);

    abstract int getAscii();

    abstract void update(Ball b, TileAndBallStorage tb);

    abstract Tile clone(int sizeTile);

    static Tile create(int ascii, int sizeTile) {
        Tile[] subClasses = new Tile[]{new Empty(Direction.NORTHSOUTHEASTWEST, 0)};
        for (Tile sc : subClasses) {
            if (sc.getAscii() == ascii) {
                return sc.clone(sizeTile);
            }
        }
        return new Comment(Direction.NORTHSOUTHEASTWEST,sizeTile,ascii);
    }
}
