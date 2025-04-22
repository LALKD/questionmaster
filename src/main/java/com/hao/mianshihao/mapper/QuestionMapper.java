package com.hao.mianshihao.mapper;

import com.hao.mianshihao.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author dell
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2025-04-14 18:13:34
* @Entity com.hao.mainshihao.model.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {


    /**
     * 查询题目列表（包括已经被删除的数据，为了实现让ES和MySQL完全同步）,若用mybatis-plus自带的则查不到isDelete=1的数据
     */
    @Select("select * from question where updateTime >= #{minUpdateTime}")
    List<Question> listQuestionWithDelete(Date minUpdateTime);
}




