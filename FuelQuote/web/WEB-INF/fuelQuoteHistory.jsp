
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Fuel Quote History</title>
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
    <h1>Fuel Quote History</h1>
    <table>
      <tr>
        <th>Email</th>
        <th>Client Name</th>
        <th>Gallons Requested</th>
        <th>Price per Gallon</th>
        <th>Total price</th>
        <th>Quote Date</th>
        <!-- Add more table headers for other fields -->
      </tr>
      <c:forEach var = "row" items = "${data}">
        <tr>
          <td><c:out value = "${row.get(0)}"/></td>
          <td><c:out value = "${row.get(1)}"/></td>
          <td><c:out value = "${row.get(2)}"/></td>
          <td><c:out value = "${row.get(3)}"/></td>
          <td><c:out value = "${row.get(4)}"/></td>
          <td><c:out value = "${row.get(5)}"/></td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
