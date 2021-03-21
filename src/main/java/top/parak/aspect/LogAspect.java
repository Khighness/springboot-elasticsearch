package top.parak.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.parak.util.IPParseUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KHighness
 * @since 2021-03-21
 */

@Aspect
@Component
public class LogAspect {

    @Data
    @AllArgsConstructor
    public class ClientRequest {

        /**
         * 请求路径
         */
        private String url;

        /**
         * 请求IP
         */
        private String ip;

        /**
         * 接口方法
         */
        private String classMethod;

        /**
         * 请求参数
         */
        private Object[] args;

    }


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>定义切面表达式</p>
     */
    @Pointcut("execution(* top.parak.controller.*.*(..))")
    public void log() {}

    /**
     * <p>前置操作</p>
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        String url = httpServletRequest.getRequestURL().toString();
        String ip = IPParseUtil.getIPAddress(httpServletRequest);

        /* 获取类名、方法名 */
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        /* 获取方法参数 */
        Object[] args = joinPoint.getArgs();

        ClientRequest requestLog = new ClientRequest(url, ip, classMethod, args);
        logger.info("{}", requestLog);
    }

    /**
     * <p>后置操作</p>
     */
    @After("log()")
    public void doAfter() { }

    /**
     * <p>输出响应</p>
     * @param response
     */
    @AfterReturning(returning = "response", pointcut = "log()")
    public void result(Object response) {
        logger.info("{}", response);
    }

}


