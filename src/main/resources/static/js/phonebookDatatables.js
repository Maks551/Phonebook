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

function savePhonebook() {
    $("#homePhoneNumber").val() === "" ? $("#homePhoneNumber").val(null) : $("#homePhoneNumber").val();
    $("#address").val() === "" ? $("#address").val(null) : $("#address").val();
    $("#email").val() === "" ? $("#email").val(null) : $("#email").val();
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