package Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import View.*;
import Model.*;
import DAL.*;

public class Controller {
	/**
	 * Account object that represents the currently logged in user.
	 */
	static Account account = null;
	
	/**
	 * @param account the account to set
	 */
	public static void setAccount(Account account) {
		Controller.account = account;
	}

	/**
	 * BufferedReader used as input stream for the Controller class.
	 */
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static PrintStream out = System.out;
	
	View view = View.get();
	
	/**
	 * @param in the in to set
	 */
	public static void setIn(BufferedReader in) {
		Controller.in = in;
	}
	
	/**
	 * @param out The out set
	 */
	public static void setOut(PrintStream out) {
		Controller.out = out;
		View.get().setOut(out);
	}

	/**
	 * Constructor for the Controller class... This Constructor does nothing.
	 */
	public Controller() {
	}
	
	/**
	 * Simply starts execution path of the application.
	 */
	public void run() {
		doLogin();
		doMenu();
	}
	
	/**
	 * Prints the Main Method Menu to the user and does logic based on their decision.
	 */
	public void doMenu() {
		int choice = 0;
		
		do {
			do {
				printMainMenu();
				choice = getIntegerFromInput();
				
				switch (choice){
				case 1:
					doSearch();
					break;
				case 2:
					viewAccount();
					break;
				case 3:
					viewSettings();
					break;
				case 4:
					out.println("Goodbye. Thanks for using Potholes.");
					break;
				default:
					out.println("Incorrect selection.  Please enter a choice between 1 and 4.");
				}
			} while (!inputIsWithinRange(choice, 1, 4));
		} while (choice != 4);
	}

	/**
	 * Displays the settings to the user.
	 */
	public void viewSettings() {
		out.println("These are the settings.");
		
		ArrayList<Configuration> settings = DAL.get().getConfigurationSettings();
		
		View.get().displayConfigurationSettings(settings);
		
	}

	/**
	 * Displays the account page to the user.
	 */
	public void viewAccount() {
		out.println("This is the account page.");
		
		printAccountMenu();
		int choice = 0;
		
		while (true) {
			out.println("Enter your selection: ");
			choice = getIntegerFromInput();
			
			switch (choice) {
			case 0:
				return;
			case 1:
				View.get().displayFavorites(account);
				return;
			case 2:
				View.get().displayOrders(DAL.get().getOrders(account.getId()));
				return;
			default:
				out.println("Incorrect selection!");
			}
		}
	}

	/**
	 * Performs search functionality of Potholes application.
	 */
	public void doSearch() {
		int choice = 0;
		String searchTerm = null;
		
		do {
			out.println("Enter your search term(s): ");
			searchTerm = getStringFromInput();
		} while (searchTerm == null || searchTerm.trim().length() == 0); // its null, empty, or all ws...
		
		do {
			printSearchMenu();
			choice = getIntegerFromInput();
		
			switch (choice) {
			case 0:
				return;
			case 1:
				doMoviesSearch(searchTerm);
				break;
			case 2:
				doPeopleSearch(searchTerm);
				break;
			case 3:
				doBothSearch(searchTerm);
				break;
			default:
				out.println("Invalid choice. Let's try that again...");
				break;
			}
		} while (!inputIsWithinRange(choice, 1, 3));
	}
	
	/**
	 * Performs search for movies option of the Potholes application.
	 */
	public void doMoviesSearch(String searchTerm) {
		int choice = 0;
		
		out.println("Please wait while Potholes performs this search...");
		ArrayList<Movie> movies = DAL.get().getMovies(searchTerm);
		
		View.get().displayMovieResults(movies);
		
		if (movies.size() == 0) {
			out.println("No results. Sorry.");
		}
		else {
			while (true) {
				out.println("Enter your selection: ");
				choice = getIntegerFromInput();
				
				if (choice == 0) {
					return;
				}
				else if (choice >= 1 && choice <= movies.size()) {
					doMovie(movies.get(choice - 1));
					return;
				}
				else {
					out.println("Entry out of range!");
				}
			}
		}
	}

	/**
	 * Performs search for people option of the Potholes application.
	 */
	public void doPeopleSearch(String searchTerm) {
		int choice = 0;
		
		ArrayList<Person> people = DAL.get().getPeople(searchTerm);

		View.get().displayPeopleResults(people);
		
		if (people.size() == 0) {
			out.println("No results. Sorry.");
		}
		else {
			while (true) {
				out.println("Enter your selection: ");
				choice = getIntegerFromInput();
				
				if (choice == 0){
					return;
				}
				else if (choice >= 1 && choice <= people.size()){
					doPerson(people.get(choice - 1));
					return;
				}
				else {
					out.println("Entry out of range!");
				}
			}
		}
	}
	
	/**
	 * Performs search for movies & people (both) option of the Potholes application.
	 */
	public void doBothSearch(String searchTerm) {
		int choice = 0;
		
		out.println("Please wait while Potholes performs this search...");
		ArrayList<Movie> movies = DAL.get().getMovies(searchTerm);
		
		out.println("Please wait while Potholes finishes this search...");
		ArrayList<Person> people = DAL.get().getPeople(searchTerm);
		
		if (movies.size() == 0 && people.size() == 0) {
			out.println("No results... Sorry.");
		}
		else {
			out.println("0) Go to menu.");
			
			View.get().displayBothResults(movies, people);
			
			while (true) {
				out.println("Enter your selection: ");
				
				choice = getIntegerFromInput();
				
				if (choice == 0) {
					return;
				}
				else if (choice >= 1 && choice <= movies.size()){
					doMovie(movies.get(choice - 1));
					return;
				}
				else if (choice > movies.size() && choice <= (movies.size() + people.size())) {
					doPerson(people.get(choice - movies.size() - 1));
					return;
				}
				else {
					out.println("Entry out of range!");
				}
			}
		}
	}

	/**
	 * Displays information about a certain person object.
	 * @return void
	 */
	private void doPerson(Person person) {
		View.get().displayPersonInformation(person);
	}
	
	/**
	 * Determines whether or not the specified movie was favorited by the current user.
	 * @return Boolean
	 */
	public Boolean favoritesContains(Movie movie) {
		ArrayList<Movie> favorites = account.getFavorites();
		for (Movie favorite : favorites) {
			if (favorite.getId().equals(movie.getId())) 
				return true;
		}
		
		return false;
	}
	
	/**
	 * Determines whether or not the specified movie was purchased by the current user.
	 * @return Boolean
	 */
	public Boolean isPurchased(Movie movie) {
		ArrayList<Order> orders = DAL.get().getOrders(account.getId());
		
		for (Order order : orders) {
			if (order.getMovieId().equals(movie.getId())) {
				if (order.getOrderType().getType() == OrderType.Type.PURCHASE)
					return true;
				else if (order.getOrderType().getType() == OrderType.Type.RENTAL) {
					Calendar calendarNow = new GregorianCalendar();
					
					Calendar calendarRental = new GregorianCalendar();
					calendarRental.set(order.getTransactionYear(), 
							order.getTransactionMonth(), 
							order.getTransactionDay(), 
							order.getTransactionHour(), 
							order.getTransactionMinute(), 
							order.getTransactionSecond());
					
					calendarRental.add(Calendar.DAY_OF_MONTH, 3);
					
					return (calendarRental.compareTo(calendarNow) > 0);
					
				}
			}
				
		}
		
		return false;
	}
	
	/**
	 * Performs movie functionality of the Potholes application.
	 */
	public void doMovie(Movie movie) {
		
		View.get().displayMovieInformation(movie);
		
		Boolean movieIsFavorite = favoritesContains(movie);
		
		Boolean movieIsPurchased = isPurchased(movie);
		
		if (!movieIsPurchased) {
			doUnownedMovie(movie, movieIsFavorite);
		}
		else {
			doOwnedMovie(movie, movieIsFavorite);
		}
	}
	
	/**
	 * Performs owned movie functionality of the Potholes application.
	 */
	public void doOwnedMovie(Movie movie, Boolean movieIsFavorite) {
		int choice = 0;
		
		
		try {
			while (true) {
				printAlreadyPurchasedMovieMenu(movieIsFavorite);
				out.println("Enter your selection: ");
				choice = getIntegerFromInput();
				
				switch (choice) {
				case 0:
					return;
				case 1:
					doServeMovieContent(movie);
					return;
				case 2:
					doFavoriteMovie(movie, movieIsFavorite);
					return;
				default:
					out.println("Incorrect selection!");
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Performs non-owned movie functionality of the Potholes application.
	 */
	public void doUnownedMovie(Movie movie, Boolean movieIsFavorite) {
		int choice = 0;
		
		printMovieMenu(movieIsFavorite);
		
		try {
			while (true) {
				out.println("Enter your selection: ");
				choice = getIntegerFromInput();
				
				switch (choice) {
				case 0:
					return;
				case 1:
					doRentMovie(movie);
					return;
				case 2:
					doPurchaseMovie(movie);
					return;
				case 3:
					doFavoriteMovie(movie, movieIsFavorite);
					return;
				default:
					out.println("Incorrect selection!");
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Processes order with an order-type of "rental".
	 * @return void
	 */
	private void doRentMovie(Movie movie) {
		doOrder(movie, new OrderType(OrderType.Type.RENTAL));
	}
	
	/**
	 * Processes order with an order-type of "purchase".
	 * @return void
	 */
	private void doPurchaseMovie(Movie movie) {
		doOrder(movie, new OrderType(OrderType.Type.PURCHASE));
	}
	
	/**
	 * Favorites a movie if it is not favorited already.
	 * @return void
	 */
	private void doFavoriteMovie(Movie movie, Boolean isFavorite) {
		if (!isFavorite) {
			DAL.get().createFavorite(account.getId(), movie.getId());
			out.println(movie.getTitle() + " has been added to your favorites.");
		}
	}
	
	/**
	 * Processes an order by getting payments and then adding information to the db.
	 * @return void
	 */
	private void doOrder(Movie movie, OrderType orderType) {
		// create / get payment method...
		PaymentMethod paymentMethod =  doPaymentMethod();
		
		// in the menus in doPaymentMethod, it returns null to return to main menu...
		if (paymentMethod == null)
			return;
		
		// create an order...
		Order order = DAL.get().createOrder(paymentMethod.getId(), orderType.getId(), 
				account.getId(), movie.getId());
		
		out.println("You have purchased: " + movie.getTitle() + ". Thank you!");
		
		doServeMovieContent(order);
	}
	
	/**
	 * Gets a payment method option from the user.
	 * @return PaymentMethod a new or existing payment method object.
	 */
	public PaymentMethod doPaymentMethod() {
		int choice = 0;
		Boolean useExistingPaymentMethod = null;
		
		PaymentMethod method = null;
		
		printPaymentMethodMenu();
		
		while (useExistingPaymentMethod == null) {
			out.println("Enter your selection: ");
			choice = getIntegerFromInput();
			
			switch (choice) {
			case 0:
				return null;
			case 1:
				useExistingPaymentMethod = true;
				break;
			case 2:
				useExistingPaymentMethod = false;
				break;
			default:
				out.println("Incorrect selection!");
			}
		}
	
		if (useExistingPaymentMethod) {
			method = doExistingPaymentMethod();
		}
		else {
			method = doNewPaymentMethod();
		}
		
		return method;
	}
	
	/**
	 * Gets an existing payment method option from the user.
	 * @return PaymentMethod an existing payment method.
	 */
	public PaymentMethod doExistingPaymentMethod() {
		int choice = 0;
		
		ArrayList<PaymentMethod> methods = DAL.get().getPaymentMethods(account.getId());
		
		while (true) {
			out.println("Choose one of the following items: ");
			out.println("0) Go Back to Menu.");
			
			View.get().displayExistingPaymentMethods(methods);
			out.println("Enter your selection: ");
			choice = getIntegerFromInput();
			
			if (choice == 0){
				return null;
			}
			else if (choice >= 1 && choice <= methods.size()){
				return methods.get(choice - 1);
			}
			else {
				out.println("Entry out of range!");
			}
		}
	}
	
	/**
	 * Creates a new payment method for the user.
	 * @return PaymentMethod a new payment method object.
	 */
	public PaymentMethod doNewPaymentMethod() {
		
		ArrayList<CreditCardType> types = DAL.get().getCreditCardTypes();
		CreditCardType creditCardType = null;
		
		while (true) {
			out.println("Please choose from the following credit card types: ");
			View.get().displayCreditCardTypes(types);

			out.println("Enter your selection: ");
			int choice = getIntegerFromInput();
			
			if (choice == 0) {
				return null;
			}
			else if (choice >= 1 && choice <= types.size()){
				creditCardType = types.get(choice - 1);
				break;
			}
			else {
				out.println("Entry out of range!");
			}
		}
		
		String creditCardNumber = "";
		
		do {
			out.println("Please enter your credit card number:");
			creditCardNumber = getStringFromInput();
			
			if (creditCardNumber == null || creditCardNumber.length() != 16) {
				out.println("Incorrect value entered.  Credit Cards are 16 digits in length.");
			}
		} while (creditCardNumber == null || creditCardNumber.length() != 16);
		
		Integer expirationMonth = 0;
		
		do {
			out.println("Please enter your expiration month:");
			expirationMonth = getIntegerFromInput();
			
			if (expirationMonth <= 0 || expirationMonth > 12) {
				out.println("Incorrect value entered.  Months of the year are between 1 - 12.");
			}
		} while (expirationMonth <= 0 || expirationMonth > 12);
		
		Integer expirationYear = 0;
		Calendar calendarNow = new GregorianCalendar();
		Integer currentYear = calendarNow.get(Calendar.YEAR);
		
		do {
			out.println("Please enter your expiration year:");
			expirationYear = getIntegerFromInput();
			
			if (expirationYear < currentYear) {
				out.println("Incorrect value entered.  The year must be greater than " + Integer.toString(currentYear));
			}
		} while (expirationYear < currentYear);
		
		
		out.println("Please enter your cvv:");
		Integer cvv = getIntegerFromInput();
		out.println("Please enter your name (as it appears on your credit card):");
		String nameOnCard = getStringFromInput();
		out.println("Please enter your zip code:");
		String zipCode = getStringFromInput();
		out.println("Thanks!");
		
		return DAL.get().createPaymentMethod(account.getId(), creditCardType.getId(), creditCardNumber, expirationMonth, expirationYear, cvv, nameOnCard, zipCode);
	}
	
	/**
	 * Serves the movie content to the user based on order.
	 * @return void.
	 */
	private void doServeMovieContent(Order order) {
		doServeMovieContent(order.getMovie());
	}
	
	/**
	 * Actually serves the movie content to the user.
	 * @return void.
	 */
	private void doServeMovieContent(Movie movie) {
		MovieData movieData = DAL.get().getMovieData(movie.getId());
		out.println("Your content is being served:");
		out.println("Content: " + movieData.getData());
		out.println("Hope you enjoyed your title! Cheers! :-D");
	}

	/**
	 * Logs the user in.
	 * @return void.
	 */
	private void doLogin() {
		String username = null;
		String password = null;
		
		while (account == null){
			out.print("Username: ");
			username = getStringFromInput();
			
			out.print("Password: ");
			password = getStringFromInput();
			
			account = DAL.get().getAccount(username, password);
		}
	}
	
	/**
	 * Gets string from input. 
	 * @return String that the user has entered
	 */
	public String getStringFromInput() {
		String s = "";
		
		try {
			s = in.readLine();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}
	
	/**
	 * Gets integer from input. If the input is not a number this function prompts the user & waits for input.
	 * @return Integer that the user has entered
	 */
	public Integer getIntegerFromInput() {
		String s = "";
		boolean validValueEntered = false;
		Integer ret = -1;
		
		do {
			try {
				s = in.readLine();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				if (s != null) {
					ret = Integer.parseInt(s);
					validValueEntered = true;
				}
			}
			catch (Exception e) {
				out.println("Let's try that again... Please enter a valid Integer value: ");
			}
		
		} while (!validValueEntered);
		
		return ret;
	}
	
	/**
	 * Validates input to make sure it is within range of expected values (inclusively).
	 * @param input number
	 * @param begin of range
	 * @param end of range
	 * @return boolean indicative of whether or not the input parameter is within the respective range
	 */
	public boolean inputIsWithinRange(int input, int begin, int end) {
		return (input >= begin && input <= end);
	}

	/**
	 * Prints the Main Menu to the user.
	 * @return void
	 */
	private void printMainMenu() {
		out.println("What do you want to do?");
		out.println("1. Search");
		out.println("2. View account");
		out.println("3. View settings");
		out.println("4. Quit");
	}

	/**
	 * Prints the Search Menu to the user.
	 * @return void
	 */
	private void printSearchMenu() {
		out.println("Search for:");
		out.println("1. Movies");
		out.println("2. People");
		out.println("3. Both");
	}
	
	/**
	 * Prints the Movie Menu to the user.
	 */
	public void printMovieMenu(Boolean favorited) {
		out.println("What would you like to do?");
		out.println("0. Go back to main menu.");
		out.println("1. Rent Movie (1.99 USD.");
		out.println("2. Buy Movie (2.99 USD).");
		
		if (!favorited)
			out.println("3. Add Movie to favorites.");
		else {
			out.println("3. Remove Movie from favorites.");
		}
	}
	
	/**
	 * Prints the Already Purchased Movie Menu to the user.
	 */
	public void printAlreadyPurchasedMovieMenu(Boolean favorited) {
		out.println("What would you like to do?");
		out.println("0. Go back to main menu.");
		out.println("1. View Movie.");
		
		if (!favorited)
			out.println("2. Add Movie to favorites.");
		else {
			out.println("2. Remove Movie from favorites.");
		}
	}
	
	/**
	 * Prints the Payment Method Menu to the user.
	 * @return void
	 */
	private void printPaymentMethodMenu() {
		out.println("What would you like to do?");
		out.println("0. Go back to main menu.");
		out.println("1. Use existing payment method.");
		out.println("2. Enter payment information.");
	}
	
	/**
	 * Prints the Account Method Menu to the user.
	 * @return void
	 */
	private void printAccountMenu() {
		out.println("What would you like to do?");
		out.println("0. Go back to main menu.");
		out.println("1. See favorites.");
		out.println("2. See previously made orders.");
	}
}
