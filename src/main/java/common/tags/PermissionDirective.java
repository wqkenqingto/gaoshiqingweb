/*
 * Copyright 2016 gaoshou360.com All right reserved. This software is the
 * confidential and proprietary information of gaoshou360.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with gaoshou360.com .
 */
package common.tags;

import com.mocentre.tehui.backend.model.RuleInstance;
import com.mocentre.tehui.common.constant.SessionKeyConstant;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.springframework.beans.MethodInvocationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类PermissionDirective.java的实现描述：权限标签
 * 
 * @author sz.gong 2016年3月15日 下午4:51:30
 */
public class PermissionDirective extends Directive {

    @Override
    public String getName() {
        return "permission";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException,
            ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        List<RuleInstance> list = (List<RuleInstance>) request.getSession().getAttribute(SessionKeyConstant.MENU);
        String base = request.getContextPath();
        SimpleNode sn_a = (SimpleNode) node.jjtGetChild(0);
        String aStr = (String) sn_a.value(context);
        aStr = aStr.replace("${base}", base);
        String aHref = getHrefValue(aStr);
        String perStr = "";
        if (list != null) {
            for (RuleInstance rule : list) {
                String url = rule.getUrl();
                if (url != null && !url.equals("") && aHref.indexOf(url) != -1) {
                    perStr = aStr;
                    break;
                }
            }
        }
        writer.write(perStr);
        return true;
    }

    /*
     * 匹配a标签的href内容
     */
    private String getHrefValue(String aStr) {
        Pattern pattern = Pattern.compile("<a\\s+[^<>]*\\s+href=\"([^<>\"]*)\"[^<>]*>");
        String href = null;
        Matcher matcher = pattern.matcher(aStr);
        if (matcher.find()) {
            href = matcher.group(1);
        }
        return href;
    }

}
