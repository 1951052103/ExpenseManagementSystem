<%-- 
    Document   : group
    Created on : Aug 25, 2022, 9:58:20 PM
    Author     : admin
--%>


<%@ taglib  prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="<c:url value="/js/pagination.js" />"></script>

<h1 class="text-center text-danger"><spring:message code="label.group" /></h1>

<div>
    <c:url value="/group" var="action" />
    <form:form method="post" action="${action}"  modelAttribute="group">
        <form:errors path="*" element="div" cssClass="alert alert-danger" />
        
        <div class="form-group">
            <label for="name"><spring:message code="label.groupName" /></label>
            <form:input type="text" id="name" path="name" class="form-control" required="required" />
        </div>

        <br/>
        <div class="form-group">
            <input type="submit" value="<spring:message code="button.create" />" class="btn btn-success" />
        </div> 
        <br/><br/>
    </form:form>


    <c:url value="/groupUser" var="action" />
    <form:form method="post" action="${action}"  modelAttribute="groupUser">
        <div class="form-group">
            <label for="groupId"><spring:message code="label.groupId" /></label>
            <form:input type="number" id="groupId" path="groupId.id" class="form-control" required="required" />
        </div>

        <br/>
        <div class="form-group">
            <input type="submit" value="<spring:message code="button.join" />" class="btn btn-success" />
        </div> 
        <br/><br/>
    </form:form>

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
        
    <c:url value="/group" var="action" />
    <div>
        <form method="get" action="${action}" class="d-flex">

            <div class="mb-3 mt-3">
                <label for="purpose" class="form-label"><spring:message code="label.groupName" /></label>
                <input type="text" value="${kw}" class="form-control" id="purpose" 
                       placeholder="<spring:message code="form.group.keyword" />" name="kw">
            </div>

            <div class="mb-3 mt-3">
                <label for="search" class="form-label">_</label>
                <input type="submit" id="search" value="<spring:message code="form.search" />" class="form-control btn btn-primary" />
            </div> 

        </form>
    </div>

    <table class="table">
        <tr>
            <th><spring:message code="label.groupId" /></th>
            <th><spring:message code="label.groupName" /></th>
            <th></th>
        </tr>
        <c:forEach items="${groups}" var="g">
            <tr id="row${g[0]}">
                <td>
                    ${g[0]}
                </td>
                <td>
                    ${g[1]}
                </td>
                <td>
                    <a class="btn btn-primary" href="${action}/${g[0]}">
                        <spring:message code="message.view" />
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
            
    <c:if test="${pageSize > 0 && Math.ceil(groupCounter/pageSize) > 1}">
        <ul class="pagination">
            <c:forEach begin="1" end="${Math.ceil(groupCounter/pageSize)}" var="i">
                <c:url value="/group" var="u">
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
