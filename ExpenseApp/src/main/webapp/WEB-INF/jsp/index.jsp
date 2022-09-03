<%-- 
    Document   : index
    Created on : Aug 8, 2022, 3:28:09 PM
    Author     : admin
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="<c:url value="/js/stats.js" />"></script>
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

<fmt:setLocale value = "<spring:message code='locale' />"/>
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

    <div>
        <form method="get" class="d-flex">
            <div class="mb-3 mt-3">
                <label for="month" class="form-label"><spring:message code="label.month" /></label>
                <c:choose>
                    <c:when test="${month < 10}">
                        <input type="month" value="${year}-0${month}" class="form-control" id="month" name="month">
                    </c:when>
                    <c:otherwise>
                        <input type="month" value="${year}-${month}" class="form-control" id="month" name="month">
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="mb-3 mt-3">
                <label for="search" class="form-label">_</label>
                <input type="submit" id="search" value="<spring:message code="form.getStats" />" class="form-control btn btn-primary" />
            </div> 

        </form>
    </div>            
     
    <h3 class="text-success"><spring:message code="label.expenseStatsByMonth" /></h3>         
    <div class="row">
        <div class="col-md-6">
            <table class="table">
                <tr>
                    <th><spring:message code="label.date" /></th>
                    <th><spring:message code="amount" /></th>
                </tr>
                <c:forEach items="${expenseStatsByMonth}" var="e">
                    <tr>
                        <td>${e[0]}</td>
                        <td>
                            <fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${e[1]}" /> <spring:message code="currency" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-6">
            <canvas id="expenseStatsByMonthChart"></canvas>
        </div>
    </div>
            
    <h3 class="text-success"><spring:message code="label.expenseStatsByYear" /> ${year}</h3>            
    <div class="row">
        <div class="col-md-6">
            <table class="table">
                <tr>
                    <th><spring:message code="label.month" /></th>
                    <th><spring:message code="amount" /></th>
                </tr>
                <c:forEach items="${expenseStatsByYear}" var="e">
                    <tr>
                        <td>${e[0]}</td>
                        <td>
                            <fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${e[1]}" /> <spring:message code="currency" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-6">
            <canvas id="expenseStatsByYearChart"></canvas>
        </div>
    </div>
                
    <h3 class="text-success"><spring:message code="label.incomeStatsByYear" /> ${year}</h3>            
    <div class="row">
        <div class="col-md-6">
            <table class="table">
                <tr>
                    <th><spring:message code="label.month" /></th>
                    <th><spring:message code="amount" /></th>
                </tr>
                <c:forEach items="${incomeStatsByYear}" var="i">
                    <tr>
                        <td>${i[0]}</td>
                        <td>
                            <fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${i[1]}" /> <spring:message code="currency" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-6">
            <canvas id="incomeStatsByYearChart"></canvas>
        </div>
    </div>   
</div>

<script>
    window.onload = function () {
        let ctx = document.getElementById('expenseStatsByMonthChart').getContext('2d');
        let labels = [], data = [];
        <c:forEach items="${expenseStatsByMonth}" var="e">
            data.push(${e[0]});
            labels.push('${e[1]}');
        </c:forEach>
        showStats(ctx, data, labels, 'bar', '<spring:message code="label.date" />');
        
        let ctx2 = document.getElementById('expenseStatsByYearChart').getContext('2d');
        let labels2 = [], data2 = [];
        <c:forEach items="${expenseStatsByYear}" var="e">
            data2.push(${e[0]});
            labels2.push('${e[1]}');
        </c:forEach>
        showStats(ctx2, data2, labels2, 'bar', '<spring:message code="label.month" />');  
        
        let ctx3 = document.getElementById('incomeStatsByYearChart').getContext('2d');
        let labels3 = [], data3 = [];
        <c:forEach items="${incomeStatsByYear}" var="i">
            data3.push(${i[0]});
            labels3.push('${i[1]}');
        </c:forEach>
        showStats(ctx3, data3, labels3, 'bar', '<spring:message code="label.month" />');
    }
</script>