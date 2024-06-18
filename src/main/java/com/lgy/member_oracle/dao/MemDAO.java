package com.lgy.member_oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class MemDAO {
	DataSource dataSource;
	
	public MemDAO() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int loginYn(String id, String pw) {
		int re=-1;
		String db_mem_pwd;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select mem_pwd from mvc_member where mem_uid=?";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
//			아이디가 있는 경우
			if (rs.next()) {
				db_mem_pwd =rs.getString("mem_pwd");
				
//				데이터베이스 조회된 비밀번호=파라미터 비밀번호
				if (db_mem_pwd.equals(pw)) {
					re=1;
//				데이터베이스 조회된 비밀번호!=파라미터 비밀번호
				} else {
					re=0;
				}
//				아이디가 없는 경우
			}else {
				re=-1;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(rs != null){rs.close(); }
				if(pstmt != null){pstmt.close(); }
				if(conn != null){conn.close(); }
			}catch(Exception se){
				se.printStackTrace();
			}
		}
		
		return re;
	}
	
}
