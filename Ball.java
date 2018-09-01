import java.util.ArrayList;

public class Ball extends GraphicsObject {
    int number;
    public void update(TileAndBallStorage tb) {
        if(thisDirection == Direction.NORTH) {
            y--;
        }
        if(thisDirection == Direction.SOUTH) {
            y++;
        }
        if(thisDirection == Direction.EAST) {
            x++;
        }
        if(thisDirection == Direction.WEST) {
            x--;
        }
        tb.getTileAtPos(x,y).update(this,tb);
    }

    @Override
    public String getName() {
        if(thisDirection == Direction.NORTH) {
            return "BallUp.jpg";
        }
        if(thisDirection == Direction.SOUTH) {
            return "BallDown.jpg";
        }
        if(thisDirection == Direction.EAST) {
            return "BallRight.jpg";
        }
        if(thisDirection == Direction.WEST) {
            return "BallLeft.jpg";
        }
        throw new Error("Warning: This ball is not facing north or south or east or west");
    }

    public static Direction[] getAllowedDirections() {
        return new Direction[]{Direction.NORTH,Direction.SOUTH,Direction.EAST, Direction.WEST};
    }

    public Ball(Direction whichDirection, int posX, int posY, int sizeTile, int thisNumber) {
        super(whichDirection,posX,posY,sizeTile);
        number = thisNumber;
    }
}
