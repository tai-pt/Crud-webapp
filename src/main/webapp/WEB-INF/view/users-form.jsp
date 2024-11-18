<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container col-md-6">
    <h2>${user != null ? "Edit User" : "Add User"}</h2>
    <form action="${user != null ? 'update' : 'insert'}" method="post">
        <!-- Hidden field for ID when editing -->
        <c:if test="${user != null}">
            <input type="hidden" name="id" value="${user.id}" />
        </c:if>
        <div class="form-group">
            <label>Name</label>
            <input type="text" name="name" class="form-control" value="${user != null ? user.name : ''}" required>
        </div>
        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" class="form-control" value="${user != null ? user.email : ''}" required>
        </div>
        <div class="form-group">
            <label>Country</label>
            <input type="text" name="country" class="form-control" value="${user != null ? user.country : ''}" required>
        </div>
        <button type="submit" class="btn btn-success">Save</button>
        <a href="list" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
