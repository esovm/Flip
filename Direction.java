public enum Direction {
    NORTH, SOUTH, WEST, EAST, NORTHSOUTH, EASTWEST, NORTHSOUTHEASTWEST;

    @Override
    public String toString() {
        return name();
    }

    public static Direction getString(String s) {
        Direction d = NORTH;
        if(s.equals(d.toString())) {
            return d;
        }
        d = SOUTH;
        if(s.equals(d.toString())) {
            return d;
        }
        d = EAST;
        if(s.equals(d.toString())) {
            return d;
        }
        d = WEST;
        if(s.equals(d.toString())) {
            return d;
        }
        d = NORTHSOUTH;
        if(s.equals(d.toString())) {
            return d;
        }
        d = EASTWEST;
        if(s.equals(d.toString())) {
            return d;
        }
        return Direction.NORTHSOUTHEASTWEST;
    }
}
