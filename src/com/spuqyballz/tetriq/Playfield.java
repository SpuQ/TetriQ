package com.spuqyballz.tetriq;

public class Playfield extends Qmatrix {

	public Playfield(){
		super(12,20);
	}
	
	// remove full lines
	public int removeLines(){
		int j, linesRemoved=0;
		for(j=super.getySize()-1;j>=0;j--){
			if(super.rowIsFull(j)){
				shiftRowsOneUp(j);
				linesRemoved++;
			}
		}
		
		return linesRemoved;
	}
	
	public void shiftRowsOneUp(int base){
		int i,j;
		
		for(j=base;j>1;j--){
			for(i=0;i<super.getxSize();i++){
				super.setElement(i, j, super.getElement(i, j-1));
			}
		}
		
		// add new blank line on bottom
		for(i=0;i<super.getxSize();i++){
			super.setElement(i, 0, super.geteEmpty());
		}
	}
	
}
