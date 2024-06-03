package rs.raf.student.exception;

import lombok.Getter;
import org.slf4j.Logger;

import java.text.MessageFormat;
import java.util.Map;

import static jakarta.ws.rs.core.Response.Status;

@Getter
public class TGException extends RuntimeException {

    private static final Map<Severity, IExceptionLogger> loggerMap = Map.of(
            Severity.TRACE,       TGException::trace,
            Severity.DEBUG,       TGException::debug,
            Severity.INFORMATION, TGException::information,
            Severity.WARNING,     TGException::warning,
            Severity.ERROR,       TGException::error
    );

    private final Status httpStatus;

    public TGException(IException exception, String... args) {
        loggerMap.get(exception.severity()).log(MessageFormat.format(exception.pattern(), args));
        httpStatus = exception.httpStatus();
    }

    private static Logger logger = null;

    public static void setLogger(Logger logger) {
        TGException.logger = logger;
    }

    private static void trace(String message) {
        logger.trace(message);
    }

    private static void debug(String message) {
        logger.info(message);
    }

    private static void information(String message) {
        logger.info(message);
    }

    private static void warning(String message) {
        logger.warn(message);
    }

    private static void error(String message) {
        logger.error(message);
    }

    @FunctionalInterface
    private interface IExceptionLogger {

        void log(String message);

    }

}
