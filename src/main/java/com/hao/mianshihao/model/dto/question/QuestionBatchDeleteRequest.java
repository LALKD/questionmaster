package com.hao.mianshihao.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class QuestionBatchDeleteRequest implements Serializable {

    private List<Long> questionIdList;
    private static final long serialVersionUID = 1L;
}
