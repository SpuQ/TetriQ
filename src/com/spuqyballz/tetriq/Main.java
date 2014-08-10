package com.spuqyballz.tetriq;

public class Main {
	public static void main(String[] args) {
		TetrisEngine t = new TetrisEngine();
		Tetrimino blub = new Tetrimino();
		blub.setPosX(5);
		blub.setPosY(8);
		t.uniteWithField(blub);
	}
}
