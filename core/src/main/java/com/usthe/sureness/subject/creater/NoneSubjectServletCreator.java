package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.NoneSubject;
import com.usthe.sureness.util.SurenessCommonUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 无认证信息的subject creator
 * 所有请求都能创建出一个NoneSubject
 * only support HttpServletRequest
 * @author tomsun28
 * @date 15:55 2020-02-28
 */
public class NoneSubjectServletCreator implements SubjectCreate {
    @Override
    public boolean canSupportSubject(Object context) {
        return context instanceof HttpServletRequest;
    }

    @Override
    public Subject createSubject(Object context) {
        String remoteHost = ((HttpServletRequest) context).getRemoteHost();
        String requestUri = ((HttpServletRequest) context).getRequestURI();
        String requestType = ((HttpServletRequest) context).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        String userAgent = SurenessCommonUtil.findUserAgent((HttpServletRequest) context);
        return NoneSubject.builder().setRemoteHost(remoteHost)
                .setTargetUri(targetUri)
                .setUserAgent(userAgent).build();
    }
}
