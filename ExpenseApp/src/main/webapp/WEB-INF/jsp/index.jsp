<%-- 
    Document   : index
    Created on : Aug 8, 2022, 3:28:09 PM
    Author     : admin
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment-with-locales.min.js"></script>

<h1 class="text-center text-danger"><spring:message code="label.home" /></h1>

<div class="row">
    <div class="col-md-6 col-xs-12" style="padding:10px;">
        <div class="card bg-warning">
            <c:url value="/expense" var="expenseUrl" />
            <div class="card-body">
                <h4 class="card-title text-light"><spring:message code="label.currentMonthExpense" /></h4>
                <p class="card-text text-end text-light">Some example text.</p>
                <div class="d-flex flex-row-reverse">
                    <a href="${expenseUrl}" class="btn btn-primary"><spring:message code="label.detail" /></a>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-6 col-xs-12"" style="padding:10px;">
        <div class="card bg-info">
            <c:url value="/income" var="incomeUrl" />
            <div class="card-body">
                <h4 class="card-title text-light"><spring:message code="label.currentMonthIncome" /></h4>
                <p class="card-text text-end text-light">Some example text.</p>
                <div class="d-flex flex-row-reverse">
                    <a href="${incomeUrl}" class="btn btn-primary"><spring:message code="label.detail" /></a>
                </div>
            </div>
        </div>
    </div>    
</div>
