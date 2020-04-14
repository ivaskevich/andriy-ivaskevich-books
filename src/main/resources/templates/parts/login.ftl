<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group">
            <label for="username">User name </label>
            <input id="username" type="text"
                   class="form-control col-sm-5 ${(usernameError??)?string('is-invalid','')}"
                   name="username" placeholder="Username" value="<#if user??>${user.username}</#if>"/>
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password">Password </label>
            <input id="password" type="password"
                   class="form-control col-sm-5 ${(passwordError??)?string('is-invalid','')}"
                   name="password" placeholder="Password"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mx-1">
            <#if isRegisterForm>Sign up<#else>Log in</#if>
        </button>
        <#if !isRegisterForm>
            <span class="ml-10">Haven`t account yet ? </span><a class="btn btn-outline-primary ml-1"
                                                                href="/registration">Sign up</a>
        </#if>
    </form>
</#macro>

<#macro logout>
    <form class="mx-1" action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="btn btn-outline-primary" type="submit" value="Log out"/>
    </form>
</#macro>