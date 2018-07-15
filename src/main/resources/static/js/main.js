$(document).ready(function () {
    $('.js-example-basic-single').select2({
        width: '100%'
    });
    $('body').bootstrapMaterialDesign();
    $('[data-toggle="datepicker"]').datepicker({
        format: "yyyy-mm-dd",
        autoHide: true
    });

});

$(document).ready(function () {
    $('#myTableFilter').DataTable({
        "pagingType": "full_numbers",
        "scrollY": "500px",
        "scrollCollapse": true,
    });
});