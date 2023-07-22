// Ariya Ansari
// Java Servlet for receiving the data from front end,
// sending data to different modules to be processed, and
// send the data back to front end to display

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "Controller",
  loadOnStartup = 1,
  urlPatterns =
  {
    "/loginPage", "/login", "/logout",
    "/register","/registerRedirect",
    "/dashboard",
    "/insertProfile", "/updateProfile",
    "/editProfile","/profile", 
    "/fuelQuote","/quoteHistory", 
    "/getQuote", "/recordQuote"
    
  })
public class Controller extends HttpServlet
{

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter())
    {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet Controller</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Something went wrong at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    // session object to communicate data
    HttpSession session = request.getSession();
    // key word sent by the client page
    String userPath = request.getServletPath();
    // clear session attributes from previous error messages
    String loginState = "";
    session.setAttribute("loginState", loginState);
    String registerState = "";
    session.setAttribute("registerState", registerState);
    String errZip = "";
    session.setAttribute("errZip", errZip);
    String errGallons = "";
    session.setAttribute("errMessage", errGallons);

    if (userPath.equals("/loginPage"))
    {
      String myUrl = "login.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    else if (userPath.equals("/logout"))
    {
      session.setAttribute("userName", "");
      String myUrl = "index.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    else if (userPath.equals("/registerRedirect"))
    {
      String myUrl = "register.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    else if (userPath.equals("/quoteHistory"))
    {
      // read the history from the database using getData module
      // db info
      String dbName = "Fuel";
      String tableName = "QuoteHistory";
      String[] tableHeaders =
      {
        "idEmail", "fullName", "gallons", "gallonPrice",
        "totalPrice", "quoteDate"
      };
      // get user name session attribute
      String userName = (String) session.getAttribute("userName");
      DataAccess objDb = new DataAccess(dbName);
      ArrayList<ArrayList<String>> data
        = objDb.getData(tableName, tableHeaders, userName);
      objDb.closeDbConn();

      // reformat sql date to us date
      for (int r = 0; r < data.size(); r++)
      {
        // read each row of the data
        ArrayList<String> row = data.get(r);
        String tempDate = row.get(5);
        // parse into a Date 
        Date parseDate = null;
        try
        {
          parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(tempDate);
        }
        catch (ParseException pe)
        {
          System.out.println("Data parse exception");
        }
        // format the date
        Format f = new SimpleDateFormat("MM-dd-yyyy");
        String formatDate = f.format(parseDate);
        // set the formatted date in the row
        row.set(5, formatDate);
        // insert the row back into data
        data.set(r, row);
      }
      // assign the read data into a session attribute
      session.setAttribute("data", data);
      // URL to go back to display in a table
      String myUrl = "/WEB-INF/fuelQuoteHistory.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    else if (userPath.equals("/dashboard"))
    {
      // URL to go back to display in a table
      String myUrl = "/WEB-INF/dashboard.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    else if (userPath.equals("/fuelQuote"))
    {
      // URL to go back to display in a table
      String myUrl = "/WEB-INF/fuelQuoteForm.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    else if (userPath.equals("/profile"))
    {
      // read the profile from the database using getData module
      // db info
      String dbName = "Fuel";
      String tableName = "Profile";
      String[] tableHeaders =
      {
        "idEmail", "fullName", "address", "city", "addressState", "zip"
      };
      // get user name session attribute
      String userName = (String) session.getAttribute("userName");
      DataAccess objDb = new DataAccess(dbName);
      ArrayList<ArrayList<String>> data
        = objDb.getData(tableName, tableHeaders, userName);
      objDb.closeDbConn();
      // if profile not found
      if (data.isEmpty())
      {
        System.out.println("User Name Not found");
      }
      // assign the read data into a session attribute
      session.setAttribute("data", data);
      // URL to go back to display in a table
      String myUrl = "/WEB-INF/viewProfile.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    else if (userPath.equals("/editProfile"))
    {
      // URL to go back
      String myUrl = "/WEB-INF/editProfile.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    // session object to communicate data
    HttpSession session = request.getSession();
    // key word sent by the client page
    String userPath = request.getServletPath();
    // clear session attributes from previous error messages
    String loginState = "";
    session.setAttribute("loginState", loginState);
    String registerState = "";
    session.setAttribute("registerState", registerState);
    String errZip = "";
    session.setAttribute("errZip", errZip);
    String errGallons = "";
    session.setAttribute("errMessage", errGallons);

    if (userPath.equals("/login"))
    {
      // read data from the form
      String idEmail = request.getParameter("idEmail");
      String password = request.getParameter("password");
      // db info
      String dbName = "Fuel";
      String tableName = "Client";

      DataAccess dbObj = new DataAccess(dbName);
      // authenticate the user
      boolean status = dbObj.validate(dbName, tableName,
        idEmail, password);
      dbObj.closeDbConn();
      if (!status)
      {
        loginState = "Login failed! Try again";
        session.setAttribute("loginState", loginState);
        // URL to go back to
        String myUrl = "login.jsp";
        try
        {
          // redirect back to new URL with new data
          request.getRequestDispatcher(myUrl).forward(request, response);
        }
        catch (Exception ex)
        {
          request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
        }
      }
      else
      {
        // set user name (to be used throughout the sesssion)
        session.setAttribute("userName", idEmail);
        // URL to go back to
        String myUrl = "/WEB-INF/dashboard.jsp";
        try
        {
          // redirect back to new URL with new data
          request.getRequestDispatcher(myUrl).forward(request, response);
        }
        catch (Exception ex)
        {
          request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
        }
      }
    }
    else if (userPath.equals("/register"))
    {
      // read data from the form
      String idEmail = request.getParameter("idEmail");
      String password = request.getParameter("password");
      String cpassword = request.getParameter("cpassword");
      // db info
      String dbName = "Fuel";
      String tableName = "Client";

      // check if the two password entries match
      if (!password.equals(cpassword))
      {
        // URL to go back to
        String myUrl = "register.jsp";
        // error message to display
        registerState = "Passwords don't match, please try again";
        session.setAttribute("registerState",
          registerState);
        try
        {
          // redirect back to new URL with new data
          request.getRequestDispatcher(myUrl).forward(request, response);
        }
        catch (Exception ex)
        {
          request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
        }
      }
      else
      {
        // check if registering user name already exists
        DataAccess dbObj = new DataAccess(dbName);
        if (dbObj.checkUserName(dbName, tableName, idEmail))
        {
          dbObj.closeDbConn();
          // URL to go back to
          String myUrl = "register.jsp";
          // error message to display
          registerState = "Email already exists, please login or "
            + "try another email address to register";
          session.setAttribute("registerState", registerState);
          try
          {
            // redirect back to new URL with new data
            request.getRequestDispatcher(myUrl).forward(request, response);
          }
          catch (Exception ex)
          {
            request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
          }
        }
        else
        {
          // register the new user
          boolean status = dbObj.register(dbName, tableName,
            idEmail, password);
          dbObj.closeDbConn();
          if (!status)
          {
            // db failure
            registerState = "Database Entry Error! Try again";
            session.setAttribute("registerState", registerState);
            // URL to go back to
            String myUrl = "register.jsp";
            try
            {
              // redirect back to new URL with new data
              request.getRequestDispatcher(myUrl).forward(request, response);
            }
            catch (Exception ex)
            {
              request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
            }
          }
          else
          {
            // set the user name session attribute
            session.setAttribute("userName", idEmail);
            // URL to go back to
            String myUrl = "/WEB-INF/profilePage.jsp";
            try
            {
              // redirect back to new URL with new data
              request.getRequestDispatcher(myUrl).forward(request, response);
            }
            catch (Exception ex)
            {
              request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
            }
          }
        }
      }
    }
    else if (userPath.equals("/insertProfile"))
    {
      // get user name session attribute
      String idEmail
        = (String) session.getAttribute("userName");
      // read the form entries
      String fname = request.getParameter("fname");
      String address = request.getParameter("address");
      String city = request.getParameter("city");
      String state = request.getParameter("state");
      String zip = request.getParameter("zip");

      // check for error in zip code entry
      try
      {
        // see if the string zip parses to an integer
        Integer.parseInt(zip);
      }
      catch (Exception e)
      {
        // did not parse, display error message
        errZip = "Please enter 5 digits for zip code";
        session.setAttribute("errZip", errZip);
        String myUrl = "/WEB-INF/profilePage.jsp";
        try
        {
          // redirect back to new URL with new data
          request.getRequestDispatcher(myUrl).forward(request, response);
        }
        catch (Exception ex)
        {
          request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
        }
      }
      // insert into profile table
      String dbName = "Fuel";
      String tableName = "Profile";
      DataAccess dbObj = new DataAccess(dbName);
      dbObj.insertProfile(dbName, tableName,
        idEmail, fname, address, city, state, zip);
      dbObj.closeDbConn();
      String myUrl = "/WEB-INF/dashboard.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    else if (userPath.equals("/updateProfile"))
    {
      // get user name session attribute
      String idEmail
        = (String) session.getAttribute("userName");
      // read the form entries
      String fname = request.getParameter("fname");
      String address = request.getParameter("address");
      String city = request.getParameter("city");
      String state = request.getParameter("state");
      String zip = request.getParameter("zip");

      // check for error in zip code entry
      try
      {
        // see if the string zip parses to an integer
        Integer.parseInt(zip);
      }
      catch (Exception e)
      {
        // did not parse, display error message
        errZip = "Please enter 5 digits for zip code";
        session.setAttribute("errZip", errZip);
        String myUrl = "/WEB-INF/editProfile.jsp";
        try
        {
          // redirect back to new URL with new data
          request.getRequestDispatcher(myUrl).forward(request, response);
        }
        catch (Exception ex)
        {
          request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
        }
      }
      // update the profile table
      String dbName = "Fuel";
      String tableName = "Profile";
      DataAccess dbObj = new DataAccess(dbName);
      dbObj.updateProfile(dbName, tableName,
        idEmail, fname, address, city, state, zip);
      dbObj.closeDbConn();
      String myUrl = "/WEB-INF/dashboard.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }    
    
    
    
    else if (userPath.equals("/getQuote"))
    {
      String idEmail
        = (String) session.getAttribute("userName");
      String gallonStr = request.getParameter("gallons");
      int gallonInt=0;
      try
      {
        gallonInt = Integer.parseInt(gallonStr);
      }
      catch (Exception e)
      {
        errGallons = "Not a whole number! Please re-enter  "
          + "gallons as a whole number without decimal points";
        session.setAttribute("errMessage", errGallons);
        String myUrl = "/WEB-INF/fuelQuoteForm.jsp";
        try
        {
          // redirect back to new URL with new data
          request.getRequestDispatcher(myUrl).forward(request, response);
        }
        catch (Exception ex)
        {
          request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
        }
      }
      Quote objQuote = new Quote();
      double[] quoteData = 
        objQuote.calculateQuote(gallonInt,idEmail);
//      double[] quoteData = new double[2];
//      quoteData[0] = 1.85;
//      quoteData[1] = 185.0;
      session.setAttribute("gallonsRequested", gallonStr);
      session.setAttribute("gallonPrice",
        (Double) quoteData[0]);
      session.setAttribute("totalPrice",
        (Double) quoteData[1]);
      String myUrl = "/WEB-INF/fuelQuoteSubmit.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    else if (userPath.equals("/recordQuote"))
    {
      // get session attributes to insert into db
      String idEmail
        = (String) session.getAttribute("userName");
      String gallonStr
        = (String) session.getAttribute("gallonsRequested");
      int gallons = Integer.parseInt(gallonStr);
      double gallonPrice
        = (Double) session.getAttribute("gallonPrice");
      double totalPrice
        = (Double) session.getAttribute("totalPrice");

      //db info
      String dbName = "Fuel";
      String tableName = "QuoteHistory";
      DataAccess dbObj = new DataAccess(dbName);

      // insert into quoteHistory table
      dbObj.insertQuote(dbName, tableName, idEmail, gallons,
        gallonPrice, totalPrice);
      dbObj.closeDbConn();

      String myUrl = "/WEB-INF/dashboard.jsp";
      try
      {
        // redirect back to new URL with new data
        request.getRequestDispatcher(myUrl).forward(request, response);
      }
      catch (Exception ex)
      {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
      }
    }
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo()
  {
    return "Short description";
  }// </editor-fold>

}
