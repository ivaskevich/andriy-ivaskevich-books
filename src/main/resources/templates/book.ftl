<#import "parts/common.ftl" as c>

<@c.page>
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
        <form action="/" class="form-inline">
            <button class="btn btn-outline-primary" type="submit">Back to catalogue</button>
        </form>
    </div>

</@c.page>