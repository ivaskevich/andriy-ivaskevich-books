<!DOCTYPE html>
<html lang="en">
<#include "parts/head.ftl">
<#include "parts/navbar.ftl">
<body>
<div class="container mt-5">
    <div id="catalogue" style="display: flex;flex-wrap: wrap;justify-content: space-around;"></div>
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

        $('#search-form').submit(function (e) {
            e.preventDefault();
            var filter = $('#filter').val();
            loadBooks(filter);
        });

        function addBookToCatalogue(catalogue, book) {
            catalogue.append(
                '<div class="card mb-3" style="width: 100%; max-width: 510px;">' +
                '<div class="row no-gutters">' +
                '<div class="col-md-4">' +
                '<img src="' + book.filename + '" class="card-img-top" alt="...">' +
                '</div>' +
                '<div class="col-md-8">' +
                '<div class="card-body">' +
                '<h5 class="card-title">' + book.title + '</h5>' +
                '<p class="card-text">Author : ' + book.author + '</p>' +
                '<p class="card-text">Publishing year : ' + book.publishingYear + '</p>' +
                '<p class="card-text"><small class="text-muted">ISBN: ' + book.isbn + '</small></p>' +
                '<div style="display: flex;flex-wrap: wrap;justify-content: space-around;">' +
                '<a class="btn btn-outline-primary" href="/catalogue/book/' + book.id + '">View</a>' +
                '<#if user??>' +
                '<button class="btn btn-outline-primary book" name="' + book.id + '">Add to favorites</a>' +
                '</#if>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>');
        }

        function addFavoriteToCatalogue(catalogue, book) {
            catalogue.append(
                '<div class="card mb-3" style="width: 100%; max-width: 510px;">' +
                '<div class="row no-gutters">' +
                '<div class="col-md-4">' +
                '<img src="' + book.filename + '" class="card-img-top" alt="...">' +
                '</div>' +
                '<div class="col-md-8">' +
                '<div class="card-body">' +
                '<h5 class="card-title">' + book.title + '</h5>' +
                '<p class="card-text">Author : ' + book.author + '</p>' +
                '<p class="card-text">Publishing year : ' + book.publishingYear + '</p>' +
                '<p class="card-text"><small class="text-muted">ISBN: ' + book.isbn + '</small></p>' +
                '<div style="display: flex;flex-wrap: wrap;justify-content: space-around;">' +
                '<a class="btn btn-outline-primary" href="/catalogue/book/' + book.id + '">View</a>' +
                '<#if user??>' +
                '<button class="btn btn-outline-danger book" name="' + book.id + '">Remove from favorites</a>' +
                '</#if>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>');
        }

        $(document).on('click', ".book", function () {
            var id = $(this).attr('name');
            if ($(this).text() === "Add to favorites") {
                $.ajax({
                    type: 'POST',
                    url: '/favorite/add/' + id,
                    success: function (response) {
                        loadBooks()
                    }
                })
            } else {
                $.ajax({
                    type: 'POST',
                    url: '/favorite/delete/' + id,
                    success: function (response) {
                        loadBooks()
                    }
                })
            }
        });

        function loadBooks(filter="") {
            var catalogue = $('#catalogue');
            var userBooks;
            catalogue.empty();

            $.ajax({
                type: 'GET',
                url: "/user-books-id",
                success: function (response) {
                    userBooks = response;
                }
            });

            $.ajax({
                type: 'GET',
                url: "/book-list?filter=" + filter,
                success: function (response) {
                    if (userBooks) {
                        response.forEach(function (book) {
                            if (userBooks.indexOf(book.id) === -1) {
                                addBookToCatalogue(catalogue, book);
                            } else {
                                addFavoriteToCatalogue(catalogue, book);
                            }
                        })
                    }
                }
            })
        }
    })
</script>
</body>
</html>