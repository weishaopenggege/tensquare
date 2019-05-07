package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签业务逻辑类
 * @author Wsp
 * @date 2019/5/5 20:25
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 根据ID查询标签
     * @param id
     * @return
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 查询全部标签
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 增加标签
     * @param label
     */
    public void save(Label label){
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 修改标签
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 删除标签
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 条件 查询
     * @param searchMap
     * @return
     */
    public List<Label> findSearch(Map<String,Object> searchMap) {
        Specification<Label> specification = createSpecification(searchMap);
        return labelDao.findAll(specification);
    }

    public Page<Label> findSearch(Map<String,Object> searchMap, int page, int size) {
        Specification<Label> specification = createSpecification(searchMap);
        Pageable pageable = PageRequest.of(page-1, size); // limit 0,10
        return labelDao.findAll(specification,pageable);
    }

    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Specification<Label> createSpecification(Map<String,Object> searchMap){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                String labelname = (String)searchMap.get("labelname");
                String state = (String)searchMap.get("state");

                List<Predicate> predicateList = new ArrayList<>();
                if (!StringUtils.isEmpty(labelname)) {
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + labelname + "%");
                    predicateList.add(predicate);
                }
                if (!StringUtils.isEmpty(state)) {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), state);
                    predicateList.add(predicate);
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
