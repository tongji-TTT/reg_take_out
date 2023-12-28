package demo.filter;

import com.alibaba.fastjson.JSON;
import demo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    /*
    AntPathMatcher 类中的 match 方法可以用来判断一个 URL 是否与指定的模式匹配。
    该方法接受两个参数：url 和 requestURI，并返回一个布尔值，表示是否匹配成功。
     */
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //对请求和响应进行强转,我们需要的是带http的,HttpServlet 是Servlet的继承类
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取本次request的url
        String requestURL = request.getRequestURI();
        //定义不需要处理的路径
        String[] urls= new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/v3/**"
        };
        //判断本次请求是否需要处理
        boolean check = check(urls,requestURL);
        if(check){
            log.info("放行URL:{}",requestURL);
            filterChain.doFilter(request,response);
            return;
        }
        //判断登陆状态
        log.info("判断URL:{}",requestURL);

        if(request.getSession().getAttribute("employee") != null){
            log.info("已登入，用户id为:{}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }
        log.info("not login");
        response.getWriter().write(JSON.toJSONString(R.error("NOT LOGIN")));
        return;
    }


    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            //把浏览器发过来的请求和我们定义的不拦截的url做比较，匹配则放行
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }

}
