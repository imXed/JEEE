<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détail membre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <a class="btn btn-link ps-0 mb-3" href="${pageContext.request.contextPath}/membres">← Retour aux membres</a>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <div class="card mb-4">
        <div class="card-header">
            <h1 class="h4 mb-0">Informations du membre</h1>
        </div>
        <div class="card-body">
            <p><strong>Nom :</strong> ${membre.nom}</p>
            <p><strong>Prénom :</strong> ${membre.prenom}</p>
            <p class="mb-0"><strong>Email :</strong> ${membre.email}</p>
        </div>
    </div>

    <div class="card">
        <div class="card-header">Sorties du membre</div>
        <div class="table-responsive">
            <table class="table table-striped align-middle mb-0">
                <thead>
                <tr>
                    <th>Nom sortie</th>
                    <th>Catégorie</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty sorties}">
                    <tr>
                        <td colspan="3" class="text-center text-muted">Aucune sortie pour ce membre.</td>
                    </tr>
                </c:if>
                <c:forEach items="${sorties}" var="sortie">
                    <tr>
                        <td>${sortie.nom}</td>
                        <td>${sortie.categorie.nom}</td>
                        <td>${sortie.dateSortie}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
