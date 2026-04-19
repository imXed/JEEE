<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h1 class="mb-3">Bienvenue</h1>
<p>Consultez les catégories et les sorties, ou connectez-vous pour gérer vos sorties.</p>
<div class="d-flex gap-2">
    <a class="btn btn-success" href="${pageContext.request.contextPath}/categories">Catégories</a>
    <a class="btn btn-info text-white" href="${pageContext.request.contextPath}/sorties">Sorties</a>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/login">Se connecter</a>
    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/register">Créer un compte</a>
</div>
</body>
</html>
