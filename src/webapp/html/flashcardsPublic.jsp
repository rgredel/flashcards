<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Public Flashcards</title>
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
                                <li><a href="/flashcards">My Flashcards</a></li>
                                <li><a href="/flashcards/public">Public Flashcards</a></li>
                            </ul>
                        </li>
                        <li><a href="#">About</a></li>
                        <li><a href="#">Contact</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#"><span class="glyphicon glyphicon-user"></span> <c:out value="${sessionScope.username}"/> </a></li>
                        <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>
                    </ul>
                </div>
            </nav>
        </header>
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-8"><h2>Public flashcards</h2></div>
                    </div>
                </div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Question</th>
                    <th>Answer</th>
                    <th>Category</th>
                    <th>Level</th>
                    <th>Scope</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.publicFlashcards}" var="flashcard">
                        <script>
                            $(document).ready(function(){
                                $("#addToDeckBtn<c:out value="${flashcard.id}"/>").click(function(){
                                    $("#addToDeckModal<c:out value="${flashcard.id}"/>").modal();
                                });
                            });
                        </script>
                        <div class="modal fade" id="addToDeckModal<c:out value="${flashcard.id}"/>" role="dialog">
                            <div class="modal-dialog">

                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 style="color:dodgerblue;" > Add <c:out value="${flashcard.title}"/> to deck</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form role="form" method="post" action="/flashcards/addToDeck">
                                            <input hidden name="flashcardId" value="<c:out value="${flashcard.id}"/>"/>
                                            <input hidden name="redirectPath" value="/flashcards/public"/>
                                            <div class="form-group">
                                                <label for="deck">Deck</label>
                                                <select class="form-control" id="deck" name="deckId">
                                                    <c:forEach items="${requestScope.decks}" var="deck">
                                                        <option value="<c:out value="${deck.id}"/>">
                                                            <c:out value="${deck.name}"/>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-lg btn-primary btn-block">Add</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <tr>
                            <td><c:out value="${flashcard.title}"/></td>
                            <td><c:out value="${flashcard.question}"/></td>
                            <td><c:out value="${flashcard.answer}"/></td>
                            <td><c:out value="${flashcard.category.name}"/></td>
                            <td><c:out value="${flashcard.level}"/></td>
                            <td>
                                <c:choose>
                                <c:when test="${flashcard.APublic}">
                                    Public
                                </c:when>
                                <c:otherwise>
                                    Private
                                </c:otherwise>
                            </c:choose>
                            </td>
                            <td>
                                <ul class="list-inline m-0">
                                    <li class="list-inline-item">
                                        <button class="btn btn-primary btn-sm rounded-0" type="button" data-toggle="tooltip" data-placement="top" title="Add to deck" id="addToDeckBtn<c:out value="${flashcard.id}"/>"><i class="fa fa-table"></i></button>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </div>
        </div>
        <footer class="bg-light text-center text-lg-start">
            <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
                &copy 2022 Copyright: Radoslaw Gredel
            </div>
        </footer>
    </body>
</html>