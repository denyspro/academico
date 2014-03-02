$(document).ready(function () {
    var path = window.location.pathname;
    path = path.replace(/\/$/, "");
    path = decodeURIComponent(path);

    $(".nav a").each(function () {
        var href = $(this).attr('href');
        if (path.substring(0, href.length) === href.substring(0, path.length)) {
            $(this).closest('li').addClass('active');
        }
    });
});