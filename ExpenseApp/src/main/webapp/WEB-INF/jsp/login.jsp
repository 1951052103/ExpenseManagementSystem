<%-- 
    Document   : login
    Created on : Aug 23, 2022, 5:12:10 PM
    Author     : admin
--%>


<%@taglib  prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-center text-danger"><spring:message code="header.login" /></h1>

<c:url value="/login" var="action" />

<form:form method="post" modelAttribute="user" action="${action}">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    
    <div class="form-group">
        <label for="username"><spring:message code="label.username" /></label>
        <form:input type="text" id="username" path="username" class="form-control" />
        <form:errors path="username" element="div" cssClass="alert alert-danger" />
    </div>
    <div class="form-group">
        <label for="password"><spring:message code="label.password" /></label>
        <form:input type="password" id="password" path="password" class="form-control" />
        <form:errors path="password" element="div" cssClass="alert alert-danger" />
    </div>
    <br/>
    <div class="form-group">
        <input type="submit" value="<spring:message code="header.login" />" class="btn btn-danger" />
    </div>
</form:form>
