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
                        <div class="col-sm-8"><h2>All flashcards</h2></div>
                        <div class="col-sm-4">
                            <button type="button" class="btn btn-info add-new" id="addBtn"><i class="fa fa-plus">Add New</i></button>

                            <!-- Modal -->
                            <div class="modal fade" id="addModal" role="dialog">
                                <div class="modal-dialog">

                                    <!-- Modal content-->
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 style="color:dodgerblue;"> New flashcard</h4>
                                        </div>
                                        <div class="modal-body">
                                            <form role="form" method="post" action="/flashcards/add">
                                                <div class="form-group">
                                                    <label for="title">Title</label>
                                                    <input type="text" class="form-control" id="title" name= "title" placeholder="Enter title">
                                                </div>
                                                <div class="form-group">
                                                    <label for="question">Question</label>
                                                    <input type="textarea" class="form-control" id="question" name="question" placeholder="Enter question">
                                                </div>
                                                <div class="form-group">
                                                    <label for="answer">Answer</label>
                                                    <input type="textarea" class="form-control" id="answer" name="answer" placeholder="Enter answer">
                                                </div>
                                                <div class="form-group">
                                                    <label for="category">Category:</label>
                                                    <select class="form-control" id="category" name="category">
                                                        <c:forEach items="${requestScope.categories}" var="category">
                                                        <option value="<c:out value="${category.id}"/>"><c:out value="${category.name}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="checkbox">
                                                    <label><input type="checkbox" value="true" id="isPublic" name="isPublic">Public</label>
                                                </div>
                                                <button type="submit" class="btn btn-lg btn-primary btn-block">Create</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
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
                    <c:forEach items="${requestScope.flashcards}" var="flashcard">
                        <script>
                            $(document).ready(function(){
                                $("#editBtn<c:out value="${flashcard.id}"/>").click(function(){
                                    $("#editModal<c:out value="${flashcard.id}"/>").modal();
                                });
                            });
                        </script>
                        <div class="modal fade" id="editModal<c:out value="${flashcard.id}"/>" role="dialog">
                            <div class="modal-dialog">

                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 style="color:dodgerblue;" > Edit flashcard</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form role="form" method="post" action="/flashcards/update">
                                            <input hidden name="flashcardId" value="<c:out value="${flashcard.id}"/>"/>
                                            <div class="form-group">
                                                <label for="newTitle">Title</label>
                                                <input type="text" class="form-control" id="newTitle" name= "newTitle" value="<c:out value="${flashcard.title}"/>">
                                            </div>
                                            <div class="form-group">
                                                <label for="newQuestion">Question</label>
                                                <input type="textarea" class="form-control" id="newQuestion" name="newQuestion" value="<c:out value="${flashcard.question}"/>">
                                            </div>
                                            <div class="form-group">
                                                <label for="newAnswer">Answer</label>
                                                <input type="textarea" class="form-control" id="newAnswer" name="newAnswer" value="<c:out value="${flashcard.answer}"/>">
                                            </div>
                                            <div class="form-group">
                                                <label for="newCategory">Category:</label>
                                                <select class="form-control" id="newCategory" name="newCategory">

                                                    <option value="<c:out value="${flashcard.category.id}"/>" checked>
                                                        <c:out value="${flashcard.category.name}"/>
                                                    </option>
                                                    <c:forEach items="${requestScope.categories}" var="category">
                                                        <option value="<c:out value="${category.id}"/>">
                                                            <c:out value="${category.name}"/>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="newLevel">Level:</label>
                                                <select class="form-control" id="newLevel" name="newLevel">
                                                    <option value="0" checked>0</option>
                                                    <option value="1" >1</option>
                                                    <option value="2" >2</option>
                                                    <option value="3" >3</option>
                                                    <option value="4" >4</option>
                                                    <option value="5" >5</option>
                                                </select>
                                            </div>

                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" value="true" id="newIsPublic" name="newIsPublic"
                                                    <c:if test="${flashcard.APublic}"> Checked </c:if>
                                                    >Public
                                                </label>
                                            </div>
                                            <button type="submit" class="btn btn-lg btn-primary btn-block">Edit</button>
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
                                        <button class="btn btn-primary btn-sm rounded-0" type="button" data-toggle="tooltip" data-placement="top" title="Add to deck"><i class="fa fa-table"></i></button>
                                    </li>
                                    <li class="list-inline-item">
                                        <button class="btn btn-success btn-sm rounded-0" type="button" data-toggle="tooltip" data-placement="top" title="Edit" id="editBtn<c:out value="${flashcard.id}"/>"><i class="fa fa-edit"></i></button>

                                    </li>
                                    <li class="list-inline-item">
                                        <form action="/flashcards/delete" method="post">
                                            <input type="hidden" name="flashcardId" value="<c:out value="${flashcard.id}"/>"/>
                                            <button type="submit" class="btn btn-danger btn-sm rounded-0" type="button" data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-trash"></i></button>
                                        </form>
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
        <script>
            $(document).ready(function(){
                $("#addBtn").click(function(){
                    $("#addModal").modal();
                });
            });
        </script>
    </body>
</html>