var main = {
    init : function () {
        var _this = this;
        $('#btn-saveBuy').on('click', function () {
            _this.save();
        });

        $('#btn-updateBuy').on('click', function () {
            _this.update();
        });

        $('.btn-deleteBuy').on('click', function () {
            var id = $(this).children(".id").val();
            _this.delete(id);
        });

        $('#searchBuy').on('click', function() {
            _this.search();
        })
    },
    save : function () {
        var data = {
            name: $('#name').val(),
            price: $('#price').val(),
            amount: $('#amount').val(),
            content: $('#content').val(),
            buydate: $('#buydate').val()
        };

        $.ajax({
            type: 'POST',
            url: '/manage/buy',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/buyList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update : function () {
        var data = {
            name: $('#name').val(),
            price: $('#price').val(),
            amount: $('#amount').val(),
            content: $('#content').val(),
            buydate: $('#buydate').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/manage/buy/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/buyList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete : function (id) {

        $.ajax({
            type: 'DELETE',
            url: '/manage/buy/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href='/buyList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    search : function() {
        var start = $('#start').val();
        var end = $('#end').val();

        location.href = "/buy/search?start=" + start + "&end=" + end;
    }
};

main.init();