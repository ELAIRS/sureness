package com.usthe.sureness.sample.tom.service.impl;

import com.usthe.sureness.sample.tom.dao.AuthResourceDao;
import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import com.usthe.sureness.sample.tom.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 13:09 2019-08-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private AuthResourceDao authResourceDao;

    @Override
    public boolean addResource(AuthResourceDO authResource) {
        if (isResourceExist(authResource)) {
            return false;
        } else {
            authResourceDao.saveAndFlush(authResource);
            return true;
        }
    }

    @Override
    public boolean isResourceExist(AuthResourceDO authResource) {
        AuthResourceDO resource = AuthResourceDO.builder()
                .uri(authResource.getUri())
                .method(authResource.getMethod())
                .build();
        Example<AuthResourceDO> example = Example.of(resource);
        return authResourceDao.exists(example);
    }

    @Override
    public boolean updateResource(AuthResourceDO authResource) {
        if (authResourceDao.existsById(authResource.getId())) {
            authResourceDao.saveAndFlush(authResource);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteResource(Long resourceId) {
        if (authResourceDao.existsById(resourceId)) {
            authResourceDao.deleteById(resourceId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<List<AuthResourceDO>> getAllResource() {
        List<AuthResourceDO> resourceList = authResourceDao.findAll();
        return Optional.of(resourceList);
    }

    @Override
    public Page<AuthResourceDO> getPageResource(Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        return authResourceDao.findAll(pageRequest);
    }
}
