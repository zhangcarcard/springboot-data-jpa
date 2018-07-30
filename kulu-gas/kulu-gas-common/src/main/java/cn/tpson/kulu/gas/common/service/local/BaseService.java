package cn.tpson.kulu.gas.common.service.local;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Zhangka in 2018/07/30
 */
public interface BaseService<DTO> {
    /**
     * 通过id查询实体.
     *
     * @param id
     * @return
     */
    DTO findById(Long id);

    /**
     * 通过id列表查询实体列表，如果查询不到，返回元素数量为0的列表.
     *
     * @param ids
     * @return
     */
    List<DTO> findAllById(List<Long> ids);

    /**
     * 查询所有实体.
     *
     * @return
     */
    List<DTO> findAll();

    /**
     * 计算实体总数量.
     *
     * @return
     */
    long count();

    /**
     * 条件查询实体列表，如果查询不到，返回元素数量为0的列表.
     *
     * @param query
     * @return
     */
    <QUERY extends DTO> List<DTO> findByExample(QUERY query);

    /**
     * 按条件计算实体数量.
     *
     * @param query
     * @return
     */
    <QUERY extends DTO> long countByExample(QUERY query);

    /**
     * 分页查询实体列表，如果查询不到，返回元素数量为0的列表.
     *
     * @param query
     * @return
     */
    <QUERY extends DTO> Page<DTO> pageByExample(QUERY query);

    /**
     * 保存实体.
     *
     * @param dto
     * @return
     */
    long save(DTO dto);

    /**
     * 保存实体.
     *
     * @param dto
     * @return
     */
    long update(DTO dto);

    /**
     * 通过id删除实体,返回被删除的记录数.
     *
     * @param id
     * @return
     */
    void deleteById(Long id);

    /**
     *
     * @param ids
     * @return
     */
    void deleteAll(Iterable<Long> ids);
}
