<%-- 
    Document   : expense
    Created on : Aug 18, 2022, 4:30:35 PM
    Author     : admin
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment-with-locales.min.js"></script>
<script src="<c:url value="/js/pagination.js" />"></script>
<script src="<c:url value="/js/expenseAction.js" />"></script>

<h1 class="text-center text-danger"><spring:message code="label.expense" /></h1>
<div class="container mt-3">    
    <div>
        <label for="page-size" class="form-label"><spring:message code="label.pagesize" /></label>
        <select id="page-size" name="page-size" onchange="setPageSize(event)">
            <c:forEach items="${sizes}" var="s" >

                <c:choose>
                    <c:when test="${pageSize == s.getValue()}">
                        <option selected value="${s.getValue()}">${s.getKey()}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${s.getValue()}">${s.getKey()}</option>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </select>
    </div>    

    <div>
        <c:url value="/expense" var="action" />
        <form method="get" action="${action}" class="d-flex">

            <div class="mb-3 mt-3">
                <label for="purpose" class="form-label"><spring:message code="label.purpose" /></label>
                <input type="text" value="${kw}" class="form-control" id="purpose" placeholder="<spring:message code="form.expense.keyword" />" name="kw">
            </div>

            <div class="mb-3 mt-3">
                <label for="from-date" class="form-label"><spring:message code="label.fromdate" /></label>
                <input type="date" value="${fd}" class="form-control" id="from-date" name="fromDate">
            </div>

            <div class="mb-3 mt-3">
                <label for="to-date" class="form-label"><spring:message code="label.todate" /></label>
                <input type="date" value="${td}" class="form-control" id="to-date" name="toDate">
            </div>   

            <div class="mb-3 mt-3">
                <label for="search" class="form-label">_</label>
                <input type="submit" id="search" value="<spring:message code="form.search" />" class="form-control btn btn-primary" />
            </div> 

        </form>
    </div>

    <c:url value="/api/expense" var="url" />        
    <table class="table">
        <tr>
            <th><spring:message code="amount" /> (<spring:message code="currency" />)</th>
            <th><spring:message code="expense.purpose" /></th>
            <th><spring:message code="date" /></th>
            <th><spring:message code="description" /></th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach items="${expenses}" var="e">
            <tr id="row${e.id}">
                <td>
                    <input type="number" class="form-control" value="${e.amount}"/>
                </td>
                <td>
                    <input type="text" class="form-control" value="${e.purpose}"/>
                </td>
                <td>
                    <fmt:formatDate pattern="yyyy-MM-dd" value="${e.date}" var="date"/>
                    <input type="date" class="form-control" value="${date}" />
                </td>
                <td>
                    <textarea class="form-control">${e.description}</textarea>
                </td>
                <td>
                    <input type="button" class="btn btn-primary" value="<spring:message code="button.update" />" />
                </td>
                <td>
                    <div class="spinner-border text-info" style="display:none" id="load${e.id}"></div>
                    <input type="button" 
                           onclick="deleteExpense('${url}/${e.id}', ${e.id}, this, '<spring:message code="message.delete" />', '<spring:message code="message.error" />')" 
                           class="btn btn-danger" value="<spring:message code="button.delete" />" />
                </td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${pageSize > 0 && Math.ceil(expenseCounter/pageSize) > 1}">
        <ul class="pagination">
            <c:forEach begin="1" end="${Math.ceil(expenseCounter/pageSize)}" var="i">
                <c:url value="/expense" var="u">
                    <c:param name="kw" value="${kw}" />
                    <c:param name="fromDate" value="${fd}" />
                    <c:param name="toDate" value="${td}" />
                    <c:param name="pageSize" value="${pageSize}" />
                    <c:param name="page" value="${i}" />
                </c:url>

                <c:choose>
                    <c:when test="${page == i}">
                        <li class="page-item"><a class="page-link bg-primary text-light" href="${u}">${i}</a></li>  
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="${u}">${i}</a></li>   
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </ul>
    </c:if>
</div>