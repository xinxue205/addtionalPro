package validate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Package name : com.skcc.sample.action File name : ResultServlet.java Author :
 * Administrator Date : 2008-9-4 Description : ResultServlet
 */
public class ResultServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		String validateC = (String) request.getSession().getAttribute(
				"validateCode");
		String veryCode = request.getParameter("veryCode");
		PrintWriter out = response.getWriter();
		if (veryCode == null || "".equals(veryCode)) {
			out.println("验证码为空");
		} else {
			if (validateC.equals(veryCode)) {
				out.println("验证码正确");
			} else {
				out.println("验证码错误");
			}
		}
		out.flush();
		out.close();
	}
}
