package View;

import Model.*;

import java.io.PrintStream;
import java.util.*;

/**
 * The Class View.
 */
public class View {
	
	/** The view. */
	private static View view;
	
	/**
	 * Gets the.
	 *
	 * @return the view
	 */
	public static View get() {
		if (view == null)
			view = new View();
		
		return view;
	}
	
	/** The out. */
	PrintStream out = System.out;
	
	/**
	 * Sets the out.
	 *
	 * @param out the out to set
	 */
	public void setOut(PrintStream out) {
		this.out = out;
	}
	
	/**
	 * Display people results.
	 *
	 * @param people the people
	 */
	public void displayPeopleResults(ArrayList<Person> people) {
		if (people == null)
			return;
		
		for (int i = 0; i < people.size(); i++) {
			Person person = people.get(i);
			if (person != null) {
				out.println(Integer.toString(i+1) + ") " + person.getFirstName() + " " + person.getLastName());
			}
		}
	}
	
	/**
	 * Display movie results.
	 *
	 * @param movies the movies
	 */
	public void displayMovieResults(ArrayList<Movie> movies) {
		if (movies == null)
			return;
		
		for (int i = 0; i < movies.size(); i++) {
			Movie movie = movies.get(i);
			if (movie != null) {
				out.println(Integer.toString(i+1) + ") " + movie.getTitle());
			}
		}
	}
	
	/**
	 * Display both results.
	 *
	 * @param movies the movies
	 * @param people the people
	 */
	public void displayBothResults(ArrayList<Movie> movies, ArrayList<Person> people) {
		int count = 0;
		
		if (movies == null || people == null)
			return;
		
		for (Movie movie : movies) {
			if (movie != null) {
				out.println(Integer.toString(count+1) + ") " + movie.getTitle());
				count++;
			}
		}
		
		for (Person person : people) {
			if (person != null) {
				out.println(Integer.toString(count+1) + ") " + person.getFirstName() + " " + person.getLastName());
				count++;
			}
		}
		
	}
	
	/**
	 * Display existing payment methods.
	 *
	 * @param methods the methods
	 */
	public void displayExistingPaymentMethods(ArrayList<PaymentMethod> methods) {
		if (methods == null)
			return;
		
		int selectionNumber = 1;
		
		for (PaymentMethod method : methods) {
			if (method != null) {
				out.println(Integer.toString(selectionNumber) + ") " + "Type: " + method.getCreditCardType().getName() + ", Card #: " + method.getCreditCardNumber());
				selectionNumber++;
			}
		}
	}
	
	/**
	 * Display credit card types.
	 *
	 * @param types the types
	 */
	public void displayCreditCardTypes(ArrayList<CreditCardType> types) {
		if (types == null)
			return;
		
		int selectionNumber = 1;
		
		out.println("0) Go to menu.");
		for (CreditCardType type : types) {
			if (type != null) {
				out.println(Integer.toString(selectionNumber) + ") " + "Type: " + type.getName());
				selectionNumber++;
			}
		}
	}
	
	/**
	 * Display movie information.
	 *
	 * @param movie the movie
	 */
	public void displayMovieInformation(Movie movie) {
		if (movie == null)
			return;
		
		out.println("******************** " + movie.getTitle() + " ********************");
		out.println();
		
		out.println("Series years: " + movie.getSeriesYears());
		out.println("Description: " + movie.getDescription());
		out.println("Production year: " + movie.getProductionYear());
		
		out.println("Cast:");
		displayPeople(movie.getCast());
		out.println("Directors:");
		displayPeople(movie.getDirectors());
		out.println("Producers:");
		displayPeople(movie.getProducers());
		out.println("Writers:");
		displayPeople(movie.getWriters());
		
		// print out results form arrays...
		// They are laid out like this because this way if there are no producers/writers/cast-members/directors
		// then the label will not even show up...
		
		out.println("Reviews:");
		displayReviews(movie.getReviews());
		
		out.println();
		out.println("****************************************");
	}
	
	/**
	 * Display people.
	 *
	 * @param people the people
	 */
	public void displayPeople(ArrayList<Person> people) {
		if (people == null || people.size() == 0) { out.println("None."); return; }
		
		for (Person person : people) {
			if (person != null) {
				out.println(person.getFirstName() + " " + person.getLastName());
			}
		}
	}
	
	/**
	 * Display reviews.
	 *
	 * @param reviews the reviews
	 */
	public void displayReviews(ArrayList<Review> reviews) {
		if (reviews == null) {
			return;
		}
		
		for (Review review : reviews) {
			if (review != null) {
				Account account = review.getAccount();
				if (account != null) {
					out.println("By: " + account.getFirstName() + " " + account.getLastName());
					out.println(review.getReview());
				}
			}
		}
	}
	
	/**
	 * Display movie.
	 *
	 * @param movies the movies
	 */
	public void displayMovie(ArrayList<Movie> movies) {
		if (movies == null) {
			return;
		}
		
		for (Movie movie : movies) {
			if (movie != null) {
				out.println(movie.getTitle());
			}
		}
	}
	
	/**
	 * Display person information.
	 *
	 * @param person the person
	 */
	public void displayPersonInformation(Person person) {
		if (person == null)
			return;
		
		out.println("******************** " + person.getFirstName() + " " + person.getLastName() + " ********************");
		out.println();
		
		out.println("Biography: " + person.getBiography());
		out.println("Gender: " + person.getGender());
		
		out.println("As CastMember:");
		displayMovie(person.getMoviesAsACastmember());
		out.println("As Director:");
		displayMovie(person.getMoviesAsADirector());
		out.println("As Writer:");
		displayMovie(person.getMoviesAsAWriter());
		out.println("As Producer:");
		displayMovie(person.getMoviesAsAProducer());
		
		out.println();
		out.println("****************************************");
	}
	
	/**
	 * Display configuration settings.
	 *
	 * @param configurationSettings the configuration settings
	 */
	public void displayConfigurationSettings(ArrayList<Configuration> configurationSettings) {
		if (configurationSettings == null)
			return;
		
		int selectionNumber = 1;
		
		for (Configuration setting : configurationSettings) {
			if (setting != null) {
				out.println(Integer.toString(selectionNumber) + ") " + setting.getAttribute() + " " + setting.getValue());
				selectionNumber++;
			}
		}
	}
	
	/**
	 * Display favorites.
	 *
	 * @param account the account
	 */
	public void displayFavorites(Account account) {
		if (account == null)
			return;
		
		ArrayList<Movie> favorites = account.getFavorites();
		
		for (Movie movie : favorites) {
			if (movie != null) {
				String title = movie.getTitle().toString();
				out.println(title);
			}
		}
	}
	
	/**
	 * Display orders.
	 *
	 * @param orders the orders
	 */
	public void displayOrders(ArrayList<Order> orders) {
		if (orders == null)
			return;
		
		//TODO: add time to this method...
		for (Order order : orders) {
			if (order != null) {
				out.println("Movie: " + order.getMovie().getTitle());
				out.println("Order-Type: " + order.getOrderType().getName());
				out.println("Payment-Method: " + order.getPaymentMethod().getCreditCardType().getName() + " " + order.getPaymentMethod().getCreditCardNumber());				
			}
		}
	}
}
