<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Fuel Quote Entry</title>
    <link rel="stylesheet" href="loginstyle.css" />
    <!-- icons for envelope and lock -->
    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />
  </head>
  <body>
    <header>
      <div class="tab">
        <a class="tabcontent" href="dashboard">Home</a>
        <a class="tabcontent" href="profile">Profile</a>
        <a class="tabcontent" href="fuelQuote">Fuel Quote</a>
        <a class="tabcontent" href="quoteHistory">Fuel Quote History</a>
        <a class="tabcontent" href="logout">Logout</a>
      </div>
    </header>
    <br>
    <h1>Fuel Quote Form</h1>
    <h3>${errMessage}</h3>
    <br>
    <h2>Get Quote First, if agree then press Submit to record the quote</h2>

    <form action="getQuote" method="POST">
      <div>
        <label for="gallons">Gallons Requested (no decimal): </label>
        <input type="text" name="gallons" id="gallons" required />
        <br>
        <label>Price Per Gallon: (Click Get Quote)</label>
        <br>
        <label>Total Price: (Click Get Quote)</label>
        <br>
        <input class="editTab" type="submit"
               value="Get Quote">
        <br><br>
        <span title="Get Quote First" class="editTab">Submit</span>
      </div>          
    </form>
  </body>
</html>
