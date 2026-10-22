package com.studyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyhub.common.PageResult;
import com.studyhub.pojo.dto.KnowledgePointCreateDTO;
import com.studyhub.pojo.dto.KnowledgePointUpdateDTO;
import com.studyhub.pojo.entity.KnowledgePoint;
import com.studyhub.pojo.query.KnowledgePointQuery;
import com.studyhub.pojo.vo.KnowledgePointVO;

public interface KnowledgePointService extends IService<KnowledgePoint> {

    /**
     * 知识点分页查询。入参是 KnowledgePointQuery（继承 Page，自带分页参数 + 筛选条件），
     * 出参是 PageResult<VO>（total + list，挡掉 MyBatis-Plus 的 Page 框架细节）。
     */
    PageResult<KnowledgePointVO> pageKnowledgePoints(KnowledgePointQuery query);

    KnowledgePointVO getKnowledgePointById(Long id);

    KnowledgePointVO createKnowledgePoint(KnowledgePointCreateDTO dto);

    KnowledgePointVO updateKnowledgePoint(KnowledgePointUpdateDTO dto);

    void deleteKnowledgePointById(Long id);
}
