<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catégories</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <h1 class="mb-4">Catégories</h1>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">${errorMessage}</div>
    </c:if>

    <div class="row g-4">
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">Liste des catégories</div>
                <ul class="list-group list-group-flush">
                    <c:forEach items="${categories}" var="categorie">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a class="text-decoration-none"
                               href="${pageContext.request.contextPath}/categories/${categorie.id}">
                                    ${categorie.nom}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <c:if test="${empty selectedCategorie}">Sorties de la catégorie</c:if>
                    <c:if test="${not empty selectedCategorie}">
                        Sorties - ${selectedCategorie.nom}
                    </c:if>
                </div>
                <div class="card-body">
                    <c:if test="${empty selectedCategorie}">
                        <p class="text-muted mb-0">Sélectionnez une catégorie pour voir ses sorties.</p>
                    </c:if>

                    <c:if test="${not empty selectedCategorie and empty selectedCategorie.sorties}">
                        <p class="text-muted mb-0">Aucune sortie pour cette catégorie.</p>
                    </c:if>

                    <c:if test="${not empty selectedCategorie and not empty selectedCategorie.sorties}">
                        <ul class="list-group">
                            <c:forEach items="${selectedCategorie.sorties}" var="sortie">
                                <li class="list-group-item">
                                    <div class="fw-semibold">${sortie.nom}</div>
                                    <div class="small text-muted">${sortie.dateSortie}</div>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
