package top.ccxh.world.web.common;

import javax.servlet.http.HttpServletRequest;

/**
 * web帮助类
 * @author ccxh
 */
public class WebUtils {
    private final  static   String AJAX_HEADER="X-Requested-With";
    public static boolean isAjax(HttpServletRequest httpRequest){
        String header = httpRequest.getHeader(AJAX_HEADER);
        return (header!=null&&"XMLHttpRequest".equals(header));
    }
}
