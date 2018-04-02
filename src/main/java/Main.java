import businessobjects.Customer;
import businessobjects.Rental;
import businessobjects.Video;
import dao.RentalDao;
import dao.VideoDao;
import types.MovieType;

import java.util.Calendar;

public class Main {
  public static void main(String[] args) {
    VideoDao videoDao = new VideoDao();
    RentalDao rentalDao = new RentalDao();

    // Adding videos to inventory
    Video video1 = new Video("THE MOVIE", true, MovieType.REGULAR_FILMS);
    videoDao.addVideo(video1);

    // Customer comes along
    Customer customer = new Customer("Mihkel");
    customer.setBonusPoints(56);

    // Register customer in our system
    rentalDao.addUser(customer);

    // The terms
    Rental rental1 = new Rental(video1);
    rental1.setDaysRented(3);
    final Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -4);
    rental1.setRentalDate(cal.getTime());

    // Renting to customer, calculating the rental price
    rentalDao.addRentalToCustomer(customer, rental1);

    // Printing the check
    int daysCoveredByBonusPoints = customer.getMaximumDaysCoveredByBonusPointsForDays(rental1.getDaysRented());
    rentalDao.printCheckFor(rental1, daysCoveredByBonusPoints);

    // Bringing back the video
    rentalDao.returnRental(customer, rental1);

    // Printing the check
    rentalDao.printCheckFor(rental1, 0);
  }
}
