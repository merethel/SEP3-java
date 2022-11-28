package org.example;

import Daos.EventDao;
import Daos.UserDao;
import domain.Event;
import domain.User;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import services.EventServiceImpl;
import services.UserServiceImpl;

public class Main {
    public static void main(String[] args) throws Exception {




        Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure("hibernate.cfg.xml");

        configuration.addAnnotatedClass(Event.class);
        configuration.addAnnotatedClass(User.class);


        //Session factory - Creates temporary connections to the database (aka sessions)

        SessionFactory sessionFactory = configuration.buildSessionFactory();




        //IMPORTANT, run this line only to populate the datebase.
        EventDao eventDao = new EventDao(sessionFactory);
        UserDao userDao = new UserDao(sessionFactory);


        //Add services to grpc server
        Server server = ServerBuilder.forPort(9090)
                .addService(new EventServiceImpl(eventDao, userDao))
                .addService(new UserServiceImpl(userDao))
                .build();

        server.start();

        System.out.println("Server is ready.");
        System.out.println("¯\\_(ツ)_/¯");
        server.awaitTermination();
    }
}