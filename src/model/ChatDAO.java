package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletContext;

public class ChatDAO {

	Connection con;
	PreparedStatement psmt;
	ResultSet rs;
	
	public ChatDAO() {
		System.out.println("chatDAO생성자 호출");
		try {
			
			String connectionString = "jdbc:mariadb://127.0.0.1:3306/kosmo61_db";
			Class.forName("org.mariadb.jdbc.Driver");
			String id = "kosmo61_user";
			String pw = "1234";
			con = DriverManager.getConnection(connectionString,id,pw);
		}
		catch(Exception ex) {
			System.out.println("DB 연결실패");
			ex.printStackTrace();
		}
	}
	
	/*public ChatDAO(ServletContext ctx) {
		try {
			Class.forName(ctx.getInitParameter("MariaJDBCDriver"));
			String id = "kosmo61_user";
			String pw = "1234";
			con = DriverManager.getConnection(
					ctx.getInitParameter("MariaConnectURL"),id,pw);
			System.out.println("DB 연결성공");
		}
		catch(Exception e) {
			System.out.println("DB 연결실패");
			e.printStackTrace();
		}
	}*/
	
	public void close() {
		try {
			if(rs != null) rs.close();
			if(psmt != null) psmt.close();
			if(con != null) con.close();
		}
		catch(Exception e) {
			System.out.println("자원반납시 예외발생");
			e.printStackTrace();
		}
	}
	
	public List<ChatDTO> chatList(){
		List<ChatDTO> list = new Vector<ChatDTO>();
		
		//기본쿼리문
		String query = "SELECT * FROM chat ";
		query +=" ORDER BY num asc";
		
		try {
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();
			//오라클이 반환해준 ResultSet의 갯수만큼 반복한다.
			while(rs.next()) {
				//하나의 레코드를 DTO객체에 저장하기 위해 새로운 객체생성
				ChatDTO dto = new ChatDTO();
				//setter()메소드를 사용하여 컬럼에 데이터 저장
				dto.setNum(rs.getInt("num"));
				dto.setId(rs.getString("id"));
				dto.setMsg(rs.getString("msg"));
				dto.setChatTime(rs.getString("chatTime"));
				
				//저장된 DTO객체를 List컬렉션에 추가
				list.add(dto);
			}
		}
		catch(Exception e) {
			System.out.println("Select시 예외발생");
			e.printStackTrace();
		}
		return list;
	}
	
	//DB저장
	public int submit(ChatDTO dto) {
		int affected = 0;
		try {
			String query = "INSERT INTO chat VALUES (null, ?, ?, now())";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getMsg());
			
			affected = psmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("insert중 예외발생");
			e.printStackTrace();
		}
		return affected;
	}
	
}
