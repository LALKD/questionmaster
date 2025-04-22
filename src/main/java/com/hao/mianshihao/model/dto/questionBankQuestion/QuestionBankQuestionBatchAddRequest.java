package com.hao.mianshihao.model.dto.questionBankQuestion;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionBankQuestionBatchAddRequest implements Serializable {
    private Long questionBankId;

    private List<Long> questionIdList;

    private static final long serialVersionUID = 1L;
}
