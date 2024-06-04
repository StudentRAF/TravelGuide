package rs.raf.student.exception;

@FunctionalInterface
public interface TGCallable {

    void call() throws TGException;

}
