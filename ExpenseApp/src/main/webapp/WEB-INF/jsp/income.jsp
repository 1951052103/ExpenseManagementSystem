<%-- 
    Document   : income
    Created on : Aug 18, 2022, 4:35:31 PM
    Author     : admin
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment-with-locales.min.js"></script>

<h1 class="text-center text-danger"><spring:message code="label.income" /></h1>

<div class="container mt-3">
           
    <table class="table">
        <tr>
            <th><spring:message code="amount" /></th>
            <th><spring:message code="income.source" /></th>
            <th><spring:message code="date" /></th>
            <th><spring:message code="description" /></th>
        </tr>

        <c:forEach items="${incomes}" var="i">
            <tr id="${i.id}">
                <td>
                    <fmt:formatNumber value="${i.amount}" maxFractionDigits="3" type = "number" /> <spring:message code="currency" />
                </td>
                <td>${i.source}</td>
                <td>
                    <script>
                        document.getElementById("${i.id}").cells[2].innerHTML = 
                                moment("${i.date}").locale("<spring:message code="locale" />").format('LL');
                    </script>
                </td>
                <td>${i.description}</td>
            </tr>
        </c:forEach>
    </table>
</div>
