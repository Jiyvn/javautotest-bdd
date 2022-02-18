package auto.exceptions;

public class AppiumNotStart extends JavAutoException {

    public AppiumNotStart(){
        super();
    }

    public AppiumNotStart(String message){
        super(message);
    }

    public AppiumNotStart(String message, Throwable cause){
        super(message, cause);
    }
}
