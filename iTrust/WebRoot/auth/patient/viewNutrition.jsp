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
<% 
if(request.getParameter("edited") != null && request.getParameter("edited").equals("true")) {	
%>
	<div align=center>
		<span class="iTrustMessage">Entry Updated</span>
	</div>
<%
} else if(request.getParameter("deleted") != null && request.getParameter("deleted").equals("true")) {
%>
	<div align=center>
		<span class="iTrustMessage">Entry Deleted</span>
	</div>
<%
}
%>

<h2>My Nutrition</h2>
	
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

/* Now take care of updating information */

if(request.getParameter("edit") != null) {
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
	
	response.sendRedirect("/iTrust/auth/patient/viewNutrition.jsp");
}

java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
java.sql.Date endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

if(request.getParameter("startDate") != null && request.getParameter("endDate") != null) {
	java.util.Date tmpStartDate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("startDate"));
	java.util.Date tmpEndDate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("endDate"));
	
	startDate = new java.sql.Date(tmpStartDate.getTime());
	endDate = new java.sql.Date(tmpEndDate.getTime());
} else
	for(FoodDiaryBean entry : diaries)
		if(startDate.compareTo(entry.getEntryDate()) > 0)
			startDate = entry.getEntryDate();

SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

String strStartDate = sdf.format(startDate);
String strEndDate = sdf.format(endDate);

int i = 0;

%>
<form action="viewNutrition.jsp" id="filterForm" method="post">
	<table class="fTable" align="center">
		<tr class="subHeader">
			<td>Start Date:</td>
			<td>
				<input name="startDate" value="<%= StringEscapeUtils.escapeHtml("" + (strStartDate)) %>" size="10">
				<input type=button value="Select Date" onclick="displayDatePicker('startDate');">
			</td>
			<td>End Date:</td>
			<td>
				<input name="endDate" value="<%= StringEscapeUtils.escapeHtml("" + (strEndDate)) %>">
				<input type=button value="Select Date" onclick="displayDatePicker('endDate');">
			</td>
		</tr>
	</table>
	<br>
	<input type="submit" name="filter" value="Filter">
</form>

<br>

<%

for(List<FoodDiaryBean> curDiary : dailyEntries) {
	
	if(curDiary.get(0).getEntryDate().compareTo(startDate) < 0 ||
			curDiary.get(0).getEntryDate().compareTo(endDate) > 0)
		continue;
	
	DailyNutritionLabelBean curLabel = null;
	
	for(DailyNutritionLabelBean l : labels)
		if(l.getDate().equals(curDiary.get(0).getEntryDate())) {
			curLabel = l;
			break;
		}
	
	//creating a better format for the date
	String stringDate = sdf.format(curDiary.get(0).getEntryDate());
	
	%>
	
	<table class="fTable" align="center" id="FoodDiaryTable" name="FoodDiaryTable">
		<tr>
			<form action="/iTrust/auth/patient/viewNutrition.jsp" method="post" id="editForm<%=i%>">
				<input type="hidden" name="edit" value="<%=curDiary.get(0).getEntryDate()%>">
				<input type="hidden" name="exists" value="<%=(curLabel != null)%>">
					
				<th colspan=6 align="center">
				<%="Entries for: " + stringDate %>
				</th>
				<th colspan=1>
					<div align="right">Label:</div>
				</th>
				<th colspan=4>
					<div style="position:relative; width:300px; top:-10px;">
						<label for="label" class="label<%=i%>" style="position:absolute; z-index:2; left:0; top:0;"><%=(curLabel != null) ? curLabel.getLabel() : ""%></label>
						<input type="text" class="edit<%=i%>" name="label" style="visibility:hidden; position:absolute; z-index:1; left:0; top:0; width:300px; color:black;" size="8" value="<%=(curLabel != null) ? curLabel.getLabel() : ""%>"/>
					</div>
				<th colspan=1>
					<div style="position:relative; width:60px; top:-16px;">
						<button type="button" id="btnEdit<%=i%>" onclick="editLabel(<%=i%>);" style="position:absolute; z-index:2; left:0; top:0; color:black;">Edit</button>
						<input type="submit" id="btnSubmit<%=i%>" value="Submit" style="visibility:hidden; position:absolute; z-index:1; left:0; top:0; color:black;" />
					</div>
				</th>
			</form>
		</tr>
		<tr>
			<th>Meal Type</th>
			<th>Food Name</th>
			<th>Num. Servings</th>
			<th>Num. Calories</th>
			<th>Fat</th>
			<th>Sodium</th>
			<th>Carb</th>
			<th>Sugar</th>
			<th>Fiber</th>
			<th>Protein</th>
			<th>Edit</th>
			<th>Delete</th>
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
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getMealType() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getFoodName() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getServings() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getCalories() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsFat() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getMilligramsSodium() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsCarb() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsSugar() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsFiber() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( b.getGramsProtein() )) %></td>	
			<td>
				<a href="editNutritionEntry.jsp?ent=<%= StringEscapeUtils.escapeHtml("" + b.getFoodEntryID()) %>">Edit</a>
			</td>
			<td><a href="deleteNutritionEntry.jsp?ent=<%= StringEscapeUtils.escapeHtml("" + b.getFoodEntryID()) %>">Delete</a></td>
		</tr>
<%		index ++;
		}
%>
		<tr>
			<th colspan=3>Daily Total:</td>
			<th colspan=1><%= calTot %></td>
			<th colspan=1><%= fatTot %></td>
			<th colspan=1><%= sodTot %></td>
			<th colspan=1><%= carbTot %></td>
			<th colspan=1><%= sugTot %></td>
			<th colspan=1><%= fibTot %></td>
			<th colspan=3><%= protTot %></td>
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