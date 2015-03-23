<%@page import="java.sql.Date"%>
<%@page import="edu.ncsu.csc.itrust.beans.DailyNutritionLabelBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditDailyNutritionLabelAction"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyNutritionAction"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Add Food Entry";
%>
<%@include file="/header.jsp" %>

<%
ViewMyNutritionAction action = new ViewMyNutritionAction(prodDAO, loggedInMID.longValue());
java.util.Date currDate = new java.util.Date();
SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
format.setLenient(false);
String dateStr = format.format(currDate);
FoodDiaryBean newEntry = new FoodDiaryBean();

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
			java.util.Date utilEntryDate = format.parse(eDate);
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
			
			//loggingAction.logEvent(
			//		TransactionType.ADD_FOOD_DIARY_ENTRY, loggedInMID, loggedInMID, ""	);
			response.sendRedirect("/iTrust/auth/patient/viewNutrition.jsp");
			
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

%>
<div align='center'>
<h2>Add New Food Entry</h2>
<form action="addFoodEntry.jsp" method="post">
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


<%@include file="/footer.jsp"%>