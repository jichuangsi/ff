package cn.com.fintheircing.admin.promisedUrls.utils;

import cn.com.fintheircing.admin.promisedUrls.entity.UrlsPermised;
import cn.com.fintheircing.admin.promisedUrls.model.UrlsModel;

public final class MappingEntity2ModelConverter {

    private MappingEntity2ModelConverter(){}

    public final static UrlsModel CONVERTERFORPERMISEDURLS(UrlsPermised url){
        UrlsModel urlsModel = new UrlsModel();
        urlsModel.setId(url.getUuid());
        urlsModel.setUrl(url.getUrl());
        urlsModel.setUrlName(url.getUrlName());

        return urlsModel;
    }
}
