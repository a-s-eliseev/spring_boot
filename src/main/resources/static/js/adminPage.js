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

        window.location.reload();
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

                    $.each(result['roles'], function (key, value) {
                        if(value.name == "ROLE_USER") {
                            $("input[name=roleUser]").attr("checked","checked");
                        };
                        if(value.name == "ROLE_ADMIN") {
                            $("input[name=roleAdmin]").attr("checked","checked");
                        };
                    });
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

        let roleUser;
        let roleAdmin;

        if ($('#role1').is(':checked')){
            roleUser = {
                id: "1",
                name: "ROLE_USER"
            }
        } else {
            roleUser = {}
        }

        if ($('#role').is(':checked')){
            roleAdmin = {
                id: "2",
                name: "ROLE_ADMIN"
            }
        } else {
            roleAdmin = {}
        }

        let formDataPatch = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            roles: [roleUser, roleAdmin],
        };

        $.ajax({
            type: "patch",
            contentType: "application/json",
            url: "/admin/editUser",
            data: JSON.stringify(formDataPatch),
            dataType: 'json',
        });
        window.location.reload();
    });
});