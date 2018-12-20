<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="js/datatableUtil.js" defer></script>
<script type="text/javascript" src="js/phonebookDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="phonebook.title"/></h3>
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card">
            <div class="card-body py-0 border">
                <form id="filter" class="my-0">
                    <div class="row">
                        <div class="offset-2 col-6">
                            <div class="form-group">
                                <label class="col-form-label" for="lastNameF"><spring:message
                                        code="phonebook.lastName"/></label>
                                <input class="form-control col-6" type="text" name="lastNameF" id="lastNameF">

                                <label class="col-form-label" for="firstNameF"><spring:message
                                        code="phonebook.firstName"/></label>
                                <input class="form-control col-6" type="text" name="firstNameF" id="firstNameF">
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="col-form-label" for="mobilePhoneNumberF"><spring:message
                                        code="phonebook.mobilePhoneNumber"/></label>
                                <input class="form-control col-6" type="text" name="mobilePhoneNumberF" id="mobilePhoneNumberF">

                                <label class="col-form-label" for="homePhoneNumberF"><spring:message
                                        code="phonebook.homePhoneNumber"/></label>
                                <input class="form-control col-6" type="text" name="homePhoneNumberF" id="homePhoneNumberF">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-danger" onclick="clearFilter()">
                    <span class="fa fa-remove"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="updateTable()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="common.filter"/>
                </button>
            </div>
        </div>
        <br/>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="phonebook.lastName"/></th>
                <th><spring:message code="phonebook.firstName"/></th>
                <th><spring:message code="phonebook.surename"/></th>
                <th><spring:message code="phonebook.mobilePhoneNumber"/></th>
                <th><spring:message code="phonebook.homePhoneNumber"/></th>
                <th><spring:message code="phonebook.address"/></th>
                <th><spring:message code="phonebook.email"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="lastName" class="col-form-label"><spring:message code="phonebook.lastName"/></label>
                        <input type="text" class="form-control" id="lastName" name="lastName"
                               placeholder="Enter your lastName">
                    </div>
                    <div class="form-group">
                        <label for="firstName" class="col-form-label"><spring:message code="phonebook.firstName"/></label>
                        <input type="text" class="form-control" id="firstName" name="firstName"
                               placeholder="Enter your firstName">
                    </div>
                    <div class="form-group">
                        <label for="surname" class="col-form-label"><spring:message code="phonebook.surename"/></label>
                        <input type="text" class="form-control" id="surname" name="surname"
                               placeholder="Enter your surname">
                    </div>
                    <div class="form-group">
                        <label for="mobilePhoneNumber" class="col-form-label"><spring:message code="phonebook.mobilePhoneNumber"/></label>
                        <input type="text" class="form-control" id="mobilePhoneNumber" name="mobilePhoneNumber"
                               placeholder="Enter your mobilePhoneNumber">
                    </div>
                    <div class="form-group">
                        <label for="homePhoneNumber" class="col-form-label"><spring:message code="phonebook.homePhoneNumber"/></label>
                        <input type="text" class="form-control" id="homePhoneNumber" name="homePhoneNumber"
                               placeholder="Enter your homePhoneNumber">
                    </div>
                    <div class="form-group">
                        <label for="address" class="col-form-label"><spring:message code="phonebook.address"/></label>
                        <input type="text" class="form-control" id="address" name="address"
                               placeholder="Enter your address">
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-form-label"><spring:message code="phonebook.email"/></label>
                        <input type="text" class="form-control" id="email" name="email"
                               placeholder="Enter your email">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="phonebook"/>
</jsp:include>
</html>
