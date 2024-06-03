package rs.raf.student.exception;

import static jakarta.ws.rs.core.Response.Status;

public interface IException {

    String pattern();

    Status httpStatus();

    Severity severity();

}
