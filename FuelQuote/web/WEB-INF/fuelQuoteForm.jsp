

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
      </div>
    </header>
    <br><br>
    <h1>Fuel Quote Form</h1>
    <br>
    <h3>${errMessage}</h3>
    <br>
    <form action="quoteCalculation" method="POST">
      <div>
        <label for="gallons">Gallons Requested (no decimal): </label>
        <input type="text" name="gallons" id="gallons" required />
        <br>
        <input type="submit" value="Submit">
      </div>          
    </form>
  </body>
</html>
