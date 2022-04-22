<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

  <title>Sign Up</title>
</head>
<body>

  <header>
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand">Flashcards</a>
        </div>
        <ul class="nav navbar-nav">
          <li class="active"><a href="home">Home</a></li>
          <li><a href="#">About</a></li>
          <li><a href="#">Contact</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li><a href="register"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
          <li><a href="login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
        </ul>
      </div>
    </nav>
  </header>

  <div class="container">
    <form class="form-signup" method="post" action="/register">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign Up</h1><br />
      <c:if test="${not empty requestScope.error}">
        <strong>Error! </strong> <c:out value="${requestScope.error}"/><br />
      </c:if>
      <label for="inputUsername" class="sr-only">Username</label>
      <input type="text" id="inputUsername" class="form-control" placeholder="Username" required="" autofocus="" name="username">

      <label for="inputEmail" class="sr-only">Username</label>
      <input type="email" id="inputEmail" class="form-control" placeholder="E-mail" required="" autofocus="" name="email">

      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" id="inputPassword" class="form-control" placeholder="Password" required="" name="password">

      <label for="repeatPassword" class="sr-only">Repeat password</label>
      <input type="password" id="repeatPassword" class="form-control" placeholder="Repeat password" required="" name="repeatedPassword">
      <button class="btn btn-lg btn-primary btn-block" type="submit">Sign Up</button>
    </form>
  </div>


  <footer class="bg-light text-center text-lg-start">
    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
      &copy 2022 Copyright: Radoslaw Gredel
    </div>
  </footer>

</body>
</html>