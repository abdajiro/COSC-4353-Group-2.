// Ariya Ansari

public class InstallDb
{

    public static void main(String[] args)
    {
        String dbName = "Fuel";
        DataAccess dbObj = new DataAccess();
        //dbObj.createDb(dbName);
        // create table
        String newTable = "CREATE TABLE Client (Name varchar(255),"
            + "Password varchar(255) )";
        dbObj.createTable(newTable, dbName);
    }
}
