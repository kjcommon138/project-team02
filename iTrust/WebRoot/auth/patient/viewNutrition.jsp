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
pageTitle = "iTrust - View Nutrition";
%>
<%@include file="/header.jsp" %>

<div align="center">
<h2>My Nutrition</h2>

<%

java.util.Date currDate = new java.util.Date();
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
	
<script type="text/javascript">
function editLabel(ind) {
	var labels = document.getElementsByClassName('label' + ind);
	
	for(var i = 0; i < labels.length; i++) {
		labels[i].style.visibility = 'hidden';
	}
	
	var edits = document.getElementsByClassName('edit' + ind);
	
	for(var i = 0; i < edits.length; i++) {
		edits[i].style.visibility = 'visible';
	}
	
	document.getElementById('btnEdit' + ind).style.visibility = 'hidden';
	
	document.getElementById('btnSubmit' + ind).style.visibility = 'visible';
}
</script>

<%
ViewMyNutritionAction action = new ViewMyNutritionAction(prodDAO, loggedInMID.longValue());
List<FoodDiaryBean> diaries = action.getMyFoodDiaryEntries();

Collections.sort(diaries, new Comparator<FoodDiaryBean>() {
	public int compare(FoodDiaryBean fd1, FoodDiaryBean fd2) {
		return fd2.getEntryDate().compareTo(fd1.getEntryDate());
	}
} );

ArrayList<ArrayList<FoodDiaryBean>> dailyEntries = new ArrayList<ArrayList<FoodDiaryBean>>();

int ind = 0;

EditDailyNutritionLabelAction labelAction = new EditDailyNutritionLabelAction(prodDAO, loggedInMID.longValue());

List<DailyNutritionLabelBean> labels = labelAction.getDailyNutritionLabels();

while(ind < diaries.size()) {
	dailyEntries.add(new ArrayList<FoodDiaryBean>());
	
	java.sql.Date curDate = diaries.get(ind).getEntryDate();
	
	while(ind < diaries.size()) {
		if(diaries.get(ind).getEntryDate().equals(curDate))
			dailyEntries.get(dailyEntries.size() - 1).add(diaries.get(ind++));
		else
			break;
	}
}

session.setAttribute("diaries", diaries);
FoodDiaryBean newEntry = new FoodDiaryBean();

/* Now take care of updating information */
boolean formIsFilled = request.getParameter("formIsFilled") != null 
	&& request.getParameter("formIsFilled").equals("true");

boolean editLabel = request.getParameter("edit") != null;

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
} else if(editLabel) {
	java.sql.Date date = Date.valueOf(request.getParameter("edit"));
	String label = request.getParameter("label");
	
	DailyNutritionLabelBean labelBean = new DailyNutritionLabelBean();
	
	labelBean.setMid(loggedInMID.longValue());
	labelBean.setDate(date);
	labelBean.setLabel(label);
	
	if(request.getParameter("exists").equals("true"))
		labelAction.editNutritionLabel(labelBean);
	else
		labelAction.addNutritionLabel(labelBean);
}

int i = 0;

for(List<FoodDiaryBean> curDiary : dailyEntries) {
	
	DailyNutritionLabelBean curLabel = null;
	
	for(DailyNutritionLabelBean l : labels)
		if(l.getDate().equals(curDiary.get(0).getEntryDate())) {
			curLabel = l;
			break;
		}
	
	%>
	
	<table class="fTable" align="center" style="width: 750px;">
		<tr>
			<form action="/iTrust/auth/patient/viewNutrition.jsp" method="post" id="editForm<%=i%>">
				<input type="hidden" name="edit" value="<%=curDiary.get(0).getEntryDate()%>">
				<input type="hidden" name="exists" value="<%=(curLabel != null)%>">
					
				<th colspan=4 align="center">
				<%=curDiary.get(0).getEntryDate() %>
				</th>
				<th colspan=2>
					<div align="right">Label:</div>
				</th>
				<th colspan=12>
					<div style="position:relative; width:300px; top:-10px;">
						<label for="label" class="label<%=i%>" style="position:absolute; z-index:2; left:0; top:0;"><%=(curLabel != null) ? curLabel.getLabel() : ""%></label>
						<input type="text" class="edit<%=i%>" name="label" style="visibility:hidden; position:absolute; z-index:1; left:0; top:0; width:300px; color:black;" size="8" value="<%=(curLabel != null) ? curLabel.getLabel() : ""%>"/>
					</div>
				<th colspan=2>
					<div style="position:relative; width:60px; top:-16px;">
						<button type="button" id="btnEdit<%=i%>" onclick="editLabel(<%=i%>);" style="position:absolute; z-index:2; left:0; top:0; color:black;">Edit</button>
						<input type="submit" id="btnSubmit<%=i%>" value="Submit" style="visibility:hidden; position:absolute; z-index:1; left:0; top:0; color:black;" />
					</div>
				</th>
			</form>
		</tr>
		<tr>
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

	if (diaries.size() > 0) {
		int index = 0;
		double calTot = 0;
		double fatTot = 0;
		double sodTot = 0;
		double carbTot = 0;
		double sugTot = 0;
		double fibTot = 0;
		double protTot = 0;
		
		
		for(FoodDiaryBean b : curDiary) {
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
	}
	%>
	</table><br>
	<%
	i++;
}
%>

<%@include file="/footer.jsp" %>