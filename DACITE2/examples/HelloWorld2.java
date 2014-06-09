
public class HelloWorld2 {
	
	
	public static void foo(){
		//System.out.println("Hello world!! ");
		int a = 1;
		int b = 3;
		int c = b + a;
	}
	
	
	public static void foo2(int c){
		//System.out.println("Hello world!! ");
		int a;
		int b = 2;
		if(c<b)
			a = 3;
		else
			a = 4;
	}
	
	
	
	
	
	
	public static void main(String [] args){
		int a = 1;
		int b = a;
		int c = a + 2;
		a = c;
		b = c;

	}
	
	
	
	public static void foo3(int c){
		//System.out.println("Hello world!! ");
		int a;
		int b = 2;
		if(c>5)
		{
			a = 3;
			b = a + c;   //b = 3+c
		}
		else
		{
			a = 4;
			b = a - b; //b = 2 
			
		}
	}

}
