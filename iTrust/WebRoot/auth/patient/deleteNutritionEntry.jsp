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
if (request.getParameter("ent") != null) {
	EditMyNutritionAction action = new EditMyNutritionAction(prodDAO, loggedInMID.longValue());
	List<FoodDiaryBean> diaries = action.getMyFoodDiaryEntries();
	session.setAttribute("diaries", diaries);
	String entParameter = request.getParameter("ent");
	int entIndex = 0;

	try {
		entIndex = Integer.parseInt(entParameter);
		
		if(entIndex >= diaries.size() || entIndex < 0) {
			entIndex = 0;
			response.sendRedirect("oops.jsp");
		}
	} catch (NumberFormatException nfe) {
		response.sendRedirect("editNutrition.jsp");
	}

	FoodDiaryBean newEntry = diaries.get(entIndex);
	Date currDate = newEntry.getEntryDate();
	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
	format.setLenient(false);
	String dateStr = format.format(currDate);
	
if (diaries.size() > 0) {
	action.deleteFoodDiaryEntry(newEntry);

	%>
	<div align=center>
		<span class="iTrustMessage">Diary entry successfully deleted.</span>
	</div>
<%

	int index = 0;
	
%>
</table>
</div>
	<br></br>

		<table class="fTable" align=center style="width: 500px;">
		<tr>
			<th colspan=4>Date</th>
			<th colspan=2>Meal Type</th>
			<th colspan=2>Food Name</th>
			<th colspan=2>Num. Servings</th>
			<th colspan=2>Num. Calories</th>
			<th colspan=2>Fat</th>
			<th colspan=2>Sodium</th>
			<th colspan=2>Carb</th>
			<th colspan=2>Sugar</th>
			<th colspan=2>Fiber</th>
			<th colspan=2>Protein</th>
		</tr>
<%
	diaries = action.getMyFoodDiaryEntries();

	for(FoodDiaryBean b : diaries) {
		String row = "<tr";
%>
		<%=row+""+((index%2 == 1)?" class=\"alt\"":"")+">"%>
			<td colspan=4><%= StringEscapeUtils.escapeHtml("" + ( b.getEntryDate() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getMealType() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getFoodName() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getServings() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getCalories() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsFat() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getMilligramsSodium() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsCarb() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsSugar() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsFiber() )) %></td>
			<td colspan=2><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsProtein() )) %></td>	
		</tr>
<%		index ++;
		}
	} else { %>
		<td> <i>You have no entries.</i> </td>
<%	} %>
</table>
<%
}
else {
	response.sendRedirect("editNutrition.jsp");
} 
%>
<%@include file="/footer.jsp" %>