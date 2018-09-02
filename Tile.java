abstract class Tile extends GraphicsObject{

    Tile(Direction whichDirection, int posX, int posY, int sizeTile) {
        super(whichDirection, posX, posY, sizeTile);
    }

    abstract boolean getModifier(Ball b);

    abstract int getAscii();

    abstract void update(Ball b, TileAndBallStorage tb);

    abstract Tile clone(int posX, int posY, int sizeTile);

    static Tile create(int ascii,int x, int y, int sizeTile) {
        Tile[] subClasses = new Tile[]{new Empty(Direction.NORTHSOUTHEASTWEST, 0, 0, 0)};
        for (Tile sc : subClasses) {
            if (sc.getAscii() == ascii) {
                return sc.clone(x, y, sizeTile);
            }
        }
        return new Comment(Direction.NORTHSOUTHEASTWEST,x,y,sizeTile,ascii);
    }
}
