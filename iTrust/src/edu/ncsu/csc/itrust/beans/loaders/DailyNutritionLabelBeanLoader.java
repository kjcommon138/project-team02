package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.DailyNutritionLabelBean;

public class DailyNutritionLabelBeanLoader implements BeanLoader<DailyNutritionLabelBean>{

	@Override
	public List<DailyNutritionLabelBean> loadList(ResultSet rs)
			throws SQLException {
		List<DailyNutritionLabelBean> list = new ArrayList<DailyNutritionLabelBean>();
		while (rs.next())
			list.add(loadSingle(rs));
		return list;
	}

	@Override
	public DailyNutritionLabelBean loadSingle(ResultSet rs) throws SQLException {
		DailyNutritionLabelBean bean = new DailyNutritionLabelBean();
		
		//Sets all contents of bean from database
		bean.setMid(rs.getLong("mid"));
		try {
			bean.setDate(rs.getDate("date1"));
			bean.setLabel(rs.getString("label"));
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		//returns bean
		return bean;
	}

	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			DailyNutritionLabelBean bean) throws SQLException {
		ps.setLong(1, bean.getPatient());
		ps.setDate(2, (java.sql.Date) bean.getDate());
		ps.setString(3, bean.getLabel());
		return ps;
	}

}
