package org.jboss.pnc.dingrogu.rest;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.pnc.dingrogu.GenerateTask;
import org.jboss.pnc.dingrogu.rest.client.RexClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.pnc.rex.dto.TaskDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Set;

@Path("/hello")
public class GreetingResource {

    @Inject
    GenerateTask generateTask;

    @ConfigProperty(name = "rexclient.url")
    String rexClientUrl;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String startProcess() throws Exception {
        Log.info("Rex client url is: " + rexClientUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(rexClientUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        Log.info("Request started");
        RexClient rexClient = retrofit.create(RexClient.class);
        Call<Set<TaskDTO>> response = rexClient.start(generateTask.generateSingleRequest());
        Response<Set<TaskDTO>> realResponse = response.execute();
        Log.info("Is successful: " + realResponse.isSuccessful());
        Log.info("Code: " + realResponse.toString());

        return "oh hey";
    }
}