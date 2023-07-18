package id.ic.aims.rest.binder;

import id.ic.aims.controller.Controller;
import id.ic.aims.rest.validator.Validator;
import org.atteo.classindex.ClassIndex;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import id.ic.aims.util.log.AppLogger;

public class ApplicationBinder extends AbstractBinder {
    private AppLogger log;

    public ApplicationBinder() {
        log = new AppLogger(this.getClass());
    }

    @Override
    protected void configure() {
        final String methodName = "configure";

        log.debug(methodName, "start");

        // Controllers
        ClassIndex.getAnnotated(Controller.class).forEach(this::bindAsContract);

        // Validators
        ClassIndex.getAnnotated(Validator.class).forEach(this::bindAsContract);

        log.debug(methodName, "completed");
    }

}
