package cn.com.xuxiaowei.security.controller;

import cn.com.xuxiaowei.security.util.response.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 忘记密码 Controller
 *
 * @author xuxiaowei
 */
@Controller
public class ForgetController {

    /**
     * 忘记密码页面
     */
    @RequestMapping("/forget")
    public String forget(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "forget";
    }

    /**
     * 重置密码页面
     *
     * @param resetPassProof 重置密码凭证
     */
    @RequestMapping("/resetPass")
    public String resetPass(HttpServletRequest request, HttpServletResponse response, Model model,
                            String resetPassProof) throws IOException {

        HttpSession session = request.getSession();

        String resetPassProofSession = (String) session.getAttribute("resetPassProof");

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        if (StringUtils.isEmpty(resetPassProofSession)) {
            map.put("msg", "重置密码页面受到攻击！");
            data.put("details", "Session 中不存在重置密码凭证！");
            ResponseUtils.response(response, map);

            // 结束此方法
            return null;
        }

        if (StringUtils.isEmpty(resetPassProof)) {
            map.put("msg", "重置密码页面受到攻击！");
            data.put("details", "未收到重置密码凭证！");
            ResponseUtils.response(response, map);

            // 结束此方法
            return null;
        }

        if (resetPassProof.equals(resetPassProofSession)) {

            // 重置密码凭证
            // 控制是否能进入重置密码页面
            // 只能用一次
            session.setAttribute("resetPassProof", null);

            return "resetPass";
        } else {
            map.put("msg", "重置密码页面受到攻击！");
            data.put("details", "重置密码凭证验证失败！");
            ResponseUtils.response(response, map);

            // 结束此方法
            return null;
        }

    }

}
