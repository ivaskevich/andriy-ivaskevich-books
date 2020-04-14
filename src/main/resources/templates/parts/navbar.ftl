<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">E-library</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/">Catalog <span class="sr-only">(current)</span></a>
            </li>
            <#if user??>
                <li class="nav-item active">
                    <a class="nav-link" href="/favorite">Favorite</a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item active">
                    <a class="nav-link" href="/admin/books">Books list</a>
                </li>
            </#if>
        </ul>
        <div style="display: flex;flex-wrap: wrap;justify-content: space-around;">
            <form class="form-inline my-2 my-lg-0 mx-1" id="search-form">
                <input id="filter" name="filter" class="form-control mr-sm-2" type="search" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
            <#if name!="Guest">
                <div class="navbar-text mx-1">
                    Login as ${name}
                </div>
                <div class="mx-1">
                    <@l.logout/>
                </div>
            <#else>
                <div class="mx-1">
                    <a class="btn btn-outline-primary" href="/login">Log in</a>
                </div>
                <div class="mx-1">
                    <a class="btn btn-outline-primary" href="/registration">Sign up</a>
                </div>
            </#if>
        </div>
    </div>
</nav>