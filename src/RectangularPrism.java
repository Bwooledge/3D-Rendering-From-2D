import java.awt.Color;

public class RectangularPrism extends Shape3D {
	//SIDE ORDER: Front, Right, Left, Top, Bottom, Back
	
	//method to insert face doubles for super constructor
	public static double[][] getPts(double width, double height, double depth, int i)
	{
		if(i == 0)
			return new double[][] {{-width/2, -height/2, -depth/2}, {width/2, -height/2, -depth/2}, {width/2, height/2, -depth/2}, {-width/2, height/2, -depth/2}};
		if(i == 1)
			return new double[][] {{width/2, -height/2, -depth/2}, {width/2, -height/2, depth/2}, {width/2, height/2, depth/2}, {width/2, height/2, -depth/2}};		
		if(i == 2)
			return new double[][] {{width/2, -height/2, depth/2}, {-width/2, -height/2, depth/2}, {-width/2, height/2, depth/2}, {width/2, height/2, depth/2}};
		if(i == 3)
			return new double[][] {{-width/2, -height/2, depth/2}, {-width/2, -height/2, -depth/2}, {-width/2, height/2, -depth/2}, {-width/2, height/2, depth/2}};
		if(i == 4)
			return new double[][] {{-width/2, height/2, -depth/2}, {width/2, height/2, -depth/2}, {width/2, height/2, depth/2}, {-width/2, height/2, depth/2}};
		if(i == 5)
			return new double[][] {{-width/2, -height/2, depth/2}, {width/2, -height/2, depth/2}, {width/2, -height/2, -depth/2}, {-width/2, -height/2, -depth/2}};		
		return new double[][] {{0, 0, 0}};
	}
	
	public RectangularPrism()
	{
		super(new Face[] {
				new Face(getPts(100, 30, 50, 0), 2, Color.BLACK, Color.WHITE),
				new Face(getPts(100, 30, 50, 1), 2, Color.BLACK, Color.WHITE),
				new Face(getPts(100, 30, 50, 2), 2, Color.BLACK, Color.WHITE),
				new Face(getPts(100, 30, 50, 3), 2, Color.BLACK, Color.WHITE),
				new Face(getPts(100, 30, 50, 4), 2, Color.BLACK, Color.WHITE),
				new Face(getPts(100, 30, 50, 5), 2, Color.BLACK, Color.WHITE)
			}, new double[] {0, 0, 0}, new double[] {0, 0, 0}, 1, new double[] {0, 0, 0}, new double[] {0, 0, 0}, new double[] {0, 0, 0}, new double[] {0, 0, 0});
	}
	public RectangularPrism(double width, double height, double depth, double[] pos, double[] orientation, double m, double[] vel, double[] accl, double[] rvel, double[] raccl, Color fill, Color edge)
	{
		super(new Face[] {
				new Face(getPts(width, height, depth, 0), 2, fill, edge),
				new Face(getPts(width, height, depth, 1), 2, fill, edge),
				new Face(getPts(width, height, depth, 2), 2, fill, edge),
				new Face(getPts(width, height, depth, 3), 2, fill, edge),
				new Face(getPts(width, height, depth, 4), 2, fill, edge),
				new Face(getPts(width, height, depth, 5), 2, fill, edge)
			}, pos, orientation, m, vel, accl, rvel, raccl);
	}
	public RectangularPrism(double width, double height, double depth, double[] pos, double[] orientation, double m, double[] vel, double[] accl, double[] rvel, double[] raccl, Color fill, Color edge, int thick)
	{
		super(new Face[] {
				new Face(getPts(width, height, depth, 0), thick, fill, edge),
				new Face(getPts(width, height, depth, 1), thick, fill, edge),
				new Face(getPts(width, height, depth, 2), thick, fill, edge),
				new Face(getPts(width, height, depth, 3), thick, fill, edge),
				new Face(getPts(width, height, depth, 4), thick, fill, edge),
				new Face(getPts(width, height, depth, 5), thick, fill, edge)
			}, pos, orientation, m, vel, accl, rvel, raccl);
	}
	public RectangularPrism(double width, double height, double depth, double[] pos, double[] orientation, double m, double[] vel, double[] accl, double[] rvel, double[] raccl, Color[][] colors, int thick)
	{
		super(new Face[] {
				new Face(getPts(width, height, depth, 0), thick, colors[0][0], colors[1][0]),
				new Face(getPts(width, height, depth, 1), thick, colors[0][1], colors[1][1]),
				new Face(getPts(width, height, depth, 2), thick, colors[0][2], colors[1][2]),
				new Face(getPts(width, height, depth, 3), thick, colors[0][3], colors[1][3]),
				new Face(getPts(width, height, depth, 4), thick, colors[0][4], colors[1][4]),
				new Face(getPts(width, height, depth, 5), thick, colors[0][5], colors[1][5])
			}, pos, orientation, m, vel, accl, rvel, raccl);
	}
}
