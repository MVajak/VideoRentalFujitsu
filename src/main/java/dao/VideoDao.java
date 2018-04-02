package dao;

import businessobjects.Video;

import java.util.ArrayList;

public class VideoDao {
  private ArrayList<Video> videoList = new ArrayList<Video>();

  public ArrayList<Video> getVideosList() {
    return videoList;
  }

  public void addVideo(Video video) {
    videoList.add(video);
  }
}
