<%-- 
    Document   : admin
    Created on : Aug 25, 2022, 8:15:03 PM
    Author     : admin
--%>

<%@ taglib  prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="<c:url value="/js/pagination.js" />"></script>

<h1 class="text-center text-danger"><spring:message code="label.admin" /></h1>

<c:url value="/admin/users" var="action" />

<div>
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
    
    <form method="get" action="${action}" class="d-flex">
        <div class="mb-3 mt-3">
            <label for="username" class="form-label"><spring:message code="label.username" /></label>
            <input type="text" value="${kw}" class="form-control" id="username" 
                   placeholder="<spring:message code='form.username.keyword' />" name="kw" />
        </div>

        <div class="mb-3 mt-3">
            <label for="search" class="form-label">_</label>
            <input type="submit" id="search" value="<spring:message code='form.search' />" class="form-control btn btn-primary" />
        </div> 
    </form>

    <c:url value="/api/users" var="url" />
    <table class="table">
        <tr>
            <th><spring:message code="label.username" /></th>
            <th><spring:message code="label.password" /></th>
            <th><spring:message code="label.role" /></th>
            <th><spring:message code="label.avatar" /></th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach items="${users}" var="u">
            <tr id="row${u.id}">
                <td>
                    <input type="text" class="form-control" value="${u.username}" />
                </td>
                <td>
                    <input type="text" class="form-control" value="${u.password}" />
                </td>
                <td>
                    <select id="role-${u.id}" class="form-control" value="${u.role}">
                        <c:choose>
                            <c:when test="${u.role == 'USER'}">
                                <option value="USER" selected><spring:message code="label.role.user" /></option>
                                <option value="BUSINESS"><spring:message code="label.role.business" /></option>
                                <option value="ADMIN"><spring:message code="label.role.admin" /></option>
                            </c:when>
                            <c:when test="${u.role == 'BUSINESS'}">
                                <option value="USER"><spring:message code="label.role.user" /></option>
                                <option value="BUSINESS" selected><spring:message code="label.role.business" /></option>
                                <option value="ADMIN"><spring:message code="label.role.admin" /></option>
                            </c:when>
                            <c:otherwise>
                                <option value="USER"><spring:message code="label.role.user" /></option>
                                <option value="BUSINESS" selected><spring:message code="label.role.business" /></option>
                                <option value="ADMIN" selected><spring:message code="label.role.admin" /></option>
                            </c:otherwise>
                        </c:choose>

                    </select>
                </td>
                <td>
                    <input type="text" class="form-control" value="${u.avatar}" disabled="" />
                </td>

                <td>
                    <div class="spinner-border text-info" style="display:none" id="updateLoad${e.id}"></div>
                    <input type="button" class="btn btn-primary" value="<spring:message code="button.update" />" />
                </td>
                <td>
                    <div class="spinner-border text-info" style="display:none" id="load${e.id}"></div>
                    <input type="button" class="btn btn-danger" value="<spring:message code="button.delete" />" />
                </td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${pageSize > 0 && Math.ceil(userCounter/pageSize) > 1}">
        <ul class="pagination">
            <c:forEach begin="1" end="${Math.ceil(userCounter/pageSize)}" var="i">
                <c:url value="/admin/users" var="u">
                    <c:param name="kw" value="${kw}" />
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