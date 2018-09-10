public class Random extends Tile {
    static boolean random = false;
    Random(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return random;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        Direction[] randomList = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        b.thisDirection = randomList[(int) Math.floor(Math.random()*randomList.length)];
    }

    @Override
    String getName() {
        return "Random.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new Random(thisDirection, tileSize);
    }

    @Override
    char getAscii() {
        return '%';
    }
}
