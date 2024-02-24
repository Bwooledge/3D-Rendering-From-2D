import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Face
{	
	private double[][] points;
	private int edge_thickness;
	private Color fill_color;
	private Color edge_color;
	
	public Face(double[][] p, Color c)
	{
		points = p;
		edge_thickness = 1;
		fill_color = c;
		edge_color = c;
	}
	public Face(double[][] p, int t, Color c, Color e)
	{
		points = p;
		edge_thickness = t;
		fill_color = c;
		edge_color = e;
	}
	
	public double[][] getPoints()
	{
		return points;
	}
	
	public void draw(Graphics window, double[] pos, double[] face_normal)
	{
		//relevant variables
		Camera cam = Simulation.cam;
		double[] cpos = cam.getPosition();
		double w = GraphicsRunner.WIDTH/2;
		double h = GraphicsRunner.HEIGHT/2;
		int[] x_points = new int[points.length];
		int[] y_points = new int[points.length];
		double slope = cam.getSlope();
		int len = points.length;
		
		//move points to position relative to cam with position and cam pos
		double[][] tpts = new double[points.length][points[0].length];
		for(int i = 0; i < tpts.length; i++)
			for(int x = 0; x < tpts[0].length; x++)
				tpts[i][x] = points[i][x] + pos[x] - cpos[x];
		
		//check if at least one point is on screen
		boolean shown = false;
		
		//move points to the screen
		for(int i = 0; i < len; i++)
		{
			//create a as vector from cam to point and b as cam normal vector (z axis)
			double[] a = tpts[i];
			double[] b = cam.getNormal();
			
			//move a to the plane of cam
			double dot = a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
			double dot2 = b[0] * b[0] + b[1] * b[1] + b[2] * b[2];
			double[] z_vector = new double[] {dot/dot2 * b[0], dot/dot2 * b[1], dot/dot2 * b[2]};
			a[0] -= z_vector[0];
			a[1] -= z_vector[1];
			a[2] -= z_vector[2];
			
			//get components of a as the right and up vectors
			double[] right = cam.getRight();
			double[] up = cam.getUp();
			dot = a[0] * right[0] + a[1] * right[1] + a[2] * right[2];
			dot2 = right[0] * right[0] + right[1] * right[1] + right[2] * right[2];
			double[] x_vector = new double[] {dot/dot2 * right[0], dot/dot2 * right[1], dot/dot2 * right[2]};
			dot = a[0] * up[0] + a[1] * up[1] + a[2] * up[2];
			dot2 = up[0] * up[0] + up[1] * up[1] + up[2] * up[2];
			double[] y_vector = new double[] {dot/dot2 * up[0], dot/dot2 * up[1], dot/dot2 * up[2]};
			
			//get single number for x and y using unit vectors up and right
			//NOTE: j ensures there is no division by 0
			int j = 0;
			while(j < 3 && x_vector[j] == 0) j++;
			double x_coordinate = 0;
			if (j < 3) x_coordinate = x_vector[j] / right[j];
			j = 0;
			while(j < 3 && y_vector[j] == 0) j++;
			double y_coordinate = 0;
			if (j < 3) y_coordinate = y_vector[j] / up[j];
			
			//shrink a to viewing screen
			//IMPORTANT NOTE: y_points has a minus sign after h to flip y axis; makes negative y down instead of up
			double z = Math.sqrt(Math.pow(z_vector[0], 2) + Math.pow(z_vector[1], 2) + Math.pow(z_vector[2], 2));
			x_points[i] = (int)(w + x_coordinate * w / slope / z);
			y_points[i] = (int)(h - y_coordinate * w / slope / z);
			
			//check if point is in viewport, then in front of camera
			if(x_points[i] > 0 && x_points[i] < w * 2 && y_points[i] > 0 && y_points[i] < h * 2)
				if(b[0] * z_vector[0] + b[1] * z_vector[1] + b[2] * z_vector[2] > 0.0)
					shown = true;
		}
		
		//don't draw the face if none of its points are on the screen
		if(!shown) return;
		
		//add lighting for colors
		double l = Math.sqrt(Math.pow(face_normal[0], 2) + Math.pow(face_normal[1], 2) + Math.pow(face_normal[2], 2));
		double[] ld = Simulation.getLightingDir();
		double lighting = ld[0] * face_normal[0] / l + ld[1] * face_normal[1] / l + ld[2] * face_normal[2] / l;
		lighting = 0.5 + 0.5 * Math.sqrt(-lighting / 2 + 0.5);
		Color temp_fill_color = new Color((int)(fill_color.getRed() * lighting), (int)(fill_color.getGreen() * lighting), (int)(fill_color.getBlue() * lighting));
		Color temp_edge_color = new Color((int)(edge_color.getRed() * lighting), (int)(edge_color.getGreen() * lighting), (int)(edge_color.getBlue() * lighting));
		
		//draw face
		window.setColor(temp_fill_color);
		window.fillPolygon(x_points, y_points, len);
		((Graphics2D)window).setStroke(new BasicStroke(edge_thickness));
		window.setColor(temp_edge_color);
		window.drawPolygon(x_points, y_points, len);
	}
}