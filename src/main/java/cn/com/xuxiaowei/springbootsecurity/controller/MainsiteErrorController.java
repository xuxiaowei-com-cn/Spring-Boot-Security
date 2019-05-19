package cn.com.xuxiaowei.springbootsecurity.controller;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
 * 简单配置：使用 {@link ErrorController}
 * 复杂配置：使用 {@link WebServerFactoryCustomizer<>}
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
    public void handleError(HttpServletRequest request, HttpServletResponse response) {

        // 获取 HttpStatus 状态，参加枚举 org.springframework.http.HttpStatus
        int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");

        // 404
        int notFound = HttpStatus.NOT_FOUND.value();

        // 302
        int found = HttpStatus.FOUND.value();

        try {

            if (notFound == statusCode) {
                // 重定向到 404 页面
                response.sendRedirect("/" + notFound);
            } else if (found == statusCode) {
                // 重定向到 302 页面
                response.sendRedirect("/" + found);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getErrorPath() {
        return EEEOR;
    }

}
