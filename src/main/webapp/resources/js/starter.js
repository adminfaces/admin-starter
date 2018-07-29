$(document).ready(function () {
    activateMobileBar();
});

/**
 * adds mobile loading bar to links 
 * 
 * @return {undefined}
 */
function activateMobileBar() {
    if (isMobile()) {
        $('ul.sidebar-menu a.ui-link, ul.navbar-nav li a.ui-link, ol.breadcrumb a').click(function () {
            $(this).prop('disabled', true);
            showBar();
        });
    }
}



