package listener;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;

public class ThreadFuzzyFilter extends AbstractMatcherFilter<ILoggingEvent> {
    private String[] threadName;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (!this.isStarted()) {
            return FilterReply.NEUTRAL;
        } else {
            for(String tn: this.threadName){
                if(event.getThreadName().startsWith(tn)){
                    return this.onMatch;
                }
            }
            return this.onMismatch;
        }
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName.split(",");
    }

    @Override
    public void start() {
        if (this.threadName != null) {
            super.start();
        }

    }
}
