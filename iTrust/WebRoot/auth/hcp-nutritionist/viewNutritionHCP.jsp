
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.*"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyNutritionAction"%>

<%@page import="java.sql.Date"%>
<%@page import="edu.ncsu.csc.itrust.beans.DailyNutritionLabelBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditDailyNutritionLabelAction"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Patient Nutrition";
%>
<%@include file="/header.jsp" %>
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
String pidString = (String)session.getAttribute("pid");
if (pidString == null || 1 > pidString.length()) {
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-nutritionist/viewNutritionHCP.jsp");
   	return;
}
	long pid = (long) Long.parseLong((String) session.getAttribute("pid"));
	ViewMyNutritionAction action = new ViewMyNutritionAction(prodDAO, pid);
	EditDailyNutritionLabelAction labelAction = new EditDailyNutritionLabelAction(prodDAO, pid);

	List<FoodDiaryBean> diaries = action.getMyFoodDiaryEntries();

	Collections.sort(diaries, new Comparator<FoodDiaryBean>() {
		public int compare(FoodDiaryBean fd1, FoodDiaryBean fd2) {
			return fd2.getEntryDate().compareTo(fd1.getEntryDate());
		}
	} );

	ArrayList<ArrayList<FoodDiaryBean>> dailyEntries = new ArrayList<ArrayList<FoodDiaryBean>>();

	int ind = 0;
	
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
	boolean editLabel = request.getParameter("edit") != null;

	if(editLabel) {
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
		
		//creating a better format for the date
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
		String stringDate = sdf.format(curDiary.get(0).getEntryDate());
		
		%>
		
		<table class="fTable" align="center" id="FoodDiaryTable">
			<tr>	
					<th colspan=6 align="center">
					<%="Entries for: " + stringDate %>
					</th>
					<th colspan=6>
						<div align="right">Label:  <%=(curLabel != null) ? curLabel.getLabel() : "None"%></div>
					</th>
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
				<th colspan=1><%= protTot %></td>
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
