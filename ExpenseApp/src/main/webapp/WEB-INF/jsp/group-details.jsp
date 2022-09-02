<%-- 
    Document   : group-details
    Created on : Aug 26, 2022, 9:25:36 AM
    Author     : admin
--%>

<%@ taglib  prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="<c:url value="/js/pagination.js" />"></script>
<script src="<c:url value="/js/expenseAction.js" />"></script>
<script src="<c:url value="/js/incomeAction.js" />"></script>
<script src="<c:url value="/js/groupAction.js" />"></script>

<h1 class="text-center text-danger"><spring:message code="label.groupDetails" /></h1>

<c:if test="${joined == true}">
    <div>
        <div class="col-md-7">
            <h1 class="text-success"><spring:message code="label.groupId" />: ${group.id}</h1>
            <h1 class="text-success"class="text-center text-danger"><spring:message code="label.groupName" />: ${group.name}</h1>
        </div>

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
            <form method="get" class="d-flex">

                <div class="mb-3 mt-3">
                    <label for="kw" class="form-label"><spring:message code="label.keyword" /></label>
                    <input type="text" value="${kw}" class="form-control" id="kw" 
                           placeholder="<spring:message code='form.keyword' />" name="kw">
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

        <h4><spring:message code="label.userList" /></h4>
        <c:url value="/api/group/${group.id}/users" var="url" />
        <table class="table">
            <tr>
                <th><spring:message code="label.username" /></th>
                <th><spring:message code="label.firstName" /></th>
                <th><spring:message code="label.lastName" /></th>
                <th><spring:message code="label.unconfirmedExpense" /></th>
                <th><spring:message code="label.unconfirmedIncome" /></th>
                <th></th>
            </tr>
            <c:forEach items="${users}" var="u">
                <tr id="user-row${u[0].id}">
                    <td>
                        ${u[0].username}
                    </td>
                    <td>
                        ${u[0].firstName}
                    </td>
                    <td>
                        ${u[0].lastName}
                    </td>
                    
                    <td>
                        ${u[1]}
                    </td>
                    <td>
                        ${u[2]}
                    </td>
                    
                    <c:if test="${isLeader == true && u[0].username != currentUser.username}">
                        <td>
                            <div class="spinner-border text-info" style="display:none" id="user-load${u[0].id}"></div>
                            <input type="button" 
                                   onclick="deleteUserFromGroup('${url}/${u[0].username}', ${u[0].id}, this, '<spring:message code="message.delete" />', '<spring:message code="message.error" />')" 
                                   class="btn btn-danger" value="<spring:message code="button.delete" />" />
                        </td>
                    </c:if>
                </tr>
            </c:forEach>

        </table>
        <h4><spring:message code="label.expense" /></h4> 
        <c:url value="/api/expense" var="url" />        
        <table class="table">
            <tr>
                <th><spring:message code="label.username" /></th>
                <th><spring:message code="amount" /> (<spring:message code="currency" />)</th>
                <th><spring:message code="expense.purpose" /></th>
                <th><spring:message code="date" /></th>
                <th><spring:message code="description" /></th>
                <th><spring:message code="label.approved" /></th>
                <th><spring:message code="label.confirmed" /></th>
                <th></th>
                <th></th>
            </tr>

            <c:forEach items="${expenses}" var="e">
                <tr id="expense-row${e.id}">
                    <td>
                        <input type="text" class="form-control" value="${e.userId.username}" id="expense-username-${e.id}" disabled/>
                    </td>

                    <td>
                        <input type="number" class="form-control" value="${e.amount}" id="expense-amount-${e.id}" />
                    </td>
                    <td>
                        <input type="text" class="form-control" value="${e.purpose}" id="expense-purpose-${e.id}" />
                    </td>
                    <td>
                        <fmt:formatDate pattern="yyyy-MM-dd" value="${e.date}" var="date"/>
                        <input type="date" class="form-control" value="${date}" id="expense-date-${e.id}" />
                    </td>
                    <td>
                        <textarea class="form-control" id="expense-description-${e.id}">${e.description}</textarea>
                    </td>


                    <td>
                        <select class="form-control" id="expense-approved-${e.id}">
                            <c:choose>
                                <c:when test="${e.approved == true}">
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
                        <select class="form-control" id="expense-confirmed-${e.id}">
                            <c:choose>
                                <c:when test="${e.confirmed == true}">
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

                    <c:if test="${isLeader == true}">
                        <td>
                            <div class="spinner-border text-info" style="display:none" id="expense-updateLoad${e.id}"></div>
                            <input type="button" 
                                   onclick="updateExpense('${url}/${e.id}', ${e.id}, this, '<spring:message code="message.update" />', '<spring:message code="message.error" />')" 
                                   class="btn btn-primary" value="<spring:message code="button.update" />" />
                        </td>
                        <td>
                            <div class="spinner-border text-info" style="display:none" id="expense-load${e.id}"></div>
                            <input type="button" 
                                   onclick="deleteExpense('${url}/${e.id}', ${e.id}, this, '<spring:message code="message.delete" />', '<spring:message code="message.error" />')" 
                                   class="btn btn-danger" value="<spring:message code="button.delete" />" />
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>


        <h4><spring:message code="label.income" /></h4>
        <c:url value="/api/income" var="url" />
        <table class="table">
            <tr>
                <th><spring:message code="label.username" />
                <th><spring:message code="amount" /> (<spring:message code="currency" />)</th>
                <th><spring:message code="income.source" /></th>
                <th><spring:message code="date" /></th>
                <th><spring:message code="description" /></th>
                <th><spring:message code="label.approved" /></th>
                <th><spring:message code="label.confirmed" /></th>
                <th></th>
                <th></th>
            </tr>

            <c:forEach items="${incomes}" var="i">
                <tr id="income-row${i.id}">
                    <td>
                        <input type="text" class="form-control" value="${i.userId.username}" id="income-username-${i.id}" disabled/>
                    </td>

                    <td>
                        <input type="number" class="form-control" value="${i.amount}" id="income-amount-${i.id}"/>
                    </td>
                    <td>  
                        <input type="text" class="form-control" value="${i.source}" id="income-source-${i.id}"/>
                    </td>
                    <td>
                        <fmt:formatDate pattern="yyyy-MM-dd" value="${i.date}" var="date"/>
                        <input type="date" class="form-control" value="${date}" id="income-date-${i.id}" />
                    </td>
                    <td>
                        <textarea class="form-control" id="income-description-${i.id}">${i.description}</textarea>
                    </td>

                    <td>
                        <select class="form-control" id="income-approved-${i.id}">
                            <c:choose>
                                <c:when test="${i.approved == true}">
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
                        <select class="form-control" id="income-confirmed-${i.id}">
                            <c:choose>
                                <c:when test="${i.confirmed == true}">
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

                    <c:if test="${isLeader == true}">
                        <td>
                            <div class="spinner-border text-info" style="display:none" id="income-updateLoad${i.id}"></div>
                            <input type="button" 
                                   onclick="updateIncome('${url}/${i.id}', ${i.id}, this, '<spring:message code="message.update" />', '<spring:message code="message.error" />')" 
                                   class="btn btn-primary" value="<spring:message code="button.update" />" />
                        </td>
                        <td>
                            <div class="spinner-border text-info" style="display:none" id="income-load${i.id}"></div>
                            <input type="button" 
                                   onclick="deleteIncome('${url}/${i.id}', ${i.id}, this, '<spring:message code="message.delete" />', '<spring:message code="message.error" />')" 
                                   class="btn btn-danger" value="<spring:message code="button.delete" />" />
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>

        <c:if test="${pageSize > 0 && Math.ceil(counter/pageSize) > 1}">
            <ul class="pagination">
                <c:forEach begin="1" end="${Math.ceil(counter/pageSize)}" var="i">
                    <c:url value="/group/${group.id}" var="u">
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
</c:if>