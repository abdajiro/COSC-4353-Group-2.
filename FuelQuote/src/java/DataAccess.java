// Ariya Ansari
// modules to validate login, check user name to register,
// register a new user, get fuel history,
// and other related modules to access database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

  // to access the history for a client
  public ArrayList<ArrayList<String>> getHistory(String tableName,
                                                 String[] tableHeaders,
                                                 String client)
  {
    this.data = new ArrayList<>();
    
/***********************************
 * hard coding values
 * true database access to be implemented later 
 * (similar to getData method)
 * *********************************/

    client = "Joe Smith";
    String gallons = "20";
    String price = "$3.95";
    String delAddress = "11 Washington, Houston, TX, 77004";
    String delDate = "04/20/2023";
    ArrayList<String> manualRow = new ArrayList<>();
    manualRow.add(client);
    manualRow.add(gallons);
    manualRow.add(price);
    manualRow.add(delAddress);
    manualRow.add(delDate);
    data.add(manualRow);
    client = "Joe Smith";
    gallons = "40";
    price = "$3.75";
    delAddress = "33 Jones, Houston, TX, 77024";
    delDate = "05/20/2023";
    manualRow = new ArrayList<>();
    manualRow.add(client);
    manualRow.add(gallons);
    manualRow.add(price);
    manualRow.add(delAddress);
    manualRow.add(delDate);
    data.add(manualRow);

    return data;
  }
  
  // to access the history for a client
  public ArrayList<ArrayList<String>> getProfile(String tableName,
                                                 String[] tableHeaders,
                                                 String clientEmail)
  {
    this.data = new ArrayList<>();
    
/***********************************
 * hard coding values
 * true database access to be implemented later 
 * (similar to getData method)
 * *********************************/

    String fname = "Joe Smith";
    clientEmail = "joe@gmail.com";
    String address1 = "11 Washington";
    String address2 = "";
    String city = "Houston";
    String state = "TX";
    String zip = "77004";
    ArrayList<String> manualRow = new ArrayList<>();
    manualRow.add(fname);
    manualRow.add(clientEmail);
    manualRow.add(address1);
    manualRow.add(address2);
    manualRow.add(city);
    manualRow.add(state);
    manualRow.add(zip);
    data.add(manualRow);
    
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
      + " WHERE Name=? AND Password=?";
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
      + " WHERE Name=?";
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
    String dbQuery = "INSERT INTO Client VALUES (?,?)";
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

  // to test registering a new user (register module)
  public static void testRegister()
  {
    String name = "roe@gmail.com";
    String password = "567";
    // db info
    String dbName = "Fuel";
    String tableName = "Client";

    DataAccess objDb = new DataAccess(dbName);
    boolean status = objDb.register(dbName, tableName,
      name, password);
    System.out.println(status);
  }

    // to test if a new user exists
    // checking email in database for checkUserName module
  public static void testCheckUserName()
  {
    String name = "roe@gmail.com";
    // db info
    String dbName = "Fuel";
    String tableName = "Client";

    DataAccess objDb = new DataAccess(dbName);
    boolean status = objDb.checkUserName(dbName, tableName,
      name);
    System.out.println(status);
  }
  // to test a login (validate module)
  public static void testValidate()
  {
    String name = "joe@gmail.com";
    String password = "456";
    // db info
    String dbName = "Fuel";
    String tableName = "Client";

    DataAccess objDb = new DataAccess(dbName);
    boolean status = objDb.validate(dbName, tableName,
      name, password);
    System.out.println(status);
  }

    // to test getHistory module
  public static void testGetHistory()
  {

    // db info
    String dbName = "Fuel";
    String tableName = "FuelHistory";
    String[] tableHeaders = {"FuelHistory Columns"};
    String client = "Joe Smith";

    DataAccess objDb = new DataAccess(dbName);
    ArrayList<ArrayList<String>> historyData = 
      objDb.getHistory(tableName, tableHeaders, client);
    System.out.println(historyData);
  }
  
    // to test getProfile module
  public static void testGetProfile()
  {

    // db info
    String dbName = "Fuel";
    String tableName = "Profile";
    String[] tableHeaders = {"Profile Columns"};
    String clientEmail = "joe@gmail.com";

    DataAccess objDb = new DataAccess(dbName);
    ArrayList<ArrayList<String>> profileData = 
      objDb.getHistory(tableName, tableHeaders, clientEmail);
    System.out.println(profileData);
  }
  
  public static void testInsertUser()
  {
    // db info
    String dbName = "Fuel";
    String tableName = "Client";
    String[] columnNames =
    {
      "Name", "Password"
    };
    // insert query
    String dbQuery = "INSERT INTO Client VALUES (?,?)";
    // connect to db
    DataAccess objDb = new DataAccess(dbName);
    Connection myDbConn = objDb.getDbConn();

    // read the data
    String readName = "joe@gmail.com";
    String readPassword = "456";

    // insert into Database
    try
    {
      // prepare statement
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      // enter data into query
      ps.setString(1, readName);
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
    testValidate();
  }
  
}
