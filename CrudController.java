package com.crud.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.crud.dao.UserDAO;
import com.crud.model.Users;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CrudController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userdao;

	public void init() {
		userdao = new UserDAO();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		try {
			switch (action) {
			case "/new":
				shownewForm(req, resp);
				break;
			case "/insert":
				insertuser(req, resp);
				break;
			case "/delete":
				delete(req, resp);
				break;
			case "/edit":
				edituser(req, resp);
				break;
			case "/update":
				updateuser(req, resp);
				break;
			default:
				ListUser(req, resp);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		userdao.deleteuser(id);
		resp.sendRedirect("list");
	}

	private void insertuser(HttpServletRequest req, HttpServletResponse resp)
			throws ClassNotFoundException, SQLException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String country = req.getParameter("country");
		Users users = new Users(name, email, country);
		userdao.insertUser(users);
		resp.sendRedirect("list");
	}

	private void edituser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, ClassNotFoundException {
		int id = Integer.parseInt(req.getParameter("id"));
		Users user = userdao.selectUser(id);
		req.setAttribute("user", user);
		req.getRequestDispatcher("WEB-INF/view/users-form.jsp").forward(req, resp);

	}

	private void shownewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/view/users-form.jsp");
		dispatcher.forward(req, resp);
	}

	private void updateuser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ClassNotFoundException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		int id = Integer.parseInt(request.getParameter("id"));

		Users book = new Users(id, name, email, country);
		userdao.updateUser(book);
		response.sendRedirect("list");
	}

	private void ListUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, ClassNotFoundException {
		List<Users> list = userdao.getAllUsers();
		req.setAttribute("list", list);
		RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/view/users-list.jsp");
		dispatcher.forward(req, resp);
	}

}
