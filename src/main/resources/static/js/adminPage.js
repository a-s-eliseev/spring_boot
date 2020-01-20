$(document).ready(function() {

    $(function () {
        $.ajax({
            method: 'get',
            url: "/admin/",
            success: function (result) {
                $.each(result, function (index, customer) {

                    let roles = '';
                    $.each(customer['roles'], function (key, value) {
                        roles += '<span>';
                        roles += value.name;
                        roles += '</span><br>';
                    });

                    let customerRow = '<tr>' +
                        '<td>' + customer.id + '</td>' +
                        '<td>' + customer.username + '</td>' +
                        '<td>' + customer.password + '</td>' +
                        '<td>' + roles + '</td>' +
                        '<td class="text-center">' +
                        '<button class="btnDelete btn btn-primary" value=' + customer.id + '>' +
                        '<span>Delete</span>' +
                        '<button class="btnEdit btn btn-primary" data-toggle="modal" data-target="#exampleModal" value=' + customer.id + '>' +
                        '<span>Edit</span>' +
                        '</td>' +
                        '</tr>';

                    $('#customerTable tbody').append(customerRow);
                });
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    });

    $(document).on("click", ".btnDelete", function () {

        let customerId = $(this).parent().find('button').val();
        let workingObject = $(this);

        $.ajax({
            type: "delete",
            url: "/admin/deleteUser/" + customerId,
            success: function (resultMsg) {
                workingObject.closest("tr").remove();
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    });

    $("#customerForm").submit(function (event) {
        event.preventDefault();

        let formData = {
            username: $("#usernameAdd").val(),
            password: $("#passwordAdd").val()
        };

        $.ajax({
            type: "put",
            contentType: "application/json",
            url: "admin/addUser",
            data: JSON.stringify(formData),
            dataType: 'json',
        });
    });

    $(document).on("click", ".btnEdit", function (e) {

        let id = $(this).parent().find('button').val();
        let username;
        let password;

        $.ajax({
            type: "get",
            url: "/admin/" + id,
            success: function (result) {
                $.each(result, function (key, value) {
                    username = result.username;
                    password = result.password;
                    $("#id").val(id);
                    $("#username").val(username);
                    $("#password").val(password);
                });
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    });

    $("#customizedForm").submit(function (event) {
        event.preventDefault();

        let formDataPatch = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
        };

        $.ajax({
            type: "patch",
            contentType: "application/json",
            url: "/admin/editUser",
            data: JSON.stringify(formDataPatch),
            dataType: 'json',
        });
        location.reload();
    });
});