package cn.tpson.kulu.common.jpa.service.remote;

import cn.tpson.kulu.common.jpa.support.Range;
import cn.tpson.kulu.common.jpa.service.local.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by Zhangka in 2018/07/27
 */
public class RemoteBaseServiceImpl<DTO> implements RemoteBaseService<DTO> {
    private static final Logger log = LoggerFactory.getLogger(RemoteBaseServiceImpl.class);
    public static final String ERROR_MSG_QUERY_ERROR = "查找时出错";
    public static final String ERROR_MSG_UPDATE_ERROR = "更新时出错";
    public static final String ERROR_MSG_DELETE_ERROR = "删除时出错";
    public static final String ERROR_MSG_SAVE_ERROR = "保存时出错";

    @Autowired
    private BaseService<DTO> baseService;

    @Override
    public RemoteResult<DTO> findById(Long id) {
        try {
            DTO dto = baseService.findById(id);
            return RemoteResult.successResult(dto);
        } catch (Exception e) {
            log.error("BaseRemoteService->findById", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<List<DTO>> findAllById(List<Long> ids) {
        try {
            List<DTO> list = baseService.findAllById(ids);
            return RemoteResult.successResult(list);
        } catch (Exception e) {
            log.error("BaseRemoteService->findAllById", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<List<DTO>> findAll() {
        try {
            List<DTO> list = baseService.findAll();
            return RemoteResult.successResult(list);
        } catch (Exception e) {
            log.error("BaseRemoteService->findAll", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<Long> count() {
        try {
            long cnt = baseService.count();
            return RemoteResult.successResult(cnt);
        } catch (Exception e) {
            log.error("BaseRemoteService->count", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public <QUERY extends DTO> RemoteResult<List<DTO>> findByExample(QUERY query) {
        try {
            List<DTO> list = baseService.findByExample(query);
            return RemoteResult.successResult(list);
        } catch (Exception e) {
            log.error("BaseRemoteService->findByExample", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public <QUERY extends DTO> RemoteResult<Long> countByExample(QUERY query) {
        try {
            long cnt = baseService.countByExample(query);
            return RemoteResult.successResult(cnt);
        } catch (Exception e) {
            log.error("BaseRemoteService->countByExample", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public <QUERY extends DTO> RemoteResult<Long> countByExample(QUERY query, Range... ranges) {
        try {
            long cnt = baseService.countByExample(query, ranges);
            return RemoteResult.successResult(cnt);
        } catch (Exception e) {
            log.error("BaseRemoteService->countByExample", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public <QUERY extends DTO> RemoteResult<Page<DTO>> pageByExample(QUERY query, Sort sort, Range... ranges) {
        try {
            Page<DTO> page = baseService.pageByExample(query, sort, ranges);
            return RemoteResult.successResult(page);
        } catch (Exception e) {
            log.error("BaseRemoteService->pageByExample", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public <QUERY extends DTO> RemoteResult<Page<DTO>> pageByExample(QUERY query) {
        try {
            Page<DTO> page = baseService.pageByExample(query);
            return RemoteResult.successResult(page);
        } catch (Exception e) {
            log.error("BaseRemoteService->pageByExample", e);
            return RemoteResult.failResult(ERROR_MSG_QUERY_ERROR);
        }
    }

    @Override
    public RemoteResult<Long> save(DTO dto) {
        try {
            long id = baseService.save(dto);
            return RemoteResult.successResult(id);
        } catch (Exception e) {
            log.error("BaseRemoteService->save", e);
            return RemoteResult.failResult(ERROR_MSG_SAVE_ERROR);
        }


    }

    @Override
    public RemoteResult<Long> update(DTO dto) {
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
            baseService.deleteById(id);
            return RemoteResult.successResult(null);
        } catch (Exception e) {
            log.error("BaseRemoteService->deleteById", e);
            return RemoteResult.failResult(ERROR_MSG_DELETE_ERROR);
        }

    }

    @Override
    public RemoteResult<Void> deleteAll(Iterable<Long> ids) {
        try {
            baseService.deleteAll(ids);
            return RemoteResult.successResult(null);
        } catch (Exception e) {
            log.error("BaseRemoteService->deleteAll", e);
            return RemoteResult.failResult(ERROR_MSG_DELETE_ERROR);
        }
    }
}
