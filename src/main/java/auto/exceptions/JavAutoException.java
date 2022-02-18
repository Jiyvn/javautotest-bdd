package auto.exceptions;

public class JavAutoException extends RuntimeException{

    public JavAutoException(){
        super();
    }

    public JavAutoException(String message){
        super(message);
    }

    public JavAutoException(String message, Throwable cause){
        super(message, cause);
    }
}
