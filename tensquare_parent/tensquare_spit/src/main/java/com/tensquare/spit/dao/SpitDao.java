package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *  吐槽数据访问层
 * @author Wsp
 * @date 2019/5/8 21:08
 */
public interface SpitDao extends MongoRepository<Spit,String> {
    public Page<Spit> findByParentid(String parentId, Pageable pageable);
}
