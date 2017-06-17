<%@ page contentType="text/html; charset=UTF8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<f:loadBundle basename="ApplicationMessages" var="bundle" />
<f:view>
  <html>
  <head>
  <title><h:outputText value="#{bundle['mancala.game.title']}" /></title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/mancala.css" />
  </head>
  <body>
  <h1><h:outputText value="#{bundle['mancala.game.header.welcome']}" /></h1>
  <h:form>
  
    <h:dataTable border="1" var="player" value="#{gameController.activePlayers}">
      <h:column>
        <f:facet name="header">
          <h:outputText value="#{bundle['mancala.game.player.name']}" />
        </f:facet>
        <h:outputText value="${player.name}" />
      </h:column>
    </h:dataTable>

    <h:commandButton id="newGame" type="submit" value="#{bundle['mancala.game.new.game']}" action="#{gameController.newGame}" />
  </h:form>
  </body>
  </html>
</f:view>
