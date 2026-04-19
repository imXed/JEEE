<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Réinitialiser le mot de passe</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h2>Nouveau mot de passe</h2>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/reset-password">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="hidden" name="token" value="${token}">
    <div class="mb-3">
        <label class="form-label" for="motDePasse">Nouveau mot de passe</label>
        <input class="form-control" id="motDePasse" name="motDePasse" type="password" minlength="6" required>
    </div>
    <button class="btn btn-primary" type="submit">Mettre à jour</button>
</form>
</body>
</html>

