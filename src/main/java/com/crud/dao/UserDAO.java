package com.crud.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.crud.config.Constants;
import com.crud.model.Users;

public class UserDAO {
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES "
			+ " (?, ?, ?);";
	private static final String Select_all_Users = "Select * From users";
	private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";

	private Connection getConnection() throws ClassNotFoundException {
		Connection con = null;

		Class.forName(Constants.dbDriver);
		try {
			con = DriverManager.getConnection(Constants.jdbcURL, Constants.jdbcUsername, Constants.jdbcPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;

	}

	public void insert(Users user) throws ClassNotFoundException, SQLException {
		System.out.println(INSERT_USERS_SQL);

		try (Connection con = getConnection()) {
			PreparedStatement preparedStatement = con.prepareStatement(INSERT_USERS_SQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	public Users selectUsers(int id) throws ClassNotFoundException {

		try (Connection con = getConnection()) {
			Users users = null;
			PreparedStatement pr = con.prepareStatement(SELECT_USER_BY_ID);
			pr.setInt(1, id);
			System.out.println(pr);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {

				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users = new Users(id, name, email, country);
				return users;

			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Users> selectAllUsers() throws ClassNotFoundException {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Users> users = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection con = getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = con.prepareStatement(Select_all_Users)) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new Users(id, name, email, country));
				con.close();
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;

	}

	public boolean deleteuser(int id) {
		boolean delete;
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(DELETE_USERS_SQL);
			st.setInt(1, id);
			delete = st.executeUpdate() > 0;
			return delete;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public boolean updateusers(Users users) {
		try (Connection con = getConnection()) {
			boolean update;
			PreparedStatement pr = con.prepareStatement(UPDATE_USERS_SQL);
			pr.setString(1, users.getName());
			pr.setString(2, users.getEmail());
			pr.setString(3, users.getCountry());
			pr.setInt(4, users.getId());
			update = pr.executeUpdate() > 0;
			return update;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
