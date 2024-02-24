import java.awt.Color;
import java.awt.Graphics;

public abstract class Body
{
	private double mass;

	private double[] position;
	private double[] velocity = new double[3];
	private double[] acceleration = new double[3];
	private double[] rotational_velocity = new double[3];
	private double[] rotational_acceleration = new double[3];
	
	private double[][] points;
	
   public Body(double[][] pts, double[] pos)
   {
	   	points = pts;
		position = pos;
		mass = 1;
   }
   
   public Body(double[][] pts, double[] pos, double m, double[] vel)
   {
	   	points = pts;
		position = pos;
		mass = m;
		velocity = vel;
   }
   
   public Body(double[][] pts, double[] pos, double m, double[] vel, double[] accl)
   {
	   	points = pts;
		position = pos;
		mass = m;
		velocity = vel;
		acceleration = accl;
   }
   
   public Body(double[][] pts, double[] pos, double m, double[] vel, double[] accl, double[] rvel, double[] raccl)
   {
	   	points = pts;
		position = pos;
		mass = m;
		velocity = vel;
		acceleration = accl;
		rotational_velocity = rvel;
		rotational_acceleration = raccl;
   }
   
   public double[] getPosition() 
   {
	   return position;
   }
   
   public double[] getVelocity() 
   {
	   return velocity;
   }
   
   public double[] getAcceleration() 
   {
	   return acceleration;
   }
   
   public double[] getRVelocity() 
   {
	   return rotational_velocity;
   }
   
   public double[] getRAcceleration() 
   {
	   return rotational_acceleration;
   }
   
   public double getMass()
   {
	   return mass;
   }
   
   public double[][] getPoints()
   {
	   return points;
   }
   
   public void setPosition(double[] pos) 
   {
	   position = pos;
   }
   
   public void setVelocity(double[] v)
   {
	   velocity = v;
   }
   
   public void setAcceleration(double[] a)
   {
	   acceleration = a;
   }
   
   public void setRVelocity(double[] v)
   {
	   rotational_velocity = v;
   }
   
   public void setRAcceleration(double[] a)
   {
	   rotational_acceleration = a;
   }
   
   public void applyForce(double[] f)
   {
	   for(int i = 0; i < f.length; i++)
		   acceleration[i] += f[i] / mass;
   }
   
   public void setPoints(double[][] pts)
   {
	   points = pts;
   }
   
   public void transform()
   {
	   int frame_ratio = Simulation.BASE_FRAME_RATE / Simulation.FRAME_RATE;
	   for(int i = 0; i < velocity.length; i++)
	   {
		   if(acceleration[i] != 0)
		   {
			   velocity[i] += acceleration[i] * frame_ratio;
		   }
		   if(velocity[i] != 0)
		   {
			   position[i] += velocity[i] * frame_ratio;
		   }
		   if(rotational_acceleration[i] != 0)
		   {
			   rotational_velocity[i] += rotational_acceleration[i] * frame_ratio;
		   }
		   if(rotational_velocity[i] != 0)
		   {
			   if(i == 0)
			   {
				   transform_points(new double[][] {
					   {1.0, 0, 0},
					   {0, Math.cos(rotational_velocity[0] * frame_ratio), -Math.sin(rotational_velocity[0] * frame_ratio)},
					   {0, Math.sin(rotational_velocity[0] * frame_ratio), Math.cos(rotational_velocity[0] * frame_ratio)}});
			   }
			   if(i == 1)
			   {
				   transform_points(new double[][] {
					   {Math.cos(rotational_velocity[1] * frame_ratio), 0, Math.sin(rotational_velocity[1] * frame_ratio)},
					   {0, 1.0, 0},
					   {-Math.sin(rotational_velocity[1] * frame_ratio), 0, Math.cos(rotational_velocity[1] * frame_ratio)}});
			   }
			   if(i == 2)
			   {
				   transform_points(new double[][] {
					   {Math.cos(rotational_velocity[2] * frame_ratio), -Math.sin(rotational_velocity[2] * frame_ratio), 0},
					   {Math.sin(rotational_velocity[2] * frame_ratio), Math.cos(rotational_velocity[2] * frame_ratio), 0},
					   {0, 0, 1.0}});
			   }
		   }
	   }
   }
   
   public void transform_points(double[][] m)
   {
	   //NOTE: new_points is essential so points aren't transformed before all the calculations are done
	   double[][] new_points = new double[points.length][points[0].length];
	   for(int i = 0; i < points.length; i++)
		   for(int j = 0; j < 3; j++)
			   new_points[i][j] = m[j][0] * points[i][0] + m[j][1] * points[i][1] + m[j][2] * points[i][2];
	   
	   //put new points in points
	   for(int i = 0; i < points.length; i++)
		   for(int j = 0; j < 3; j++)
			   points[i][j] = new_points[i][j];
   }
   
   public abstract void draw(Graphics window);

   public abstract void moveAndDraw(Graphics window);
}