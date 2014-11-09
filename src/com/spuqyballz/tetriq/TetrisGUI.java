package com.spuqyballz.tetriq;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class TetrisGUI extends JFrame {
	private int unit;
	
	private JLayeredPane container;
	private JPanel gameOverPnl;
	private Tetrimino buffer;
	private Tetrimino dropper;
	private Tetrimino next;
	private Playfield playfield;
	private TetrisEngine e;
	
	private JLayeredPane playzone;
	private JLabel score;
	private JLabel scorev;
	private JLabel linesv;
	private JLabel speedv;
	
	public TetrisGUI(Tetrimino buffer, Tetrimino dropper, Tetrimino next,Playfield playfield, TetrisEngine e) {
		this.buffer = buffer;
		this.dropper = dropper;
		this.next = next;
		this.playfield = playfield;
		this.unit = playfield.getuSize();
		this.e =e;
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, (playfield.getxSize()+buffer.getxSize()+3)*unit, (playfield.getySize()+4)*unit);
		container = new JLayeredPane();
		container.setLayout(null);
		//container.setOpaque(false);
		setContentPane(container);
		container.setVisible(true);
		
		
		/*
		 * The control panel
		 */
		
		buffer.setBounds(unit*(playfield.getxSize()+2), unit*17,unit*buffer.getxSize(),unit*buffer.getySize());
		container.add(buffer, JLayeredPane.DEFAULT_LAYER);
		buffer.setBorder(BorderFactory.createLineBorder(Color.black));
		buffer.setBackground(Color.DARK_GRAY);
		
		// Create next block window
		next.setBounds(unit*(playfield.getxSize()+2), unit,unit*next.getxSize(),unit*next.getySize());
		container.add(next);
		next.setBorder(BorderFactory.createLineBorder(Color.black));
		next.setBackground(Color.DARK_GRAY);
		
		score = new JLabel("score");
		score.setBounds(unit*(playfield.getxSize()+2), unit*(buffer.getySize()+2),unit*4,unit);
		container.add(score);
		
		scorev = new JLabel("");
		scorev.setBounds(unit*(playfield.getxSize()+2), unit*(buffer.getySize()+3),unit*4,unit);
		container.add(scorev);
		
		JLabel lines = new JLabel("lines");
		lines.setBounds(unit*(playfield.getxSize()+2), unit*(buffer.getySize()+5),unit*4,unit);
		container.add(lines);
		
		linesv = new JLabel("");
		linesv.setBounds(unit*(playfield.getxSize()+2), unit*(buffer.getySize()+6),unit*4,unit);
		container.add(linesv);

		/*
		 * The playground
		 */
		
		playzone = new JLayeredPane();
		playzone.setBounds(unit,unit,unit*playfield.getxSize(), unit*playfield.getySize());
		playzone.setBorder(BorderFactory.createLineBorder(Color.black));
		container.add(playzone);
		
		playfield.setBounds(0,0,unit*playfield.getxSize(), unit*playfield.getySize());
		playzone.add(playfield, JLayeredPane.DEFAULT_LAYER);
		playzone.setLayout(null);
		playfield.setBackground(Color.DARK_GRAY);
		dropper.setBounds((playfield.getxSize()/2-dropper.getxSize())*unit,(playfield.getySize()-dropper.getySize())*unit, unit*dropper.getxSize(), unit*dropper.getySize());
		dropper.setOpaque(false);
		playzone.add(dropper, JLayeredPane.DRAG_LAYER);
		
		refresh();
	}
	
	public void refresh(){
		linesv.setText(""+e.getfLines());
		scorev.setText(""+e.getScore());
		dropper.setLocation(dropper.getPosX()*unit, dropper.getPosY()*unit);
		dropper.repaint();
		playfield.repaint();
		buffer.repaint();
		next.repaint();
	}
	
	public void gameOver(){
		gameOverPnl = new JPanel();
		gameOverPnl.setLayout(null);
		gameOverPnl.setBounds(0, 0, (playfield.getxSize()+buffer.getxSize()+3)*unit, (playfield.getySize()+4)*unit);
		
		JLabel gameOverLbl = new JLabel("<html><h1><font color='lime'>Game Over</font></h1></html>");
		gameOverLbl.setBounds(unit*(playfield.getxSize()/2-2), unit*(playfield.getySize()/2-7),unit*12,unit*10);
		gameOverPnl.add(gameOverLbl);
		
		JLabel gameOverScore = new JLabel("<html><h2><font color='lime'>Score: "+this.scorev.getText()+"</font></h2></html>");
		gameOverScore.setBounds(unit*(playfield.getxSize()/2), unit*(playfield.getySize()/2-5),unit*12,unit*10);
		gameOverPnl.add(gameOverScore);
		
		container.add(gameOverPnl,0);
		gameOverPnl.setBackground( new Color(0, 0, 0, 130) );
		gameOverPnl.setVisible(true);
	}

}
