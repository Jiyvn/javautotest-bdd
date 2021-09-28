package listener;

import helper.cucumberHelper;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.plugin.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm;

//reference: io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm
public class cucumberListener implements ConcurrentEventListener {
    static Logger log = LoggerFactory.getLogger(cucumberListener.class);
//    private final EventHandler<TestCaseStarted> caseStartedHandler;
//    private final EventHandler<TestCaseFinished> caseFinishedHandler;
    private final EventHandler<TestStepStarted> stepStartedHandler;
    private final EventHandler<TestStepFinished> stepFinishedHandler;
    private final ThreadLocal<Boolean> scenarioFinished = new ThreadLocal<Boolean>(){
        protected synchronized Boolean initialValue() {
            return false;
        }
    };

    public cucumberListener() {
        this.stepStartedHandler = this::handleStepStarted;
        this.stepFinishedHandler = this::handleStepFinished;
    }

    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this.stepStartedHandler);
        publisher.registerHandlerFor(TestStepFinished.class, this.stepFinishedHandler);
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
            scenarioFinished.set(!event.getResult().getStatus().equals(Status.PASSED));
        }
    }


//    @Override
//    public void setEventPublisher(EventPublisher publisher) {
//
//        publisher.registerHandlerFor(TestStepStarted.class, new EventHandler<TestStepStarted>() {
//            @Override
//            public void receive(TestStepStarted event) {
//                if (event.getTestStep() instanceof PickleStepTestStep) {
//                    final PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
////                    stepName.set(step.getStep().getKeyword() + step.getStep().getText());
////                    log.info("wholeStep(" + stepName.get() + ")");
//                    if (!scenarioFinished.get()) {
//                        cucumberHelper.setStep(step);
//                        // step.getCodeLocation(): get the step method
////                        log.info("toSetSTEP(" + stepName + ")");
//                    }
//
//                }
//            }
//        });
//
//        publisher.registerHandlerFor(TestStepFinished.class, new EventHandler<TestStepFinished>() {
//            @Override
//            public void receive(TestStepFinished event) {
//                if (event.getTestStep() instanceof PickleStepTestStep) {
//                    scenarioFinished.set(!event.getResult().getStatus().equals(Status.PASSED));
////                    event.getTestCase().getTestSteps().size();
////                    log.info("TestStepFinished - steplistener");
//                }
//            }
//        });
//
//    }

}
