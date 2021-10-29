package com.dawnop.p5home.controller;

import com.dawnop.p5home.commons.Result;
import com.dawnop.p5home.entity.Sketch;
import com.dawnop.p5home.service.SketchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sketch")
public class SketchController {

    @Resource
    private SketchService sketchService;

    @GetMapping("/list")
    public Result<List<Sketch>> querySketchList() {
        return Result.success(sketchService.querySketchList());
    }

    @PostMapping
    public Result insertSketch(@RequestBody Sketch sketch) {
        if (sketchService.insertSketch(sketch)) {
            return Result.success(null);
        } else {
            return Result.failed();
        }
    }
}
