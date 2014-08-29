package com.spuqyballz.tetriq;

import java.util.Random;

public class Tetrimino extends Qmatrix {
	private int shape;
	private int posX=0;
	private int posY=0;
	
	private int[][][] shapes= {
			{{-1,-1,-1,-1},{-1,-1,-1,-1},{1,1,1,1},{-1,-1,-1,-1}}, // I-shape
			{{-1,-1,-1,-1},{-1,1,1,-1},{-1,1,1,-1},{-1,-1,-1,-1}}, // O-shape
			{{-1,-1,-1,-1},{-1,1,-1,-1},{1,1,1,-1},{-1,-1,-1,-1}}, // T-shape
			{{-1,-1,-1,-1},{-1,1,1,-1},{1,1,-1,-1},{-1,-1,-1,-1}}, // Z-shape
			{{-1,-1,-1,-1},{1,1,-1,-1},{-1,1,1,-1},{-1,-1,-1,-1}}, // S-shape
			{{-1,1,-1,-1},{-1,1,-1,-1},{-1,1,1,-1},{-1,-1,-1,-1}}, // L-shape
			{{-1,-1,1,-1},{-1,-1,1,-1},{-1,1,1,-1},{-1,-1,-1,-1}}  // J-shape
	};
	
	public Tetrimino() {
		super(4, 4);
		this.setRandomTetrimino();
	}
	
	/*
	 * Stuff to shape the tetrimino
	 */
	public void setRandomTetrimino(){
		int shape = randInt(0,6);
		
		super.setMatrix(this.setShape(shape));
		super.colorizeMatrix(shape);
	}
	
	/*
	 * Stuff to shape the tetrimino
	 * With added shape so we can define what our next shape will be
	 */
	public void setTetrimino(int shape){
		super.setMatrix(this.setShape(shape));
		super.colorizeMatrix(shape);
	}
	
	public int[][] setShape(int shape){
		this.shape = shape;
		return this.shapes[this.shape];
	}
	
	// Return int representation of shape
	public int getShape(){
		return this.shape;
	}
	

	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

	/*
	 * stuff for the abstract positioning
	 */
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}
