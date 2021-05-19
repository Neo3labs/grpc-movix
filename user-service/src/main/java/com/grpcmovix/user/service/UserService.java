package com.grpcmovix.user.service;

import com.grpcmovix.user.repository.UserRepository;
import com.sb.grpcmovix.common.Genre;
import com.sb.grpcmovix.user.UserGenreUpdateRequest;
import com.sb.grpcmovix.user.UserResponse;
import com.sb.grpcmovix.user.UserSearchRequest;
import com.sb.grpcmovix.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository repository;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.repository.findById(request.getLoginId())
                .ifPresent(user -> {
                    builder.setName(user.getName()).setLoginId(user.getLogin()).setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.repository.findById(request.getLoginId()).ifPresent(user -> {
            user.setGenre(request.getGenre().toString());
            builder.setName(user.getName()).setLoginId(user.getLogin()).setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
