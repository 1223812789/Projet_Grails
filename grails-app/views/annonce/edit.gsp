<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'annonce.label', default: 'Annonce')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#edit-annonce" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-annonce" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.annonce}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.annonce}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.annonce}" method="PUT">
                <g:hiddenField name="version" value="${this.annonce?.version}" />

                <fieldset class="form">
                    <div class="fieldcontain required">
                        <label for="title">Title
                            <span class="required-indicator">*</span>
                        </label><input type="text" name="title" value="title 1 Alice" required="" maxlength="50" id="title">
                    </div>
                    <div class="fieldcontain required">
                    <label for="description">Description
                        <span class="required-indicator">*</span>
                    </label>
                        <input type="text" name="description" value="Description 1 Alice" required="" id="description">
                </div>
                    <div class="fieldcontain required">
                    <label for="price">Price
                        <span class="required-indicator">*</span>
                    </label>
                        <input type="number decimal" name="price" value="100" required="" step="0.01" min="0.0" id="price">
                    </div>

                    <div class="fieldcontain">
                    <label for="illustrations">Illustrations</label><ul>
                        <li class="fieldcontain">
                        <span id="illustrations-label" class="property-label">Illustrations</span>
                        <td><g:each var="illustration" in="${annonce.illustrations}">
                            <img src="http://localhost:8081/assets/${illustration.filename}" alt="${illustration.filename}" width="60" height="80"/>
                        </g:each>
                        </td>
                        </li>>

                        <a href="/illustration/create?annonce.id=1">Add Illustration</a>
                    </div>

                    <div class="fieldcontain">
                    <label for="isActive">Is Active</label><input type="hidden" name="_isActive"><input type="checkbox" name="isActive" checked="checked" id="isActive">
                </div><div class="fieldcontain required">
                    <label for="author">Author
                        <span class="required-indicator">*</span>
                    </label><select name="author.id" required="" id="author">
                        <option value="1">User(username:admin)</option>
                        <option value="2">User(username:moderator)</option>
                        <option value="3">User(username:client)</option>
                        <option value="4" selected="selected">User(username:Alice)</option>
                        <option value="5">User(username:Bobby)</option>
                        <option value="6">User(username:Charly)</option>
                        <option value="7">User(username:Denis)</option>
                        <option value="8">User(username:Etienne)</option>
                    </select>
                </div>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
