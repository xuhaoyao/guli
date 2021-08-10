package com.scnu.edu.controller.front;

import com.scnu.edu.client.UserClient;
import com.scnu.edu.entity.Comment;
import com.scnu.edu.service.CommentService;
import com.scnu.exceptions.CommentException;
import com.scnu.utils.JwtUtils;
import com.scnu.utils.Result;
import com.scnu.utils.dto.CommentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "课程评论控制器")
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserClient userClient;

    @ApiOperation("前台添加一条评论")
    @PostMapping
    public Result saveComment(@RequestBody Comment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            throw new CommentException("请登录后再评论");
        }
        CommentUser commentUser = userClient.commentUser(memberId);
        if(commentUser == null){
            throw new CommentException("远程调用失败...");
        }
        comment.setAvatar(commentUser.getAvatar());
        comment.setNickname(commentUser.getNickname());
        comment.setMemberId(memberId);
        commentService.save(comment);
        return Result.ok();
    }

    @ApiOperation("分页查询评论")
    @GetMapping("/{current}/{size}")
    public Result getCommentPage(
            @ApiParam(name = "current",value = "当前页码",required = true)
            @PathVariable("current") Integer current,

            @ApiParam(name = "size",value = "每页记录数",required = true)
            @PathVariable("size") Integer size){
        Map<String,Object> map = commentService.getCommentPage(current,size);
        return Result.ok().data(map);
    }

}
