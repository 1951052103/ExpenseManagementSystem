<%-- 
    Document   : header
    Created on : Aug 17, 2022, 6:13:03 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="javascript:void(0)"><spring:message code="header.brand" /></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mynavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/" />">&#127968; <spring:message code="label.home" /></a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/expense" />"><spring:message code="label.expense" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/income" />"><spring:message code="label.income" /></a>
                </li>

                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link text-info" href="<c:url value="/login" />"><spring:message code="header.login" /></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-info" href="<c:url value="/register" />"><spring:message code="header.register" /></a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link text-info" href="<c:url value="/" />">
                            <%--<sec:authentication property="principal.username"/>--%>
                            ${pageContext.session.getAttribute("currentUser").username}
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link text-danger" href="<c:url value="/logout" />"><spring:message code="header.logout" /></a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>