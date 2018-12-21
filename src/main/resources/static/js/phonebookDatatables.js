const ajaxUrl = "ajax/profile/phonebooks/";
let datatableApi;

function updateTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

function prepareToSave() {
    $("#homePhoneNumber").val().trim() === "" ? $("#homePhoneNumber").val('') : $("#homePhoneNumber").val();
    $("#address").val().trim() === "" ? $("#address").val('') : $("#address").val();
    $("#email").val().trim() === "" ? $("#email").val('') : $("#email").val();
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "language": {
            "search": i18n["common.search"]
        },
        "columns": [
            {
                "data": "lastName"
            },
            {
                "data": "firstName"
            },
            {
                "data": "surname"
            },
            {
                "data": "mobilePhoneNumber"
            },
            {
                "data": "homePhoneNumber"
            },
            {
                "data": "address"
            },
            {
                "data": "email"
            },
            {
                "render": renderEditBtn,
                "defaultContent": "",
                "orderable": false
            },
            {
                "render": renderDeleteBtn,
                "defaultContent": "",
                "orderable": false
            }
        ],
        "initComplete": makeEditable
    });
});