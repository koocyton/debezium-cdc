package com.doopp.youlin.define;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntArrayTypeHandler extends BaseTypeHandler<Integer[]> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Integer[] parameter, JdbcType jdbcType) throws SQLException {
        List<String> parameters = new ArrayList<>();
        for(int par : parameter) {
            parameters.add(String.valueOf(par));
        }
        ps.setString(i, String.join(",", parameters));
    }

    @Override
    public Integer[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        if (rs.wasNull())
            return null;
        return splitToArray(str);
    }

    @Override
    public Integer[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        if (rs.wasNull())
            return null;
        return splitToArray(str);
    }

    @Override
    public Integer[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        if (cs.wasNull())
            return null;
        return splitToArray(str);
    }

    private Integer[] splitToArray(String value) {
        List<Integer> intList = new ArrayList<>();
        for (String sp : value.split(",")) {
            if (sp!=null && sp.length()!=0) {
                intList.add(Integer.valueOf(sp));
            }
        }
        return intList.toArray(new Integer[]{});
    }
}
