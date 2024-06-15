package rs.raf.student.exception;

import jakarta.ws.rs.core.Response;

import java.util.function.Supplier;

public class ExceptionUtils {

    public static <Type> Type handleReturning(Supplier<Type> supplier) {
        try {
            return supplier.get();
        }
        catch (TGException exception) {
            handleException(exception).close();

            return null;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Response handleResponse(Supplier<Response> supplier) {
        try {
            return supplier.get();
        }
        catch (TGException exception) {
            return handleException(exception);
        }
    }

    public static Response handleResponse(TGCallable callable, Response successfulResponse) {
        try {
            callable.call();

            return successfulResponse;
        }
        catch (TGException exception) {
            return handleException(exception);
        }
    }

    private static Response handleException(TGException exception) {
        Exception thrownException = exception.getException();

        if (thrownException != null)
            thrownException.printStackTrace(System.err);

        return Response.status(exception.getHttpStatus())
                       .build();
    }

}
