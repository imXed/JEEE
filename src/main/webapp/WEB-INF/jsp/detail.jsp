<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détail sortie</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <a class="btn btn-link ps-0 mb-3" href="${pageContext.request.contextPath}/sorties">← Retour aux sorties</a>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <div class="card">
        <div class="card-header">
            <h1 class="h4 mb-0">${sortie.nom}</h1>
        </div>
        <div class="card-body">
            <p><strong>Date :</strong> ${sortie.dateSortie}</p>
            <p><strong>Catégorie :</strong> ${sortie.categorie.nom}</p>
            <p><strong>Description :</strong></p>
            <p>${sortie.description}</p>

            <c:if test="${isAuthenticated}">
                <p><strong>Créateur :</strong> ${sortie.createur.prenom} ${sortie.createur.nom}</p>
                <p>
                    <strong>Site web :</strong>
                    <c:if test="${not empty sortie.siteWeb}">
                        <a href="${sortie.siteWeb}" target="_blank" rel="noopener noreferrer">${sortie.siteWeb}</a>
                    </c:if>
                    <c:if test="${empty sortie.siteWeb}">
                        <span class="text-muted">Non renseigné</span>
                    </c:if>
                </p>
            </c:if>

            <c:if test="${not isAuthenticated}">
                <p class="text-muted mb-0">Connectez-vous pour voir le créateur et le site web.</p>
            </c:if>
        </div>
        <c:if test="${isAuthenticated and currentMembreId == sortie.createur.id}">
            <div class="card-footer d-flex gap-2">
                <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/sorties/edit/${sortie.id}">Modifier</a>
                <form method="post" action="${pageContext.request.contextPath}/sorties/delete/${sortie.id}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit" class="btn btn-outline-danger">Supprimer</button>
                </form>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
