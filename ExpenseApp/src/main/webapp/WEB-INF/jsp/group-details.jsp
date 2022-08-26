<%-- 
    Document   : group-details
    Created on : Aug 26, 2022, 9:25:36 AM
    Author     : admin
--%>

<%@ taglib  prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-center text-danger"><spring:message code="label.groupDetails" /></h1>

<div>
    <div class="col-md-7">
        <h1 class="text-success"><spring:message code="label.groupId" />: ${group.id}</h1>
        <h1 class="text-success"class="text-center text-danger"><spring:message code="label.groupName" />: ${group.name}</h1>
    </div>
    
    <table class="table">
        <tr>
            <th><spring:message code="label.username" /></th>
            <th><spring:message code="label.firstName" /></th>
            <th><spring:message code="label.lastName" /></th>
            <th></th>
        </tr>
        <c:forEach items="${users}" var="u">
            <tr id="row${u.id}">
                <td>
                    ${u.username}
                </td>
                <td>
                    ${u.firstName}
                </td>
                <td>
                    ${u.lastName}
                </td>
                <td>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>