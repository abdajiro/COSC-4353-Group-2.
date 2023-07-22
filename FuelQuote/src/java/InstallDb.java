// Ariya Ansari

public class InstallDb
{

    public static void main(String[] args)
    {
        String dbName = "Fuel";
        DataAccess dbObj = new DataAccess();
        dbObj.createDb(dbName);
        // create table
        String newTable1 = "CREATE TABLE Client "
          + "(idEmail varchar(255) NOT NULL,"
          + "password varchar(255) NOT NULL, "
          + "PRIMARY KEY(idEmail))";
        dbObj.createTable(newTable1, dbName);
        String newTable2 = "CREATE TABLE Profile "
          + "(idEmail varchar(255) NOT NULL, "
          + " fullName varchar(100), address varchar(100),"
          + " city varchar(100), addressState varchar(2), "
          + "zip varchar(5), PRIMARY KEY(idEmail) )";
        dbObj.createTable(newTable2, dbName);
        String newTable3 = "CREATE TABLE QuoteHistory"
          + "(idEmail varchar(255) NOT NULL,"
          + " fullName varchar(100), gallons int, "
          + "gallonPrice double, totalPrice double, "
          + "quoteDate varchar(20), PRIMARY KEY(idEmail) )";
        dbObj.createTable(newTable3, dbName);
    }
}
