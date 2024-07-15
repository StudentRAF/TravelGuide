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

import static jakarta.ws.rs.core.Response.Status;

public enum ExceptionType implements IException {

    //region Activity Repository

    REPOSITORY_ACTIVITY_SQL_EXCEPTION      ("""
                                            SQL exception inside Activity repository. Thrown message: "{0}".\
                                            """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_ACTIVITY_FIND_ID_NOT_FOUND  ("""
                                            Could not find activity with id: {0}.\
                                            """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_ACTIVITY_FIND_NAME_NOT_FOUND("""
                                            Could not find activity with name: {0}.\
                                            """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_ACTIVITY_CREATE_NO_RESULT_SET("""
                                             No result set found after creating Activity with name: {0}. Activity data - name: "{0}".\
                                             """, Severity.WARNING, Status.BAD_REQUEST),

    //endregion Activity Repository

    //region Article Repository

    REPOSITORY_ARTICLE_SQL_EXCEPTION       ("""
                                            SQL exception inside Article repository. Thrown message: "{0}".\
                                            """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_ARTICLE_FIND_ID_NOT_FOUND   ("""
                                            Could not find article with id: {0}.\
                                            """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_ARTICLE_UPDATE_ID_NOT_FOUND ("""
                                            Could not update article with id: {0}, id was not found. Article data - id: {0}, title: "{1}".\
                                            """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_ARTICLE_CREATE_NO_RESULT_SET("""
                                            No result set found after creating Article with title: {0}. Article data - title: "{0}", author_id: {2}, destination_id: {3}.\
                                            """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_ARTICLE_UPDATE_NO_RESULT_SET("""
                                            No result set found after updating Article with id: {0}. Article data - id: {0}, title: "{1}".\
                                            """, Severity.WARNING, Status.BAD_REQUEST),

    //endregion Article Repository

    //region Article Activity Repository

    REPOSITORY_ARTICLE_ACTIVITY_SQL_EXCEPTION       ("""
                                                     SQL exception inside ArticleActivity repository. Thrown message: "{0}".\
                                                     """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_ARTICLE_ACTIVITY_CREATE_NO_RESULT_SET("""
                                                     No result set found after creating ArticleActivity. ArticleActivity data - articleId: "{0}", activityId: "{1}".\
                                                     """, Severity.WARNING, Status.BAD_REQUEST),
    //endregion Article Activity Repository

    //region Comment Repository

    REPOSITORY_COMMENT_SQL_EXCEPTION       ("""
                                            SQL exception inside Comment repository. Thrown message: "{0}".\
                                            """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_COMMENT_CREATE_NO_RESULT_SET("""
                                            No result set found after creating Comment. Comment data - displayName: "{1}", articleId: "{2}".\
                                            """, Severity.WARNING, Status.BAD_REQUEST),
    //endregion Comment Repository

    //region Destination Repository

    REPOSITORY_DESTINATION_SQL_EXCEPTION       ("""
                                                SQL exception inside Destination repository. Thrown message: "{0}".\
                                                """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_DESTINATION_FIND_ID_NOT_FOUND   ("""
                                                Could not find destination with id: {0}.\
                                                """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_DESTINATION_UPDATE_ID_NOT_FOUND ("""
                                                Could not update destination with id: {0}, id was not found. Destination data - id: {0}, name: "{1}".\
                                                """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_DESTINATION_CREATE_NO_RESULT_SET("""
                                                No result set found after creating Destination with name: {0}. Destination data - name: "{0}", description: "{1}".\
                                                """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_DESTINATION_UPDATE_NO_RESULT_SET("""
                                                No result set found after updating Destination with id: {0}. Destination data - id: {0}, name: "{1}".\
                                                """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_DESTINATION_DELETE_ID_NOT_FOUND ("""
                                                Could not delete destination with id: {0}, id was not found.\
                                                """, Severity.WARNING, Status.BAD_REQUEST),

    //endregion Destination Repository

    //region User Repository

    REPOSITORY_USER_SQL_EXCEPTION        ("""
                                          SQL exception inside User repository. Thrown message: "{0}".\
                                          """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_USER_FIND_ID_NOT_FOUND    ("""
                                          Could not find user with id: {0}.\
                                          """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_USER_FIND_EMAIL_NOT_FOUND ("""
                                          Could not find user with email: {0}.\
                                          """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_USER_CREATE_NO_RESULT_SET ("""
                                          No result set found after creating User. User data - email: {0}, first_name: "{1}", last_name: "{2}", role_id: "{3}".\
                                          """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_USER_UPDATE_NO_RESULT_SET ("""
                                          No result set found after updating User with id: {0}. User data - id: {0}, first_name: "{1}", last_name: "{2}", role_id: "{3}".\
                                          """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_USER_UPDATE_USER_NOT_FOUND("""
                                          Could not update user with id: {0}, id was not found. User data - id: {0}, first_name: "{1}", last_name: "{2}", role_id: "{3}".\
                                          """, Severity.WARNING, Status.BAD_REQUEST),

    //endregion User Repository

    //region User Role Repository

    REPOSITORY_USER_ROLE_SQL_EXCEPTION      ("""
                                             SQL exception inside UserRole repository. Thrown message: "{0}".\
                                             """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_USER_ROLE_FIND_ID_NOT_FOUND  ("""
                                             Could not find user role with id: {0}.\
                                             """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_USER_ROLE_FIND_NAME_NOT_FOUND("""
                                             Could not find user role with name: {0}.\
                                             """, Severity.WARNING, Status.BAD_REQUEST),

    //endregion User Role Repository

    //region User Service

    SERVICE_USER_LOGIN_INVALID_EMAIL   ("""
                                        Could not login in User with username: {0}, username not found. Login data - email:"{0}", password:"{1}".\
                                        """, Severity.TRACE, Status.BAD_REQUEST),
    SERVICE_USER_LOGIN_INVALID_PASSWORD("""
                                        Could not login in User with username: {0}, password is not valid. Login data - email:"{0}", password:"{1}".\
                                        """, Severity.TRACE, Status.BAD_REQUEST),
    SERVICE_USER_DECODE_INVALID_TOKEN  ("""
                                        Could not decode token: "{0}". Thrown message: "{1}".\
                                        """, Severity.TRACE, Status.BAD_REQUEST);
    //endregion User Service

    private final String   pattern;
    private final Severity severity;
    private final Status   httpStatus;

    ExceptionType(String pattern, Severity severity, Status httpStatus) {
        this.pattern    = pattern;
        this.severity   = severity;
        this.httpStatus = httpStatus;
    }

    @Override
    public String pattern() {
        return pattern;
    }

    @Override
    public Status httpStatus() {
        return httpStatus;
    }

    @Override
    public Severity severity() {
        return severity;
    }

}
