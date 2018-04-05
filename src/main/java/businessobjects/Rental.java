package businessobjects;

import java.util.Date;

public class  Rental {
  public static final int BONUS_POINT_PRICE_FOR_DAY_OFF = 25;
  public static final int BASIC_PRICE = 3;
  public static final int PREMIUM_PRICE = 4;
  private Video video;
  private int daysRented;
  private int daysOverdue;
  private int rentalPrice;
  private int overduePrice;
  private Date rentalDate;
  private Date returnDate;
  private int daysCoveredByBonusPoints;

  public Rental(Video video) {
    this.video = video;
    this.rentalDate = new Date();
  }
  public int getDaysCoveredByBonusPoints() {
    return daysCoveredByBonusPoints;
  }
  public void setDaysCoveredByBonusPoints(int daysCoveredByBonusPoints) {
    this.daysCoveredByBonusPoints = daysCoveredByBonusPoints;
  }

  public Video getVideo() {
    return video;
  }

  public void setVideo(Video video) {
    this.video = video;
  }

  public int getDaysRented() {
    return daysRented;
  }

  public void setDaysRented(int daysRented) {
    calculateRentalPrice();
    this.daysRented = daysRented;
  }

  @Override
  public String toString() {
    return video.toString();
  }

  public int getRentalPrice() {
    return rentalPrice;
  }

  public void setRentalPrice(int rentalPrice) {
    this.rentalPrice = rentalPrice;
  }

  public Date getRentalDate() {
    return rentalDate;
  }

  public void setRentalDate(Date rentalDate) {
    this.rentalDate = rentalDate;
  }

  public Date getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }

  public int getDaysOverdue() {
    return daysOverdue;
  }

  public void setDaysOverdue(int daysOverdue) {
    this.daysOverdue = daysOverdue;
  }

  public void calculateRentalPrice() {
    calculateRentalPriceForDays(getDaysRented());
  }

  public void calculateRentalPriceForDays(int days) {
    if (days == 0) {
      setRentalPrice(0);
      return;
    }
    int daysOverLimit = 0;

    switch (getVideo().getType()) {
      case OLD_FILMS:
        daysOverLimit = 5;
        break;
      case REGULAR_FILMS:
        daysOverLimit = 3;
        break;
      case NEW_RELEASES:
        setRentalPrice(days * PREMIUM_PRICE);
        return;
    }

    if (days <= daysOverLimit) {
      setRentalPrice(BASIC_PRICE);
    } else {
      int leftDays = days - daysOverLimit;
      setRentalPrice(BASIC_PRICE + leftDays * BASIC_PRICE);
    }
  }

  public void calculateOverduePrice() {
    switch (getVideo().getType()) {
      case REGULAR_FILMS:
      case OLD_FILMS:
        setOverduePrice(this.getDaysOverdue() * BASIC_PRICE);
        break;
      case NEW_RELEASES:
        setOverduePrice(this.getDaysOverdue() * PREMIUM_PRICE);
        break;
    }
  }

  public int getOverduePrice() {
    return overduePrice;
  }

  public void setOverduePrice(int overduePrice) {
    this.overduePrice = overduePrice;
  }
}
