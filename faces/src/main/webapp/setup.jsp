<%@ page contentType="text/html; charset=UTF8" %> 
<%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
<f:loadBundle basename="ApplicationMessages" var="bundle"/>
<f:view>
<html>
<head>
  <title><h:outputText value="#{bundle['mancala.setup.title']}"/></title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/mancala.css" />
</head>
<body>
  <h:form>  
    <h:dataTable border="1" var="player" value="#{gameController.players}">
      <h:column>
        <f:facet name="header">
          <h:outputText value="#{bundle['mancala.setup.player.name']}"/>
        </f:facet>        
        <h:outputText value="#{player.name}"/>
      </h:column>
      <h:column>
        <h:commandButton id="removePlayer" type="submit" value="#{bundle['mancala.setup.remove.player']}" action="#{gameController.removePlayer}"/>
      </h:column>
    </h:dataTable>
    <h1><h:outputText value="#{bundle['mancala.setup.header.add.player']}"/></h1>
    <table>
      <tr>
        <td><h:outputText value="#{bundle['mancala.setup.player.name']}"/></td>
        <td><h:inputText value="#{gameController.newPlayer}"/></td>
      </tr>
     </table>
     <table>
     <h:commandButton id="addPlayer" type="submit" value="#{bundle['mancala.setup.add.player']}" action="#{gameController.addPlayer}"/>
     <h1><h:outputText value="#{bundle['mancala.setup.header.initialize.game']}"/></h1>
      <tr>
        <td><h:outputText value="#{bundle['mancala.setup.amount.holes']}"/></td>
        <td><h:inputText value="#{gameController.holes}"/></td>
      </tr>
      <tr>
        <td><h:outputText value="#{bundle['mancala.setup.amount.beads']}"/></td>
        <td><h:inputText value="#{gameController.beads}"/></td>
      </tr>
    </table>            
    <h:commandButton id="startGame" type="submit" value="#{bundle['mancala.setup.start.game']}" action="#{gameController.startGame}"/>
  </h:form>
</body>
</html>
</f:view>