import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TileAndBallStorage {
    private ArrayList<ArrayList<Tile>> tiles;
    private ArrayList<Ball> balls;
    private int tileSize;
    public void read(String pathName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathName));
            tiles = new ArrayList<>();
            int a = 0;
            boolean twoNewLines = false;
            while(true) {
                ArrayList<Tile> al = new ArrayList<>();
                int b = 0;
                while(true) {
                    int c = br.read();
                    if(c != '\n') {
                        al.add(Tile.create(br.read(), a, b, tileSize));
                        twoNewLines = false;
                    } else {
                        tiles.add(al);
                        break;
                    }
                }
                if(twoNewLines) {
                    break;
                }
                twoNewLines = true;
                a++;
            }
            while(true) {
                if(br.read()=='[') {
                    break;
                }
            }
            Scanner sc = new Scanner(br);
            br.read();
            while(true) {
                sc.useDelimiter(",");
                int x = sc.nextInt();
                int y = sc.nextInt();
                int number = sc.nextInt();
                sc.useDelimiter("}");
                Direction d = Direction.getString(sc.next());
                br.read();
                balls.add(new Ball(d,x,y,tileSize,number));
                if(!sc.hasNext()) {
                    break;
                }
            }
        } catch(IOException e) {
            System.err.println("Warning: Could not read from file.");
            e.printStackTrace();
        }
        removeEmpty();
    }
    public void write(String pathName) {
        removeEmpty();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathName));
            for(int a = 0; a < tiles.size(); a++) {
                for(int b = 0; b < tiles.size(); b++) {
                    bw.write(tiles.get(a).get(b).getAscii());
                }
                bw.newLine();
            }
            bw.newLine();
            bw.write("Balls:[");
            for(int i = 0; i < balls.size(); i++) {
                Ball b = balls.get(i);
                bw.write("{"+b.x+","+b.y+","+b.number+","+b.thisDirection.toString()+"}");
            }
            bw.write("]");
            bw.close();
        } catch(IOException e) {
            System.err.println("Warning: Could not write to file.");
            e.printStackTrace();
        }
    }
    private void removeEmpty() {
        for(int i = 0; i < tiles.size(); i++) {
            while(true) {
                if(tiles.get(i).get(tiles.get(i).size()-1) instanceof Empty) {
                    tiles.get(i).remove(tiles.get(i).size()-1);
                } else {
                    break;
                }
            }
        }
        while(true) {
            if(tiles.get(tiles.size()-1).size()==0) {
                tiles.remove(tiles.size()-1);
            } else {
                break;
            }
        }
    }
    public Tile getTileAtPos(int x, int y) {
        if(tiles.size()>x) {
            if(tiles.get(x).size()>y) {
                return tiles.get(x).get(y);
            }
        }
        return new Empty(Direction.NORTHSOUTHEASTWEST,x,y,tileSize);
    }
    public void draw(GraphicsContext gc) {
        for(int a = 0; a < tiles.size(); a++) {
            for(int b = 0; b < tiles.get(a).size(); b++) {
                tiles.get(a).get(b).draw(gc);
            }
        }
        for(int i = 0; i < balls.size(); i++) {
            balls.get(i).draw(gc);
        }
    }
    public void update() {
        for(int i = 0; i < balls.size(); i++) {
            balls.get(i).update(this);
        }
    }
    public void place(int x, int y, GraphicsObject go) {
        if(go instanceof Ball) {
            placeBall((Ball) go);
        }
        if(go instanceof Tile) {
            placeTile(x,y,(Tile) go);
        }
    }
    private void placeBall(Ball b) {
        balls.add(b);
    }
    private void placeTile(int x, int y, Tile t) {
        if(tiles.size()>x) {
            if(tiles.get(x).size()>y) {
                tiles.get(x).set(y,t);
            } else {
                for(int i = 0; i < y - tiles.get(x).size() + 1; i++) {
                    tiles.get(x).add(new Empty(Direction.NORTHSOUTHEASTWEST,x,i,tileSize));
                }
                placeTile(x,y,t);
            }
        } else {
            for(int i = 0; i < x - tiles.size() + 1; i++) {
                tiles.add(new ArrayList<>());
            }
            placeTile(x,y,t);
        }
    }
    public void remove(int x, int y) {
        removeBall(x,y);
        removeTile(x,y);
        removeEmpty();
    }
    private void removeBall(int x, int y) {
        balls.removeIf(ball -> ball.x == x && ball.y == y);
    }
    private void removeTile(int x, int y) {
        tiles.get(x).set(y, new Empty(Direction.NORTHSOUTHEASTWEST, x, y, tileSize));
    }
    public void removeRect(int startX, int startY, int endX, int endY) {
        for(int a = startX; a < endX; a++) {
            for(int b = startY; b < endY; b++) {
                remove(a,b);
            }
        }
    }
    public void removeCol(int whichCol) {
        tiles.remove(whichCol);
    }
    public void removeRow(int whichRow) {
        for(int i = 0; i < tiles.size(); i++) {
            tiles.get(i).remove(whichRow);
        }
    }
    public void deleteRect(int startX, int startY, int endX, int endY) {
        int a = startX;
        while(true) {
            int b = startY;
            while(true) {
                tiles.get(a).remove(startY);
                b++;
                if(b == endY) {
                    break;
                }
            }
            a++;
            if(a == endX) {
                break;
            }
        }
    }
}
