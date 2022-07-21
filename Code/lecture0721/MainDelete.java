package lecture0721;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainDelete {
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC Driver Loading
			// forName 이라는 Static method
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loading Clear!");
			// ( ) 사이에는 Full Package명을 포함해서 class의 이름이 들어가야한다.

			// 2.데이터베이스 연결
			String jdbcURL = "jdbc:mysql://localhost:3306/sql3db?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			con = DriverManager.getConnection(jdbcURL, "root", "jm19980630!");
			System.out.println("Database Connection Clear!");

			con.setAutoCommit(false); // transaction의 시작!

			// PrepareStatement는 IN Parameter를 사용할 수 있어요! (값을 매칭시킬 수 있는 "?" 같은것들!)
			String sql = "SELECT btitle, bdate, bauthor, bprice FROM book WHERE btitle = 'C로 구현한 알고리즘'";
			// 3. Statement 생성
			// stmt = con.createStatement(); // 일반 Statement
			// PreparedStatement 생성
			pstmt = con.prepareStatement(sql); // PreparedStatement
			// pstmt.setString(1, "C로 구현한 알고리즘"); // 첫번째는 순서, 두번째는 문자열 "?"로 처리했던 문자열을 세팅해준다.
			// 4. 실행
			// int result = pstmt.executeUpdate();
			rs = pstmt.executeQuery(sql);

			// 5. 결과처리

			while (rs.next()) {
				String btitle = rs.getString(1);
				String bdate = rs.getString(2);
				String bauthor = rs.getString(3);
				System.out.println(btitle + "," + bdate + "," + bauthor);
			}

			// System.out.println("총 " + result + "행이 삭제되었습니다.");

			// con.commit(); // transaction이 종료된다.
			// 6. 사용한 자원을 해제해요!
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e2) {
			System.out.println(e2);
		} finally {

			try {

				if (rs != null)
					rs.close();

				if (con != null)
					con.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}