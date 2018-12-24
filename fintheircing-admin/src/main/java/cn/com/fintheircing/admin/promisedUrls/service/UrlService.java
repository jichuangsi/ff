package cn.com.fintheircing.admin.promisedUrls.service;

import cn.com.fintheircing.admin.common.constant.PositionCode;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.promisedUrls.dao.mapper.IPermisedUrlsMapper;
import cn.com.fintheircing.admin.promisedUrls.dao.mapper.IUrlsRelationsMapper;
import cn.com.fintheircing.admin.promisedUrls.dao.repository.IPermisedUrlsRepository;
import cn.com.fintheircing.admin.promisedUrls.dao.repository.IUrlsRelationsRepository;
import cn.com.fintheircing.admin.promisedUrls.entity.UrlsPermised;
import cn.com.fintheircing.admin.promisedUrls.entity.UrlsRelations;
import cn.com.fintheircing.admin.promisedUrls.exception.UrlException;
import cn.com.fintheircing.admin.promisedUrls.model.JsonMap;
import cn.com.fintheircing.admin.promisedUrls.model.TranferUrlModel;
import cn.com.fintheircing.admin.promisedUrls.model.UrlsModel;
import cn.com.fintheircing.admin.promisedUrls.utils.MappingEntity2ModelConverter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UrlService {

    @Resource
    private IPermisedUrlsRepository permisedRepository;
    @Resource
    private IPermisedUrlsMapper permisedUrlsMapper;
    @Resource
    private IUrlsRelationsRepository urlsRelationsRepository;
    @Resource
    private IUrlsRelationsMapper urlsRelationsMapper;
    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @Value("${custom.urlKey.manageKey}")
    private String manageKey;
    @Value("${custom.urlKey.proxyOneKey}")
    private String proxyOneKey;
    @Value("${custom.urlKey.proxyTwoKey}")
    private String proxyTwoKey;
    @Value("${custom.urlKey.employKey}")
    private String employKey;
    @Value("${custom.urlKey.userKey}")
    private String userKey;
    @Value("${custom.urlKey.searchKey}")
    private String searchKey;
    @Value("${custom.urlKey.positionKey}")
    private String positionKey;
    @Value("${custom.urlKey.roleKey}")
    private String roleKey;


    Logger logger = LoggerFactory.getLogger(getClass());

    //保存url路径
    public void savePermisedUrl(UserTokenInfo userInfo,UrlsModel model){
        UrlsPermised newUrl = new UrlsPermised();
        newUrl.setUrl(model.getUrl());
        newUrl.setUrlName(model.getUrlName());
        newUrl.setCreatedTime(new Date());
        newUrl.setCreatorId(userInfo.getUuid());
        newUrl.setCreatorName(userInfo.getUserName());
        newUrl.setUpdatedTime(new Date());
        newUrl.setUpdateUserId(userInfo.getUuid());
        newUrl.setUpdateUserName(userInfo.getUserName());
        permisedRepository.save(newUrl);
    }


    //修改url路径
    public void updatePermisedUrl(UserTokenInfo userInfo,UrlsModel model) throws UrlException{
        UrlsPermised updateUrl =  permisedRepository.findOneByUuidAndDeleteFlag(model.getId(),"0");
        if (updateUrl!=null) {
            if (StringUtils.isEmpty(model.getUrl())) {
                updateUrl.setUrl(model.getUrl());
            }
            if (StringUtils.isEmpty(model.getUrlName())) {
                updateUrl.setUrlName(model.getUrlName());
            }
            updateUrl.setUpdateUserName(userInfo.getUserName());
            updateUrl.setUpdateUserId(userInfo.getUuid());
            updateUrl.setUpdatedTime(new Date());
            permisedRepository.save(updateUrl);
        }else {
            throw new UrlException(ResultCode.SELECT_NULL_MSG);
        }
    }


    //删除url路径
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUrls(UserTokenInfo userInfo,IdModel ids){
        /*List<UrlsPermised> permisedUrls = permisedRepository.findAllById(ids.getIds());
        permisedUrls.forEach(url->{
            url.setUpdateUserName(userInfo.getUserName());
            url.setUpdateUserId(userInfo.getUuid());
            url.setUpdatedTime(new Date());
            url.setDeleteFlag("1");
            permisedRepository.save(url);
        });*/
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",userInfo.getUuid());
        params.put("name",userInfo.getUserName());
        params.put("time",new Date());
        params.put("list",ids.getIds());
        return permisedUrlsMapper.updateDeleteFlag(params)>0;
    }


    //保存角色和url的关联
    public void saveRelationUrls(UserTokenInfo userInfo,UrlsModel model){
        UrlsRelations relations = new UrlsRelations();
        relations.setCreatedTime(new Date());
        relations.setCreatorId(userInfo.getUuid());
        relations.setCreatorName(userInfo.getUserName());
        relations.setUpdatedTime(new Date());
        relations.setUpdateUserId(userInfo.getUuid());
        relations.setUpdateUserName(userInfo.getUserName());

        relations.setPosition(model.getPosition());
        relations.setRole(model.getRole());
        relations.setUrlId(model.getId());
        urlsRelationsRepository.save(relations);
    }


    //获取所有的urls
    public List<UrlsModel> getAllUrls(){
        List<UrlsPermised> permisedUrls = permisedRepository.findByDeleteFlag("0");
        return changeForPermisedUrls(permisedUrls);
    }

    //获取所有的url关联
    public List<UrlsModel> getAllRelations(){
        List<UrlsModel> urlsModels = urlsRelationsMapper.selectAllRelations();
        return urlsModels;
    }

    //删除指定的url关联
    public Boolean deleteRelations(UserTokenInfo userInfo,IdModel ids){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",userInfo.getUuid());
        params.put("name",userInfo.getUserName());
        params.put("time",new Date());
        params.put("list",ids.getIds());
        return urlsRelationsMapper.updateDeleteFlag(params)>0;
    }


    public void selectAllUrls(){
        List<UrlsModel> urlsModels = urlsRelationsMapper.selectAllRelations();
        List<UrlsModel> manage = new ArrayList<UrlsModel>();
        List<UrlsModel> proxyOne = new ArrayList<UrlsModel>();
        List<UrlsModel> proxyTwo = new ArrayList<UrlsModel>();
        List<UrlsModel> employ = new ArrayList<UrlsModel>();
        List<UrlsModel> user = new ArrayList<UrlsModel>();
        urlsModels.forEach(model->{
            if (PositionCode.POSITION_MANAGE.getIndex().equals(model.getPosition())
                    &&"1".equals(model.getRole())){
                user.add(model);
            }else if (PositionCode.POSITION_PROXY_ONE.getIndex().equals(model.getPosition())
                    &&"0".equals(model.getRole())){
                proxyOne.add(model);
            }else if (PositionCode.POSITION_PROXY_TWO.getIndex().equals(model.getPosition())
                    &&"0".equals(model.getRole())){
                proxyTwo.add(model);
            }else if (PositionCode.POSITION_EMP.getIndex().equals(model.getPosition())
                    &&"0".equals(model.getRole())){
                employ.add(model);
            }else if (PositionCode.POSITION_MANAGE.getIndex().equals(model.getPosition())
                    &&"0".equals(model.getRole())){
                manage.add(model);
            }
        });
        JsonMap jsonMap = new JsonMap();
        jsonMap.getMap().put(manageKey,manage);
        jsonMap.getMap().put(proxyOneKey,proxyOne);
        jsonMap.getMap().put(proxyTwoKey,proxyTwo);
        jsonMap.getMap().put(employKey,employ);
        jsonMap.getMap().put(userKey,user);
        String json = JSON.toJSONString(jsonMap);
        logger.debug(json);
/*        map = JSONObject.parseObject(json,Map.class);*/
        redisTemplate.opsForValue().set(searchKey,json);
    }



    public Boolean checkUrl(TranferUrlModel model) {
        String json = redisTemplate.opsForValue().get(searchKey);
        JsonMap jsonMap = JSONObject.parseObject(json, JsonMap.class);
        Map<String, List<UrlsModel>> map = jsonMap.getMap();
        String key = positionKey + model.getPosition() +"_"+ roleKey + model.getRole();
        List<UrlsModel> models = map.get(key);
        Boolean flag = false;
        if (models != null && models.size() > 0){
            for (UrlsModel m : models) {
                if (m.getUrl().equals(model.getUrl())) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    private List<UrlsModel> changeForPermisedUrls(List<UrlsPermised> permisedUrls){
        List<UrlsModel> urlsModels = new ArrayList<UrlsModel>();
        if(permisedUrls!=null&&permisedUrls.size()>0){
            permisedUrls.forEach(p->{
                urlsModels.add(MappingEntity2ModelConverter.CONVERTERFORPERMISEDURLS(p));
            });
        }
        return urlsModels;
    }
}
