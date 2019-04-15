package com.wufeng.spring.DI.springframework.webmvc.servlet;

import com.wufeng.spring.DI.springframework.annotation.MyController;
import com.wufeng.spring.DI.springframework.annotation.MyRequestMapping;
import com.wufeng.spring.DI.springframework.context.MyApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: ninghu
 * @Description: MVC 启动入口
 */
@Slf4j
public class MyDispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private MyApplicationContext context;

    private List<MyHandlerMapping> handlerMappings = new ArrayList<MyHandlerMapping>();

    private Map<MyHandlerMapping, MyHandlerAdapter> handlerAdapters = new HashMap<MyHandlerMapping, MyHandlerAdapter>();

    private List<MyViewResolver> viewResolvers = new ArrayList<MyViewResolver>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、初始化ApplicationContext
        context = new MyApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //2、初始化Spring MVC 九大组件
        initStrategies(context);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            this.doDispatch(req, resp);
        }catch(Exception e){
            //如果匹配过程出现异常，将异常信息打印出去
            resp.getWriter().write("500 Exception,Details:\r\n" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "\r\n"));
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1、通过从request中拿到URL，去匹配一个HandlerMapping
        MyHandlerMapping handler = getHandler(req);

        if(handler == null){
            //new ModelAndView("404")
            return;
        }

        //2、准备调用前的参数
        MyHandlerAdapter ha = getHandlerAdapter(handler);

        //3、真正的调用方法,返回ModelAndView存储了要穿页面上值，和页面模板的名称
        MyModelAndView mv = ha.handle(req,resp,handler);

        //封装ModleAndView
        processDispatchResult(req, resp, mv);
    }

    /**
     * @Description: 根据 url 获取映射关系
     * @author ninghu
     */
    private MyHandlerMapping getHandler(HttpServletRequest req) {
        if (this.handlerMappings.isEmpty()) { return null; }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        try {
            for (MyHandlerMapping handler : this.handlerMappings) {
                Matcher matcher = handler.getPattern().matcher(url);
                if (!matcher.matches()) {
                    continue;
                }
                return handler;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MyHandlerAdapter getHandlerAdapter(MyHandlerMapping handler) {
        return null;
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, MyModelAndView mv) {
    }

    protected void initStrategies(MyApplicationContext context) {
        //多文件上传的组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocaleResolver(context);
        //初始化主题解析
        initThemeResolver(context);
        //初始化请求映射关系
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);
        //初始化视图转换器
        initViewResolvers(context);
        //参数缓存器
        initFlashMapManager(context);
    }

    private void initHandlerMappings(MyApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object controller = context.getBean(beanName);
            Class clazz = controller.getClass();
            if (!clazz.isAnnotationPresent(MyController.class)) { continue; }

            String baseUrl = "";
            // 获取 Controller 的url配置
            if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                MyRequestMapping annotation = (MyRequestMapping) clazz.getAnnotation(MyRequestMapping.class);
                baseUrl = annotation.value();
            }
            // 获取 Method 的url配置
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(MyRequestMapping.class)) { continue; }
                MyRequestMapping requestMapping = method.getAnnotation(MyRequestMapping.class);

                String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);

                this.handlerMappings.add(new MyHandlerMapping(pattern, controller, method));
                log.info("Mapped " + regex + "," + method.getName());
            }
        }
    }

    private void initHandlerAdapters(MyApplicationContext context) {
    }

    private void initViewResolvers(MyApplicationContext context) {
    }


    private void initThemeResolver(MyApplicationContext context) {
    }

    private void initLocaleResolver(MyApplicationContext context) {
    }

    private void initMultipartResolver(MyApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(MyApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(MyApplicationContext context) {
    }

    private void initFlashMapManager(MyApplicationContext context) {
    }
}
