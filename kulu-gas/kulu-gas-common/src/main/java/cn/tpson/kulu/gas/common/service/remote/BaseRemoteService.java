package cn.tpson.kulu.gas.common.service.remote;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Zhangka in 2018/07/27
 */
public interface BaseRemoteService<DTO> {
    /**
     * 通过id查询实体.
     *
     * @param id
     * @return
     */
    RemoteResult<DTO> findById(Long id);

    /**
     * 通过id列表查询实体列表，如果查询不到，返回元素数量为0的列表.
     *
     * @param ids
     * @return
     */
    RemoteResult<List<DTO>> findAllById(List<Long> ids);

    /**
     * 查询所有实体.
     *
     * @return
     */
    RemoteResult<List<DTO>> findAll();

    /**
     * 计算实体总数量.
     *
     * @return
     */
    RemoteResult<Long> count();

    /**
     * 条件查询实体列表，如果查询不到，返回元素数量为0的列表.
     *
     * @param query
     * @return
     */
    <QUERY extends DTO> RemoteResult<List<DTO>> findByExample(QUERY query);

    /**
     * 按条件计算实体数量.
     *
     * @param query
     * @return
     */
    <QUERY extends DTO> RemoteResult<Long> countByExample(QUERY query);

    /**
     * 分页查询实体列表，如果查询不到，返回元素数量为0的列表.
     *
     * @param query
     * @return
     */
    <QUERY extends DTO> RemoteResult<Page<DTO>> pageByExample(QUERY query);

    /**
     * 保存实体.
     *
     * @param dto
     * @return
     */
    RemoteResult<Long> save(DTO dto);

    /**
     * 保存实体.
     *
     * @param dto
     * @return
     */
    RemoteResult<Long> update(DTO dto);

    /**
     * 通过id删除实体,返回被删除的记录数.
     *
     * @param id
     * @return
     */
    RemoteResult<Void> deleteById(Long id);

    /**
     *
     * @param ids
     * @return
     */
    RemoteResult<Void> deleteAll(Iterable<Long> ids);
}
