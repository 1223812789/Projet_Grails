<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'annonce.label', default: 'Annonce')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-annonce" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>



    <div id="list-annonce" class="content scaffold-list" role="main">
        <h1><g:message code="default.list.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

        <table>
            <thead>
            <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Price</th>
                <th>Illustrations</th>
                <th>Is active</th>
                <th>Author</th>
            </tr>
            </thead>

            <tbody>
            <g:each var="annonce" in="${annonceList}" status="i" >
                <tr >
                    <td>
                        <g:link controller="annonce" action="show" id="${annonce.id}">${annonce.title}</g:link>
                    </td>
                    <td><div class="property-value" aria-labelledby="description-label">${annonce.description}</div></td>
                    <td><div class="property-value" aria-labelledby="price-label">${annonce.price}</div></td>
                    <td><g:each var="illustration" in="${annonce.illustrations}">
                        <a href="${createLink(controller:'illustration',action:'show',id:illustration.id)}"><img src="http://localhost:8081/assets/${illustration.filename}" alt="${illustration.filename}" width="60" height="80"/></a>
                    </g:each>
                    </td>

                    <td>
                        <div class="property-value" aria-labelledby="isActive-label">${annonce.isActive}</div>
                    </td>

                    <td><g:each in="${annonce.author}" var="user">
                        <g:link controller="user" action="show" id="${user.id}">
                            ${user.username}
                        </g:link>
                    </g:each>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <g:paginate total="${annonceCount ?: 0}" />
        </div>
    </div>

    </body>
</html>