
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Post;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import services.PostDB;

public class list extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, JSONException {
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {   
    
    
    response.setContentType("application/json;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {

      ArrayList<Post> aPost = null;
      Post p = null;
      PostDB db = new PostDB();

      aPost = db.getPosts();

      JSONObject obj = new JSONObject();
      obj.put("error", false);
      obj.put("message", " ");

      JSONArray aC = new JSONArray();
      for (int i = 0; i < aPost.size(); i++) {
        JSONObject jsonD = new JSONObject();
        p = aPost.get(i);
        jsonD.put("id", p.getId());
        jsonD.put("user", p.getUser());
        jsonD.put("message", p.getMessage());
        jsonD.put("date", p.getDate().toInstant());
        jsonD.put("status", p.getStatus());
        aC.add(jsonD);
      }
      obj.put("posts", aC);

      out.println(obj);
      out.flush();
      out.close();
    } catch (JSONException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      boolean error;
      String message;
      StringBuilder sb = new StringBuilder();
      BufferedReader br = request.getReader();
      String str = null;
      while ((str = br.readLine()) != null) {
        sb.append(str);
      }

      JSONObject jsObj = new JSONObject(sb.toString());
      Post p = new Post(
              jsObj.getString("user"),
              jsObj.getString("message")
      );

      PostDB db = new PostDB();
      int totalReg = db.insertPost(p);

      if (totalReg == 1) {
        error = false;
        message = "Se ingreso correctamente el post";
      } else {
        error = true;
        message = "Error al insertar post";
      }

      JSONObject obj = new JSONObject();
      obj.put("error", error);
      obj.put("message", message);

      response.setContentType("application/json; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println(obj);
      out.flush();
      out.close();

    } catch (JSONException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
