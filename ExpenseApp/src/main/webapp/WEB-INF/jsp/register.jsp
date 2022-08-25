<%-- 
    Document   : register
    Created on : Aug 23, 2022, 9:36:04 PM
    Author     : admin
--%>

<%@ taglib  prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-center text-danger"><spring:message code="header.register" /></h1>

<c:url value="/register" var="action" />

<form:form method="post" action="${action}" 
           enctype="multipart/form-data" modelAttribute="user">

    <form:errors path="*" element="div" cssClass="alert alert-danger" />

    <div class="form-group">
        <label for="username"><spring:message code="label.username" /></label>
        <form:input type="text" id="username" path="username" class="form-control" required="required" />
        <form:errors path="username" cssClass="text-danger" />
    </div>
    <div class="form-group">
        <label for="password"><spring:message code="label.password" /></label>
        <form:input type="password" id="password" path="password" class="form-control" required="required" />
        <form:errors path="password" cssClass="text-danger" />
    </div>
    <div class="form-group">
        <label for="confirm-password"><spring:message code="label.confirmPassword" /></label>
        <form:input type="password" id="confirm-password" path="confirmPassword" class="form-control" required="required" />
        <form:errors path="confirmPassword" cssClass="text-danger" />
    </div>
    <div class="form-group">
        <label for="role"><spring:message code="label.role" /></label>
        <form:select id="role" path="role" class="form-control" required="required">
            <form:option value="USER"><spring:message code="label.role.user" /></form:option>
            <form:option value="BUSINESS"><spring:message code="label.role.business" /></form:option>
        </form:select>
    </div>

    <div class="form-group">
        <label for="firstname"><spring:message code="label.firstName" /> <spring:message code="label.optional" /></label>
        <form:input type="text" id="firstname" path="firstName" class="form-control" />
        <form:errors path="firstName" cssClass="text-danger" />
    </div>
    <div class="form-group">
        <label for="lastname"><spring:message code="label.lastName" /> <spring:message code="label.optional" /></label>
        <form:input type="text" id="lastname" path="lastName" class="form-control" />
        <form:errors path="lastName" cssClass="text-danger" />
    </div>
    <div class="form-group">
        <label for="email"><spring:message code="label.email" /> <spring:message code="label.optional" /></label>
        <form:input type="email" id="email" path="email" class="form-control" />
        <form:errors path="email" cssClass="text-danger" />
    </div>
    <div class="form-group">
        <label for="phone"><spring:message code="label.phone" /> <spring:message code="label.optional" /></label>
        <form:input type="tel" id="phone" path="phone" class="form-control"  pattern="[0-9]{10}"/>
        <form:errors path="phone" cssClass="text-danger" />
    </div>
    <div class="form-group">
        <label for="phone"><spring:message code="label.dateOfBirth" /> <spring:message code="label.optional" /></label>
        <form:input type="date" id="dateOfBirth" path="dateOfBirth" class="form-control" />
        <form:errors path="dateOfBirth" cssClass="text-danger" />
    </div>

    <div class="form-group">
        <label for="avatar"><spring:message code="label.avatar" /> <spring:message code="label.optional" /></label>
        <form:input type="file" id="avatar" path="file" class="form-control"
                    accept="image/png, image/jpeg" />
    </div>
    <br/>
    <div class="form-group">
        <input type="submit" value="<spring:message code="header.register" />" class="btn btn-success" />
    </div>
</form:form>