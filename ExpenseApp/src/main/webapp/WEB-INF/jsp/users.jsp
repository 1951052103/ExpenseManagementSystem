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
<script src="<c:url value="/js/userAction.js" />"></script>

<h1 class="text-center text-danger"><spring:message code="label.admin" /></h1>

<c:url value="/admin/users" var="action" />

<div>
    <div>
        <label for="page-size" class="form-label"><spring:message code="label.pagesize" /></label>
        <select id="page-size" name="page-size" onchange="setPageSize(event)">
            <c:forEach items="${sizes}" var="s" >

                <c:choose>
                    <c:when test="${pageSize == s[1]}">
                        <option selected value="${s[1]}">${s[0]}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${s[1]}">${s[0]}</option>
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
            <th><spring:message code="label.active" /></th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach items="${users}" var="u">
            <tr id="user-row${u.id}">
                <td>
                    <input onClick="this.select();" type="text" class="form-control" value="${u.username}" id="user-username-${u.id}" />
                </td>
                <td>
                    <input onClick="this.select();" type="password" class="form-control" value="${u.password}" id="user-password-${u.id}" />
                </td>
                <td>
                    <select id="user-role-${u.id}" class="form-control" value="${u.role}">
                        <c:choose>
                            <c:when test="${u.role == 'USER'}">
                                <option value="<spring:message code="label.role.user" />" selected><spring:message code="label.role.user" /></option>
                                <option value="<spring:message code="label.role.business" />"><spring:message code="label.role.business" /></option>
                                <option value="<spring:message code="label.role.admin" />"><spring:message code="label.role.admin" /></option>
                            </c:when>
                            <c:when test="${u.role == 'BUSINESS'}">
                                <option value="<spring:message code="label.role.user" />"><spring:message code="label.role.user" /></option>
                                <option value="<spring:message code="label.role.business" />" selected><spring:message code="label.role.business" /></option>
                                <option value="<spring:message code="label.role.admin" />"><spring:message code="label.role.admin" /></option>
                            </c:when>
                            <c:otherwise>
                                <option value="<spring:message code="label.role.user" />"><spring:message code="label.role.user" /></option>
                                <option value="<spring:message code="label.role.business" />"><spring:message code="label.role.business" /></option>
                                <option value="<spring:message code="label.role.admin" />" selected><spring:message code="label.role.admin" /></option>
                            </c:otherwise>
                        </c:choose>

                    </select>
                </td>
                
                <td>
                    <select class="form-control" id="user-active-${u.id}">
                        <c:choose>
                            <c:when test="${u.active == true}">
                                <option value="<spring:message code="label.true" />" selected><spring:message code="label.true" /></option>
                                <option value="<spring:message code="label.false" />"><spring:message code="label.false" /></option>
                            </c:when>
                            <c:otherwise>
                                <option value="<spring:message code="label.true" />"><spring:message code="label.true" /></option>
                                <option value="<spring:message code="label.false" />" selected><spring:message code="label.false" /></option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </td>

                <td>
                    <div class="spinner-border text-info" style="display:none" id="user-updateLoad${u.id}"></div>
                    <input type="button" class="btn btn-primary" value="<spring:message code="button.update" />" 
                           onclick="updateUser('${url}/${u.id}', ${u.id}, this, '<spring:message code="message.update" />', '<spring:message code="message.error" />')" />
                </td>
                <td>
                    <div class="spinner-border text-info" style="display:none" id="user-load${u.id}"></div>
                    <input type="button" class="btn btn-danger" value="<spring:message code="button.delete" />" 
                           onclick="deleteUser('${url}/${u.id}', ${u.id}, this, '<spring:message code="message.delete" />', '<spring:message code="message.error" />')" />
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