package services;

import Daos.UserDao;
import shared.GrpcFactory;
import shared.model.User;
import event.*;
import io.grpc.stub.StreamObserver;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
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


    @Override
    public void deleteUser(IntRequest request, StreamObserver<UserMessage> responseObserver) {
        User userToDelete = userDao.deleteUser(request.getInt());
        UserMessage reply = GrpcFactory.fromUserToMessage(userToDelete);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
