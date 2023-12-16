package org.vnsemkin.taskmanagementsystem.util.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.vnsemkin.taskmanagementsystem.dto.CommentDto;
import org.vnsemkin.taskmanagementsystem.entity.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class CommentMapper {
    private UserMapper userMapper;
    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .text(comment.getText())
                .author(UserMapper.UserToUserDto(comment.getAuthor()))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public  Comment toComment(CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .author(userMapper.UserDtoToUser(commentDto.getAuthor()))
                .createdAt(commentDto.getCreatedAt())
                .updatedAt(commentDto.getUpdatedAt())
                .build();
    }

    public static List<CommentDto> toCommentDtoList(List<Comment> comments) {
        if (Objects.nonNull(comments)) {
            return comments.stream().map(CommentMapper::toCommentDto).toList();
        } else {
            return new ArrayList<>();
        }
    }

    public  List<Comment> toCommentList(List<CommentDto> comments) {
        if (Objects.nonNull(comments)) {
            return comments.stream().map(this::toComment).toList();
        } else {
            return new ArrayList<>();
        }
    }
}
