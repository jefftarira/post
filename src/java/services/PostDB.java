package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Post;

public class PostDB {

  private conMysql con;

  private String sPosts = "select id, user, message, date, status from post where status='A' order by date desc";
  private String iPost = "INSERT INTO post(user,message,date) VALUES (?,?,sysdate())";

  public PostDB() {
    con = new conMysql();
  }

  public ArrayList getPosts() throws ClassNotFoundException, SQLException {
    ArrayList<Post> aPost = new ArrayList<>();
    Post p;
    PreparedStatement ps;
    ResultSet rs;

    con.conect();
    ps = con.prepareStatement(sPosts);
    rs = ps.executeQuery();

    while (rs.next()) {
      p = new Post(
              rs.getInt("id"),
              rs.getString("user"),
              rs.getString("message"),
              rs.getTimestamp("date"),
              rs.getString("status")
      );
      aPost.add(p);
    }

    rs.close();
    con.close();
    return aPost;
  }

  public int insertPost(Post p) throws ClassNotFoundException, SQLException {
    int totalReg = -1;
    PreparedStatement ps;

    try {
      con.conect();
      con.autoCommit(false);

      ps = con.prepareStatement(iPost);
      ps.setString(1, p.getUser());
      ps.setString(2, p.getMessage());
      totalReg = ps.executeUpdate();

      con.Commit();
      con.close();
    } catch (SQLException ex) {
      con.Rollback();
      con.close();
      ex.printStackTrace();
    }
    return totalReg;
  }

}
