package services;


import Daos.EventDao;
import Daos.UserDao;
import domain.Event;
import domain.GrpcFactory;
import event.*;

import io.grpc.stub.StreamObserver;

import java.util.List;

public class EventServiceImpl extends EventServiceGrpc.EventServiceImplBase {

    private final EventDao eventDao;
    private final UserDao userDao;

    public EventServiceImpl(EventDao eventDao, UserDao userDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
    }

    @Override
    public void create(EventCreationDtoMessage request, StreamObserver<EventMessage> responseObserver) {
        Event eventToCreate = GrpcFactory.fromEventCreationDtoMessageToEvent(request);
        eventToCreate.setOwner(userDao.getByUsername(request.getUsername()));
        Event eventReply = eventDao.create(eventToCreate);
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

    @Override
    public void getAllEvents(IntRequest request, StreamObserver<ListEventMessage> responseObserver) {
        List<Event> events = eventDao.getAllEvents();
        ListEventMessage reply = GrpcFactory.fromEventListToEventListMessage(events);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void addAttendeeToEventAttendeeList(AddAttendeeRequest request, StreamObserver<IntRequest> responseObserver) {
        eventDao.addAttendeeToEventAttendeeList(request.getUserId(), request.getEventId());
        responseObserver.onNext(IntRequest.newBuilder().build());
        responseObserver.onCompleted();
    }
}
