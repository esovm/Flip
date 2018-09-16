/*
Copyright 2018 Rouli Freeman

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class GraphicsObjectStorage {
    private ArrayList<ArrayList<Tile>> tiles;
    private ArrayList<Ball> balls;
    private final int tileSize;
    private GraphicsObjectStorage copy = null;
    private boolean isSuspended = false;

    GraphicsObjectStorage(int sizeTile) {
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

    void read(File toRead, Flip flip) {
        swap();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(toRead))) {
                tiles.clear();
                balls.clear();
                boolean toBreak = false;
                boolean toReadBalls = true;
                do {
                    ArrayList<Tile> al = new ArrayList<>();
                    while (true) {
                        int c = br.read();
                        if (c == '\t') {
                            tiles.add(al);
                            toBreak = true;
                            break;
                        } else if (c == -1) {
                            tiles.add(al);
                            balls.add(new Ball(Direction.EAST, 0, -1, 0));
                            toReadBalls = false;
                            toBreak = true;
                            break;
                        } else if (c == '\r') {
                            //Do nothing
                        } else if (c == '\n') {
                            tiles.add(al);
                            break;
                        } else {
                            Tile t = Tile.create((char) c, tileSize);
                            if(t instanceof ControlTerm) {
                                ((ControlTerm) t).setFlip(flip);
                            }
                            al.add(t);
                        }
                    }
                } while (!toBreak);
                if(toReadBalls) {
                	Scanner sc = new Scanner(br);
                	sc.skip("Balls:\\[\\{");
                	do {
                		sc.useDelimiter(",");
                		int x = sc.nextInt();
                		int y = sc.nextInt();
                		int number = sc.nextInt();
                		sc.skip(",");
                		sc.useDelimiter("}");
                		String s = sc.next();
                		Direction d = Direction.getString(s);
                		balls.add(new Ball(d, x, y, number));
                		sc.skip("}");
                		sc.skip("(])|(\\{)");
                	} while (sc.hasNext());
                	sc.close();
                }
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

    void write(File toWrite) {
        swap();
        removeEmpty();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(toWrite));
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
        return new Empty(Direction.NORTHSOUTHEASTWEST);
    }

    synchronized void draw(GraphicsContext gc) {
        for (int a = 0; a < tiles.size(); a++) {
            for (int b = 0; b < tiles.get(a).size(); b++) {
                tiles.get(a).get(b).draw(gc, a, b, tileSize);
            }
        }
        for (Ball ball : balls) {
            ball.draw(gc, tileSize);
        }
    }

    synchronized void update() {
        if(!isSuspended) {
            ArrayList<Ball> ballsTemp = new ArrayList<>(balls);
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

    synchronized void place(GraphicsObject go, int x, int y) {
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
                    tiles.get(x).add(new Empty(Direction.NORTHSOUTHEASTWEST));
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
                tiles.get(x).set(y, new Empty(Direction.NORTHSOUTHEASTWEST));
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
        copy = new GraphicsObjectStorage(tileSize);
        for (int a = startX; a < endX; a++) {
            for (int b = startY; b < endY; b++) {
                if (a < tiles.size()) {
                    if (b < tiles.get(a).size()) {
                        copy.placeTile(tiles.get(a).get(b).clone(), a - startX, b - startY);
                    }
                }
            }
        }
        for (int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            if (b.x >= startX && b.x < endX) {
                if (b.y >= startY && b.y < endY) {
                    Ball ball = b.clone();
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
                placeTile(copy.tiles.get(a).get(b).clone(), startX + a, startY + b);
            }
        }
        for (int i = 0; i < copy.balls.size(); i++) {
            Ball b = copy.balls.get(i).clone();
            b.x += startX;
            b.y += startY;
            balls.add(b);
        }
    }
}
