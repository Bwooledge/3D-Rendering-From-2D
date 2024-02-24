import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Scanner;

public class GraphicsRunner extends JFrame implements KeyListener
{
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static Camera cam;

	public GraphicsRunner()
	{
		super("Physics Simulator");
		setSize(WIDTH,HEIGHT);

		getContentPane().add(new Simulation());

		this.addKeyListener(this);
			
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main( String args[] )
	{
		GraphicsRunner run = new GraphicsRunner();
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		return;
	}
	
	@Override
    public void keyPressed(KeyEvent e) 
	{
		switch (e.getKeyCode())
		{
			//translational movement
			case KeyEvent.VK_W: cam.setMovement(2, 1); break;
			case KeyEvent.VK_S: cam.setMovement(2, -1); break;
			case KeyEvent.VK_D: cam.setMovement(0, 1); break;
			case KeyEvent.VK_A: cam.setMovement(0, -1); break;
			case KeyEvent.VK_SPACE: cam.setMovement(1, 1); break;
			case KeyEvent.VK_SHIFT: cam.setMovement(1, -1); break;
			//rotational movement
			case KeyEvent.VK_I: cam.setRotation(1, 1); break;
			case KeyEvent.VK_K: cam.setRotation(1, -1); break;
			case KeyEvent.VK_L: cam.setRotation(0, 1); break;
			case KeyEvent.VK_J: cam.setRotation(0, -1); break;
			//pause
			case KeyEvent.VK_P: Simulation.pause(); break;
			//speed multiplier
			case KeyEvent.VK_CONTROL: cam.setMultiplier(2.25); break;
			default: break;
		}
    }

	@Override
    public void keyReleased(KeyEvent e) {
		//translational movement
		if(e.getKeyCode() == KeyEvent.VK_W && cam.getMovement(2) == 1)
			cam.setMovement(2, 0);
		else if(e.getKeyCode() == KeyEvent.VK_S && cam.getMovement(2) == -1)
			cam.setMovement(2, 0);
		else if(e.getKeyCode() == KeyEvent.VK_D && cam.getMovement(0) == 1)
			cam.setMovement(0, 0);
		else if(e.getKeyCode() == KeyEvent.VK_A && cam.getMovement(0) == -1)
			cam.setMovement(0, 0);
		else if(e.getKeyCode() == KeyEvent.VK_SPACE && cam.getMovement(1) == 1)
			cam.setMovement(1, 0);
		else if(e.getKeyCode() == KeyEvent.VK_SHIFT && cam.getMovement(1) == -1)
			cam.setMovement(1, 0);
	
		//rotational movement
		else if(e.getKeyCode() == KeyEvent.VK_I && cam.getRotation(1) == 1)
			cam.setRotation(1, 0);
		else if(e.getKeyCode() == KeyEvent.VK_K && cam.getRotation(1) == -1)
			cam.setRotation(1, 0);
		else if(e.getKeyCode() == KeyEvent.VK_L && cam.getRotation(0) == 1)
			cam.setRotation(0, 0);
		else if(e.getKeyCode() == KeyEvent.VK_J && cam.getRotation(0) == -1)
			cam.setRotation(0, 0);
		
		//speed multiplier
		else if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			cam.setMultiplier(1);
    }
}