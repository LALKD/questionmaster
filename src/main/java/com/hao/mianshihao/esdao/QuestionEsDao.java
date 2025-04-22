package com.hao.mianshihao.esdao;

import com.hao.mianshihao.model.dto.question.QuestionEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
// 定义 QuestionEsDao 接口，继承自 ElasticsearchRepository
// 泛型参数 <QuestionEsDTO, Long> 表示操作的实体类是 QuestionEsDTO，实体类的 ID 类型是 Long
public interface QuestionEsDao extends ElasticsearchRepository<QuestionEsDTO, Long> {

    /**
     * 根据用户 id 查询       ,支持根据方法名自动映射未查询操作，这个方法就会根据userId查询数据
     * @param userId
     * @return
     */
    List<QuestionEsDTO> findByUserId(Long userId);

}
