package listener;

import helper.cucumberHelper;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.invoke.MethodHandles.lookup;


//reference: import io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm;
//import io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm;
public class cucumberListener implements ConcurrentEventListener {
    static Logger log = LoggerFactory.getLogger(lookup().lookupClass());
    private final EventHandler<TestCaseStarted> caseStartedHandler;
    private final EventHandler<TestCaseFinished> caseFinishedHandler;
    private final EventHandler<TestStepStarted> stepStartedHandler;
    private final EventHandler<TestStepFinished> stepFinishedHandler;
    private final ThreadLocal<Boolean> scenarioFinished = new ThreadLocal<Boolean>(){
        @Override
        protected synchronized Boolean initialValue() {
            return false;
        }
    };

    public cucumberListener() {
        this.stepStartedHandler = this::handleStepStarted;
        this.stepFinishedHandler = this::handleStepFinished;
        this.caseStartedHandler = this::handleCaseStarted;
        this.caseFinishedHandler = this::handleCaseFinished;
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this.stepStartedHandler);
        publisher.registerHandlerFor(TestStepFinished.class, this.stepFinishedHandler);
        publisher.registerHandlerFor(TestCaseStarted.class, this.caseStartedHandler);
        publisher.registerHandlerFor(TestCaseFinished.class, this.caseFinishedHandler);
    }

    public void handleStepStarted(TestStepStarted event){
        if (event.getTestStep() instanceof PickleStepTestStep) {
            final PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            if (!scenarioFinished.get()) {
                cucumberHelper.setStep(step);
                log.info("\033[35m "+step.getStep().getKeyword() + step.getStep().getText()+" \033[0m");
            }
        }
    }

    public void handleStepFinished(TestStepFinished event){
        if (event.getTestStep() instanceof PickleStepTestStep) {
            // get step stopped at
            scenarioFinished.set(!event.getResult().getStatus().equals(Status.PASSED));
        }
    }

    public void handleCaseStarted(TestCaseStarted event){

    }

    public void handleCaseFinished(TestCaseFinished event){
        if(!event.getResult().getStatus().isOk()){
            cucumberHelper.setException(event.getResult().getError());
        }
    }

}
