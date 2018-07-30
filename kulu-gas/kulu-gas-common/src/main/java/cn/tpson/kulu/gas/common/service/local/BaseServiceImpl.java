package cn.tpson.kulu.gas.common.service.local;

import cn.tpson.kulu.gas.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.gas.common.util.BeanUtils;
import cn.tpson.kulu.gas.common.util.ReferenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangka in 2018/07/30
 */
public class BaseServiceImpl<DTO, DO> implements BaseService<DTO> {
    @Autowired
    private BaseRepository<DO, Long> baseRepository;

    @Override
    public DTO findById(Long id) {
        DO entity = baseRepository.getOne(id);
        Class<DTO> dto = getGenericClassForDTO();
        return BeanUtils.copyProperties(dto, entity);
    }

    @Override
    public List<DTO> findAllById(List<Long> ids) {
        List<DO> list = baseRepository.findAllById(ids);
        Class<DTO> dto = getGenericClassForDTO();
        return BeanUtils.copyPropertiesForList(dto, list);
    }

    @Override
    public List<DTO> findAll() {
        List<DO> list = baseRepository.findAll();
        Class<DTO> dto = getGenericClassForDTO();
        return BeanUtils.copyPropertiesForList(dto, list);
    }

    @Override
    public long count() {
        long cnt = baseRepository.count();
        return cnt;
    }

    @Override
    public <QUERY extends DTO> List<DTO> findByExample(QUERY query) {
        DO t = BeanUtils.copyProperties(getGenericClassForDO(), query);
        List<DO> list = baseRepository.findAll(Example.of(t));
        Class<DTO> dto = getGenericClassForDTO();
        return BeanUtils.copyPropertiesForList(dto, list);
    }

    @Override
    public <QUERY extends DTO> long countByExample(QUERY query) {
        DO t = BeanUtils.copyProperties(getGenericClassForDO(), query);
        long cnt = baseRepository.count(Example.of(t));
        return cnt;
    }

    @Override
    public <QUERY extends DTO> Page<DTO> pageByExample(QUERY query) {
        Integer pageNumber = (Integer) ReferenceUtils.get(query, "pageNumber");
        Integer pageSize = (Integer) ReferenceUtils.get(query, "pageSize");

        DO t = BeanUtils.copyProperties(getGenericClassForDO(), query);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<DO> page = baseRepository.findAll(Example.of(t), pageable);

        Class<DTO> dto = getGenericClassForDTO();
        List<DTO> list = BeanUtils.copyPropertiesForList(dto, page.getContent());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Transactional
    @Override
    public long save(DTO dto) {
        DO t = BeanUtils.copyProperties(getGenericClassForDO(), dto);
        t = baseRepository.save(t);
        return (long)ReferenceUtils.get(t, "id");
    }

    @Transactional
    @Override
    public long update(DTO dto) {
        return save(dto);
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
        baseRepository.deleteInBatch(list);

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
