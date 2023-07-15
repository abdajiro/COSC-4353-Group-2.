

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Client Register</title>
    <link rel="stylesheet" href="loginstyle.css" />
    <!-- icons for envelope and lock -->
    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />
  </head>
  <body>
    <br><br><br>
    <div class="form-box-register">

      <form action="register" method="POST">
        <h2>Register</h2>
        <br>
        <h3>${registerState}</h3>
        <br>
        <div>
          <input type="email" placeholder="Enter your email" 
                 name="name" id ="name" required />
          <i class="uil uil-envelope-alt email"></i>
        </div>
        <div>
          <input type="password" placeholder="Create password" 
                 name="password" id="password" required />
          <i class="uil uil-lock password"></i>
          <i class="uil uil-eye-slash pw_hide"></i>
        </div>
        <div>
          <input type="password" placeholder="Confirm password" 
                 name="cpassword" id="cpassword" required />
          <i class="uil uil-lock password"></i>
          <i class="uil uil-eye-slash pw_hide"></i>
        </div>
        <input class="button" type="submit" value="Register Now">
        <div class="login_register">Have an account? 
          <a href="index.jsp">Login</a></div>
      </form>
    </div>
  </body>
</html>
