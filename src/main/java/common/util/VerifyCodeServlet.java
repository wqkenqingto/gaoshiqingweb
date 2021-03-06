/*
 * Copyright 2016 com.blogadmin.gaoshiqing.com All right reserved. This software is the
 * confidential and proprietary information of com.blogadmin.gaoshiqing.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with com.blogadmin.gaoshiqing.com .
 */
package common.util;



import common.constant.SessionKeyConstant;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*import org.apache.jasper.tagplugins.jstl.core.Out;*/

/**
 * 类VerifyCodeServlet.java的实现描述： 验证码生成
 *
 * @author sz.gong 2016年3月14日 上午9:30:51
 */
public class VerifyCodeServlet extends HttpServlet implements Servlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expries", 0);
        resp.setContentType("image/jpeg");
        //获取白背景
        String imageDir = req.getSession().getServletContext().getRealPath("/") + "/com/blogadmin/gaoshiqing/images/gesturepw.jpg";
        StringBuffer strBuffer = RandomUtil.generateVerifyCode(resp.getOutputStream(), imageDir);
        String code = strBuffer.toString();
        req.getSession().setAttribute(SessionKeyConstant.VERIFYCODE, code);
        resp.getOutputStream().flush();
        resp.getOutputStream().close();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

}
