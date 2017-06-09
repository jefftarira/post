package model;

import java.sql.Timestamp;

public class Post {

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  private int id;
  private String user;
  private String message;
  private Timestamp date;
  private String status;

  public Post(int id, String user, String message, Timestamp date, String status) {
    this.setId(id);
    this.setUser(user);
    this.setMessage(message);
    this.setDate(date);
    this.setStatus(status);
  }

  public Post(String user, String message) {
    this.setUser(user);
    this.setMessage(message);

  }

}
