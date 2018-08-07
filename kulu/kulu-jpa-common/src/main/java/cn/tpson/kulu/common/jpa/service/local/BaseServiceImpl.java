package cn.tpson.kulu.common.jpa.service.local;

import cn.tpson.kulu.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.common.jpa.support.ExampleLikeRangeSpecification;
import cn.tpson.kulu.common.jpa.support.Like;
import cn.tpson.kulu.common.jpa.support.Range;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.common.util.DateUtils;
import cn.tpson.kulu.common.util.ReferenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Zhangka in 2018/07/30
 */
public class BaseServiceImpl<DTO, DO> implements BaseService<DTO> {
    @Autowired
    private BaseRepository<DO, Long> baseRepository;

    @Override
    public DTO findById(Long id) {
        Optional<DO> optional = baseRepository.findById(id);
        Class<DTO> dto = getGenericClassForDTO();
        return optional.isPresent() ? BeanUtils.newAndCopyProperties(dto, optional.get()) : null;
    }

    @Override
    public List<DTO> findAllById(List<Long> ids) {
        List<DO> list = baseRepository.findAllById(ids);
        Class<DTO> dto = getGenericClassForDTO();
        return BeanUtils.newAndCopyPropertiesForList(dto, list);
    }

    @Override
    public List<DTO> findAll() {
        List<DO> list = baseRepository.findAll();
        Class<DTO> dto = getGenericClassForDTO();
        return BeanUtils.newAndCopyPropertiesForList(dto, list);
    }

    @Override
    public long count() {
        long cnt = baseRepository.count();
        return cnt;
    }

    @Override
    public <QUERY extends DTO> List<DTO> findByExample(QUERY query) {
        DO t = BeanUtils.newAndCopyProperties(getGenericClassForDO(), query);
        List<DO> list = baseRepository.findAll(Example.of(t));
        Class<DTO> dto = getGenericClassForDTO();
        return BeanUtils.newAndCopyPropertiesForList(dto, list);
    }

    @Override
    public <QUERY extends DTO> long countByExample(QUERY query) {
        DO t = BeanUtils.newAndCopyProperties(getGenericClassForDO(), query);
        long cnt = baseRepository.count(Example.of(t));
        return cnt;
    }

    @Override
    public <QUERY extends DTO> long countByExample(QUERY query, Range... ranges) {
        DO t = BeanUtils.newAndCopyProperties(getGenericClassForDO(), query);
        ExampleLikeRangeSpecification<DO> spec = ExampleLikeRangeSpecification.of(Example.of(t), null, ranges);
        long cnt = baseRepository.count(spec);
        return cnt;
    }

    @Override
    public <QUERY extends DTO> long countByExample(QUERY query, Like like, Range... ranges) {
        DO t = BeanUtils.newAndCopyProperties(getGenericClassForDO(), query);
        ExampleLikeRangeSpecification<DO> spec = ExampleLikeRangeSpecification.of(Example.of(t), like, ranges);
        long cnt = baseRepository.count(spec);
        return cnt;
    }

    @Override
    public <QUERY extends DTO> Page<DTO> pageByExample(QUERY query, Like like, Sort sort, Range... ranges) {
        Integer pageNumber = (Integer) ReferenceUtils.get(query, "pageNumber");
        Integer pageSize = (Integer) ReferenceUtils.get(query, "pageSize");

        DO t = BeanUtils.newAndCopyProperties(getGenericClassForDO(), query);
        ExampleLikeRangeSpecification<DO> spec = ExampleLikeRangeSpecification.of(Example.of(t), like, ranges);
        sort = (sort == null) ? Sort.by(Sort.Order.desc("gmtModified"), Sort.Order.desc("id")) : sort;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<DO> page = baseRepository.findAll(spec, pageable);

        Class<DTO> dto = getGenericClassForDTO();
        List<DTO> list = BeanUtils.newAndCopyPropertiesForList(dto, page.getContent());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public <QUERY extends DTO> Page<DTO> pageByExample(QUERY query, Sort sort, Range... ranges) {
        return pageByExample(query, null, sort, ranges);
    }

    @Override
    public <QUERY extends DTO> Page<DTO> pageByExample(QUERY query) {
        return pageByExample(query, null);
    }

    @Override
    public Page<DTO> pageByKeywordContaining(Integer pageNumber, Integer pageSize, Sort sort, String search) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public long save(DTO dto) {
        if (ReferenceUtils.get(dto, "id") != null) {
            return update(dto);
        } else {
            ReferenceUtils.set(dto, "gmtCreate", DateUtils.now());
            ReferenceUtils.set(dto, "deleted", false);
            DO t = BeanUtils.newAndCopyProperties(getGenericClassForDO(), dto);
            t = baseRepository.save(t);
            return (long) ReferenceUtils.get(t, "id");
        }
    }

    @Transactional
    @Override
    public long saveAll(Iterable<DTO> entities) {
        List<DTO> updateList = new ArrayList<>();
        List<DTO> saveList = new ArrayList<>();
        for (DTO dto : entities) {
            if (ReferenceUtils.get(dto, "id") != null) {
                updateList.add(dto);
            } else {
                saveList.add(dto);
            }
        }

        long ret = 0;
        if (!updateList.isEmpty()) {
            ret = updateAll(updateList);
        } else if (!saveList.isEmpty()) {
            List<DO> list = BeanUtils.newAndCopyPropertiesForList(getGenericClassForDO(), entities);
            for (DO t : list) {
                ReferenceUtils.set(t, "gmtCreate", DateUtils.now());
                ReferenceUtils.set(t, "deleted", false);
            }
            ret = baseRepository.saveAll(list).size();
        }
        return ret;
    }

    @Transactional
    @Override
    public long update(DTO dto) {
        Long id = (Long)ReferenceUtils.get(dto, "id");
        DO t = baseRepository.findById(id).orElse(null);

        if (t == null) {
            t = BeanUtils.newAndCopyProperties(getGenericClassForDO(), dto);
            ReferenceUtils.set(t, "gmtCreate", DateUtils.now());
            ReferenceUtils.set(t, "deleted", false);
        } else {
            BeanUtils.copyProperties(t, dto, "deleted", "gmtCreate");
            ReferenceUtils.set(t, "gmtModified", DateUtils.now());
        }

        t = baseRepository.save(t);
        return (long)ReferenceUtils.get(t, "id");
    }

    @Transactional
    @Override
    public long updateAll(Iterable<DTO> entities) {
        long count = 0;
        for (DTO dto : entities) {
            update(dto);
            ++count;
        }

        return count;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        baseRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll(Iterable<Long> ids) {
        List<DO> list = new ArrayList<>();
        Class<DO> clazz = getGenericClassForDO();
        for (Long id : ids) {
            DO t = ReferenceUtils.newAndSet(clazz, "id", id);
            list.add(t);
        }
        baseRepository.deleteAll(list);

    }

    @Transactional
    @Override
    public void deleteAllEntity(Iterable<DTO> entities) {
        List<DO> list = BeanUtils.newAndCopyPropertiesForList(getGenericClassForDO(), entities);
        baseRepository.deleteAll(list);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    protected <T> Class<T> getGenericClass(int idx) {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[idx];
        return (Class<T>) type;
    }

    @SuppressWarnings("unchecked")
    protected <T> Class<T> getGenericClassForDTO() {
        return getGenericClass(0);
    }

    @SuppressWarnings("unchecked")
    protected <T> Class<T> getGenericClassForDO() {
        return getGenericClass(1);
    }
}
