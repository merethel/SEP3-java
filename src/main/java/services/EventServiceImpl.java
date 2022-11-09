package services;


import Daos.EventDao;
import Daos.UserDao;
import domain.Event;
import domain.GrpcFactory;
import event.EventCreationDtoMessage;
import event.EventMessage;
import event.EventServiceGrpc;

import event.IntRequest;
import io.grpc.stub.StreamObserver;
import org.hibernate.SessionFactory;

public class EventServiceImpl extends EventServiceGrpc.EventServiceImplBase {

    private final EventDao eventDao;
    private final UserDao userDao;
    public EventServiceImpl(EventDao eventDao, UserDao userDao) {

        this.eventDao = eventDao;
        this.userDao = userDao;

    }

    @Override
    public void create(EventCreationDtoMessage request, StreamObserver<EventMessage> responseObserver) {

        Event eventReply = eventDao.create(GrpcFactory.fromEventCreationDtoMessageToEvent(request));
        EventMessage reply = GrpcFactory.fromEventToMessage(eventReply);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void getById(IntRequest request, StreamObserver<EventMessage> responseObserver) {
        Event eventReply = eventDao.getById(request.getInt());
        EventMessage reply = GrpcFactory.fromEventToMessage(eventReply);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
