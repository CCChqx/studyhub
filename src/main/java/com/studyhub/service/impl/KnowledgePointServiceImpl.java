package com.studyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyhub.common.ErrorCode;
import com.studyhub.common.PageResult;
import com.studyhub.converter.KnowledgePointConverter;
import com.studyhub.exception.BusinessException;
import com.studyhub.pojo.dto.KnowledgePointCreateDTO;
import com.studyhub.pojo.entity.KnowledgePoint;
import com.studyhub.mapper.KnowledgePointMapper;
import com.studyhub.pojo.query.KnowledgePointQuery;
import com.studyhub.service.KnowledgePointService;
import com.studyhub.pojo.vo.KnowledgePointVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class KnowledgePointServiceImpl extends ServiceImpl<KnowledgePointMapper, KnowledgePoint> implements KnowledgePointService {

    @Override
    public PageResult<KnowledgePointVO> pageKnowledgePoints(KnowledgePointQuery query) {
        // 1. 拼 where 条件：三个条件都判空，null/空串就不加这个条件
        LambdaQueryWrapper<KnowledgePoint> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getSubjectId() != null, KnowledgePoint::getSubjectId, query.getSubjectId());
        wrapper.eq(query.getCategoryId() != null, KnowledgePoint::getCategoryId, query.getCategoryId());
        wrapper.like(StringUtils.hasText(query.getKeyword()), KnowledgePoint::getTitle, query.getKeyword());
        wrapper.orderByDesc(KnowledgePoint::getId);

        // 2. 查库：query 本身就是 Page，this.page 会把 records/total 填回 query
        this.page(query, wrapper);

        // 3. entity 转 VO：getRecords() 是 List<KnowledgePoint>，逐个 map 成 VO
        List<KnowledgePointVO> voList = query.getRecords().stream()
                .map(KnowledgePointConverter::toVO)
                .toList();

        // 4. 组装成 PageResult 返回：total 是全部页的总条数，list 是当前页数据
        return PageResult.of(query.getTotal(), voList);
    }

    @Override
    public KnowledgePointVO getKnowledgePointById(Long id) {
        KnowledgePoint knowledgePoint = this.getById(id);
        if (knowledgePoint == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return KnowledgePointConverter.toVO(knowledgePoint);
    }

    @Override
    public KnowledgePointVO insertKnowledgePoint(KnowledgePointCreateDTO dto) {

    }
}
