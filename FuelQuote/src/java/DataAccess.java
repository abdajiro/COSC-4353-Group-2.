// Ariya Ansari
// modules to validate login, check user name to register,
// register a new user, get data for profile & fuel history,
// insert into profile & quote history tables
// and other related modules to access database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataAccess
{
// attributes

  private String dbName;
  private Connection dbConn;
  private ArrayList<ArrayList<String>> data;

// 2 constructors for existing and new database
  public DataAccess()
  {
    dbName = "";
    dbConn = null;
    data = null;
  }

  public DataAccess(String dbName)
  {
    setDbName(dbName);
    setDbConn();
    data = null;
  }

// get & set methods
  public String getDbName()
  {
    return dbName;
  }

  public void setDbName(String dbName)
  {
    this.dbName = dbName;
  }

  public Connection getDbConn()
  {
    return dbConn;
  }

  public void setDbConn()
  {
    String connectionURL = "jdbc:mysql://localhost:3306/"
      + this.dbName + "?autoReconnect=true&useSSL=false";
    this.dbConn = null;
    try
    {
      Class.forName("com.mysql.cj.jdbc.Driver");
      this.dbConn = DriverManager.getConnection(connectionURL,
        "root", "mysql1");
    }
    catch (ClassNotFoundException ex)
    {
      System.out.println("Driver not found, check library");
    }
    catch (SQLException err)
    {
      System.out.println("SQL Connection error.");
    }
  }

  public ArrayList<ArrayList<String>> getData(String tableName,
                                              String[] tableHeaders)
  {
    int columnCount = tableHeaders.length;
    Statement s = null;
    ResultSet rs = null;
    String dbQuery = "SELECT * FROM " + tableName;
    this.data = new ArrayList<>();
    // read the data
    try
    {
      // send the query and receive data
      s = this.dbConn.createStatement();
      rs = s.executeQuery(dbQuery);

      // read the data using rs and store in ArrayList data        
      while (rs.next())
      {
        // row object to hold one row data
        ArrayList<String> row = new ArrayList<>();
        // go through the row and read each cell
        for (int i = 0; i < columnCount; i++)
        {
          // read cell i
          // example: String cell = rs.getString("Name");
          // reads the cell in column Name
          // tableHeader={"Name", "Age", "Color"}
          String cell = rs.getString(tableHeaders[i]);
          // add the cell to the row
          // example row.add("Vinny");
          row.add(cell);
        }
        // add the row to the data
        // example: data.add "Vinny",15,"Pink"
        this.data.add(row);
      }
    }
    catch (SQLException se)
    {
      System.out.println("SQL Error: Not able to get data");
    }
    return data;
  }

  public void setData(ArrayList<ArrayList<String>> data)
  {
    this.data = data;
  }

// create new database
  public void createDb(String newDbName)
  {
    setDbName(newDbName);
    Connection newConn;
    String connectionURL = "jdbc:mysql://localhost:3306/";
    String query = "CREATE DATABASE " + this.dbName;
    try
    {
      Class.forName("com.mysql.cj.jdbc.Driver");
      newConn = DriverManager.getConnection(connectionURL, "root", "mysql1");
      Statement s = newConn.createStatement();
      s.executeUpdate(query);
      System.out.println("New database created.");
      newConn.close();
    }
    catch (ClassNotFoundException ex)
    {
      System.out.println("Driver not found, check library");
    }
    catch (SQLException se)
    {
      System.out.println("SQL Connection error, Db was not created!");
    }
  }

// create new table
  public void createTable(String newTable, String dbName)
  {
    System.out.println(newTable);
    setDbName(dbName);
    setDbConn();
    Statement s;
    try
    {
      s = this.dbConn.createStatement();
      s.execute(newTable);
      System.out.println("New table created.");
      this.dbConn.close();
    }
    catch (SQLException err)
    {
      System.out.println("Error creating table " + newTable);
    }
  }

// close db connection
  public void closeDbConn()
  {
    try
    {
      this.dbConn.close();
    }
    catch (Exception err)
    {
      System.out.println("DB closing error.");
    }
  }

  // to access data (history or profile) for a client
  public ArrayList<ArrayList<String>> getData(String tableName,
                                              String[] tableHeaders,
                                              String userName)
  {
    int columnCount = tableHeaders.length;
    Statement s = null;
    ResultSet rs = null;
    String dbQuery = "SELECT * FROM " + tableName
      + " WHERE idEmail='" + userName + "'";
    this.data = new ArrayList<>();
    // read the data
    try
    {
      // send the query and receive data
      s = this.dbConn.createStatement();
      rs = s.executeQuery(dbQuery);

      // read the data using rs and store in ArrayList data        
      while (rs.next())
      {
        // row object to hold one row data
        ArrayList<String> row = new ArrayList<>();
        // go through the row and read each cell
        for (int i = 0; i < columnCount; i++)
        {
          // read cell i
          // example: String cell = rs.getString("Name");
          // reads the cell in column Name
          // tableHeader={"Name", "Age", "Color"}
          String cell = rs.getString(tableHeaders[i]);
          // add the cell to the row
          // example row.add("Vinny");
          row.add(cell);
        }
        // add the row to the data
        // example: data.add "Vinny",15,"Pink"
        this.data.add(row);
      }
    }
    catch (SQLException se)
    {
      System.out.println("SQL Error: Not able to get data");
    }
    return data;
  }

  // to validate login info
  public boolean validate(String dbName,
                          String tableName,
                          String userName,
                          String password)
  {
    // connect to db
    DataAccess objDb = new DataAccess(dbName);
    Connection myDbConn = objDb.getDbConn();

    String dbQuery = "SELECT * FROM " + tableName
      + " WHERE idEmail=? AND password=PASSWORD(?)";
    boolean status = false;
    try
    {
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      ps.setString(1, userName);
      ps.setString(2, password);
      ResultSet rs = ps.executeQuery();
      status = rs.next();
      myDbConn.close();
    }
    catch (SQLException se)
    {
      System.out.println("Sql validate error!");
    }
    return status;
  }

  // to check if a registering user already exists
  public boolean checkUserName(String dbName,
                               String tableName,
                               String userName)
  {
    userName = userName.toLowerCase();
    // connect to db
    DataAccess objDb = new DataAccess(dbName);
    Connection myDbConn = objDb.getDbConn();
    String dbQuery = "SELECT * FROM " + tableName
      + " WHERE idEmail=?";
    boolean status = false;
    try
    {
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      ps.setString(1, userName);
      ResultSet rs = ps.executeQuery();
      status = rs.next();
      myDbConn.close();
    }
    catch (SQLException se)
    {
      System.out.println("Sql validate error!");
    }
    return status;
  }

  // to register a new user
  public boolean register(String dbName,
                          String tableName,
                          String userName,
                          String password)
  {
    userName = userName.toLowerCase();
    // insert query
    String dbQuery = "INSERT INTO " + tableName
      + " VALUES (?,PASSWORD(?))";
    // connect to db
    DataAccess objDb = new DataAccess(dbName);
    Connection myDbConn = objDb.getDbConn();
    // insert into Database
    try
    {
      // prepare statement
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      // enter data into query
      ps.setString(1, userName);
      ps.setString(2, password);
      // execute the query
      ps.executeUpdate();
      System.out.println("Data inserted successfully");
      return true;
    }
    catch (SQLException se)
    {
      System.out.println("Error inserting data");
      return false;
    }
  }

  // to insert a new profile
  public boolean insertProfile(String dbName,
                               String tableName,
                               String idEmail,
                               String fname,
                               String address,
                               String city,
                               String state,
                               String zip)
  {
    // insert query
    String dbQuery = "INSERT INTO " + tableName
      + " VALUES (?,?,?,?,?,?)";
    // connect to db
    DataAccess objDb = new DataAccess(dbName);
    Connection myDbConn = objDb.getDbConn();
    // insert into Database
    try
    {
      // prepare statement
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      // enter data into query
      ps.setString(1, idEmail);
      ps.setString(2, fname);
      ps.setString(3, address);
      ps.setString(4, city);
      ps.setString(5, state);
      ps.setString(6, zip);
      // execute the query
      ps.executeUpdate();
      System.out.println("Data inserted successfully");
      return true;
    }
    catch (SQLException se)
    {
      System.out.println("Error inserting data");
      return false;
    }
  }

  
  // to update a profile
  public boolean updateProfile(String dbName,
                               String tableName,
                               String idEmail,
                               String fname,
                               String address,
                               String city,
                               String state,
                               String zip)
  {
    // insert query
    String dbQuery = "UPDATE " + tableName
      + " SET fullName = ?, "
      + " address = ?, "
      + " city = ?, "
      + " addressState = ?, "
      + " zip = ? "
      + " WHERE idEmail = ?";
    // connect to db
    DataAccess objDb = new DataAccess(dbName);
    Connection myDbConn = objDb.getDbConn();
    // insert into Database
    try
    {
      // prepare statement
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      // enter data into query
      ps.setString(1, fname);
      ps.setString(2, address);
      ps.setString(3, city);
      ps.setString(4, state);
      ps.setString(5, zip);
      ps.setString(6, idEmail);
      // execute the query
      ps.executeUpdate();
      System.out.println("Data inserted successfully");
      return true;
    }
    catch (SQLException se)
    {
      System.out.println("Error inserting data");
      return false;
    }
  }  
  
  
  // to insert Quote
  public boolean insertQuote(String dbName,
                             String tableName,
                             String idEmail,
                             int gallons,
                             double gallonPrice,
                             double totalPrice)
  {
    // insert query with current date function
    String dbQuery = "INSERT INTO " + tableName
      + " VALUES (?,?,?,?,?,CURDATE())";
    // connect to db
    DataAccess objDb = new DataAccess(dbName);
    Connection myDbConn = objDb.getDbConn();
    // read full name of the user from profile
    String[] tableHeaders =
    {
      "idEmail", "fullName", "address",
      "city", "addressState", "zip"
    };
    ArrayList<ArrayList<String>> data
      = objDb.getData("Profile", tableHeaders,
        idEmail);
    String fname = data.get(0).get(1);
    // insert into Database
    try
    {
      // prepare statement
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      // enter data into query
      ps.setString(1, idEmail);
      ps.setString(2, fname);
      ps.setInt(3, gallons);
      ps.setDouble(4, gallonPrice);
      ps.setDouble(5, totalPrice);
      // execute the query
      ps.executeUpdate();
      System.out.println("Data inserted successfully");
      return true;
    }
    catch (SQLException se)
    {
      System.out.println("Error inserting data");
      return false;
    }
  }

  public static void testInsertQuote()
  {
    String dbName = "Fuel";
    String tableName = "QuoteHistory";
    String[] tableHeaders =
    {
      "idEmail", "fullName", "gallons", "gallonPrice",
      "totalPrice", "quoteDate"
    };
    String userName = "joe@gmail.com";
    DataAccess dbObj = new DataAccess(dbName);
//    dbObj.insertQuote(dbName, tableName,
//      userName, 1500, 1.71, 2565.0);
    ArrayList<ArrayList<String>> data
      = dbObj.getData(tableName, tableHeaders, userName);
    dbObj.closeDbConn();
    System.out.println(data);
    for (int r = 0; r < data.size();r++)
    {
      ArrayList<String> row = data.get(r);
      String cdate = row.get(5);
      Date date1 = null;
      try
      {
        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(cdate);
      }
      catch (ParseException pe)
      {
        System.out.println("Data parse exception");
      }
      Format f = new SimpleDateFormat("MM-dd-yyyy");
      String curDate = f.format(date1);
      row.set(5, curDate);
      data.set(r, row);
    }
    System.out.println(data);
  }

  // to test registering a new user (register module)
  public static void testRegister()
  {
    String idEmail = "roe@gmail.com";
    String password = "567";
    // db info
    String dbName = "Fuel";
    String tableName = "Client";

    DataAccess objDb = new DataAccess(dbName);
    boolean status = objDb.register(dbName, tableName,
      idEmail, password);
    System.out.println(status);
  }

  // to test if a new user exists
  // checking email in database for checkUserName module
  public static void testCheckUserName()
  {
    String idEmail = "roe@gmail.com";
    // db info
    String dbName = "Fuel";
    String tableName = "Client";

    DataAccess objDb = new DataAccess(dbName);
    boolean status = objDb.checkUserName(dbName, tableName,
      idEmail);
    System.out.println(status);
  }

  // to test a login (validate module)
  public static void testValidate()
  {
    String idEmail = "joe@gmail.com";
    String password = "456";
    // db info
    String dbName = "Fuel";
    String tableName = "Client";

    DataAccess objDb = new DataAccess(dbName);
    boolean status = objDb.validate(dbName, tableName,
      idEmail, password);
    System.out.println(status);
  }

  // to test getHistory module
  public static void testGetHistory()
  {

    // db info
    String dbName = "Fuel";
    String tableName = "QuoteHistory";
    String[] tableHeaders =
    {
      "idEmail", "fullName", "gallons", "gallonPrice",
      "totalPrice", "quoteDate"
    };
    String idEmail = "joe@gmail.com";

    DataAccess objDb = new DataAccess(dbName);
    ArrayList<ArrayList<String>> historyData
      = objDb.getData(tableName, tableHeaders, idEmail);
    System.out.println(historyData);
  }

  // to test getProfile module
  public static void testGetProfile()
  {

    // db info
    String dbName = "Fuel";
    String tableName = "Profile";
    String[] tableHeaders =
    {
      "idEmail", "fullName", "address", "city", "addressState", "zip"
    };
    String clientEmail = "joe@gmail.com";

    DataAccess objDb = new DataAccess(dbName);
    ArrayList<ArrayList<String>> profileData
      = objDb.getData(tableName, tableHeaders, clientEmail);
    System.out.println(profileData);
  }

  public static void testInsertUser()
  {
    // db info
    String dbName = "Fuel";
    String tableName = "Client";
    String[] columnNames =
    {
      "idEmail", "password"
    };
    // insert query
    String dbQuery = "INSERT INTO Client VALUES (?,"
      + "PASSWORD(?))";
    // connect to db
    DataAccess objDb = new DataAccess(dbName);
    Connection myDbConn = objDb.getDbConn();

    // read the data
    String idEmail = "joe@gmail.com";
    String readPassword = "456";

    // insert into Database
    try
    {
      // prepare statement
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      // enter data into query
      ps.setString(1, idEmail);
      ps.setString(2, readPassword);
      // execute the query
      ps.executeUpdate();
      System.out.println("Data inserted successfully");
    }
    catch (SQLException se)
    {
      System.out.println("Error inserting data");
    }
    ArrayList<ArrayList<String>> data
      = objDb.getData(tableName, columnNames);
    System.out.println(data);
  }

  public static void main(String[] args)
  {
    // test any of the modules
    testInsertQuote();
  }

}
