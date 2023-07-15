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
import java.util.ArrayList;

@WebServlet(name = "Controller",
  loadOnStartup = 1,
  urlPatterns =
  {
    "/login", "/register", "/insertProfile",
    "/dashboard", "/profile", "/fuelQuote",
    "/quoteHistory", "/quoteCalculation", "/editProfile"
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

    if (userPath.equals("/quoteHistory"))
    {
      // read the history from the database using getHistorry module
      String dbName = "Fuel";
      String tableName = "QuoteHistory";
      String[] tableHeaders =
      {
        "history-table columns"
      };
      String client = (String) session.getAttribute("clientName");
      DataAccess objDb = new DataAccess(dbName);
      ArrayList<ArrayList<String>> data
        = objDb.getHistory(tableName, tableHeaders, client);

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
      // read the profile from the database using getProfile module
      String dbName = "Fuel";
      String tableName = "Profile";
      String[] tableHeaders =
      {
        "profile-table columns"
      };
      String clientEmail = (String) session.getAttribute("clientEmail");
      DataAccess objDb = new DataAccess(dbName);
      ArrayList<ArrayList<String>> data
        = objDb.getProfile(tableName, tableHeaders, clientEmail);

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
      // URL to go back to display in a table
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

    if (userPath.equals("/login"))
    {
      String name = request.getParameter("name");
      String password = request.getParameter("password");
      // db info
      String dbName = "Fuel";
      String tableName = "Client";
      String loginState = "";

      DataAccess objDb = new DataAccess(dbName);
      boolean status = objDb.validate(dbName, tableName,
        name, password);
      if (!status)
      {
        loginState = "Login failed! Try again";
        session.setAttribute("loginState", loginState);
        // URL to go back to
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
      else
      {
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
      String name = request.getParameter("name");
      String password = request.getParameter("password");
      String cpassword = request.getParameter("cpassword");
      // db info
      String dbName = "Fuel";
      String tableName = "Client";
      String registerState = "";

      if (!password.equals(cpassword))
      {
        // URL to go back to
        String myUrl = "register.jsp";
        registerState = "Passwords don't match, please try again";
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
        DataAccess objDb = new DataAccess(dbName);
        if (objDb.checkUserName(dbName, tableName, name))
        {
          // URL to go back to
          String myUrl = "register.jsp";
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
          boolean status = objDb.register(dbName, tableName,
            name, password);
          if (!status)
          {
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
      String fname = request.getParameter("fname");
      String addy1 = request.getParameter("addy1");
      String addy2 = request.getParameter("addy2");
      String city = request.getParameter("city");
      String state = request.getParameter("state");
      String zip = request.getParameter("zip");
      try
      {
        int zipInt = Integer.parseInt(zip);
      }
      catch (Exception e)
      {
        String errZip = "Please enter 5 digits for zip code";
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
    else if (userPath.equals("/quoteCalculation"))
    {
      String gallonStr = request.getParameter("gallons");
      try
      {
        int gallonInt = Integer.parseInt(gallonStr);
      }
      catch (Exception e)
      {
        String errMessage = "Not a whole number! Please re-enter  "
          + "gallons as a whole number without decimal points";
        session.setAttribute("errMessage", errMessage);
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
      /**
       * ********************************************************
       * // call a module from Quote class to first access data // from
       * database such as location and history and use // the gallonInt to
       * calculate quote and send back to display
       * *********************************************************
       */
      String myUrl = "/WEB-INF/quoteDisplay.jsp";
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
