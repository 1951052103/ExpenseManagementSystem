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

<div>
    <c:if test="${currentMonthExpense > currentMonthIncome}">
        <p class="alert alert-danger">
            <spring:message code="label.currentMonth.warning" />
        </p>
    </c:if>   
    <c:if test="${currentMonthExpense > lastMonthExpense}">
        <p class="alert alert-danger">
            <spring:message code="label.lastMonth.warning" />
        </p>
    </c:if>
    <c:if test="${currentMonthExpense > lastYearExpense}">
        <p class="alert alert-danger">
            <spring:message code="label.lastYear.warning" />
        </p>
    </c:if>
</div>

<div class="row">
    <div class="col-md-6 col-xs-12" style="padding:10px;">
        <div class="card bg-warning">
            <c:url value="/expense" var="expenseUrl">
                <c:param name="fromDate" value="${cmfd}" />
                <c:param name="toDate" value="${cmtd}" />
            </c:url>
            <div class="card-body">
                <h4 class="card-title text-light"><spring:message code="label.currentMonthExpense" /></h4>
                <p class="card-text text-end text-light">
                    <c:choose>
                        <c:when test="${currentMonthExpense > 0}">
                            <fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${currentMonthExpense}" /> <spring:message code="currency" />
                        </c:when>
                        <c:otherwise>
                            0 <spring:message code="currency" />
                        </c:otherwise>
                    </c:choose>
                </p>
                <div class="d-flex flex-row-reverse">
                    <a href="${expenseUrl}" class="btn btn-primary"><spring:message code="label.detail" /></a>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-6 col-xs-12"" style="padding:10px;">
        <div class="card bg-info">
            <c:url value="/income" var="incomeUrl">
                <c:param name="fromDate" value="${cmfd}" />
                <c:param name="toDate" value="${cmtd}" />
            </c:url>
            <div class="card-body">
                <h4 class="card-title text-light"><spring:message code="label.currentMonthIncome" /></h4>
                <p class="card-text text-end text-light">
                    <c:choose>
                        <c:when test="${currentMonthIncome > 0}">
                            <fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${currentMonthIncome}" /> <spring:message code="currency" />
                        </c:when>
                        <c:otherwise>
                            0 <spring:message code="currency" />
                        </c:otherwise>
                    </c:choose>
                </p>
                <div class="d-flex flex-row-reverse">
                    <a href="${incomeUrl}" class="btn btn-primary"><spring:message code="label.detail" /></a>
                </div>
            </div>
        </div>
    </div> 

    <div class="col-md-6 col-xs-12"" style="padding:10px;">
        <div class="card bg-info">
            <c:url value="/expense" var="expenseUrl">
                <c:param name="fromDate" value="${lmfd}" />
                <c:param name="toDate" value="${lmtd}" />
            </c:url>
            <div class="card-body">
                <h4 class="card-title text-light"><spring:message code="label.lastMonthExpense" /></h4>
                <p class="card-text text-end text-light">
                    <c:choose>
                        <c:when test="${lastMonthExpense > 0}">
                            <fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${lastMonthExpense}" /> <spring:message code="currency" />
                        </c:when>
                        <c:otherwise>
                            0 <spring:message code="currency" />
                        </c:otherwise>
                    </c:choose>
                </p>
                <div class="d-flex flex-row-reverse">
                    <a href="${expenseUrl}" class="btn btn-primary"><spring:message code="label.detail" /></a>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-6 col-xs-12"" style="padding:10px;">
        <div class="card bg-info">
            <c:url value="/expense" var="expenseUrl">
                <c:param name="fromDate" value="${lyfd}" />
                <c:param name="toDate" value="${lytd}" />
            </c:url>
            <div class="card-body">
                <h4 class="card-title text-light"><spring:message code="label.lastYearExpense" /></h4>
                <p class="card-text text-end text-light">
                    <c:choose>
                        <c:when test="${lastYearExpense > 0}">
                            <fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${lastYearExpense}" /> <spring:message code="currency" />
                        </c:when>
                        <c:otherwise>
                            0 <spring:message code="currency" />
                        </c:otherwise>
                    </c:choose>
                </p>
                <div class="d-flex flex-row-reverse">
                    <a href="${expenseUrl}" class="btn btn-primary"><spring:message code="label.detail" /></a>
                </div>
            </div>
        </div>
    </div> 
</div>

