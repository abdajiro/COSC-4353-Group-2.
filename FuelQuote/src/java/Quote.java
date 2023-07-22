// Ariya Ansari
// this class has the modules necessary to get the data
// and calculate the fuel quote to be displayed

import java.util.ArrayList;

public class Quote
{
  // calculate quote after cking location & history
  public double[] calculateQuote(int gallons, String userName)
  {
    final double REFINERY_PRICE = 1.5;
    final double PROFIT_MARGIN = 0.1;
    final int GALLONS_VOLUME = 1000;
    double locationCharge = 0.04;
    double historyDisc = 0;
    double gallonsCharge = 0.03;
    double gallonPrice = 0;
    double totalPrice = 0;
    double[] quoteData = new double[2];

    // check the location for discount
    locationCharge = checkLocation(userName);
    if (checkHistory(userName))
    {
      // IN-STATE
      historyDisc = 0.01;
    }
    // check the gallons threshold for discount
    if (gallons > GALLONS_VOLUME)
    {
      gallonsCharge = 0.02;
    }
    // calculate price per gallon
    gallonPrice = REFINERY_PRICE
      + (REFINERY_PRICE * (PROFIT_MARGIN + locationCharge
      - historyDisc + gallonsCharge));
    quoteData[0] = gallonPrice;
    // calculate total price
    quoteData[1] = gallonPrice * gallons;
    return quoteData;
  }

  public static boolean checkHistory(String userName)
  {
    // db info
    String dbName = "Fuel";
    String tableName = "QuoteHistory";
    String[] tableHeaders =
    {
      "idEmail", "fullName", "gallons", "gallonPrice",
      "totalPrice", "quoteDate"
    };
    DataAccess objDb = new DataAccess(dbName);
    ArrayList<ArrayList<String>> data
      = objDb.getData(tableName, tableHeaders, userName);
    objDb.closeDbConn();
    // check the size of data returned from db
    //if empty return true, else return false;
    return !(data.isEmpty());
  }

  public static double checkLocation(String userName)
  {
    final double OUT_STATE = 0.04;
    final double IN_STATE = 0.02;
    final String LOCAL = "TX";
    String location;
    // db info
    String dbName = "Fuel";
    String tableName = "Profile";
    String[] tableHeaders =
    {
      "idEmail", "fullName", "address", "city",
      "addressState", "zip"
    };

    DataAccess objDb = new DataAccess(dbName);
    ArrayList<ArrayList<String>> data
      = objDb.getData(tableName, tableHeaders, userName);
    objDb.closeDbConn();
    if (data.isEmpty())
    {
      return OUT_STATE;
    }
    location = data.get(0).get(4);
    if (location.equalsIgnoreCase(LOCAL))
    {
      return IN_STATE;
    }
    else
    {
      return OUT_STATE;
    }
  }

  public static void testCalculateQuote()
  {
    Quote objQuote = new Quote();
    double[] data = objQuote.calculateQuote(1500,
      "joe@gmail.com");
    System.out.println(data[0]);
    System.out.println(data[1]);
  }

  public static void main(String[] args)
  {
    testCalculateQuote();
  }
}
