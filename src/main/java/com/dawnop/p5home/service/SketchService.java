package com.dawnop.p5home.service;

import com.dawnop.p5home.entity.Sketch;
import com.dawnop.p5home.mapper.SketchMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SketchService {

    @Resource
    private SketchMapper sketchMapper;

    public List<Sketch> querySketchList() {
        return sketchMapper.querySketchList();
    }

    public boolean insertSketch(Sketch sketch) {
        return sketchMapper.insertSketch(sketch) == 1;
    }

}
