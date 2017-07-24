package com.dwbook.phonebook;

import com.dwbook.phonebook.resources.ClientResource;
import com.dwbook.phonebook.resources.ContactResource;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.sun.jersey.api.client.Client;

public class App extends Application<PhoneBookConfiguration>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public void initialize(Bootstrap<PhoneBookConfiguration> b){

    }


    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(PhoneBookConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Method App#run() called");
//        for(int i = 0; i < configuration.getMessageRepetitions(); i++){
//            System.out.println(configuration.getMessage());
//        }

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDatabase(), "mysql");
        environment.jersey().register(new ContactResource(jdbi, environment.getValidator()));

        final Client client = new JerseyClientBuilder(environment).build("REST Client");
        environment.jersey().register(new ClientResource(client));

    }
}
