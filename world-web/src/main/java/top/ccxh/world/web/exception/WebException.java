package top.ccxh.world.web.exception;

/**
 * controller 层异常
 * @author ccxh
 */
public class WebException extends RuntimeException {
    public WebException(String message) {
        super(message);
    }
}
