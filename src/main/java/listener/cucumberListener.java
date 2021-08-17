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

//reference: io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm
public class cucumberListener implements ConcurrentEventListener {
    static Logger log = LoggerFactory.getLogger(cucumberListener.class);
    private  ThreadLocal<String> stepName = new ThreadLocal<String>() {
        protected synchronized String initialValue() {
            return null;
        }
    };
    private  ThreadLocal<Boolean> scenarioFinished = new ThreadLocal<Boolean>(){
        protected synchronized Boolean initialValue() {
            return false;
        }
    };

//    public cucumberListener() {
//
//    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(TestStepStarted.class, new EventHandler<TestStepStarted>() {
            @Override
            public void receive(TestStepStarted event) {
                if (event.getTestStep() instanceof PickleStepTestStep) {
                    final PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
//                    stepName.set(step.getStep().getKeyword() + step.getStep().getText());
//                    log.info("wholeStep(" + stepName.get() + ")");
                    if (!scenarioFinished.get()) {
                        cucumberHelper.setStep(step);
                        // step.getCodeLocation(): get the step method
//                        log.info("toSetSTEP(" + stepName + ")");
                    }

                }
            }
        });

        publisher.registerHandlerFor(TestStepFinished.class, new EventHandler<TestStepFinished>() {
            @Override
            public void receive(TestStepFinished event) {
                if (event.getTestStep() instanceof PickleStepTestStep) {
                    scenarioFinished.set(!event.getResult().getStatus().equals(Status.PASSED));
//                    event.getTestCase().getTestSteps().size();
//                    log.info("TestStepFinished - steplistener");
                }
            }
        });

    }

}
