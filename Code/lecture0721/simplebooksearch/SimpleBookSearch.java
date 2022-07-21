package lecture0721.simplebooksearch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lecture0721.simplebooksearch.service.BookService;
import lecture0721.simplebooksearch.vo.BookVO;

public class SimpleBookSearch extends Application {

	TextArea textarea;
	TextField input;
	Button selectBtn, deleteBtn;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// service 객체
		BookService service = new BookService();

		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);

		textarea = new TextArea();
		textarea.setEditable(false);
		root.setCenter(textarea);

		BorderPane bottom = new BorderPane();
		bottom.setPrefHeight(40);
		bottom.setPrefWidth(Double.MAX_VALUE);

		selectBtn = new Button("Keyword검색");
		selectBtn.setPrefSize(100, 60);
		selectBtn.setOnAction(e -> {
			textarea.clear();
			ArrayList<BookVO> result = service.bookSearchByKeyword(input.getText());
			// result를 이용해서 화면에 출력!
			// textarea.appendText(result.toString());
			for (int i = 0; i < result.size(); i++) {
				String str = result.get(i).getBtitle();
				String isbn = result.get(i).getBisbn();
				textarea.appendText("제목 : " + str + "        일련번호 : " + isbn + "\n");
			}
			// System.out.println(result);
		});

		input = new TextField();
		input.setPrefSize(40, 60);
		deleteBtn = new Button("ISBN으로 삭제");
		deleteBtn.setPrefSize(100, 60);
		deleteBtn.setOnAction(e -> {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				String jdbcURL = "jdbc:mysql://localhost:3306/sql3db?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
				con = DriverManager.getConnection(jdbcURL, "root", "jm19980630!");

				String sql = "delete from book where bisbn = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, input.getText());

				int count = pstmt.executeUpdate();

				textarea.clear();

				if (count == 1) {
					textarea.appendText("[삭제성공] \n");
				} else {
					textarea.appendText("[삭제실패] \n");
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		});

		bottom.setLeft(selectBtn);
		bottom.setCenter(input);
		bottom.setRight(deleteBtn);

		root.setBottom(bottom);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("도서검색 및 삭제");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}
