
$(document).ready(function () {
    if (isMobile()) {
        $('ul.sidebar-menu a.ui-link, ul.nav navbar a.ui-link').click(function () {
            $(this).prop('disabled', true);
            showBar();
        })
    }
});

