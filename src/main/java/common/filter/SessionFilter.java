package common.filter;

import com.mocentre.tehui.common.constant.SessionKeyConstant;
import com.mocentre.tehui.core.utils.response.BaseResult;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class SessionFilter implements Filter {

    private static String path;
    private String        ignoredUrl;

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        //System.out.println(req.getMethod() + "---------------");
        HttpServletResponse res = (HttpServletResponse) response;
        String conPath = req.getContextPath();
        String reqUrl = req.getRequestURI();
        Object user = req.getSession().getAttribute(SessionKeyConstant.USER);
        if (user == null && !pass(reqUrl)) {
            if ("".equals(conPath) || null == conPath) {
                conPath = "/";
            }
            String draw = req.getParameter("draw");
            if (draw != null) {
                BaseResult br = new BaseResult();
                br.setErrorMessage("404", "user not login");
                res.getWriter().write(br.toJsonString());
            } else {
                res.getWriter()
                        .write("<script type='text/javascript'>window.location.href='" + conPath + "';</script>");
            }
        } else {
            chain.doFilter(request, response);
            return;
        }
    }

    private Boolean pass(String url) {
        String[] igUrl = ignoredUrl.split(",");
        for (int i = 0; i < igUrl.length; i++) {
            if (url.contains(igUrl[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ignoredUrl = filterConfig.getInitParameter("ignored");
        setPath(filterConfig.getServletContext().getRealPath(File.separator));
    }

    public static void setPath(String path) {

        SessionFilter.path = path;
    }

    public static String getPath() {

        return path;
    }

}
