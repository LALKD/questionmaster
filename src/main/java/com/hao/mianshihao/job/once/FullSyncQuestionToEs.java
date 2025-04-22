package com.hao.mianshihao.job.once;

import cn.hutool.core.collection.CollUtil;
import com.hao.mianshihao.esdao.QuestionEsDao;
import com.hao.mianshihao.model.dto.question.QuestionEsDTO;
import com.hao.mianshihao.model.entity.Question;
import com.hao.mianshihao.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CommandLineRunner 是 Spring Boot 提供的一个接口，它允许开发者在 Spring Boot 应用启动完成后立即执行特定的代码逻辑。
 *
 * 如果应用中有多个 CommandLineRunner 实现类，并且需要控制它们的执行顺序，可以使用 @Order 注解或者实现 Ordered 接口。
 * @Order(1)    @Order(2)值越小，越先执行。
 */
@Component
@Slf4j
public class FullSyncQuestionToEs implements CommandLineRunner {

    @Resource
    private QuestionEsDao questionEsDao;
    @Resource
    private QuestionService questionService;
    @Override
    public void run(String... args) throws Exception {
        questionEsDao.deleteAll();
        //全量获取题目（数据量不大的情况下使用）
        List<Question> questionList = questionService.list();
        if (CollUtil.isEmpty(questionList)) {
            return;
        }
        //转为ES实体类
        List<QuestionEsDTO> questionEsDTOList = questionList.stream().map((QuestionEsDTO::objToDto)).collect(Collectors.toList());
        //分页批量插入到ES
        final int pageSize = 500;
        int total = questionEsDTOList.size();
        log.info("FullSyncQuestionToEs start. total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            //注意同步的数据下标不能超过总数据量
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            questionEsDao.saveAll(questionEsDTOList.subList(i, end));
        }
        log.info("FullSyncQuestionToEs end. total {}", total);
    }
}
