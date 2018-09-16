public class Random extends Tile {
    static boolean random = false;
    Random(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return random;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        Direction[] randomList = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        b.thisDirection = randomList[(int) Math.floor(Math.random()*randomList.length)];
    }

    @Override
    String getName() {
        return "Random.png";
    }

    @Override
    public Tile clone() {
        return new Random(thisDirection);
    }

    @Override
    char getAscii() {
        return '%';
    }
}
