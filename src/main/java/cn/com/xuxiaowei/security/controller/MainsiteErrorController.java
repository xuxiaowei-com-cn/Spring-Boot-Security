package cn.com.xuxiaowei.security.controller;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 主站点错误 Controller
 * <p>
 * 增加 {@link Controller} 注解即可使用
 * <p>
 * {@link WebServerFactoryCustomizer <>} 与 {@link ErrorController} 各有各的特点，根据自己的需要选择
 * <p>
 * 自定义请求转发、重定向：使用 {@link ErrorController}
 * 只能请求转发：使用 {@link WebServerFactoryCustomizer<>}
 * <p>
 * 优先级：{@link WebServerFactoryCustomizer<>} 大于 {@link ErrorController}
 *
 * @author xuxiaowei
 */
public class MainsiteErrorController implements ErrorController {

    private final String EEEOR = "/error";

    /**
     * HttpStatus 状态，参见枚举 {@link HttpStatus}
     */
    @RequestMapping(EEEOR)
    public void handleError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // 获取 HttpStatus 状态，参加枚举 org.springframework.http.HttpStatus
        int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");

        // 404
        int notFound = HttpStatus.NOT_FOUND.value();

        // 302
        int found = HttpStatus.FOUND.value();

        if (notFound == statusCode) {

            // 重定向到 404 页面
            // <code>response.sendRedirect("/" + notFound);</code>

            // 请求转发到 404 页面
            request.getRequestDispatcher("/" + notFound).forward(request, response);

        } else if (found == statusCode) {

            // 重定向到 302 页面
            // <code>response.sendRedirect("/" + found);</code>

            // 请求转发到 302
            request.getRequestDispatcher("/" + found).forward(request, response);

        }

    }

    @Override
    public String getErrorPath() {
        return EEEOR;
    }

}
