import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Shape3D extends Body {
	
	private Face[] faces;
	
	public Shape3D(Face[] fcs, double[] pos, double[] o, double m, double[] vel, double[] accl, double[] rvel, double[] raccl)
	{
		super(new double[0][0], pos, m, vel, accl, rvel, raccl);
		faces = fcs;

		//add points to object
		ArrayList<double[]> pts = new ArrayList<double[]>();
		for(Face f : fcs)
			for(double[] p : f.getPoints())
				if(!pts.contains(p))
					pts.add(p);
		double[][] new_pts = new double[pts.size()][3];
		new_pts = pts.toArray(new_pts);
		setPoints(new_pts);
		
		//rotate to orientation
		if(o[0] != 0 || o[1] != 0 || o[2] != 0)
		{
			o[0] *= Math.PI / 180;
			o[1] *= Math.PI / 180;
			o[2] *= Math.PI / 180;
			transform_points(new double[][] {
				{Math.cos(o[0]) * Math.cos(o[1]), 
				Math.cos(o[0]) * Math.sin(o[1]) * Math.sin(o[2]) - Math.sin(o[0]) * Math.cos(o[2]), 
				Math.cos(o[0]) * Math.sin(o[1]) * Math.cos(o[2]) + Math.sin(o[0]) * Math.sin(o[2])},
											   
				{Math.sin(o[0]) * Math.cos(o[1]), 
				Math.sin(o[0]) * Math.sin(o[1]) * Math.sin(o[2]) + Math.cos(o[0]) * Math.cos(o[2]), 
				Math.sin(o[0]) * Math.sin(o[1]) * Math.cos(o[2]) - Math.cos(o[0]) * Math.sin(o[2])},
											   
				{-Math.sin(o[1]), Math.cos(o[1]) * Math.sin(o[2]), Math.cos(o[1]) * Math.cos(o[2])}});
		}
	}
	
	public void draw(Graphics window)
	{	
		double[] cam_pos = Simulation.cam.getPosition();
		
		for(int i = 0; i < faces.length; i++)
		{
			//get normal
			double[] normal = new double[3];
			double[][] points = faces[i].getPoints();
			double[] line1 = new double[] {points[0][0] - points[1][0], points[0][1] - points[1][1], points[0][2] - points[1][2]};
			double[] line2 = new double[] {points[2][0] - points[1][0], points[2][1] - points[1][1], points[2][2] - points[1][2]};
			normal[0] = line1[1] * line2[2] - line1[2] * line2[1];
			normal[1] = -line1[0] * line2[2] + line1[2] * line2[0];
			normal[2] = line1[0] * line2[1] - line1[1] * line2[0];
			
			//draw face if the angle between normal and to_point is acute
			double[] pos = getPosition();
			double[] to_point = new double[] {points[1][0] + pos[0] - cam_pos[0], points[1][1] + pos[1] - cam_pos[1], points[1][2] + pos[2] - cam_pos[2]};
			if(normal[0] * to_point[0] + normal[1] * to_point[1] + normal[2] * to_point[2] < 0.0)
				faces[i].draw(window, pos, normal);
		}
	}
	
	public void moveAndDraw(Graphics window)
	{
		transform();
		draw(window);
	}
	
	//specific method for Shapes class
	public Face[] getFaces()
	{
		return faces;
	}
	
	//specific method for Shapes class
	public int getFaceCount()
	{
		return faces.length;
	}
}
