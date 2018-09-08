import javafx.scene.canvas.GraphicsContext;

class Ball extends GraphicsObject {
    int number;
    int x;
    int y;

    public Ball clone(int sizeTile) {
        return new Ball(thisDirection, x, y, sizeTile, number);
    }

    @Override
    char getAscii() {
        if (thisDirection == Direction.NORTH) {
            return '8';
        }
        if (thisDirection == Direction.SOUTH) {
            return '2';
        }
        if (thisDirection == Direction.EAST) {
            return '6';
        }
        if (thisDirection == Direction.WEST) {
            return '4';
        }
        return ' ';
    }

    void draw(GraphicsContext gc) {
        gc.drawImage(findImage(), x * tileSize, y * tileSize, tileSize, tileSize);
    }

    void update(TileAndBallStorage tb) {
        if (thisDirection == Direction.NORTH) {
            y--;
        }
        if (thisDirection == Direction.SOUTH) {
            y++;
        }
        if (thisDirection == Direction.EAST) {
            x++;
        }
        if (thisDirection == Direction.WEST) {
            x--;
        }
        tb.getTileAtPos(x, y).update(this, tb);
    }

    @Override
    String getName() {
        if (thisDirection == Direction.NORTH) {
            return "BallUp.png";
        }
        if (thisDirection == Direction.SOUTH) {
            return "BallDown.png";
        }
        if (thisDirection == Direction.EAST) {
            return "BallRight.png";
        }
        if (thisDirection == Direction.WEST) {
            return "BallLeft.png";
        }
        throw new Error("Warning: This ball is not facing north or south or east or west");
    }

    static Direction[] getAllowedDirections() {
        return new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    }

    Ball(Direction whichDirection, int posX, int posY, int sizeTile, int thisNumber) {
        super(whichDirection, sizeTile);
        x = posX;
        y = posY;
        number = thisNumber;
    }
}
