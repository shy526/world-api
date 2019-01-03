package top.ccxh.world.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.ccxh.world.web.common.WebResult;
import top.ccxh.world.web.common.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 * @author ccxh
 */
@RestControllerAdvice
@Slf4j
public class WebControllerAdvice {
    private final static String ERROR_PAGE = "error_page";
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder binder
     */
    @InitBinder
    @ResponseStatus
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * controller 异常处理
     *
     * @param e           异常
     * @param model       mod
     * @param httpRequest 请求头
     * @return Object
     */
    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(Exception e, Model model, HttpServletRequest httpRequest) {
        Object result = null;
        if (WebUtils.isAjax(httpRequest)) {
            result = WebResult.failure(e.getMessage());
        } else {
            ModelAndView modelAndView=new ModelAndView(ERROR_PAGE);
            model.addAttribute("message",e.getMessage());
            model.addAttribute("exception",e);
            model.addAttribute("url",httpRequest.getRequestURI());
            result=modelAndView;
        }
        return result;
    }

}
