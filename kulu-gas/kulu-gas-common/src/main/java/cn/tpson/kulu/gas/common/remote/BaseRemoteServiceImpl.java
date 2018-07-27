package cn.tpson.kulu.gas.common.remote;

import cn.tpson.kulu.gas.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.gas.common.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangka in 2018/07/27
 */
@Service
public class BaseRemoteServiceImpl<DTO, QUERY, T> implements BaseRemoteService<DTO, QUERY> {
    private static final Logger log = LoggerFactory.getLogger(BaseRemoteServiceImpl.class);
    public static final String ERROR_MSG_QUERY_ERROR = "查找时出错";
    public static final String ERROR_MSG_UPDATE_ERROR = "更新时出错";
    public static final String ERROR_MSG_DELETE_ERROR = "删除时出错";
    public static final String ERROR_MSG_SAVE_ERROR = "保存时出错";

    @Autowired
    private BaseRepository<T, Long> baseRepository;

    @Override
    public RemoteResult<DTO> findById(Long id) {
        try {
            T entity = baseRepository.getOne(id);
            return RemoteResult.successResult(BeanUtils.copyProperties(getGenericClass(0), entity));
        } catch (Exception e) {
            log.error("BaseRemoteService->findById", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<List<DTO>> findAllById(List<Long> ids) {
        try {
            List<T> list = baseRepository.findAllById(ids);
            return RemoteResult.successResult(BeanUtils.copyPropertiesForList(getGenericClass(0), list));
        } catch (Exception e) {
            log.error("BaseRemoteService->findAllById", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<List<DTO>> findAll() {
        try {
            List<T> list = baseRepository.findAll();
            return RemoteResult.successResult(BeanUtils.copyPropertiesForList(getGenericClass(0), list));
        } catch (Exception e) {
            log.error("BaseRemoteService->findAll", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<Long> count() {
        try {
            long cnt = baseRepository.count();
            return RemoteResult.successResult(cnt);
        } catch (Exception e) {
            log.error("BaseRemoteService->count", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<List<DTO>> findByExample(QUERY query) {
        try {
            T t = BeanUtils.copyProperties(getGenericClass(2), query);
            List<T> list = baseRepository.findAll(Example.of(t));
            return RemoteResult.successResult(BeanUtils.copyPropertiesForList(getGenericClass(0), list));
        } catch (Exception e) {
            log.error("BaseRemoteService->findByExample", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<Long> countByExample(QUERY query) {
        try {
            T t = BeanUtils.copyProperties(getGenericClass(2), query);
            long cnt = baseRepository.count(Example.of(t));
            return RemoteResult.successResult(cnt);
        } catch (Exception e) {
            log.error("BaseRemoteService->countByExample", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<Page<DTO>> pageByExample(QUERY query) {
        try {
            Class<QUERY> clazz = getGenericClass(1);
            Method method = clazz.getMethod("getPageNumber");
            Integer pageNumber = (Integer) method.invoke(query);
            method = clazz.getMethod("getPageSize");
            Integer pageSize = (Integer) method.invoke(query);

            T t = BeanUtils.copyProperties(getGenericClass(2), query);
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<T> page = baseRepository.findAll(Example.of(t), pageable);

            List<DTO> list = BeanUtils.copyPropertiesForList(getGenericClass(0), page.getContent());
            return RemoteResult.successResult(new PageImpl<>(list, pageable, page.getTotalElements()));
        } catch (Exception e) {
            log.error("BaseRemoteService->pageByExample", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<DTO> save(DTO dto) {
        try {
            T t = BeanUtils.copyProperties(getGenericClass(2), dto);
            return BeanUtils.copyProperties(getGenericClass(0), baseRepository.save(t));
        } catch (Exception e) {
            log.error("BaseRemoteService->update", e);
            return RemoteResult.failResult(ERROR_MSG_SAVE_ERROR);
        }


    }

    @Override
    public RemoteResult<DTO> update(DTO dto) {
        try {
            return save(dto);
        } catch (Exception e) {
            log.error("BaseRemoteService->update", e);
            return RemoteResult.failResult(ERROR_MSG_UPDATE_ERROR);
        }
    }

    @Override
    public RemoteResult<Void> deleteById(Long id) {
        try {
            baseRepository.deleteById(id);
            return RemoteResult.successResult(null);
        } catch (Exception e) {
            log.error("BaseRemoteService->deleteById", e);
            return RemoteResult.failResult(ERROR_MSG_DELETE_ERROR);
        }

    }

    @Override
    public RemoteResult<Void> deleteAll(Iterable<Long> ids) {
        try {
            List<T> list = new ArrayList<>();
            Class<T> clazz = getGenericClass(2);
            for (Long id : ids) {
                T t = clazz.newInstance();
                Method method = clazz.getMethod("setId");
                method.invoke(t, id);
                list.add(t);
            }
            baseRepository.deleteInBatch(list);
            return RemoteResult.successResult(null);
        } catch (Exception e) {
            log.error("BaseRemoteService->deleteAll", e);
            return RemoteResult.failResult(ERROR_MSG_DELETE_ERROR);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    protected <T> Class<T> getGenericClass(int idx) {
        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[idx];
        return (Class<T>)type;
    }
}
