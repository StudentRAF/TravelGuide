/*
 * Copyright (C) 2024. Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
