import businessobjects.Customer;
import businessobjects.Rental;
import businessobjects.Video;
import dao.RentalDao;
import dao.VideoDao;
import org.junit.Test;
import types.MovieType;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class VideoRentalTest {
    @Test
    public void addVideo_VideoAddedToInventory() {
        VideoDao videoDao = new VideoDao();
        Video video = new Video("THE MOVIE", true, MovieType.REGULAR_FILMS);
        videoDao.addVideo(video);

        assertTrue(videoDao.getVideosList().contains(video));
    }

    @Test
    public void removeVideo_VideoRemovedFromInventory() {
        VideoDao videoDao = new VideoDao();
        Video video = new Video("THE MOVIE", true, MovieType.REGULAR_FILMS);
        videoDao.addVideo(video);
        videoDao.getVideosList().remove(video);

        assertFalse(videoDao.getVideosList().contains(video));
    }

    @Test
    public void setType_VideoTypeChanged() {
        Video video = new Video("THE MOVIE", true, MovieType.REGULAR_FILMS);
        video.setType(MovieType.NEW_RELEASES);
        assertTrue(video.getType().equals(MovieType.NEW_RELEASES));
    }

    @Test
    public void addRentalToCustomer_CustomerHasRentedMovie() {
        RentalDao rentalDao = new RentalDao();
        Customer customer = new Customer("Mihkel");
        rentalDao.addUser(customer);

        Video video = new Video("THE MOVIE", true, MovieType.REGULAR_FILMS);
        Rental rental = new Rental(video);
        rentalDao.addRentalToCustomer(customer, rental);
        assertTrue(rentalDao.getAllRentals().get(customer).contains(rental));
    }

    @Test
    public void calculateRentalPriceForDays_RentalPriceCalculated() {
        Video video = new Video("THE MOVIE", true, MovieType.REGULAR_FILMS);
        Rental rental = new Rental(video);
        rental.calculateRentalPriceForDays(4);
        assertTrue(rental.getRentalPrice() == 6);
    }

    @Test
    public void calculateOverduePrice_OverduePriceCalculated() {
        RentalDao rentalDao = new RentalDao();
        Customer customer = new Customer("Mihkel");
        rentalDao.addUser(customer);

        Video video = new Video("THE MOVIE", true, MovieType.REGULAR_FILMS);
        Rental rental = new Rental(video);
        rental.setDaysRented(2);

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -4);
        rental.setRentalDate(cal.getTime());

        rentalDao.returnRental(customer, rental);
        rental.setForCheckOut(true);
        rentalDao.printCheckFor(customer);

        assertTrue(rental.getOverduePrice() == 6);
    }

    @Test
    public void getMaximumDaysCoveredByBonusPointsForDays_MaxDaysCoveredByPonusPoints() {
        Customer customer = new Customer("mihkel");
        Video video = new Video("THE MOVIE", true, MovieType.NEW_RELEASES);
        Rental rental = new Rental(video);
        customer.setBonusPoints(56);
        rental.setDaysRented(3);
        int maximumDaysCoveredByBonusPointsForDays = customer.getMaximumDaysCoveredByBonusPointsForDays(rental);

        assertTrue(customer.getBonusPoints() == 6);
        assertTrue(maximumDaysCoveredByBonusPointsForDays == 2);
    }
}
