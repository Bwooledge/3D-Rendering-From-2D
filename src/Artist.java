import java.awt.Graphics;
import java.util.ArrayList;

public class Artist {
	private static ArrayList<Shape3D> shape_list = new ArrayList<Shape3D>();
	private static Face[] all_faces;
	private static int[] corresponding_shapes;
	
	public static void addShape(Shape3D s)
	{
		shape_list.add(s);
	}
	
	public static void drawFaces(Graphics window)
	{		
		double[] cam_pos = Simulation.cam.getPosition();
		double max_dist = Simulation.cam.getMaximumDistance();
		
		//add valid faces to the shown_faces array
		ArrayList<Face> shown_faces = new ArrayList<Face>();
		ArrayList<double[]> corr_pos = new ArrayList<double[]>();
		ArrayList<double[]> corr_norm = new ArrayList<double[]>();
		for(int i = 0; i < all_faces.length; i++)
		{
			//get normal
			double[] normal = new double[3];
			double[][] points = all_faces[i].getPoints();
			double[] line1 = new double[] {points[0][0] - points[1][0], points[0][1] - points[1][1], points[0][2] - points[1][2]};
			double[] line2 = new double[] {points[2][0] - points[1][0], points[2][1] - points[1][1], points[2][2] - points[1][2]};
			normal[0] = line1[1] * line2[2] - line1[2] * line2[1];
			normal[1] = -line1[0] * line2[2] + line1[2] * line2[0];
			normal[2] = line1[0] * line2[1] - line1[1] * line2[0];
			
			//include face if the angle between normal and to_point is acute and it is within the camera's maximum distance
			double[] pos = shape_list.get(corresponding_shapes[i]).getPosition();
			double[] to_point = new double[] {points[1][0] + pos[0] - cam_pos[0], points[1][1] + pos[1] - cam_pos[1], points[1][2] + pos[2] - cam_pos[2]};
			double to_point_distance = Math.sqrt(Math.pow(to_point[0], 2) + Math.pow(to_point[1], 2) + Math.pow(to_point[2], 2));
			if(to_point_distance < max_dist && normal[0] * to_point[0] + normal[1] * to_point[1] + normal[2] * to_point[2] < 0.0)
			{
				shown_faces.add(all_faces[i]);
				corr_pos.add(pos);
				corr_norm.add(normal);
			}
		}
		
		//get the order to draw each face in
		int[] draw_order = new int[shown_faces.size()];
		for(int x = draw_order.length - 1; x >= 0; x--)
		{
			//search each face for closest
			double closest_dist = Double.MAX_VALUE;
			int closest_face = 0;
			for(int y = 0; y < shown_faces.size(); y++)
			{
				//skip faces that are already drawn
				int i = 0;
				for(; i < draw_order.length; i++)
					if(draw_order[i] - 1 == y) break;
				if(i != draw_order.length) continue;
					
				//check each face for furthest point
				//NOTE: using only the furthest point of a face improves draw order
				double furthest_point_dist = 0;
				double[][] t_pts = shown_faces.get(y).getPoints();
				double[] pos = corr_pos.get(y);
				for(double[] p : t_pts)
				{
					double dist = Math.sqrt(Math.pow(p[0] + pos[0] - cam_pos[0], 2) + Math.pow(p[1] + pos[1] - cam_pos[1], 2) + Math.pow(p[2] + pos[2] - cam_pos[2], 2));
					if(dist > furthest_point_dist)
						furthest_point_dist = dist;
				}
				
				//checks if face is closest
				if(furthest_point_dist < closest_dist)
				{
					closest_dist = furthest_point_dist;
					closest_face = y;
				}
			}
			//add closest index to taken (+1 so default 0 doesn't count)
			draw_order[x] = closest_face + 1;
		}
		
		//draw faces according to their order
		int x;
		for(int i = 0; i < draw_order.length; i++)
		{
			x = draw_order[i] - 1;
			shown_faces.get(x).draw(window, corr_pos.get(x), corr_norm.get(x));
		}
	}
	
	public static void moveAndDrawFaces(Graphics window)
	{		
		for(Shape3D s : shape_list)
			s.transform();
		drawFaces(window);
	}
	
	public static void defineFaces()
	{
		//get total number of faces
		int face_count = 0;
		for(Shape3D s : shape_list)
			face_count += s.getFaceCount();
		
		//fill all_faces with every face
		all_faces = new Face[face_count];
		corresponding_shapes = new int[face_count];
		int i = 0;
		for(int a = 0; a < shape_list.size(); a++)
		{
			Face[] shape_faces = shape_list.get(a).getFaces();
			for(int b = 0; b < shape_faces.length; b++)
			{
				all_faces[i] = shape_faces[b];
				corresponding_shapes[i] = a;
				i++;
			}
		}
	}
}
