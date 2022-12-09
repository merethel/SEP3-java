package services;

import DaoInterfaces.EventDaoInterface;
import DaoInterfaces.UserDaoInterface;
import Daos.EventDao;
import Daos.UserDao;
import shared.model.Event;
import shared.GrpcFactory;
import event.*;

import io.grpc.stub.StreamObserver;

import java.util.List;

public class EventServiceImpl extends EventServiceGrpc.EventServiceImplBase {

    //private final EventDao eventDao;
    private final EventDaoInterface eventDao;
    private final UserDaoInterface userDao;

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
    public void getAllEvents(CriteriaDtoMessage criteriaDtoMessage, StreamObserver<ListEventMessage> responseObserver) {
        List<Event> events = eventDao.getAllEvents(criteriaDtoMessage);
        ListEventMessage reply = GrpcFactory.fromEventListToEventListMessage(events);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void addAttendeeToEventAttendeeList(AddAttendeeRequest request, StreamObserver<EventMessage> responseObserver) {
        Event event = eventDao.addAttendeeToEventAttendeeList(request.getUserId(), request.getEventId());
        EventMessage reply = GrpcFactory.fromEventToMessage(event);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

   @Override
   public void cancel(IntRequest request, StreamObserver<EventMessage> responseObserver) {
        System.out.println(request.getInt());
        Event eventCancelReply = eventDao.cancel(request.getInt());
        EventMessage reply = GrpcFactory.fromEventToMessage(eventCancelReply);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }
}
