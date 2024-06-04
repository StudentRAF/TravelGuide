package rs.raf.student.exception;

import jakarta.ws.rs.core.Response;

import java.util.function.Supplier;

public class ExceptionUtils {

    public static Response handleResponse(Supplier<Response> supplier) {
        try {
            return supplier.get();
        }
        catch (TGException exception) {
            return Response.status(exception.getHttpStatus())
                           .build();
        }
    }

    public static Response handleResponse(TGCallable callable, Response successfulResponse) {
        try {
            callable.call();

            return successfulResponse;
        }
        catch (TGException exception) {
            return Response.status(exception.getHttpStatus())
                           .build();
        }
    }

}
