<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sorties</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="mb-0">Sorties</h1>
        <c:if test="${isAuthenticated}">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/sorties/create">Créer une sortie</a>
        </c:if>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <div class="card mb-4">
        <div class="card-header">Recherche</div>
        <div class="card-body">
            <form method="get" action="${pageContext.request.contextPath}/sorties/search" class="row g-3">
                <div class="col-md-4">
                    <label class="form-label" for="nom">Nom</label>
                    <input class="form-control" id="nom" name="nom" type="text" value="${nom}">
                </div>
                <div class="col-md-4">
                    <label class="form-label" for="categorieId">Catégorie</label>
                    <select class="form-select" id="categorieId" name="categorieId">
                        <option value="">Toutes</option>
                        <c:forEach items="${categories}" var="categorie">
                            <option value="${categorie.id}" ${categorieId == categorie.id ? 'selected' : ''}>${categorie.nom}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="form-label" for="date">Date</label>
                    <input class="form-control" id="date" name="date" type="date" value="${date}">
                </div>
                <div class="col-12 d-flex gap-2">
                    <button type="submit" class="btn btn-outline-primary">Rechercher</button>
                    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/sorties">Réinitialiser</a>
                </div>
            </form>
        </div>
    </div>

    <div class="card">
        <div class="card-header">Liste</div>
        <div class="table-responsive">
            <table class="table table-striped align-middle mb-0">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Date</th>
                    <th>Catégorie</th>
                    <c:if test="${isAuthenticated}">
                        <th>Créateur</th>
                        <th>Site web</th>
                    </c:if>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty sorties}">
                    <tr>
                        <td colspan="${isAuthenticated ? 6 : 4}" class="text-center text-muted">Aucune sortie trouvée.</td>
                    </tr>
                </c:if>
                <c:forEach items="${sorties}" var="sortie">
                    <tr>
                        <td>${sortie.nom}</td>
                        <td>${sortie.dateSortie}</td>
                        <td>${sortie.categorie.nom}</td>
                        <c:if test="${isAuthenticated}">
                            <td>${sortie.createur.prenom} ${sortie.createur.nom}</td>
                            <td>
                                <c:if test="${not empty sortie.siteWeb}">
                                    <a href="${sortie.siteWeb}" target="_blank" rel="noopener noreferrer">Lien</a>
                                </c:if>
                            </td>
                        </c:if>
                        <td class="text-end">
                            <a class="btn btn-sm btn-outline-secondary"
                               href="${pageContext.request.contextPath}/sorties/${sortie.id}">Détail</a>
                            <c:if test="${isAuthenticated and currentMembreId == sortie.createur.id}">
                                <a class="btn btn-sm btn-outline-primary"
                                   href="${pageContext.request.contextPath}/sorties/edit/${sortie.id}">Modifier</a>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/sorties/delete/${sortie.id}"
                                      style="display:inline-block;">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">Supprimer</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <c:if test="${totalPages > 1}">
        <nav class="mt-3" aria-label="Pagination">
            <ul class="pagination justify-content-center">
                <c:forEach begin="0" end="${totalPages - 1}" var="p">
                    <li class="page-item ${p == currentPage ? 'active' : ''}">
                        <c:if test="${isSearch}">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/sorties/search?page=${p}&size=${pageSize}&nom=${nom}&categorieId=${categorieId}&date=${date}">${p + 1}</a>
                        </c:if>
                        <c:if test="${not isSearch}">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/sorties?page=${p}&size=${pageSize}">${p + 1}</a>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </c:if>
</div>
</body>
</html>
