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
	
	private static final String SELECT_USER_BY_ID = "SELECT * FROM crud_webapp.users where id = ?";
	private static final String SELECT_ALL_USERS = "select * from crud_webapp.users";
	private static final String DELETE_USERS_SQL = "delete from crud_webapp.users where id = ?";
	private static final String UPDATE_USERS_SQL = "UPDATE users SET name = ?, email = ?, country = ?, WHERE id = ?";
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES " +
	        " (?, ?, ?);";

	private Connection getConnection() throws ClassNotFoundException {
		Connection con = null;

		Class.forName(Constants.dbDriver);
		try {
			con = DriverManager.getConnection(Constants.jdbcURL, Constants.jdbcUsername, Constants.jdbcPassword);
			if (con != null) {
				System.out.println("Connect Success");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;

	}

	public void insertUser(Users user) throws SQLException, ClassNotFoundException {
	    System.out.println(INSERT_USERS_SQL);
	    try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
	        preparedStatement.setString(1, user.getName());
	        preparedStatement.setString(2, user.getEmail());
	        preparedStatement.setString(3, user.getCountry());
	        System.out.println(preparedStatement);
	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        printSQLException(e);
	    }
	}


	public Users selectUser(int id) throws ClassNotFoundException {
		Users user = null;
		// Step 1: Establishing a Connection
		try (Connection con = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = con.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			try (// Step 3: Execute the query or update query
					ResultSet rs = preparedStatement.executeQuery()) {
				// Step 4: Process the ResultSet object.
				while (rs.next()) {
					String name = rs.getString("name");
					String email = rs.getString("email");
					String country = rs.getString("country");
					user = new Users(id, name, email, country);
				}
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

	public List<Users> getAllUsers() {
		List<Users> users = new ArrayList<Users>();

		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
			// preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				// Get Data and add to questions
				users.add(
						new Users(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("country")

						));

			}
			connection.close();
		} catch (SQLException e) {
			printSQLException(e);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		return users;
	}

	  public boolean updateUser(Users user) throws SQLException, ClassNotFoundException {
	        boolean rowUpdated;
	        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
	            statement.setString(1, user.getName());
	            statement.setString(2, user.getEmail());
	            statement.setString(3, user.getCountry());
	            statement.setInt(4, user.getId());

	            rowUpdated = statement.executeUpdate() > 0;
	        }
	        return rowUpdated;
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
