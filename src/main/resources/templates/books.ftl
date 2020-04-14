<!DOCTYPE html>
<html lang="en">
<#include "parts/head.ftl">
<#include "parts/navbar.ftl">
<body>
<div class="container mt-5">
    <div>
        <table class="table table-striped">
            <thead>
            <th>ISBN</th>
            <th>Title</th>
            <th>Author</th>
            <th>Publishing year</th>
            <th></th>
            <th></th>
            <th></th>
            </thead>
            <tbody id="booksTable"></tbody>
        </table>
    </div>
    <br>
    <div>
        <a class="btn btn-outline-primary" data-toggle="collapse" href="#collapseExample"
           role="button" aria-expanded="false" aria-controls="collapseExample">
            Add book
        </a><br><br>
        <div class="collapse" id="collapseExample">
            <div class="form-group">
                <form id="newBookForm">
                    <div id="isbnDiv" class="form-group">
                        <input type="text" class="form-control" name="isbn" placeholder="ISBN">
                    </div>
                    <div class="form-group mt-3">
                        <input type="text" class="form-control" name="title" placeholder="Book title">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="author" placeholder="Author">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="publishingYear" placeholder="Publishing year">
                    </div>
                    <div class="form-group">
                        <textarea class="form-control" name="description" rows="3" placeholder="Description"></textarea>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="filename" placeholder="Book cover url">
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <div class="form-group">
                        <button id="save-book-button" class="btn btn-outline-primary" type="submit">Save book</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<#include "parts/script.ftl">
<script>
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr) {
            xhr.setRequestHeader(header, token);
        });

        loadBooks();

        function loadBooks(filter="") {
            var books = $('#booksTable');
            books.empty();
            $.ajax({
                type: 'GET',
                url: "/book-list?filter=" + filter,
                success: function (response) {
                    response.forEach(function (book) {
                        addBookToTable(books, book);
                    })
                }
            })
        }

        $('#search-form').submit(function (e) {
            e.preventDefault();
            var filter = $('#filter').val();
            loadBooks(filter);
        });

        $('#newBookForm').submit(function (e) {
            e.preventDefault();
            var submitType = $('#save-book-button').text();
            var books = $('#booksTable');

            if (submitType === "Save book") {
                $.ajax({
                    type: 'POST',
                    url: '/admin/books',
                    dataType: 'json',
                    data: JSON.stringify({
                        isbn: $(this).find('[name=isbn]').val(),
                        title: $(this).find('[name=title]').val(),
                        author: $(this).find('[name=author]').val(),
                        publishingYear: $(this).find('[name=publishingYear]').val(),
                        description: $(this).find('[name=description]').val(),
                        filename: $(this).find('[name=filename]').val()
                    }),
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader('Content-Type', 'application/json')
                    },
                    success: function (response) {
                        $("#collapseExample").removeClass("show");
                        var isbn = $("input[name='isbn']");
                        if (isbn.hasClass("is-invalid")) {
                            isbn.removeClass("is-invalid");
                            $(".invalid-feedback").remove();
                        }
                        $(".form-control").val('');
                        addBookToTable(books, response);
                    },
                    error: function (data, textStatus, xhr) {
                        if (data.responseText === "isbnError") {
                            $("input[name='isbn']").addClass("is-invalid");
                            $("#isbnDiv").append('<div class="invalid-feedback">' +
                                'Invalid ISBN ! Please, check and input again !' +
                                '</div>'
                            )
                        } else alert("All of fields shouldn`t be empty !")
                    }
                });
            } else {
                var id = $('#save-book-button').attr('name');

                $.ajax({
                    type: 'POST',
                    url: '/admin/books/update/' + id,
                    dataType: 'json',
                    data: JSON.stringify({
                        isbn: $(this).find('[name=isbn]').val(),
                        title: $(this).find('[name=title]').val(),
                        author: $(this).find('[name=author]').val(),
                        publishingYear: $(this).find('[name=publishingYear]').val(),
                        description: $(this).find('[name=description]').val(),
                        filename: $(this).find('[name=filename]').val()
                    }),
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader('Content-Type', 'application/json')
                    },
                    success: function (response) {
                        $("#collapseExample").removeClass("show");
                        $(".form-control").val('');
                        $('#save-book-button').html("Save book").attr('name', '');
                        loadBooks();
                    }
                });
            }
        });

        $(document).on('click', ".edit", function () {
            var id = $(this).attr('name');
            if (id === $('#save-book-button').attr('name')) {
                $("#collapseExample").removeClass("show");
                $('#save-book-button').html("Save book").attr('name', '');
                $(".form-control").val('');
            } else {
                $.ajax({
                    type: 'GET',
                    url: '/admin/books/update/' + id,
                    success: function (response) {
                        $(document).find('[name=isbn]').val(response.isbn);
                        $(document).find('[name=title]').val(response.title);
                        $(document).find('[name=author]').val(response.author);
                        $(document).find('[name=publishingYear]').val(response.publishingYear);
                        $(document).find('[name=description]').val(response.description);
                        $(document).find('[name=filename]').val(response.filename);
                        $('#save-book-button').html("Update book").attr('name', id);
                        $("#collapseExample").addClass("show");

                    }
                })
            }
        });

        $(document).on('click', ".delete", function () {
            var id = $(this).attr('name');
            $.ajax({
                type: 'POST',
                url: '/admin/books/delete/' + id,
                success: function (response) {
                    loadBooks();
                }
            })
        });

        function addBookToTable(books, book) {
            books.append('<tr>');
            books.append('<td>' + book.isbn + '</td>');
            books.append('<td>' + book.title + '</td>');
            books.append('<td>' + book.author + '</td>');
            books.append('<td>' + book.publishingYear + '</td>');
            books.append('<td><a class="btn btn-outline-primary" href="/catalogue/book/' + book.id + '">View</a></td>');
            books.append('<td><button class="btn btn-outline-primary edit" name="' + book.id + '">Edit</button></td>');
            books.append('<td><button class="btn btn-outline-danger delete" name="' + book.id + '">Delete</button></td>');
            books.append('</tr>');
        }
    })
</script>
</body>
</html>
