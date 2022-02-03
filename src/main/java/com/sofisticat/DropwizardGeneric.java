package com.sofisticat;

import com.codahale.metrics.jdbi3.strategies.DefaultNameStrategy;
import com.sofisticat.controllers.AccountController;
import com.sofisticat.managers.AccountsManager;
import com.sofisticat.storage.Accounts;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

public class DropwizardGeneric extends Application<DropwizardGenericConfiguration> {

    @Override
    public void initialize(Bootstrap<DropwizardGenericConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<DropwizardGenericConfiguration>() {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(DropwizardGenericConfiguration dropwizardGenericConfiguration) {
                return dropwizardGenericConfiguration.getAccountDatabaseConfiguration();
            }
            @Override
            public String name() {
                return "accountdb";
            }
        });
    }

    @Override
    public void run(DropwizardGenericConfiguration config, Environment environment) throws Exception {


        // database connection
        JdbiFactory jdbiFactory = new JdbiFactory(DefaultNameStrategy.CHECK_EMPTY);
        Jdbi accountJdbi = jdbiFactory.build(environment, config.getAccountDatabaseConfiguration(), "accountdb");

        // Database
        Accounts accounts = new Accounts(accountJdbi);

        // DAO
        AccountsManager accountsManager = new AccountsManager(accounts);

        // Controllers
        AccountController accountController = new AccountController(accountsManager);

        // TODO: JWT integration

        // Registration
        environment.jersey().register(accountController);
    }

    @Override
    public String getName() {
        return "account-authenticator";
    }

    public static void main(String[] args) throws Exception {
        new DropwizardGeneric().run(args);
    }

}
