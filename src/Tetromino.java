package src;

import java.lang.Math;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tetromino{
	public static String[] Blocks = {"I", "J", "L", "O", "Z", "T", "S"};

	public Rectangle[] components;
	public Position pos;
	public Color col;

	public Tetromino(){
		String nextBlock = Blocks[(int)(Math.random()*7)];
		pos = new Position(Board.getDimensions()[0]/2, 0);
		components = new Rectangle[4];


		components[0] = new Rectangle(0, 0, 0, 0);
		switch (nextBlock){
			case "I":
				col = Color.CYAN;
				components[1] = new Rectangle(0, 1, 0, 0);
				components[2] = new Rectangle(0, 2, 0, 0);
				components[3] = new Rectangle(0, 3, 0, 0);
				break;
			case "J":
				col = Color.DARKORANGE;
				components[1] = new Rectangle(-1, 0, 0, 0);
				components[2] = new Rectangle(0, 1, 0, 0);
				components[3] = new Rectangle(0, 2, 0, 0);
				break;
			case "L":
				col = Color.BLUE;
				components[1] = new Rectangle(1, 0, 0, 0);
				components[2] = new Rectangle(0, 1, 0, 0);
				components[3] = new Rectangle(0, 2, 0, 0);
				break;
			case "O":
				col = Color.GOLD;
				components[1] = new Rectangle(1, 0, 0, 0);
				components[2] = new Rectangle(1, 1, 0, 0);
				components[3] = new Rectangle(0, 1, 0, 0);
				break;
			case "S":
				col = Color.RED;
				components[1] = new Rectangle(-1, 1, 0, 0);
				components[2] = new Rectangle(0, 1, 0, 0);
				components[3] = new Rectangle(1, 0, 0, 0);
				break;
			case "T":
				col = Color.DARKORCHID;
				components[1] = new Rectangle(-1, 0, 0, 0);
				components[2] = new Rectangle(0, -1, 0, 0);
				components[3] = new Rectangle(1, 0, 0, 0);
				break;
			case "Z":
				col = Color.LAWNGREEN;
				components[1] = new Rectangle(0, -1, 0, 0);
				components[2] = new Rectangle(-1, -1, 0, 0);
				components[3] = new Rectangle(1, 0, 0, 0);
				break;
		}

		for(Rectangle component : components){
			component.setFill(col);
			component.setStroke(Color.BLACK);
			component.setStrokeWidth(10);
		}
	}

	public Position[] getLocations(){
		Position[] posArr = new Position[4];

		for(int i = 0; i < components.length; i++) {
			posArr[i] = new Position((int) (components[i].getX() + pos.x), (int) (components[i].getY() + pos.y));
			//System.out.println("x: " + posArr[i].x + ", y: " + posArr[i].y);
		}

		return posArr;
	}

	public void addToArray(Rectangle[][] board){
		for(Rectangle component : components) {
			int x = (int) (pos.x + component.getX());
			int y = (int) (pos.y + component.getY());

			if(x >= 0 && y >= 0 && y < board.length && x < board[0].length)
				board[y][x] = component;
		}
	}

	public void putInBounds(Rectangle[][] arr){
		boolean inBounds = false;

		while(!inBounds){
			inBounds = true;

			for(Rectangle component : components){
				int x = (int)(pos.x + component.getX());
				int y = (int)(pos.y + component.getY());

				if(!(x >= 0 && y >= 0 && y < arr.length && x < arr[0].length)){
					inBounds = false;
					move(((x >= 0 && x < arr[0].length) ? 0 : 1)*((x > 0 ? arr[0].length-1 : 0) - x), ((y >= 0 && y < arr.length) ? 0 : 1)*((y > 0 ? arr.length-1 : 0) - y));
					break;
				}
			}
		}
	}

	public boolean canMoveToPosition(Rectangle [][] brd, Position p){
		for(Rectangle component : components){
			int x = (int)(p.x + component.getX());
			int y = (int)(p.y + component.getY());

			if(!(x >= 0 && x < brd[0].length && y >= 0 && y < brd.length) || brd[y][x] != null)
				return  false;
		}

		return true;
	}

	public boolean canRotate(Rectangle[][] brd, Position p){
		for (Rectangle component : components){
			int x = (int)(p.x + component.getY());
			int y = (int)(p.y - component.getX());

			if(y >= 0 && y < brd.length && x >= 0 && x < brd[0].length) {
				if (brd[y][x] != null)
					return false;
			}
		}

		return true;
	}

	public void move(int x, int y){ //Relative
		pos.x += x;
		pos.y += y;
	}

	public void rotate(){
		for(Rectangle component : components){
			double x = component.getX();
			component.setX(component.getY());
			component.setY(-x);
		}
	}
}