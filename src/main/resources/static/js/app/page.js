var page = {
    init: function () {
        var param = location.search.split('page=')[1];
        var lastPage = $("#lastPage").val();

        if (param == null || param == 1) {
            $("#page1").addClass('active');
            $(".prevPage").css("display", "none");
            if (lastPage == 1) {
                $(".nextPage").css("display", "none");
            }
        } else {
            $("#page" + param).addClass('active');
        }

        if (param == lastPage) {
            $(".nextPage").css("display", "none");
        }
    }
};

page.init();