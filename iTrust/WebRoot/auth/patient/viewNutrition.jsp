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
<%@page import="edu.ncsu.csc.itrust.action.ViewMyNutritionAction"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Nutrition";
%>
<%@include file="/header.jsp" %>

<div align="center">
<h2>My Nutrition</h2>

<%

Date currDate = new Date();
SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
format.setLenient(false);
String dateStr = format.format(currDate);
	
%>
<form action="viewNutrition.jsp" method="post">
	<input type="hidden" name="formIsFilled" value="true">

<table class="fTable" align=center style="width: 500px;">
	<tr>
		<tr>
			<th colspan=2>Add a New Food Entry</th>
		</tr>
		<tr>
			<td class="subHeaderVertical">Entry Date: [MM/DD/YY]</td>
			<td><input name="entryDate"
			           value="<%=dateStr%>"
					   type="text"></td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Meal Type:</td>
			<td><select name="mealType">
				<option value="Breakfast">Breakfast</option>
				<option value="Lunch">Lunch</option>
				<option value="Dinner">Dinner</option>
				<option value="Snack">Snack</option>
			</select></td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Name of Food:</td>
			<td><input name="foodName"
			           value=""
					   type="text"></td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Number of Servings:</td>
			<td><input name="numServings"
			           value=""
					   type="text"></td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Number of Calories:</td>
			<td><input name="numCalories"
			           value=""
					   type="text"></td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Grams of Fat Per Serving:</td>
			<td><input name="numFat"
			           value=""
					   type="text"></td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Milligrams of Sodium Per Serving:</td>
			<td><input name="numSodium"
			           value=""
					   type="text"></td>
		</tr>
		<tr>
			<td class="subHeaderVertical"> Grams of Carbs Per Servings:</td>
			<td><input name="numCarbs"
			           value=""
					   type="text"></td>
		</tr>
		<tr>
			<td class="subHeaderVertical"> Grams of Sugar Per Servings:</td>
			<td><input name="numSugar"
			           value=""
					   type="text"></td>
		</tr>
		<tr>
			<td class="subHeaderVertical"> Grams of Fiber Per Servings:</td>
			<td><input name="numFiber"
			           value=""
					   type="text"></td>
		</tr>
		<tr>
			<td class="subHeaderVertical"> Grams of Protein Per Servings:</td>
			<td><input name="numProtein"
			           value=""
					   type="text"></td>
		</tr>
	</tr>
</table>
	<br></br>
	<div align="center">
		<input type="submit" name="action"
		       style="font-size: 16pt; font-weight: bold;"
			   value="Add Nutrition Entry">
	</div>
</form>
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
ViewMyNutritionAction action = new ViewMyNutritionAction(prodDAO, loggedInMID.longValue());
List<FoodDiaryBean> diaries = action.getMyFoodDiaryEntries();
session.setAttribute("diaries", diaries);
FoodDiaryBean newEntry = new FoodDiaryBean();

/* Now take care of updating information */
boolean formIsFilled = request.getParameter("formIsFilled") != null 
	&& request.getParameter("formIsFilled").equals("true");

if (formIsFilled) {
	String mealType = request.getParameter("mealType");
	String foodName = request.getParameter("foodName");
	try {
		String eDate = request.getParameter("entryDate");
		double numServing = Double.parseDouble(request.getParameter("numServings"));
		double numCalories = Double.parseDouble(request.getParameter("numCalories"));
		double numFat = Double.parseDouble(request.getParameter("numFat"));
		double numSodium = Double.parseDouble(request.getParameter("numSodium"));
		double numCarbs = Double.parseDouble(request.getParameter("numCarbs"));
		double numSugar = Double.parseDouble(request.getParameter("numSugar"));
		double numFiber = Double.parseDouble(request.getParameter("numFiber"));
		double numProtein = Double.parseDouble(request.getParameter("numProtein"));

		try {
			//gets current date
			Date utilEntryDate = format.parse(eDate);
			java.sql.Date entryDate = new java.sql.Date(utilEntryDate.getTime());
			
			// Take info from fields and put in bean.
			newEntry.setEntryDate(entryDate);
			newEntry.setPatient(loggedInMID.longValue());
			newEntry.setMealType(mealType);
			newEntry.setFoodName(foodName);
			newEntry.setServings(numServing);
			newEntry.setCalories(numCalories);
			newEntry.setGramsFat(numFat);
			newEntry.setMilligramSodium(numSodium);
			newEntry.setGramsCarb(numCarbs);
			newEntry.setGramsSugar(numSugar);
			newEntry.setGramsFiber(numFiber);
			newEntry.setGramsProtein(numProtein);
			action.addAFoodDiaryEntry(newEntry);
			%>
			<div align=center>
				<span class="iTrustMessage">Food Diary Entry Successfully Added</span>
			</div>
	<%
			}	
		catch (Exception e) {
			%>
			<div align=center>
			<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage())%></span>
			</div>
			<%
		
		}
	} catch (NumberFormatException p) {
		%>
		<div align=center>
		<span class="iTrustError"><%=StringEscapeUtils.escapeHtml("Missing or incorrect input.")%></span>
		</div>
		<%		
	}
}
	if (diaries.size() > 0) {
		int index = 0;
		double calTot = 0;
		double fatTot = 0;
		double sodTot = 0;
		double carbTot = 0;
		double sugTot = 0;
		double fibTot = 0;
		double protTot = 0;
		
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
<%	} %>
</table>
<%@include file="/footer.jsp" %>