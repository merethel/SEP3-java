package services;

import Daos.EventDao;
import Daos.UserDao;
import domain.GrpcFactory;
import domain.User;
import event.*;
import io.grpc.stub.StreamObserver;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final EventDao eventDao;
    private final UserDao userDao;

    public UserServiceImpl(EventDao eventDao, UserDao userDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
    }


    @Override
    public void create(UserCreationDtoMessage request, StreamObserver<UserMessage> responseObserver) {
        User userReply = userDao.create(GrpcFactory.fromUserCreationDtoMessageToUser(request));
        UserMessage reply = GrpcFactory.fromUserToMessage(userReply);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void getById(IntRequest request, StreamObserver<UserMessage> responseObserver) {
        User userReply = userDao.getById(request.getInt());
        UserMessage reply = GrpcFactory.fromUserToMessage(userReply);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void getByUsername(StringRequest request, StreamObserver<UserMessage> responseObserver) {
        User userReply = userDao.getByUsername(request.getString());
        UserMessage reply = UserMessage.newBuilder().build();
        if (userReply != null) {
            reply = GrpcFactory.fromUserToMessage(userReply);
        }
        System.out.println(reply);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
