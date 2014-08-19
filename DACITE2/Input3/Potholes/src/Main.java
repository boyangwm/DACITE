import DAL.DAL;
import Controller.*;
//import package Controller;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		setupDatabase(args);
			
		System.out.println("Welcome to Potholes");
		Controller controller = new Controller();
		controller.run();
		System.out.println("Cheers!");
		
		DAL.get().closeDAL();
	}

	/**
	 * @param args
	 */
	private static void setupDatabase(String[] args) {
		if(args.length != 0)
		{
			if(args.length == 4)
			{
				String dbUrl = args[0];
				String dbName = args[1];
				String dbUserName = args[2];
				String dbPassword = args[3];
				DAL.get().setupDALSingleton("jdbc:mysql://" + dbUrl + "/", dbName, dbUserName, dbPassword);
				
			}
			else
			{
				System.out.println("Invalid Input to Program, exiting");
				System.out.println("Input should be <DB URL:PORT> <DB NAME> <DB USERNAME> <DB PASSWORD> or no input");
				System.exit(-1);
			}
		}
	}
}
