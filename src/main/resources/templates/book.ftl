<!DOCTYPE html>
<html lang="en">
<#include "parts/head.ftl">
<#include "parts/navbar.ftl">
<body>
<div class="container mt-5">
    <div class="card mb-3" style="max-width: 1200px;">
        <div class="row no-gutters">
            <div class="col-md-4">
                <img src="${book.filename}" class="card-img" alt="Card image cap">
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">${book.title}</h5>
                    <p class="card-text">Author : ${book.author}</p>
                    <p class="card-text">Publishing year : ${book.publishingYear}</p>
                    <br>
                    <p class="card-text">${book.description}</p>
                    <p class="card-text">
                        <small class="text-muted">ISBN: ${book.isbn}</small>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div>
        <a href="/" class="btn btn-outline-primary">Back to catalogue</a>
    </div>
</div>
<br><br>
<#include "parts/script.ftl">
</body>
</html>