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
            var id = $(this).children(".id").val();
            _this.delete(id);
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
            url: '/manage/products',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('상품이 등록되었습니다.');
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
            url: '/manage/products/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('상품이 수정되었습니다.');
            window.location.href = '/productList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete : function (id) {

        $.ajax({
            type: 'DELETE',
            url: '/manage/products/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            alert('상품이 삭제되었습니다.');
            window.location.href='/productList';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();