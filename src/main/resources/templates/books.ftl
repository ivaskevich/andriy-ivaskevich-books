<#import "parts/common.ftl" as c>

<@c.page>
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
                    <div class="form-group">
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
                    <div class="form-group">
                        <button id="save-book-button" class="btn btn-outline-primary" type="submit">Save book</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</@c.page>