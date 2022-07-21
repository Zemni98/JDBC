package lecture0721;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class MainDBCP {
	private static BasicDataSource basicDS;

	static {
		try {
			basicDS = new BasicDataSource();
			Properties properties = new Properties();

			InputStream is = new FileInputStream("resources/db.properties");
			properties.load(is);

			basicDS.setDriverClassName(properties.getProperty("DRIVER_CLASS"));
			basicDS.setUrl(properties.getProperty("JDBC_URL"));
			basicDS.setUsername(properties.getProperty("DB_USER"));
			basicDS.setPassword("DB_PASSWORD");

			// 어떻게 설정해야 하나요?
			basicDS.setInitialSize(10);
			basicDS.setMaxTotal(10);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static DataSource getDataSource() {
		return basicDS;
	}

	public static void main(String[] args) {

		Connection con = null;
		DataSource ds = getDataSource();
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);

			String sql = "DELETE FROM buytbl";
			PreparedStatement pstmt = con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
