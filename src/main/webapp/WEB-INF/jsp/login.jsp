<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body class="container mt-5">

<h2>Connexion</h2>

<c:if test="${param.error != null}">
    <div class="alert alert-danger">
        Email ou mot de passe incorrect
    </div>
</c:if>

<c:if test="${param.logout != null}">
    <div class="alert alert-success">
        Déconnexion réussie
    </div>
</c:if>

<form method="post" action="/login">

    <!-- IMPORTANT CSRF -->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="mb-3">
        <label>Email</label>
        <input type="text" name="username" class="form-control" required/>
    </div>

    <div class="mb-3">
        <label>Mot de passe</label>
        <input type="password" name="password" class="form-control" required/>
    </div>

    <button type="submit" class="btn btn-primary">
        Se connecter
    </button>

</form>

</body>
</html>