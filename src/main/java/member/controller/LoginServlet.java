package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.CommaUtils;
import member.model.dto.Member;
import member.model.service.MemberService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/member/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request);
		HttpSession session = request.getSession();
		String id = request.getParameter("memberId");
		String pwd = CommaUtils.getEncryptedPassword(request.getParameter("password"), id);
		System.out.println("id =" + id + ", pwd = " + pwd);
		
		Member member = memberService.selectOneMember(id);
		
		if(member != null && pwd.equals(member.getPassword())) {
			session.setAttribute("loginMember", member);
		}else {
			session.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		response.sendRedirect(request.getContextPath() + "/");
	}

}
