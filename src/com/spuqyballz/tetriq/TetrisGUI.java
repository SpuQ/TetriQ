package com.spuqyballz.tetriq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TetrisGUI extends JFrame {
	private int unit;
	
	private JPanel container;
	private Tetrimino buffer;
	private Tetrimino dropper;
	private Playfield playfield;
	private TetrisEngine e;
	
	private JLayeredPane playzone;
	private JLabel score;
	private JLabel scorev;
	private JLabel linesv;
	private JLabel speedv;
	
	public TetrisGUI(Tetrimino buffer, Tetrimino dropper, Playfield playfield, TetrisEngine e) {
		this.buffer = buffer;
		this.dropper = dropper;
		this.playfield = playfield;
		this.unit = playfield.getuSize();
		this.e =e;

		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, (playfield.getxSize()+buffer.getxSize()+3)*unit, (playfield.getySize()+4)*unit);
		container = new JPanel();
		container.setVisible(true);
		container.setLayout(null);
		setContentPane(container);
		
		/*
		 * The control panel
		 */
		
		buffer.setBounds(unit*(playfield.getxSize()+2), unit,unit*buffer.getxSize(),unit*buffer.getySize());
		container.add(buffer);
		buffer.setBorder(BorderFactory.createLineBorder(Color.black));
		buffer.setBackground(Color.DARK_GRAY);
		
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

		JLabel speed = new JLabel("clock");
		speed.setBounds(unit*(playfield.getxSize()+2), unit*(buffer.getySize()+8),unit*4,unit);
		container.add(speed);

		speedv = new JLabel("");
		speedv.setBounds(unit*(playfield.getxSize()+2), unit*(buffer.getySize()+9),unit*4,unit);
		container.add(speedv);
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
		speedv.setText(e.getSpeed()+"ms");
		dropper.setLocation(dropper.getPosX()*unit, dropper.getPosY()*unit);
		dropper.repaint();
		playfield.repaint();
		buffer.repaint();
	}

}
