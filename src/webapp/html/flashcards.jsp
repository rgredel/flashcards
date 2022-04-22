<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <title>Flashcards</title>
</head>
<body>

<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Flashcards</a>
            </div>
            <ul class="nav navbar-nav">
                <li><a href="home">Home</a></li>
                <li><a href="decks">Decks</a></li>
                <li class="dropdown active">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Flashcards
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="flashcards">My Flashcards</a></li>
                        <li><a href="flashcards">Public Flashcards</a></li>
                    </ul>
                </li>
                <li><a href="#">About</a></li>
                <li><a href="#">Contact</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon-user"></span> <h3><%=session.getAttribute("username")%></h3> </a></li>
                <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>
            </ul>
        </div>
    </nav>
</header>
<div class="container">
    <h2>Striped Rows</h2>
    <p>The .table-striped class adds zebra-stripes to a table:</p>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Title</th>
            <th>Question</th>
            <th>Answer</th>
            <th>Category</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${requestScope.flashcards}" var="flashcard">
                <tr>
                    <td><c:out value="${flashcard.title}"/></td>
                    <td><c:out value="${flashcard.question}"/></td>
                    <td><c:out value="${flashcard.answer}"/></td>
                    <td><c:out value="${flashcard.category.name}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<footer class="bg-light text-center text-lg-start">
    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        &copy 2022 Copyright: Radoslaw Gredel
    </div>
</footer>

</body>
</html>