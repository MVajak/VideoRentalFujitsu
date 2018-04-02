package dao;

import businessobjects.Customer;
import businessobjects.Rental;
import businessobjects.Video;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static businessobjects.Rental.BONUS_POINT_PRICE_FOR_DAY_OFF;

public class RentalDao {
  private static final String RENTAL_TEMPLATE = "{0}({1}) {2} days {3} EUR";
  private static final String RENTAL_OVERDUE_TEMPLATE = "{0}({1}) {2} extra days {3} EUR";
  private static final String RENTAL_BONUS_POINTS_TEMPLATE = "{0}({1}) {2} days (Paid with {3} Bonus points)";
  private Map<Customer, ArrayList<Rental>> customerRentedVideos = new HashMap<>();

  public void addRentalToCustomer(Customer customer, Rental rental) {
    if (rental.getVideo().getAvailability()) {
      rental.calculateRentalPrice();
      int previousBonusPoints = customer.getBonusPoints();
      int bonusPointsForRental = rental.getVideo().getType().getBonusPoints();
      customer.setBonusPoints(previousBonusPoints + bonusPointsForRental);
      customerRentedVideos.get(customer).add(rental);
    }
  }

  public void printCheckFor(Rental rental, int daysCoveredByBonusPoints) {
    Video video = rental.getVideo();
    int daysRentedFor = rental.getDaysRented();
    if (rental.getReturnDate() == null) {
      System.out.println("RENTED FOR: " + rental.getDaysRented());
      if (daysCoveredByBonusPoints > 0) {
        System.out.println(MessageFormat.format(RENTAL_BONUS_POINTS_TEMPLATE, video.getTitle(), video.getType(), daysRentedFor, daysCoveredByBonusPoints * BONUS_POINT_PRICE_FOR_DAY_OFF));
        daysRentedFor = rental.getDaysRented() - daysCoveredByBonusPoints;
      }
      rental.calculateRentalPriceForDays(daysRentedFor);
      System.out.println(MessageFormat.format(RENTAL_TEMPLATE, video.getTitle(), video.getType(), daysRentedFor, rental.getRentalPrice()));
      System.out.println("TOTAL PRICE: " + (rental.getRentalPrice()) + "\n");
    } else {
      System.out.println("OVERDUE DAYS: " + rental.getDaysOverdue());
      rental.calculateOverduePrice();
      System.out.println(MessageFormat.format(RENTAL_OVERDUE_TEMPLATE, video.getTitle(), video.getType(), rental.getDaysOverdue(), rental.getOverduePrice()));
      System.out.println("TOTAL PRICE: " + (rental.getOverduePrice()) + "\n");
    }
  }

  public void addUser(Customer customer) {
    customerRentedVideos.put(customer, new ArrayList<Rental>());
  }

  public Map<Customer, ArrayList<Rental>> getAllRentals() {
    return customerRentedVideos;
  }

  public ArrayList<Rental> getAllCustomerRentals(Customer customer) {
    return customerRentedVideos.get(customer);
  }

  public void returnRental(Customer customer, Rental rental) {
    rental.setReturnDate(new Date());
    int diff = daysBetween(rental.getRentalDate(), rental.getReturnDate());
    if (diff > rental.getDaysRented()) {
      int daysOverdue = diff - rental.getDaysRented();
      rental.setDaysOverdue(daysOverdue);
      rental.calculateOverduePrice();
    }
    rental.getVideo().setAvailability(true);
    customerRentedVideos.get(customer).remove(rental);
  }

  private int daysBetween(Date d1, Date d2) {
    return (int) ((d2.getTime() - d1.getTime()) / (999 * 60 * 60 * 24));
  }
}
