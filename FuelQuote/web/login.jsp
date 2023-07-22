
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Client Login</title>
    <link rel="stylesheet" href="loginstyle.css" />
    <!-- icons for envelope and lock -->
    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />
  </head>
  <body>
    <section>
      <br><br><br>
      <div class="form-box-login">
        <form action="login" method="POST">
          <h2>Login</h2>
          <br>
          <h3>${loginState}</h3>
          <br>
          <div>
            <input type="email" placeholder="Enter your email" 
                   name="idEmail" id ="idEmail"required />
            <i class="uil uil-envelope-alt email"></i>
          </div>
          <div>
            <input type="password" placeholder="Enter your password" 
                   name="password" id="password" required />
            <i class="uil uil-lock password"></i>
            <i class="uil uil-eye-slash pw_hide"></i>
          </div>
          <input class="button" type="submit" value="Login Now">
          <div class="login_register">
            &nbsp; Don't have an account? 
            <a href="registerRedirect">Register</a> &nbsp;
          </div>
        </form>
      </div>
    </section>
  </body>
</html>
