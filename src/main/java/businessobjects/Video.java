package businessobjects;

import types.MovieType;

public class Video {
  private String title;
  private boolean availability;
  private MovieType type;

  public Video(String title, boolean availability, MovieType type) {
    this.title = title;
    this.availability = availability;
    this.type = type;
  }

  public MovieType getType() {
    return type;
  }

  public void setType(MovieType type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean getAvailability() {
    return availability;
  }

  public void setAvailability(boolean availability) {
    this.availability = availability;
  }

  @Override
  public String toString() {
    return title + ":" + type;
  }
}
