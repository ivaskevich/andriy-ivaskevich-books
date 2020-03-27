<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>E-library</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
              crossorigin="anonymous">
    </head>
    <body>
    <#include "navbar.ftl">
    <div class="container mt-5">
        <#nested>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script>
        $(function () {
            loadBooks();

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
                        url: '/books',
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
                            addBookToTable(books, response);
                        }
                    });
                } else {
                    var id = $('#save-book-button').attr('name');

                    $.ajax({
                        type: 'POST',
                        url: '/books/update/' + id,
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
                        url: '/books/update/' + id,
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
                    url: '/books/delete/' + id,
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
                books.append('<td><a class="btn btn-outline-primary" href="/books/' + book.id + '">View</a></td>');
                books.append('<td><button class="btn btn-outline-primary edit" name="' + book.id + '">Edit</button></td>');
                books.append('<td><button class="btn btn-outline-danger delete" name="' + book.id + '">Delete</button></td>');
                books.append('</tr>');
            }

            function loadBooks(filter="") {
                var books = $('#booksTable');
                books.empty();
                $.ajax({
                    type: 'GET',
                    url: "/books?filter=" + filter,
                    success: function (response) {
                        response.forEach(function (book) {
                            addBookToTable(books, book);
                        })
                    }
                })
            }
        })
    </script>
    </body>
    </html>
</#macro>