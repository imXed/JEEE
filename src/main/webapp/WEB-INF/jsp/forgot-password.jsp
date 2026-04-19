<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mot de passe oublié</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h2>Récupération du mot de passe</h2>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success"><c:out value="${successMessage}"/></div>
</c:if>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/forgot-password">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <div class="mb-3">
        <label class="form-label" for="email">Email</label>
        <input class="form-control" id="email" name="email" type="email" required>
    </div>
    <button class="btn btn-primary" type="submit">Envoyer le lien</button>
    <a class="btn btn-link" href="${pageContext.request.contextPath}/login">Retour connexion</a>
</form>
</body>
</html>
