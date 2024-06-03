package rs.raf.student.exception;

import static jakarta.ws.rs.core.Response.Status;

public enum ExceptionType implements IException {

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

    REPOSITORY_ARTICLE_SQL_EXCEPTION          ("""
                                               SQL exception inside Article repository. Thrown message: "{0}".\
                                               """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_ARTICLE_ACTIVITY_SQL_EXCEPTION ("""
                                               SQL exception inside ArticleActivity repository. Thrown message: "{0}".\
                                               """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_COMMENT_SQL_EXCEPTION          ("""
                                               SQL exception inside Comment repository. Thrown message: "{0}".\
                                               """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),

    REPOSITORY_DESTINATION_SQL_EXCEPTION               ("""
                                                        SQL exception inside Destination repository. Thrown message: "{0}".\
                                                        """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_DESTINATION_FIND_ID_NOT_FOUND           ("""
                                                        Could not find destination with id: {0}.\
                                                        """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_DESTINATION_UPDATE_DESTINATION_NOT_FOUND("""
                                                        Could not update destination with id: {0}, id was not found. Destination data - id: {0}, name: "{1}".\
                                                        """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_DESTINATION_CREATE_NO_RESULT_SET        ("""
                                                        No result set found after creating Destination with name: {0}. Destination data - name: "{0}", description: "{1}".\
                                                        """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_DESTINATION_UPDATE_NO_RESULT_SET        ("""
                                                        No result set found after updating Destination with id: {0}. Destination data - id: {0}, name: "{1}".\
                                                        """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_DESTINATION_DELETE_DESTINATION_NOT_FOUND("""
                                                        Could not delete destination with id: {0}, id was not found.\
                                                        """, Severity.WARNING, Status.BAD_REQUEST),

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

    REPOSITORY_USER_ROLE_SQL_EXCEPTION      ("""
                                             SQL exception inside UserRole repository. Thrown message: "{0}".\
                                             """, Severity.ERROR, Status.INTERNAL_SERVER_ERROR),
    REPOSITORY_USER_ROLE_FIND_ID_NOT_FOUND  ("""
                                             Could not find user role with id: {0}.\
                                             """, Severity.WARNING, Status.BAD_REQUEST),
    REPOSITORY_USER_ROLE_FIND_NAME_NOT_FOUND("""
                                             Could not find user role with name: {0}.\
                                             """, Severity.WARNING, Status.BAD_REQUEST);



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
