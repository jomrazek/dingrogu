package org.jboss.pnc.dingrogu.rest.server;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import org.jboss.pnc.dingrogu.common.GenerateTask;
import org.jboss.pnc.dingrogu.rest.client.RexTaskEndpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.pnc.rex.dto.TaskDTO;

import java.util.Set;

@Path("/hello")
public class GreetingResource {

    @Inject
    RexTaskEndpoint rexTaskEndpoint;

    @Inject
    GenerateTask generateTask;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String startProcess(@Context HttpHeaders headers) throws Exception {

        Set<TaskDTO> response = rexTaskEndpoint.start(generateTask.generateSingleRequest());
        for (TaskDTO item : response) {
            Log.info("Code: " + item);
        }

        return "oh hey";
    }
}