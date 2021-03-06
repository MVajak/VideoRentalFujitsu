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
    Video video1 = new Video("THE MOVIE", true, MovieType.NEW_RELEASES);
    Video video2 = new Video("THE MOVIE", true, MovieType.REGULAR_FILMS);
    videoDao.addVideo(video1);
    videoDao.addVideo(video2);

    // Customer comes along
    Customer customer = new Customer("Mihkel");
    customer.setBonusPoints(75);

    // Register customer in our system
    rentalDao.addUser(customer);

    // The terms
    Rental rental1 = new Rental(video1);
    Rental rental2 = new Rental(video2);
    rental1.setDaysRented(2);
    rental2.setDaysRented(4);

    rental1.setForCheckOut(true);
    rental2.setForCheckOut(true);

    final Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -4);
    rental1.setRentalDate(cal.getTime());

    // Renting to customer, calculating the rental price
    rentalDao.addRentalToCustomer(customer, rental1);
    rentalDao.addRentalToCustomer(customer, rental2);

    // Printing the check

    rental1.setDaysCoveredByBonusPoints(customer.getMaximumDaysCoveredByBonusPointsForDays(rental1));
    rental2.setDaysCoveredByBonusPoints(customer.getMaximumDaysCoveredByBonusPointsForDays(rental2));

    rentalDao.printCheckFor(customer);
    System.out.println("LEFT BONUSPOINTS: " + customer.getBonusPoints() + '\n');

    rental1.setForCheckOut(true);
    rental2.setForCheckOut(true);

    // Bringing back the video
    rentalDao.returnRental(customer, rental1);
    rentalDao.returnRental(customer, rental2);

    // Printing the check
    rentalDao.printCheckFor(customer);

  }
}
