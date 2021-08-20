package priv.gsc.guiforum.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import priv.gsc.guiforum.entity.LoginTicket;
import priv.gsc.guiforum.entity.User;
import priv.gsc.guiforum.service.LoginTicketService;
import priv.gsc.guiforum.service.UserService;
import priv.gsc.guiforum.util.CookieUtil;
import priv.gsc.guiforum.util.HostHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketService loginTicketService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 在请求开始前，查询登录用户
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从Cookie中获取ticket
        String ticket = CookieUtil.getValue(request, "ticket");

        // 用户已经登录过
        if (ticket != null) {
            // 查询登录凭证
            LoginTicket loginTicket = loginTicketService.findLoginTicketByTicket(ticket);
            // 检查登录凭证是否有效
            if (loginTicket != null && loginTicket.getValidStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                // 根据登录凭证查找用户
                User user = userService.findUserById(loginTicket.getUserId());
                // 在本次请求（处理本次请求的线程 ThreadLocal是该线程的局部变量）中持有用户数据
                hostHolder.setUser(user);
            }
        }

        return true;
    }

    /**
     * 在模板视图上，显示用户数据
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null)
            modelAndView.addObject("loginUser", user);
    }

    /**
     * 请求结束时，清理用户数据
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
