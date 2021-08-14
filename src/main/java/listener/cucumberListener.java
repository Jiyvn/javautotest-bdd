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

public class cucumberListener implements ConcurrentEventListener {
    static Logger log = LoggerFactory.getLogger(cucumberListener.class);
    public static String stepName=null;
    public static boolean scenarioFinished =false;



    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(TestStepStarted.class, new EventHandler<TestStepStarted>() {
            @Override
            public void receive(TestStepStarted event) {
                if (event.getTestStep() instanceof PickleStepTestStep) {
                    final PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
                    if (stepName == null) scenarioFinished = false;
//                    log.info("scenarioFinished: " + scenarioFinished);
                    stepName = step.getStep().getKeyword() + step.getStep().getText();
//                    log.info("wholeStep(" + stepName + ")");
                    if (!scenarioFinished) {
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
                    //
                    if (!event.getResult().getStatus().equals(Status.PASSED)){
                        scenarioFinished = true;
                    }else {
                        scenarioFinished = false;
                    }
                    event.getTestCase().getTestSteps().size();
//                    log.info("TestStepFinished - steplistener");
                }
            }
        });

    }

}
