package top.ccxh.world.web.exception;

/**
 * 服务类异常
 * @author ccxh
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
