<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditMyNutritionAction"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Delete Nutrition Entry";
%>
<%@include file="/header.jsp" %>

<div align="center">
<h2>My Nutrition</h2>

<%
	if(request.getParameter("ent") == null) {
		response.sendRedirect("/iTrust/auth/patient/viewNutrition.jsp");
	}
	
	boolean formIsFilled = request.getParameter("formIsFilled") != null
			&& request.getParameter("formIsFilled").equals("true");
	
	if(formIsFilled) {
		int ent = Integer.parseInt(request.getParameter("ent"));
		EditMyNutritionAction action = new EditMyNutritionAction(prodDAO, loggedInMID.longValue());

		action.deleteFoodDiaryEntry(action.getSingleEntry(ent));
		
		/* loggingAction.logEvent(
				TransactionType.PATIENT_DELETE_DIARY, loggedInMID,
				loggedInMID, ""); */
		response.sendRedirect("/iTrust/auth/patient/viewNutrition.jsp?deleted=true");
	}
%>

<div align=center>
	<h3>Are you sure you want to delete this entry?</h3>
</div>
<br />
<table align=center>
	<tr>
		<td>
			<form action="deleteNutritionEntry.jsp" method="post">
				<input type="hidden" name="formIsFilled" value="true">
				<input type="hidden" name="ent" value="<%= request.getParameter("ent") %>">
				<input type="submit" style="font-size: 16pt; font-weight: bold;" value="Delete">
			</form>
		</td>
		<td>
			<form action="viewNutrition.jsp" method="post">
				<input type="submit" style="font-size: 16pt; font-weight: bold;" value="Return">
			</form>
		</td>
	</tr>
</table>

<%@include file="/footer.jsp" %>