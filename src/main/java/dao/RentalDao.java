package dao;

import businessobjects.Customer;
import businessobjects.Rental;
import businessobjects.Video;
import types.MovieType;

import java.text.MessageFormat;
import java.util.*;

import static businessobjects.Rental.BONUS_POINT_PRICE_FOR_DAY_OFF;

public class RentalDao {
private static final String RENTAL_TEMPLATE = "{0}({1}) {2} days {3} EUR";
private static final String RENTAL_OVERDUE_TEMPLATE = "{0}({1}) {2} extra days {3} EUR";
private static final String RENTAL_BONUS_POINTS_TEMPLATE = "{0}({1}) {2} days (Paid with {3} Bonus points)";
private Map<Customer, ArrayList<Rental>> customerRentedVideos = new HashMap<>();
private List<Rental> rentalsForCheck = new ArrayList<>();

public void addRentalsForCheck(Rental rental) {
    rentalsForCheck.add(rental);
}
public List<Rental> getRentalsForCheck(){
    return rentalsForCheck;
}
public void addRentalToCustomer(Customer customer, Rental rental) {
    if (rental.getVideo().getAvailability()) {
        rental.calculateRentalPrice();
        int previousBonusPoints = customer.getBonusPoints();
        int bonusPointsForRental = rental.getVideo().getType().getBonusPoints();
        customer.setBonusPoints(previousBonusPoints + bonusPointsForRental);
        customerRentedVideos.get(customer).add(rental);
    }
}

public void printCheckFor(List<Rental> rentals) {
    int totalPrice = 0;
    for (Rental rental : rentals) {
        Video video = rental.getVideo();
        int daysCoveredByBonusPoints = rental.getDaysCoveredByBonusPoints();
        int daysRentedFor = rental.getDaysRented();
        if (rental.getReturnDate() == null) {
            System.out.println("RENTED FOR: " + rental.getDaysRented());

            if (daysCoveredByBonusPoints > 0 && rental.getVideo().getType() == MovieType.NEW_RELEASES) {
                System.out.println(MessageFormat.format(RENTAL_BONUS_POINTS_TEMPLATE, video.getTitle(), video.getType(), daysRentedFor, daysCoveredByBonusPoints * BONUS_POINT_PRICE_FOR_DAY_OFF));
                daysRentedFor = rental.getDaysRented() - daysCoveredByBonusPoints;
            }

            rental.calculateRentalPriceForDays(daysRentedFor);
            System.out.println(MessageFormat.format(RENTAL_TEMPLATE, video.getTitle(), video.getType(), daysRentedFor, rental.getRentalPrice()));
            totalPrice += rental.getRentalPrice();
        } else {
            System.out.println("OVERDUE DAYS: " + rental.getDaysOverdue());
            rental.calculateOverduePrice();
            System.out.println(MessageFormat.format(RENTAL_OVERDUE_TEMPLATE, video.getTitle(), video.getType(), rental.getDaysOverdue(), rental.getOverduePrice()));
            totalPrice += rental.getOverduePrice();
        }
    }
    System.out.println("TOTAL PRICE: " + totalPrice + "\n");
    rentalsForCheck.clear();
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
