package com.scnu.edu.controller;


import com.scnu.edu.entity.Chapter;
import com.scnu.edu.service.ChapterService;
import com.scnu.edu.vo.ChapterVo;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
@Api(tags = "章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("返回前端发过来的courseId,即对应课程下的章节以及小节")
    @GetMapping("/getAllChapters/{courseId}")
    public Result getAllChapters(@PathVariable String courseId){
        List<ChapterVo> chapterVos = chapterService.getAllChapterVos(courseId);
        return Result.ok().data("items",chapterVos);
    }

    @ApiOperation("添加章节")
    @PostMapping
    public Result saveChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return Result.ok();
    }

    @ApiOperation("修改章节")
    @PutMapping
    public Result updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return Result.ok();
    }

    @ApiOperation("删除章节,章节下有小节的话不能删除")
    @DeleteMapping("/{id}")
    public Result deleteChapter(@PathVariable("id") String id){
        chapterService.deleteChapter(id);
        return Result.ok();
    }


    @ApiOperation("根据id得到一个章节")
    @GetMapping("/{id}")
    public Result getChapter(@PathVariable("id") String id){
        Chapter chapter = chapterService.getById(id);
        return Result.ok().data("item",chapter);
    }

}

