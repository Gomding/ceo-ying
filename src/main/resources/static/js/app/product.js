var main = {
    init : function () {
        var _this = this;
        $('#btn-saveProduct').on('click', function () {
            _this.save();
        });

        $('#btn-updateProduct').on('click', function () {
            _this.update();
        });

        $('#btn-deleteProduct').on('click', function () {
            _this.delete();
        })
    },
    save : function () {
        var data = {
            name: $('#name').val(),
            amount: $('#amount').val(),
            price: $('#price').val(),
            costprice: $('#costprice').val(),
            sellByDate: $('#sellByDate').val()
        };

        $.ajax({
            type: 'POST',
            url: '/products',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/productList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update : function () {
        var data = {
            name: $('#name').val(),
            amount: $('#amount').val(),
            price: $('#price').val(),
            costprice: $('#costprice').val(),
            sellByDate: $('#sellByDate').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/products/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/productList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/products/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href='/productList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();