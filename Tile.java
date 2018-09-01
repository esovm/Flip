public abstract class Tile extends GraphicsObject{
    public static final Tile[] subClasses = new Tile[]{new Empty(Direction.NORTHSOUTHEASTWEST,0,0,0)};

    public Tile(Direction whichDirection, int posX, int posY, int sizeTile) {
        super(whichDirection, posX, posY, sizeTile);
    }

    public abstract boolean getModifier(Ball b);

    public abstract int getAscii();

    public abstract void update(Ball b, TileAndBallStorage tb);

    public abstract Tile clone(int posX, int posY, int sizeTile);

    public static Tile create(int ascii,int x, int y, int sizeTile) {
        for(int i = 0; i < subClasses.length; i++) {
            Tile sc = subClasses[i];
            if(sc.getAscii()==ascii) {
                return sc.clone(x,y,sizeTile);
            }
        }
        return new Comment(Direction.NORTHSOUTHEASTWEST,x,y,sizeTile,ascii);
    }
}
