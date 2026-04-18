<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>
</head>
<body>
<h1>Connexion</h1>

<c:if test="${param.error != null}">
    <p style="color:red;">Identifiants invalides.</p>
</c:if>

<c:if test="${param.logout != null}">
    <p style="color:green;">Vous avez été déconnecté.</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
    <div>
        <label for="email">Email</label>
        <input id="email" name="email" type="email" required/>
    </div>
    <div>
        <label for="password">Mot de passe</label>
        <input id="password" name="password" type="password" required/>
    </div>
    <button type="submit">Se connecter</button>
</form>
</body>
</html>
