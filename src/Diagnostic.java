
public class Diagnostic {
	//array print
	public static void arr(String name, int[] array)
	{
		System.out.print(name + ": [");
		for(int i = 0; i < array.length - 1; i++)
			System.out.print(array[i] + ", ");
		System.out.println(array[array.length - 1] + "]");
	}
	public static void arr(String name, double[] array)
	{
		System.out.print(name + ": [");
		for(int i = 0; i < array.length - 1; i++)
			System.out.print(array[i] + ", ");
		System.out.println(array[array.length - 1] + "]");
	}
	
	//2d array print
	public static void arrd(String name, double[][] array)
	{
		System.out.println(name + ": [");
		for(int x = 0; x < array.length - 1; x++)
		{
			System.out.print("[");
			for(int i = 0; i < array[x].length - 1; i++)
				System.out.print(array[x][i] + ", ");
			System.out.println(array[x][array[x].length - 1] + "],");
		}
		System.out.print("[");
		for(int i = 0; i < array.length - 1; i++)
			System.out.print(array[i] + ", ");
		System.out.println(array[array.length - 1] + "]]");
	}
	
	//single value prints
	public static void val(String name, double v)
	{
		System.out.println(name + ": " + v);
	}
	public static void val(String name, int v)
	{
		System.out.println(name + ": " + v);
	}
	public static void val(String name, boolean v)
	{
		System.out.println(name + ": " + v);
	}
}
