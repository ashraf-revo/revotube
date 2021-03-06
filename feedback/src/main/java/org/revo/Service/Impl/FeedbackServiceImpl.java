package org.revo.Service.Impl;

import org.revo.Domain.*;
import org.revo.Repository.UserMediaCommentRepository;
import org.revo.Repository.UserMediaLikeRepository;
import org.revo.Repository.UserMediaViewRepository;
import org.revo.Repository.UserUserFollowRepository;
import org.revo.Service.FeedbackService;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMediaCommentRepository userMediaCommentRepository;
    @Autowired
    private UserMediaLikeRepository userMediaLikeRepository;
    @Autowired
    private UserMediaViewRepository userMediaViewRepository;
    @Autowired
    private UserUserFollowRepository userUserFollowRepository;

    @Override
    public List<UserUserFollow> followers(String id) {
        return userUserFollowRepository.findByTo(id);
    }

    @Override
    public List<UserUserFollow> following(String id) {
        return userUserFollowRepository.findByFrom(id);
    }

    @Override
    public UserInfo userInfo(String id) {
        return UserInfo.builder()
                .id(id)
                .followers(userUserFollowRepository.countByTo(id))
                .following(userUserFollowRepository.countByFrom(id))
                .build();
    }

    @Override
    public MediaInfo mediaInfo(String id) {
        return MediaInfo.builder()
                .id(id)
                .likes(userMediaLikeRepository.countByMedia(id))
                .comments(userMediaCommentRepository.countByMedia(id))
                .views(userMediaViewRepository.countByMedia(id))
                .build();
    }

    @Override
    public List<UserMediaComment> comments(String id) {
        return userMediaCommentRepository.findAllByMedia(id);
    }

    @Override
    public UserMediaLike like(String id) {
        return userMediaLikeRepository.findByUserIdAndMedia(userService.current(), id).orElseGet(() -> userMediaLikeRepository.save(UserMediaLike.builder().media(id).build()));
    }

    @Override
    public void unlike(String id) {
        userMediaLikeRepository.deleteByMediaAndUserId(id, userService.current());
    }

    @Override
    public UserMediaView view(String id) {
        return userMediaViewRepository.save(UserMediaView.builder().media(id).build());
    }

    @Override
    public UserUserFollow follow(String id) {
        return userUserFollowRepository.findByFromAndTo(userService.current(), id).orElseGet(() -> userUserFollowRepository.save(UserUserFollow.builder().to(id).build()));
    }

    @Override
    public void unfollow(String id) {
        userUserFollowRepository.deleteByFromAndTo(userService.current(), id);
    }

    @Override
    public UserMediaComment comment(String id, String message) {
        UserMediaComment userMediaComment = new UserMediaComment();
        userMediaComment.setMedia(id);
        userMediaComment.setMessage(message);
        return userMediaCommentRepository.save(userMediaComment);
    }

    @Override
    public void uncomment(String id) {
        userMediaCommentRepository.deleteByIdAndUserId(id, userService.current());
    }

    @Override
    public boolean liked(String id) {
        return userMediaLikeRepository.findByUserIdAndMedia(userService.current(), id).isPresent();
    }

    @Override
    public boolean followed(String id) {
        return userUserFollowRepository.findByFromAndTo(userService.current(), id).isPresent();
    }

}
