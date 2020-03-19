<#import "parts/common.ftl" as c>

<@c.page>
    <#if alert??>
        <div class="alert alert-secondary">
            ${alert}
        </div>
    </#if>
    <#if !book??>
        <div>
            <#if (books?size>0) >
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
                    <tbody>
                    <#list books as book>
                        <tr>
                            <td>${book.isbn}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.publishingYear}</td>
                            <td>
                                <form action="/books/${book.id}" method="get" class="form-inline">
                                    <button class="btn btn-outline-primary" type="submit">View the book</button>
                                </form>
                            </td>
                            <td>
                                <form action="/books/update/${book.id}" method="get" class="form-inline">
                                    <button class="btn btn-outline-primary" type="submit">Update the book</button>
                                </form>
                            </td>
                            <td>
                                <form action="/books/delete/${book.id}" method="post" class="form-inline">
                                    <button class="btn btn-outline-danger" type="submit">Delete book</button>
                                </form>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </#if>
        </div>
    </#if>
    <br>
    <div>
        <#if !book??>
            <a class="btn btn-outline-primary" data-toggle="collapse" href="#collapseExample" role="button"
               aria-expanded="false"
               aria-controls="collapseExample">
                Add book
            </a><br>
        </#if>
        <br>
        <div class="collapse <#if book??>show</#if>" id="collapseExample">
            <div class="form-group">
                <form method="post" action="<#if !book??>/books<#else>/books/update/${book.id}</#if>">
                    <div class="form-group">
                        <input type="text" class="form-control" name="isbn"
                               value="<#if book??>${book.isbn}</#if>" placeholder="ISBN">
                    </div>
                    <div class="form-group mt-3">
                        <input type="text" class="form-control" name="title" value="<#if book??>${book.title}</#if>"
                               placeholder="Book title">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="author"
                               value="<#if book??>${book.author}</#if>" placeholder="Author">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="publishingYear" name="publishingYear"
                               value="<#if book??>${book.publishingYear}</#if>" placeholder="Publishing year">
                    </div>
                    <div class="form-group">
                        <textarea class="form-control" name="description" rows="4"
                                  placeholder="Description"><#if book??>${book.description}</#if></textarea>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="filename"
                               value="<#if book??>${book.filename}</#if>" placeholder="Book cover url">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-outline-primary"
                                type="submit"><#if !book??>Save book<#else>Update book</#if></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</@c.page>