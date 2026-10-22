package com.studyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyhub.common.ErrorCode;
import com.studyhub.common.PageResult;
import com.studyhub.converter.KnowledgePointConverter;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.KnowledgePointMapper;
import com.studyhub.mapper.QuestionMapper;
import com.studyhub.pojo.dto.KnowledgePointCreateDTO;
import com.studyhub.pojo.dto.KnowledgePointUpdateDTO;
import com.studyhub.pojo.entity.KnowledgePoint;
import com.studyhub.pojo.entity.Question;
import com.studyhub.pojo.query.KnowledgePointQuery;
import com.studyhub.pojo.vo.KnowledgePointVO;
import com.studyhub.service.KnowledgePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgePointServiceImpl extends ServiceImpl<KnowledgePointMapper, KnowledgePoint> implements KnowledgePointService {

    private final QuestionMapper questionMapper;

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

    // 根据 Id 查询单个知识点
    @Override
    public KnowledgePointVO getKnowledgePointById(Long id) {
        KnowledgePoint knowledgePoint = this.getById(id);
        if (knowledgePoint == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return KnowledgePointConverter.toVO(knowledgePoint);
    }

    // 插入知识点
    @Override
    public KnowledgePointVO createKnowledgePoint(KnowledgePointCreateDTO dto) {
        KnowledgePoint knowledgePoint = KnowledgePointConverter.toEntity(dto);
        this.save(knowledgePoint);
        return KnowledgePointConverter.toVO(knowledgePoint);
    }

    // 更新知识点
    @Override
    public KnowledgePointVO updateKnowledgePoint(KnowledgePointUpdateDTO dto) {
        // 判断是否存在该知识点
        KnowledgePoint knowledgePoint = this.getById(dto.getId());
        if (knowledgePoint == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        // 字段赋值更新
        knowledgePoint.setTitle(dto.getTitle());
        knowledgePoint.setCategoryId(dto.getCategoryId());
        knowledgePoint.setSubjectId(dto.getSubjectId());
        knowledgePoint.setContent(dto.getContent());
        knowledgePoint.setTags(dto.getTags());
        knowledgePoint.setImportance(dto.getImportance());

        this.updateById(knowledgePoint);
        return KnowledgePointConverter.toVO(knowledgePoint);
    }


    // 根据 Id 删除知识点
    @Override
    @Transactional
    public void deleteKnowledgePointById(Long id) {
        // 判断知识点下是否有题目
        long questionCount = questionMapper.selectCount(new LambdaQueryWrapper<Question>()
                .eq(Question::getKpId,id));
        if (questionCount > 0) {
            throw new BusinessException(ErrorCode.KNOWLEDGE_POINT_HAS_QUESTION);
        }

        if (! this.removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }
}
