package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;

public class FoodDiaryBeanLoader implements BeanLoader<FoodDiaryBean> {

	public List<FoodDiaryBean> loadList(ResultSet rs) throws SQLException {
		List<FoodDiaryBean> list = new ArrayList<FoodDiaryBean>();
		while (rs.next())
			list.add(loadSingle(rs));
		return list;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, FoodDiaryBean bean) throws SQLException {
		ps.setLong(1, bean.getPatient());
		ps.setDate(2, (java.sql.Date) bean.getEntryDate());
		ps.setString(3, bean.getMealType());
		ps.setString(4, bean.getFoodName());
		ps.setDouble(5, bean.getServings());
		ps.setDouble(6, bean.getCalories());
		ps.setDouble(7, bean.getGramsFat());
		ps.setDouble(8, bean.getMilligramsSodium());
		ps.setDouble(9, bean.getGramsCarb());
		ps.setDouble(10, bean.getGramsSugar());
		ps.setDouble(11, bean.getGramsFiber());
		ps.setDouble(12, bean.getGramsProtein());
		return ps;
	}

	public FoodDiaryBean loadSingle(ResultSet rs) throws SQLException {
		FoodDiaryBean bean = new FoodDiaryBean();
		
		//Sets all contents of bean from database
		bean.setFoodEntryID(rs.getInt("entry_id"));
		bean.setPatient(rs.getLong("patient_id"));
		try {
			bean.setEntryDate(rs.getDate("entry_date"));
			bean.setMealType(rs.getString("meal_type"));
			bean.setFoodName(rs.getString("food_name"));
			bean.setServings(rs.getDouble("servings"));
			bean.setCalories(rs.getDouble("calories"));
			bean.setGramsFat(rs.getDouble("fat"));
			bean.setMilligramSodium(rs.getDouble("sodium"));
			bean.setGramsCarb(rs.getDouble("carbs"));
			bean.setGramsSugar(rs.getDouble("sugar"));
			bean.setGramsFiber(rs.getDouble("fiber"));
			bean.setGramsProtein(rs.getDouble("protein"));
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		//returns bean
		return bean;
	}

}
