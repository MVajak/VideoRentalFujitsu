package businessobjects;

import types.MovieType;

import static businessobjects.Rental.BONUS_POINT_PRICE_FOR_DAY_OFF;

public class Customer {
  private String name;
  private int bonusPoints;

  public Customer(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getBonusPoints() {
    return bonusPoints;
  }

  public void setBonusPoints(int bonusPoints) {
    this.bonusPoints = bonusPoints;
  }

  public int getMaximumDaysCoveredByBonusPointsForDays(int daysRented, Rental rental) {
    if (rental.getVideo().getType() == MovieType.NEW_RELEASES) {
      int maxDaysOff = (int) Math.floor(getBonusPoints() / BONUS_POINT_PRICE_FOR_DAY_OFF);
      System.out.println("MAX DAYS OFF: " + maxDaysOff);
      if(daysRented < maxDaysOff){
        setBonusPoints(getBonusPoints() - (daysRented) * BONUS_POINT_PRICE_FOR_DAY_OFF);
        System.out.println("CUSTOMER BONUSPOINTS (IF): " + getBonusPoints());
        return daysRented;
      } else {
        setBonusPoints(getBonusPoints() - (maxDaysOff * BONUS_POINT_PRICE_FOR_DAY_OFF));
        System.out.println("CUSTOMER BONUSPOINTS (ELSE): " + getBonusPoints());
        return maxDaysOff;
      }
    }
    return daysRented;
  }

  @Override
  public String toString() {
    return name + ":" + bonusPoints;
  }
}
