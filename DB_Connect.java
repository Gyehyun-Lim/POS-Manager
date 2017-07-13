package POS;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class DB_Connect {
	private static Connection dbTest;
	private String username, password;
	DB_Connect(String username, String password) {
		this.username = username;
		this.password = password;
		connectDB();
	}
	private void connectDB(){
		try{
			Class.forName("oracle.jdbc.OracleDriver");
			dbTest = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE", username,password);
			dbTest.commit();
			System.out.println("데이터베이스에 연결되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 연결이 실패하였습니다.");
			System.out.println("SQLException : " + e);
		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}
	}
	
	public void login(String name, String id, Controller c) throws SQLException {
		String sqlStr = "select position from staff where staff_name = '"+name+"' and staff_id = "+id;
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		String position = "";
		while(rs.next()) {
			position = rs.getString("position");
		}
		stmt.close();
		rs.close();
		if(position.equals("")) {
			JOptionPane.showMessageDialog(null, "해당 직원정보가 존재하지 않습니다.");
		}
		else {
			JOptionPane.showMessageDialog(null, "로그인을 성공했습니다.");
			c.staff_position = position;
			c.staff_name = name;
			c.main.title.Staff.setText("직원명 : " + name + "  직급 : " + position);
		}
	}
	public void UpdateDB1(String staff_name, String customer_name, int total_pay, Controller c) throws SQLException {
		if(customer_name.equals(""))
			customer_name = "비회원";

		String sqlStr1 = "update customer set total_purchase = total_purchase + " + total_pay + " where customer_name = '"+ customer_name + "'";
		String sqlStr2 = "update staff set total_sale = total_sale + "+total_pay+" where staff_name = '"+staff_name+"'";
		PreparedStatement stmt1 = dbTest.prepareStatement(sqlStr1); 
		PreparedStatement stmt2 = dbTest.prepareStatement(sqlStr2); 
		stmt1.executeUpdate(); stmt1.close();
		stmt2.executeUpdate(); stmt2.close();
			
		String sqlStr3 = "update customer set Rank = 'Bronze' where total_purchase >= 300000 and not customer_name = '비회원'";
		String sqlStr4 = "update customer set Rank = 'Silver' where total_purchase >= 500000 and not customer_name = '비회원'";
		String sqlStr5 = "update customer set Rank = 'Gold' where total_purchase >= 1000000 and not customer_name = '비회원'";
		PreparedStatement stmt3 = dbTest.prepareStatement(sqlStr3); 
		PreparedStatement stmt4 = dbTest.prepareStatement(sqlStr4); 
		PreparedStatement stmt5 = dbTest.prepareStatement(sqlStr5); 
		stmt3.executeUpdate(); stmt3.close();
		stmt4.executeUpdate(); stmt4.close();
		stmt5.executeUpdate(); stmt5.close();
	}
	public void UpdateDB2(String date, String menu_name, int menu_price, Controller c) throws SQLException {
		int order_count = 0;
		String sqlStr1 = "select count(order_no) from orderlist";
		PreparedStatement stmt1 = dbTest.prepareStatement(sqlStr1);
		ResultSet rs1 = stmt1.executeQuery();
		while(rs1.next()) {
			order_count = rs1.getInt("count(order_no)");
		}
		
		String sqlStr2 = "insert into orderlist values(" + (order_count + 1) + ", '" + menu_name + "'," + menu_price + ", '" + date + "')";
		PreparedStatement stmt2 = dbTest.prepareStatement(sqlStr2);
		stmt2.executeQuery(); stmt2.close();
		System.out.println("b");
		
		String sqlStr3 = "update menu set sale_count = sale_count + 1 where menu_name = '" + menu_name + "'";
		PreparedStatement stmt3 = dbTest.prepareStatement(sqlStr3);
		stmt3.executeUpdate(); stmt3.close();
		dbTest.commit();
	}
	public void createTable(File f) throws SQLException, IOException, NullPointerException {
		File file = new File(f.getAbsolutePath());
		StringBuilder b = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			while(line != null) {
				b.append(line);
				b.append("\n");
				line = br.readLine();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		String text= b.toString();
		
		String customer_table = "create table customer(customer_name	varchar2(20),"
				+ "birth	number(4), customer_id	number(4),phone		varchar2(20),"
				+ "Rank		varchar2(10),total_purchase	number(20),	primary key (customer_id))";
		PreparedStatement customer_stmt = dbTest.prepareStatement(customer_table); 
		customer_stmt.executeQuery(); customer_stmt.close();
		
		String staff_table = "create table staff(staff_name	varchar2(20),staff_id	number(4),"
				+ "position		varchar2(10),total_sale	number(20),primary key (staff_id))";
		PreparedStatement staff_stmt = dbTest.prepareStatement(staff_table); 
		staff_stmt.executeQuery(); staff_stmt.close();
		
		String menu_table = "create table menu(menu_name	varchar2(25),price	number(15),sale_count	number(15),primary key (menu_name))";
		PreparedStatement menu_stmt = dbTest.prepareStatement(menu_table); 
		menu_stmt.executeQuery(); menu_stmt.close();
		
		String order_table = "create table orderlist(order_no	number(25), menu_name 	varchar2(40), price	number(10), pay_date	varchar2(10),primary key (order_no))";
		PreparedStatement order_stmt = dbTest.prepareStatement(order_table); 
		order_stmt.executeQuery(); order_stmt.close();
		
		StringTokenizer token = new StringTokenizer(text, "\t\n");
		int temp = Integer.parseInt(token.nextToken());
		int id = 1000;
		for(int i = 0; i < temp; i++){
			String sqlStr = "insert into customer values('"+token.nextToken()+"','"
					+token.nextToken()+"','"+id+"','"+token.nextToken()+"',";
			String tempCheck = token.nextToken();
			if(tempCheck.equals("Normal")){
				sqlStr += "'" + "Normal" + "', '0')";
			} else if (tempCheck.equals("Bronze")){
				sqlStr += "'" + "Bronze" + "', '300000')";
			} else if (tempCheck.equals("Silver")){
				sqlStr += "'" + "Silver" + "', '500000')";
			} else if (tempCheck.equals("Gold")){
				sqlStr += "'" + "Gold" + "', '1000000')";
			}
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr); 
			stmt.executeQuery(); stmt.close();			
			id++;
		}
		
		temp = Integer.parseInt(token.nextToken());
		id = 2000;
		for(int i = 0; i < temp; i++){
			String sqlStr = "insert into staff values('"+token.nextToken()+"','"
					+id+"','"+token.nextToken()+"','0')";
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr); 
			stmt.executeQuery(); stmt.close();
			id++;
		}
		
		temp = Integer.parseInt(token.nextToken());
		for(int i = 0; i < temp; i++){
			String sqlStr = "insert into menu values('"+token.nextToken()+"','"+token.nextToken()+"', '0')";
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr); 
			stmt.executeQuery(); stmt.close();
		}
		dbTest.commit();
		JOptionPane.showMessageDialog(null, "테이블이 생성되었습니다.");
	}
	
	public void dropTable() throws SQLException {
		String sqlStr1 = "drop table orderlist";
		PreparedStatement stmt1 = dbTest.prepareStatement(sqlStr1);
		stmt1.executeQuery(); stmt1.close();
		
		String sqlStr2 = "drop table customer";
		PreparedStatement stmt2 = dbTest.prepareStatement(sqlStr2);
		stmt2.executeQuery(); stmt2.close();
		
		String sqlStr3 = "drop table staff";
		PreparedStatement stmt3 = dbTest.prepareStatement(sqlStr3);
		stmt3.executeQuery(); stmt3.close();
		
		String sqlStr4 = "drop table menu";
		PreparedStatement stmt4 = dbTest.prepareStatement(sqlStr4);
		stmt4.executeQuery(); stmt4.close();
		
		dbTest.commit();
	}
	public void creatMenu(menu_Panel p, Controller c) throws SQLException {
		p.removeAll();
		String sqlStr = "select menu_name, price from menu";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		int i = 0;
		while(rs.next()) {
			p.menu_B[i] = new menu(rs.getString("menu_name"));
			p.menu_B[i].menu_Name = rs.getString("menu_name");
			p.menu_B[i].menu_Price = rs.getShort("price");
			p.menu_B[i].menu_Button.addActionListener(c);
			p.add(p.menu_B[i].menu_Button);
			i++;
		}
		c.Menu_Num = i;	
	}
	
	
	public void add_Customer(String name, int birth, String phone) throws SQLException {
		String sqlStr1 = "select customer_id from customer";
		PreparedStatement stmt1 = dbTest.prepareStatement(sqlStr1);
		ResultSet rs1 = stmt1.executeQuery();
		int id = 0;
		while(rs1.next()) {
			if(id < rs1.getInt("customer_id")) 
				id = rs1.getInt("customer_id");
		} stmt1.close(); rs1.close();
		
		String sqlStr2 = "select customer_name from customer where customer_name = '" + name + "'";
		PreparedStatement stmt2 = dbTest.prepareStatement(sqlStr2);
		ResultSet rs2 = stmt2.executeQuery();
		String temp = "";
		while(rs2.next()) {
			temp = rs2.getString("customer_name");
		} stmt2.close(); rs2.close();
		if(!temp.equals(""))
			JOptionPane.showMessageDialog(null, "동일한 회원이 존재합니다.");
		else {
			String sqlStr3 = "insert into customer values('" + name + "', '" + birth + "', '" + (id + 1) + "', '" + phone + "', 'Normal', '0')";
			PreparedStatement stmt3 = dbTest.prepareStatement(sqlStr3);
			stmt3.executeQuery(); stmt3.close();
			dbTest.commit();
			JOptionPane.showMessageDialog(null, "고객 가입이 완료되었습니다.");
		}
	}
	
	public void add_Menu(String name, int price, Controller c, int menu_Count) throws SQLException {
		if(menu_Count < 20) {
			String sqlStr1 = "select menu_name from menu where menu_name = '" + name +"'";
			PreparedStatement stmt1 = dbTest.prepareStatement(sqlStr1);
			ResultSet rs1 = stmt1.executeQuery();
			String temp = "";
			while(rs1.next()) {
				temp = rs1.getString("menu_name");
			} stmt1.close(); rs1.close();
			if(!temp.equals(""))
				JOptionPane.showMessageDialog(null, "동일한 메뉴가 존재합니다.");
			else {
				String sqlStr2 = "insert into Menu values('" + name + "', '" + price + "', '0')";
				PreparedStatement stmt2 = dbTest.prepareStatement(sqlStr2);
				stmt2.executeQuery(); stmt2.close();
				dbTest.commit();
				c.Menu_Num++;
				JOptionPane.showMessageDialog(null, "메뉴 등록이 완료되었습니다.");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "메뉴가 꽉 찼습니다.");
		}
		
	}
	
	public void add_Staff(String name, String position) throws SQLException {
		String sqlStr1 = "select staff_id from staff";
		PreparedStatement stmt1 = dbTest.prepareStatement(sqlStr1);
		ResultSet rs1 = stmt1.executeQuery();
		int id = 0;
		while(rs1.next()) {
			if(id < rs1.getInt("staff_id"))
				id = rs1.getInt("staff_id");
		} stmt1.close(); rs1.close();
		
		String sqlStr2 = "select staff_name from staff where staff_name = '" + name + "'";
		PreparedStatement stmt2 = dbTest.prepareStatement(sqlStr2);
		ResultSet rs2 = stmt2.executeQuery();
		String temp = "";
		while(rs2.next()) {
			temp = rs2.getString("staff_name");
		} stmt2.close(); rs2.close();
		if(!temp.equals(""))
			JOptionPane.showMessageDialog(null, "동일한 직원이 존재합니다.");
		else {
			String sqlStr3 = "insert into staff values('" + name + "', '" + (id + 1) + "', '" + position + "', 0)";
			PreparedStatement stmt3 = dbTest.prepareStatement(sqlStr3);
			stmt3.executeQuery(); stmt3.close();
			dbTest.commit();
			JOptionPane.showMessageDialog(null, "직원 등록이 완료되었습니다.");
		}
		
	}
	
	public void show_Sale(String date, data_Panel p) throws SQLException {
		String data = "";
		String sqlStr1 = "select sum(price) as total_pay from orderlist where pay_date = '" + date +"'";
		PreparedStatement stmt1 = dbTest.prepareStatement(sqlStr1); 
		ResultSet rs1 = stmt1.executeQuery();
		while(rs1.next()){
			data += (" 일 매출 : " + rs1.getString("total_pay") + "원\n");
		} stmt1.close(); rs1.close();
				
		data += " ======================================\n 가장 많이 팔린 메뉴\n : ";
		String sqlStr2 = "select menu_name from menu where sale_count >= all(select sale_count from menu)";
		PreparedStatement stmt2 = dbTest.prepareStatement(sqlStr2); 
		ResultSet rs2 = stmt2.executeQuery();
		while(rs2.next()){
				data += rs2.getString("menu_name") + " ";
		} stmt2.close(); rs2.close();
		String sqlStr3 = "select menu_name from menu where sale_count <= all(select sale_count from menu)";
		PreparedStatement stmt3 = dbTest.prepareStatement(sqlStr3); 
		ResultSet rs3 = stmt3.executeQuery();
		data += "\n 가장 적게 팔린 메뉴\n : ";
		while(rs3.next()){
			data += rs3.getString("menu_name") + " ";
		} stmt3.close(); rs3.close();

		data += "\n ======================================\n";
		String sqlStr4 = "select sum(price) as total_pay from orderlist where pay_date <= '" + date + "'";
		PreparedStatement stmt4 = dbTest.prepareStatement(sqlStr4); 
		ResultSet rs4 = stmt4.executeQuery();
		while(rs4.next()){
			data += "누적 매출금액 : " + rs4.getString("total_pay") + "원\n";
		} stmt4.close(); rs4.close();
		p.sale_data.setText(data);
	}
	
	public void search_Customer(String name, data_Panel p)  throws SQLException {
		String data = "";
		String sqlStr = "select * from customer where customer_name ='" + name + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr); 
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			data = "고  객  명 : "  + rs.getString("customer_name") + "\n고 객  I D : " + rs.getString("customer_id") + "\n생        일 : " + rs.getString("birth") + "\n전화번호 : " + rs.getString("phone") + "\n고객등급 : " + rs.getString("Rank") + "\n총 구매금액 : " + rs.getString("total_purchase");
		} stmt.close(); rs.close();
		if(data.equals("")) {
			p.customer_data.setText("검색 결과 없음");
		}
		else {
			p.customer_data.setText(data);
		}
	}
	
	public String search_Rank(String name) throws SQLException {
		String Rank = "";
		String sqlStr = "select Rank from customer where customer_name = '" + name + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Rank = rs.getString("Rank");
		} stmt.close(); rs.close();
		return Rank;
	}
	
	public void search_Staff(String name, data_Panel p) throws SQLException {
		String data = "";
		String sqlStr = "select * from staff where staff_name = '" + name + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			data = "직원명 : "  + rs.getString("staff_name") + "\n직    급 : " + rs.getString("position") + "\n총실적 : " + rs.getString("total_sale");
		} stmt.close(); rs.close();
		if(data.equals("")) {
			p.staff_data.setText("검색 결과 없음");
		}
		else {
			p.staff_data.setText(data);
		}
	}
	
	public void search_Menu(String name, data_Panel p) throws SQLException {
		String data = "";
		String sqlStr = "select * from menu where menu_name = '" + name + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			data = "메뉴명 : " + rs.getString("menu_name") + "\n가   격 : " + rs.getString("price");
		} stmt.close(); rs.close();
		if(data.equals("")) {
			p.menu_data.setText("검색 결과 없음");
		}
		else {
			p.menu_data.setText(data);
		}
	}
	
	public boolean search_cus(String customer_name) throws SQLException {
		String sqlStr = "select customer_name from customer where customer_name = '" + customer_name + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		String name = "";
		while(rs.next()) {
			name = rs.getString("customer_name");
		} stmt.close(); rs.close();
		if(name.equals(""))
			return false;
		return true;
	}
	
	public Vector<String> getDate(Controller c) throws SQLException{
		Vector<String> Date = new Vector<String>();
		String sqlStr = "select distinct pay_date from orderlist order by pay_date";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr); 
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			Date.add((String)rs.getString("pay_date"));
		} stmt.close(); rs.close();
		return Date;
	}
}
