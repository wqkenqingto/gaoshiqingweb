package common.interceptor;

import com.mocentre.tehui.common.util.CommUtil;
import com.mocentre.tehui.core.annotation.Token;
import com.mocentre.tehui.core.utils.JsonUtils;
import com.mocentre.tehui.core.utils.response.BaseResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 类TokenInterceptor.java的实现描述：拦截器，实现防止表单重复提交
 * 
 * @author sz.gong 2016年3月28日 下午3:59:14
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private Object clock = new Object();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token tokenAnn = method.getAnnotation(Token.class);
            if (tokenAnn != null) {
                boolean save = tokenAnn.save();
                if (save) {
                    String uuid = CommUtil.generateStr(32);
                    request.setAttribute("token", uuid);
                    request.getSession().setAttribute("mc-token", uuid);
                }
                boolean remove = tokenAnn.remove();
                if (remove) {
                    boolean pass = handleToken(request, response, handlerMethod);
                    if (pass) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean handleToken(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        synchronized (clock) {
            String serverToken = (String) request.getSession().getAttribute("mc-token");
            if (serverToken == null) {
                return true;
            }
            String clientToken = request.getParameter("token");
            if (clientToken == null) {
                return true;
            }
            if (serverToken.equals(clientToken)) {
                return handleValidToken(request, response, handler);
            }
        }
        return handleInvalidToken(request, response, handler);
    }

    private boolean handleValidToken(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.getSession().removeAttribute("mc-token");
        return false;
    }

    private boolean handleInvalidToken(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        BaseResult br = new BaseResult();
        br.setErrorMessage("500", "请不要频繁操作！");
        writeMessageUtf8(response, br);
        return true;
    }

    private void writeMessageUtf8(HttpServletResponse response, BaseResult br) throws IOException {
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(br.toJsonString());
        } finally {
            response.getWriter().close();
        }
    }

    private void writeMessageUtf8(HttpServletResponse response, Map<String, Object> json) throws IOException {
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.bean2Json(json));
        } finally {
            response.getWriter().close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token tokenAnn = method.getAnnotation(Token.class);
            if (tokenAnn != null) {
                boolean remove = tokenAnn.remove();
                if (remove) {
                    Object result = request.getAttribute("result");
                    if (result != null) {
                        String resStr = String.valueOf(result);
                        BaseResult br = new BaseResult();
                        //br.setErrorMessage(code, message)
                        writeMessageUtf8(response, br);
                    }
                }
            }
        } else {
            super.afterCompletion(request, response, handler, ex);
        }
    }

}
