<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer un compte</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h2>Créer un compte</h2>

<c:if test="${not empty validationErrors}">
    <div class="alert alert-danger">
        <ul class="mb-0">
            <c:forEach items="${validationErrors}" var="error">
                <li>${error.defaultMessage}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/register">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="mb-3">
        <label class="form-label" for="nom">Nom</label>
        <input class="form-control" id="nom" name="nom" value="${registerForm.nom}" required>
    </div>

    <div class="mb-3">
        <label class="form-label" for="prenom">Prénom</label>
        <input class="form-control" id="prenom" name="prenom" value="${registerForm.prenom}" required>
    </div>

    <div class="mb-3">
        <label class="form-label" for="email">Email</label>
        <input class="form-control" id="email" name="email" type="email" value="${registerForm.email}" required>
    </div>

    <div class="mb-3">
        <label class="form-label" for="motDePasse">Mot de passe</label>
        <input class="form-control" id="motDePasse" name="motDePasse" type="password" required>
    </div>

    <button class="btn btn-primary" type="submit">Créer mon compte</button>
    <a class="btn btn-link" href="${pageContext.request.contextPath}/login">J'ai déjà un compte</a>
</form>
</body>
</html>
