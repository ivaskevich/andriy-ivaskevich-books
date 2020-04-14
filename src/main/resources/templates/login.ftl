<#import "parts/login.ftl" as l>

<!DOCTYPE html>
<html lang="en">
<#include "parts/head.ftl">
<#include "parts/navbar.ftl">
<body>
<div class="container mt-5">
    <@l.login "/login" false/>
</div>
<#include "parts/script.ftl">
<script>
    $(function () {

    })
</script>
</body>
</html>