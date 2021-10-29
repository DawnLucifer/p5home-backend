package com.dawnop.p5home.mapper;

import com.dawnop.p5home.entity.Sketch;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SketchMapper {

    @Select("select * from tb_sketch")
    List<Sketch> querySketchList();

    @Insert("insert into tb_sketch(name,author,description,content) " +
            "values (#{name},#{author},#{description},#{content})")
    int insertSketch(Sketch sketch);
}
