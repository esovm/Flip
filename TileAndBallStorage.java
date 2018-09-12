import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class TileAndBallStorage {
    private ArrayList<ArrayList<Tile>> tiles;
    private ArrayList<Ball> balls;
    private int tileSize;
    private TileAndBallStorage copy = null;
    private boolean isSuspended = false;

    TileAndBallStorage(int sizeTile) {
        tiles = new ArrayList<>();
        balls = new ArrayList<>();
        tileSize = sizeTile;
    }

    void suspend() {
        isSuspended = true;
    }

    void resume() {
        isSuspended = false;
    }

    void read(File f, Flip flip) {
        swap();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                tiles = new ArrayList<>();
                balls = new ArrayList<>();
                boolean toBreak = false;
                do {
                    ArrayList<Tile> al = new ArrayList<>();
                    while (true) {
                        int c = br.read();
                        if (c == '\t') {
                            tiles.add(al);
                            toBreak = true;
                            break;
                        }
                        if (c == -1) {
                            tiles.add(al);
                            swap();
                            removeEmpty();
                            balls.add(new Ball(Direction.EAST, -1, 0, tileSize, 0));
                            return;
                        }
                        if (c != '\r') {
                            if (c != '\n') {
                                Tile t = Tile.create((char) c, tileSize);
                                if(t instanceof ControlTerm) {
                                    ((ControlTerm) t).setFlip(flip);
                                }
                                al.add(t);
                            } else {
                                tiles.add(al);
                                break;
                            }
                        }
                    }
                } while (!toBreak);
                while (true) {
                    if (br.read() == '{') {
                        break;
                    }
                }
                Scanner sc = new Scanner(br);
                do {
                    sc.useDelimiter(",");
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    int number = sc.nextInt();
                    sc.skip(",");
                    sc.useDelimiter("}");
                    String s = sc.next();
                    Direction d = Direction.getString(s);
                    balls.add(new Ball(d, x, y, tileSize, number));
                    sc.skip("}");
                    sc.skip("(])|(\\{)");
                } while (sc.hasNext());
                sc.close();
            }
        } catch (IOException e) {
            Flip.output.appendText("Warning: Could not read from file.");
            e.printStackTrace();
        }
        swap();
        removeEmpty();
    }

    private void swap() {
        ArrayList<ArrayList<Tile>> oldTiles = tiles;
        tiles = new ArrayList<>();
        for(int a = 0; a < oldTiles.size(); a++) {
            for(int b = 0; b < oldTiles.get(a).size(); b++) {
                placeTile(oldTiles.get(a).get(b), b, a);
            }
        }
        ArrayList<Ball> oldBalls = balls;
        balls = new ArrayList<>();
        for (Ball b : oldBalls) {
            int temp = b.x;
            b.x = b.y;
            b.y = temp;
            balls.add(b);
        }
    }

    void write(File f) {
        swap();
        removeEmpty();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for (ArrayList<Tile> tile : tiles) {
                for (Tile aTile : tile) {
                    bw.write(aTile.getAscii());
                }
                bw.newLine();
            }
            if(balls.size() > 0) {
                bw.write('\t');
                bw.write("Balls:[");
                for (Ball b : balls) {
                    bw.write("{" + b.x + "," + b.y + "," + b.number + "," + b.thisDirection.toString() + "}");
                }
                bw.write("]");
            }
            bw.close();
        } catch (IOException e) {
            Flip.output.appendText("Warning: Could not write to file.");
            e.printStackTrace();
        }
        swap();
    }

    private void removeEmpty() {
        for (ArrayList<Tile> tile : tiles) {
            for (int i = tile.size() - 1; i >= 0; i--) {
                if (tile.get(tile.size() - 1) instanceof Empty) {
                    tile.remove(tile.size() - 1);
                } else {
                    break;
                }
            }
        }
        for (int i = tiles.size() - 1; i >= 0; i--) {
            if (tiles.get(tiles.size() - 1).size() == 0) {
                tiles.remove(tiles.size() - 1);
            }
        }
    }

    Tile getTileAtPos(int x, int y) {
        if (tiles.size() > x && x >= 0) {
            if (tiles.get(x).size() > y && y >= 0) {
                return tiles.get(x).get(y);
            }
        }
        return new Empty(Direction.NORTHSOUTHEASTWEST, tileSize);
    }

    void draw(GraphicsContext gc) {
        for (int a = 0; a < tiles.size(); a++) {
            for (int b = 0; b < tiles.get(a).size(); b++) {
                tiles.get(a).get(b).draw(gc, a, b);
            }
        }
        for (Ball ball : balls) {
            ball.draw(gc);
        }
    }

    synchronized void update() {
        if(!isSuspended) {
            ArrayList<Ball> ballsTemp = new ArrayList<>();
            ballsTemp.addAll(balls);
            for (Ball ball : ballsTemp) {
                ball.update(this);
            }
        }
    }

    void removeExactBall(Ball b) {
        int toRemove = 0;
        for(int i = 0; i < balls.size(); i++) {
            if(balls.get(i) == b) {
                toRemove = i;
            }
        }
        balls.remove(toRemove);
    }

    void place(GraphicsObject go, int x, int y) {
        if (go instanceof Empty) {
            remove(x, y);
        } else {
            if (go instanceof Ball) {
                Ball b = (Ball) go;
                b.x = x;
                b.y = y;
                placeBall(b);
            }
            if (go instanceof Tile) {
                placeTile((Tile) go, x, y);
            }
        }
    }

    private void placeBall(Ball b) {
        balls.add(b);
    }

    private void placeTile(Tile t, int x, int y) {
        if (tiles.size() > x) {
            if (tiles.get(x).size() > y) {
                tiles.get(x).set(y, t);
            } else {
                for (int i = 0; i < y - tiles.get(x).size() + 1; i++) {
                    tiles.get(x).add(new Empty(Direction.NORTHSOUTHEASTWEST, tileSize));
                }
                placeTile(t, x, y);
            }
        } else {
            for (int i = 0; i < x - tiles.size() + 1; i++) {
                tiles.add(new ArrayList<>());
            }
            placeTile(t, x, y);
        }
    }

    void remove(int x, int y) {
        removeBall(x, y);
        removeTile(x, y);
    }

    private void removeBall(int x, int y) {
        balls.removeIf(ball -> ball.x == x && ball.y == y);
    }

    private void removeTile(int x, int y) {
        if (x < tiles.size()) {
            if (y < tiles.get(x).size()) {
                tiles.get(x).set(y, new Empty(Direction.NORTHSOUTHEASTWEST, tileSize));
            }
        }
    }

    void removeRect(int startX, int startY, int endX, int endY) {
        for (int a = startX; a < endX; a++) {
            for (int b = startY; b < endY; b++) {
                remove(a, b);
            }
        }
    }

    void removeCol(int whichCol) {
        if (whichCol < tiles.size()) {
            tiles.remove(whichCol);
        }
    }

    void removeRow(int whichRow) {
        for (ArrayList<Tile> tile : tiles) {
            if (whichRow < tile.size()) {
                tile.remove(whichRow);
            }
        }
    }

    void deleteRect(int startX, int startY, int endX, int endY) {
        for (int a = startX; a < endX; a++) {
            for (int b = startY; b < endY; b++) {
                if (a < tiles.size()) {
                    if (startY < tiles.get(a).size()) {
                        tiles.get(a).remove(startY);
                    }
                }
            }
        }
    }

    void copyRect(int startX, int startY, int endX, int endY) {
        copy = new TileAndBallStorage(tileSize);
        for (int a = startX; a < endX; a++) {
            for (int b = startY; b < endY; b++) {
                if (a < tiles.size()) {
                    if (b < tiles.get(a).size()) {
                        copy.placeTile(tiles.get(a).get(b).clone(tileSize), a - startX, b - startY);
                    }
                }
            }
        }
        for (int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            if (b.x >= startX && b.x < endX) {
                if (b.y >= startY && b.y < endY) {
                    Ball ball = b.clone(tileSize);
                    ball.x -= startX;
                    ball.y -= startY;
                    copy.balls.add(ball);
                }
            }
        }
    }

    void pasteRect(int startX, int startY) {
        for (int a = 0; a < copy.tiles.size(); a++) {
            for (int b = 0; b < copy.tiles.get(a).size(); b++) {
                placeTile(copy.tiles.get(a).get(b).clone(tileSize), startX + a, startY + b);
            }
        }
        for (int i = 0; i < copy.balls.size(); i++) {
            Ball b = copy.balls.get(i).clone(tileSize);
            b.x += startX;
            b.y += startY;
            balls.add(b);
        }
    }
}
