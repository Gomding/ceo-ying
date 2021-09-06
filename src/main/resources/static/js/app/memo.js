var main = {
    init: function () {
        var _this = this;
        $('#btn-saveMemo').on('click', function () {
            _this.save();
        });

        $('.btn-deleteMemo').on('click', function () {
            var id = $(this).children(".id").val();
            _this.delete(id);
        });
    },
    save: function () {
        var data = {
            content: $('#content').val(),
            link: $('#link').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/manage/memo',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/memoList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },


    delete: function (id) {

        $.ajax({
            type: 'DELETE',
            url: '/manage/memo/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/memoList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();