<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer une sortie</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <a class="btn btn-link ps-0 mb-3" href="${pageContext.request.contextPath}/sorties">← Retour aux sorties</a>

    <div class="card">
        <div class="card-header">
            <h1 class="h4 mb-0">Créer une sortie</h1>
        </div>
        <div class="card-body">
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>

            <form:form modelAttribute="sortieForm" method="post" action="${pageContext.request.contextPath}/sorties/create">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <div class="mb-3">
                    <label class="form-label" for="nom">Nom</label>
                    <form:input path="nom" id="nom" cssClass="form-control"/>
                    <form:errors path="nom" cssClass="text-danger small"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="description">Description</label>
                    <form:textarea path="description" id="description" rows="4" cssClass="form-control"/>
                    <form:errors path="description" cssClass="text-danger small"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="siteWeb">Site web</label>
                    <form:input path="siteWeb" id="siteWeb" cssClass="form-control"/>
                    <form:errors path="siteWeb" cssClass="text-danger small"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="dateSortie">Date de sortie</label>
                    <form:input path="dateSortie" id="dateSortie" type="date" cssClass="form-control"/>
                    <form:errors path="dateSortie" cssClass="text-danger small"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="categorieId">Catégorie</label>
                    <form:select path="categorieId" id="categorieId" cssClass="form-select">
                        <form:option value="" label="Choisir une catégorie"/>
                        <form:options items="${categories}" itemValue="id" itemLabel="nom"/>
                    </form:select>
                    <form:errors path="categorieId" cssClass="text-danger small"/>
                </div>

                <button class="btn btn-primary" type="submit">Créer</button>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
