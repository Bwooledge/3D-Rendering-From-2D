import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.TextArea;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

public class Simulation extends JPanel implements Runnable
{
	//handle pausing of simulation
	private static int simulation_counter = 0;
	private static int simulation_delay = 10;
	private static boolean paused = true;
	
	//frame rate stuff
	public static final int BASE_FRAME_RATE = 75;
	public static final int FRAME_RATE = 75;
	
	//background and lighting
	public static Camera cam;
	private static double[] lighting_direction = new double[] {-0.5, 0.2, -Math.sqrt(0.35)};
	private static Color background_color = new Color(15, 15, 15);

	//Constructor for Main
	public Simulation()
	{		
		setVisible(true);
		
		//camera
		cam = new Camera(new double[] {0, -20, -10}, new double[] {90, 0});
		GraphicsRunner.cam = cam;
		
		//create objects
		/*
		SCENE 1
		Color[][] colors = new Color[][] {
			{Color.BLACK, Color.ORANGE, Color.WHITE, Color.CYAN, Color.MAGENTA, Color.RED}, 
			{Color.PINK, Color.RED, Color.BLUE, Color.GREEN, Color.GRAY, Color.YELLOW}};
		Color[][] colors2 = new Color[][] {
			{new Color(113, 29, 176), new Color(194, 18, 146), new Color(113, 29, 176), new Color(194, 18, 146), new Color(239, 64, 64), new Color(255, 167, 50)}, 
			{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE}};
		Color[][] colors3 = new Color[][] {
			{new Color(155, 184, 205), new Color(255, 247, 212), new Color(238, 199, 89), new Color(177, 195, 129), new Color(177, 195, 129), new Color(155, 184, 205)}, 
			{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK}};
		Color[][] colors4 = new Color[][] {
			{new Color(61, 48, 162), new Color(61, 48, 162), new Color(255, 163, 60), new Color(255, 163, 60), new Color(177, 94, 255), new Color(255, 251, 115)}, 
			{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK}};
		Artist.addShape(new Cube(200, new double[]{0, 0, 400}, new double[] {0, 0, 0}, 1, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0.02, 0}, new double[]{0, 0, 0}, colors, 4));
		Artist.addShape(new Cube(100, new double[]{0, 300, 0}, new double[] {0, 45, 45}, 1, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, -0.04, 0}, new double[]{0, 0, 0}, Color.WHITE, Color.BLACK, 2));
		Artist.addShape(new Cube(150, new double[]{500, 100, 200}, new double[] {0, 0, 0}, 1, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0.03, 0.02, -0.025}, new double[]{0, 0, 0}, Color.BLACK, Color.WHITE, 2));
		Artist.addShape(new Cube(50, new double[]{-100, -50, -300}, new double[] {0, 0, 0}, 1, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0.08}, new double[]{0, 0, 0}, Color.WHITE, Color.BLACK, 2));
		Artist.addShape(new Cube(400, new double[]{250, 0, -500}, new double[] {0, 50, 0}, 1, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0.01, 0, 0}, new double[]{0, 0, 0}, colors2, 2));
		Artist.addShape(new Cube(200, new double[]{50, -400, 50}, new double[] {121, -16, 43}, 1, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0.00001, -0.04, 0.0321347}, new double[]{0, 0, 0}, Color.ORANGE, Color.ORANGE, 1));
		Artist.addShape(new Cube(125, new double[]{-350, -50, 50}, new double[] {20, 0, 20}, 1, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0.01, 0.01, 0.01}, new double[]{0, 0, 0}, new Color(144, 3, 252), Color.BLACK, 8));
		*/
		
		/*
		SCENE 2
		Color[][] clrs = new Color[][] {
			{new Color(144, 3, 252), new Color(144, 3, 252), new Color(144, 3, 252), new Color(144, 3, 252), Color.BLACK, Color.BLACK}, 
			{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, new Color(144, 3, 252), new Color(144, 3, 252)}};
		Artist.addShape(new RectangularPrism(1000, 100, 1000, new double[]{0, -600, 0}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, Color.WHITE, Color.BLACK, 2));
		Artist.addShape(new RectangularPrism(100, 500, 1000, new double[]{-550, -300, 0}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, Color.WHITE, Color.BLACK, 2));
		Artist.addShape(new RectangularPrism(1000, 500, 100, new double[]{0, -300, 550}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, Color.WHITE, Color.BLACK, 2));
		Artist.addShape(new Cube(150, new double[]{-150, -200, 600}, new double[] {30, 45, 0}, 1, 
				new double[]{7, 24, -2}, new double[]{0, -0.4, 0}, new double[]{0.03, 0, 0}, new double[]{0, 0, 0}, clrs, 3));
		Artist.addShape(new Cube(200, new double[]{-3000, 0, -3000}, new double[] {0, 0, 0}, 1, 
				new double[]{8, 0, 8}, new double[]{0, 0, 0}, new double[]{0, 0.05, 0}, new double[]{0, 0, 0}, Color.GREEN, Color.GREEN, 0));
		Artist.addShape(new Cube(300, new double[]{0, 20000, 0}, new double[] {0, 0, 0}, 1, 
				new double[]{0, -50, 0}, new double[]{0, 0.06, 0}, new double[]{0, 0.05, 0}, new double[]{0, 0, 0}, Color.BLACK, Color.WHITE, 1));
		*/
		
		Color[][] rectcols1 = new Color[][] {
			{new Color(0, 168, 120), new Color(216, 241, 160), new Color(243, 193, 120), new Color(254, 94, 65), Color.BLACK, Color.BLACK}, 
			{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE}};
		Color[][] rectcols2 = new Color[][] {
			{new Color(9, 38, 53), new Color(27, 66, 66), new Color(92, 131, 116), new Color(158, 200, 185), new Color(9, 38, 53), new Color(9, 38, 53)}, 
			{new Color(9, 38, 53), new Color(9, 38, 53), new Color(9, 38, 53), new Color(9, 38, 53), new Color(9, 38, 53), new Color(9, 38, 53)}};
		Color[][] pyrcols3 = new Color[][] {
			{new Color(34, 40, 49), new Color(57, 62, 70), new Color(238, 238, 238), new Color(0, 173, 181), new Color(34, 40, 49)}, 
			{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE}};
		Color[][] rectcols4 = new Color[][] {
			{new Color(227, 253, 253), new Color(203, 241, 245), new Color(166, 227, 233), new Color(113, 201, 206), new Color(113, 201, 206), new Color(227, 253, 253)}, 
			{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK}};
		Color[][] pyrcols5 = new Color[][] {
			{new Color(8, 217, 214), new Color(255, 46, 99), new Color(8, 217, 214), new Color(255, 46, 99), new Color(234, 234, 234)}, 
			{new Color(37, 42, 52), new Color(37, 42, 52), new Color(37, 42, 52), new Color(37, 42, 52), new Color(37, 42, 52)}};
		Color[][] pyrcols6 = new Color[][] {
			{new Color(0,0,0), new Color(52,113,93), new Color(80,204,144), new Color(255,255,255), new Color(249,12,155)}, 
			{Color.PINK, Color.PINK, Color.PINK, Color.PINK, Color.PINK}};
		Color[][] rectcols7 = new Color[][] {
			{new Color(243, 129, 129), new Color(149, 225, 211), new Color(243, 129, 129), new Color(252, 227, 138), new Color(252, 227, 138), new Color(234, 255, 208)}, 
			{Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN}};
		Color[][] rectcols8 = new Color[][] {
			{new Color(5, 5, 5), new Color(15, 15, 15), new Color(80, 80, 80), new Color(25, 25, 25), new Color(35, 35, 35), new Color(10, 10, 10)}, 
			{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE}};
		Artist.addShape(new Cube(200, new double[]{0, 0, 170}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, rectcols1, 0));
		Artist.addShape(new RectangularPrism(50, 200, 50, new double[]{-300, 200, -50}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0.5, 0}, new double[]{0, 0, 0}, new double[]{0, 0.06, 0.005}, new double[]{0, 0, 0}, rectcols2, 3));
		Artist.addShape(new Pyramid(100, 150, new double[]{-50, 175, 120}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, pyrcols3, 0));
		Artist.addShape(new Pyramid(100, 150, new double[]{50, 175, 120}, new double[] {0, 90, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, pyrcols3, 0));
		Artist.addShape(new Pyramid(100, 150, new double[]{-50, 175, 220}, new double[] {0, 270, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, pyrcols3, 0));
		Artist.addShape(new Pyramid(100, 150, new double[]{50, 175, 220}, new double[] {0, 180, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, pyrcols3, 0));
		Artist.addShape(new Cube(500, new double[]{200, 300, -600}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0.05, 0.02, -0.003}, new double[]{0, -0.0002, 0.00004}, rectcols4, 2));
		Artist.addShape(new Pyramid(200, 50, new double[]{150, 700, 50}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0.03, 0.03, 0}, new double[]{0, 0, 0}, pyrcols5, 5));
		Artist.addShape(new Pyramid(100, 200, new double[]{200, 900, -300}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0.05}, new double[]{0, 0, 0}, pyrcols6, 0));
		Artist.addShape(new RectangularPrism(200, 100, 100, new double[]{-200, 900, 0}, new double[] {30, 25, -50}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0.02, 0}, new double[]{0, 0, 0}, rectcols7, 3));
		Artist.addShape(new Cube(250, new double[]{-100, 1200, -100}, new double[] {0, 20, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0.0003, 0.03, -0.01}, new double[]{0, 0, 0}, rectcols8, 0));
		Artist.addShape(new Cube(100, new double[]{150, 1400, -100}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0.02, 0.02, 0.02}, new double[]{0, 0, 0}, Color.WHITE, Color.WHITE, 0));
		Artist.addShape(new Pyramid(300, 500, new double[]{0, 1800, 0}, new double[] {0, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0.01, 0}, Color.RED, Color.GREEN, 2));
		Artist.addShape(new Pyramid(300, 500, new double[]{0, 2300, 0}, new double[] {180, 0, 0}, 1, 
				new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0, 0}, new double[]{0, 0.01, 0}, Color.GREEN, Color.RED, 2));
		
		Artist.defineFaces();
		new Thread(this).start();
	}

	public void update(Graphics window)
	{
		paint(window);
	}

	public void paint(Graphics window)
	{
		//countdown to start simulation
		if(paused && simulation_counter < simulation_delay)
		{
			simulation_counter++;
			if(simulation_counter == simulation_delay)
				pause();
		}
			
		//background
		window.setColor(background_color);
		window.fillRect(0, 0, getWidth(), getHeight());
			
		//move camera based on key input
		cam.moveCam();
		cam.rotateCam();
		
		//only move shapes if game is not paused
		if(paused)
		{
			Artist.drawFaces(window);
			
			//pause symbol
			if(simulation_counter == simulation_delay)
			{
				window.setColor(new Color(0, 0, 0, 100));
				window.fillRect(getWidth() / 2 - 40, getHeight() / 2 - 30, 30, 100);
				window.fillRect(getWidth() / 2 + 25, getHeight() / 2 - 30, 30, 100);
			}
		}
		else
			Artist.moveAndDrawFaces(window);
		
		//crosshair in center
		/*int w = GraphicsRunner.WIDTH / 2;
		int h = GraphicsRunner.HEIGHT / 2;
		window.setColor(Color.WHITE);
		((Graphics2D)window).setStroke(new BasicStroke(2));
		window.drawLine(w - 10, h, w + 10, h);
		window.drawLine(w, h - 10, w, h + 10);*/
	}

	public static void pause()
	{
		paused = !paused;
	}
	
	public static double[] getLightingDir()
	{
		return lighting_direction;
	}
	
	public void run()
	{
		try
		{
	   		long executionStamp = System.nanoTime();
	   		while(true) 
	   		{ 
	   			if (System.nanoTime() - executionStamp > 1000000000 / FRAME_RATE) 
	   			{
	   			    repaint();
	   			    executionStamp = System.nanoTime();
	   			}
	   		}
		} catch(Exception e){}
	}
}