
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Profile Page</title>
    <link rel="stylesheet" href="loginstyle.css" />
    <!-- icons for envelope and lock -->
    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />
  </head>
  <body>
    <form action="insertProfile" method="POST">
      <div class="container">
        <h1>Client Profile Page</h1>
        <hr>
        <br>
        <h3>${errZip}</h3>
        <br>
        <label for="fname"><b>Full Name</b></label>
        <input type="text" placeholder="Enter Full Name" name="fname" id="fname"
               maxlength="50" required>
        <br><br>
        <label for="addy1"><b>Address 1</b></label>
        <input type="text" placeholder="Enter Address 1" name="addy1" maxlength="100" required>
        <br><br>
        <label for="addy2"><b>Address 2</b></label>
        <input type="text" placeholder="Enter Address 2" name="addy2" id="addy2" 
               maxlength="100">
        <br><br>
        <label for="city"><b>City</b></label>
        <input type="text" placeholder="Enter City" name="city" id="city" 
               maxlength="100" required>
        <br><br>
        <label for="state"><b>State:</b></label> 
        <select name="state" id="state" required> 
          <option value="">n/a</option>
          <option value="AL">AL</option>
          <option value="AK">AK</option>
          <option value="AZ">AZ</option>
          <option value="AR">AR</option>
          <option value="CA">CA</option>
          <option value="CZ">CZ</option>
          <option value="CO">CO</option>
          <option value="CT">CT</option>
          <option value="DE">DE</option>
          <option value="DC">DC</option>
          <option value="FL">FL</option>
          <option value="GA">GA</option>
          <option value="GU">GU</option>
          <option value="HI">HI</option>
          <option value="ID">ID</option>
          <option value="IL">IL</option>
          <option value="IN">IN</option>
          <option value="IA">IA</option>
          <option value="KS">KS</option>
          <option value="KY">KS</option>
          <option value="LA">LA</option>
          <option value="ME">ME</option>
          <option value="MD">MD</option>
          <option value="MA">MA</option>
          <option value="MI">MI</option>
          <option value="MN">MN</option>
          <option value="MS">MS</option>
          <option value="MO">MO</option>
          <option value="MT">MT</option>
          <option value="NE">NE</option>
          <option value="NV">NV</option>
          <option value="NH">NH</option>
          <option value="NJ">NJ</option>
          <option value="NM">NM</option>
          <option value="NY">NY</option>
          <option value="ND">ND</option>
          <option value="OH">OH</option>
          <option value="OK">OK</option>
          <option value="OR">OR</option>
          <option value="PA">PA</option>
          <option value="PR">PR</option>
          <option value="RI">RI</option>
          <option value="SC">SC</option>
          <option value="SD">SD</option>
          <option value="TN">TN</option>
          <option value="TX">TX</option>
          <option value="UT">UT</option>
          <option value="VT">VT</option>
          <option value="VI">VI</option>
          <option value="VA">VA</option>
          <option value="WA">WA</option>
          <option value="WV">WV</option>
          <option value="WI">WI</option>
          <option value="WY">WY</option>
        </select>
        <br><br>
        <label for="zip"><b>Zip code</b></label>
        <input type="text" placeholder="Enter Zipcode" name="zip" id="zip" 
               minlength="5" maxlength="5" required>
        <br><br>
        <div class="clearfix">
          <button type="submit" class="submitbtn">Submit</button>
        </div>
      </div>      
    </form>
  </body>
</html>
