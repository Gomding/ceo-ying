var main = {
    init : function () {
        var _this = this;
        $('#btn-saveSell').on('click', function () {
            _this.save();
        });

        $('#btn-updateSell').on('click', function () {
            _this.update();
        });

        $('.btn-deleteSell').on('click', function () {
            var id = $(this).children(".id").val();
            _this.delete(id);
        });

        $('#searchSell').on('click', function() {
            _this.search();
        })
    },
    save : function () {
        var data = {
            name: $('#name').val(),
            product: $('#product').val(),
            price: $('#price').val(),
            amount: $('#amount').val(),
            methodOfPayment: $('#methodOfPayment').val(),
            profit: $('#profit').val(),
            selldate: $('#selldate').val()
        };

        $.ajax({
            type: 'POST',
            url: '/manage/sells',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/sellList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update : function () {
        var data = {
            name: $('#name').val(),
            product: $('#product').val(),
            price: $('#price').val(),
            amount: $('#amount').val(),
            methodOfPayment: $('#methodOfPayment').val(),
            profit: $('#profit').val(),
            selldate: $('#selldate').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/manage/sells/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/sellList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete : function (id) {

        $.ajax({
            type: 'DELETE',
            url: '/manage/sells/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href='/sellList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    search : function() {
        var start = $('#start').val();
        var end = $('#end').val();

        location.href = "/sells/search?start=" + start + "&end=" + end;
    }
};

main.init();