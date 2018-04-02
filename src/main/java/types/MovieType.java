package types;

public enum MovieType {
  NEW_RELEASES("New Releases", 2),
  REGULAR_FILMS("Regular film", 1),
  OLD_FILMS("Old film", 1);


  private final String movieType;
  private int bonusPoints;

  MovieType(String movieType, int bonusPoints) {
    this.movieType = movieType;
    this.bonusPoints = bonusPoints;
  }

  @Override
  public String toString() {
    return movieType;
  }

  public int getBonusPoints() {
    return bonusPoints;
  }

  public void setBonusPoints(int bonusPoints) {
    this.bonusPoints = bonusPoints;
  }
}
