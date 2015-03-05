
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyNutritionAction"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Patient Nutrition";
%>
<%@include file="/header.jsp" %>

<div align="center">
<h2>Patient Nutrition</h2>
Enter a patient MID.
<form action="viewNutritionHCP.jsp" method="post">
	<input type="hidden" name="formIsFilled" value="true">
	<input name="searchMID"
	   	value=""
	   	type="text">
<br><br>
	<div align="center">
		<input type="submit" name="action"
		style="font-size: 16pt; font-weight: bold;"
		value="Search">
	</div>
</form>
<br><br>
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
	boolean formIsFilled = request.getParameter("formIsFilled") != null 
	&& request.getParameter("formIsFilled").equals("true");

	if (formIsFilled) {
		long searchMID = Integer.parseInt(request.getParameter("searchMID"));
		ViewMyNutritionAction action = new ViewMyNutritionAction(prodDAO, searchMID);
		List<FoodDiaryBean> diaries = action.getMyFoodDiaryEntries();

		if (diaries.size() > 0) {
	
			int index = 0;
			int calTot = 0;
			int fatTot = 0;
			int sodTot = 0;
			int carbTot = 0;
			int sugTot = 0;
			int fibTot = 0;
			int protTot = 0;
			
			for(FoodDiaryBean b : diaries) {
				String row = "<tr";
				calTot += b.getCalories() * b.getServings();
				fatTot += b.getGramsFat() * b.getServings();
				sodTot += b.getMilligramsSodium() * b.getServings();
				carbTot += b.getGramsCarb() * b.getServings();
				sugTot += b.getGramsSugar() * b.getServings();
				fibTot += b.getGramsFiber() * b.getServings();
				protTot += b.getGramsProtein() * b.getServings();
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
	%>
			<tr>
				<td colspan=2></td>
				<td colspan=2></td>
				<td colspan=2>Daily</td>
				<td colspan=2>Total</td>
				<td colspan=2></td>
				<td colspan=2><%= calTot %></td>
				<td colspan=2><%= fatTot %></td>
				<td colspan=2><%= sodTot %></td>
				<td colspan=2><%= carbTot %></td>
				<td colspan=2><%= sugTot %></td>
				<td colspan=2><%= fibTot %></td>
				<td colspan=2><%= protTot %></td>
			</tr>
	<%
		} else { %>
			<td> <i>You have no entries.</i> </td>
	<%	}
	}
%>
</div>

<%@include file="/footer.jsp" %>
