package cn.com.fintheircing.admin.usermanag.service.Impl;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.proxy.dao.repository.IAdminClientInfoRepository;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.dao.mapper.IBankMapper;
import cn.com.fintheircing.admin.usermanag.dao.mapper.IUserMapper;

import cn.com.fintheircing.admin.usermanag.dao.repsitory.IBankIdRepository;
import cn.com.fintheircing.admin.usermanag.entity.BankCard;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import cn.com.fintheircing.admin.usermanag.model.BankCardModel;
import cn.com.fintheircing.admin.usermanag.service.IUserService;

import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    IUserMapper usermapper;
//    @Autowired
//    fundsAskMapper askMapper;
    @Resource
    IAdminClientInfoRepository adminClientInfoRepository;
    @Resource
    IBankIdRepository iBankIdRepository;
    @Resource
    IBankMapper iBankMapper;

//    @Autowired
//    IContactInfoRepository contactInfoRepository;
    @Override
    public List<AdminClientInfoModel> findAllUserInfo(AdminClientInfoModel Model
    ) throws UserServiceException {
        List<AdminClientInfo> users=null;
        try {

            List<AdminClientInfoModel> all = usermapper.findAll(Model);

                return all;

        }catch (Exception e){
            throw  new UserServiceException(e.getMessage());
        }
    }


//    public AdminClientInfoModel findOneById(String id)throws UserServiceException {
//        try {
//
//            UserInfo oneById = userInfoRepository.findOneByIdAndStatus(id,"A");
//            if (StringUtils.isEmpty(id)){
//                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
//            }else if (oneById==null){
//                throw new UserServiceException(ResultCode.USER_EXITS);
//            }else {
//                return EntityToModel.coverUserInfo(oneById);
//            }
//        }catch (Exception e){
//            throw new UserServiceException(e.getMessage());
//        }



    @Override
    public boolean changeStatus(String id) throws UserServiceException {
        try {
            if (StringUtils.isEmpty(id)){
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);}
            else {
                int i = usermapper.updateStatus(id);
                if (i>0){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (Exception e){
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public List<AdminClientInfoModel> findByOption(AdminClientInfoModel model)throws UserServiceException {

        try {
            List<AdminClientInfoModel> all = usermapper.findAllUseless(model);
            if (all==null||all.size()==0){
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            }else {

                return all;
            }
        }catch (Exception e){
            throw  new UserServiceException(e.getMessage());
        }

    }

    @Override
    public int changeProxyNum(AdminClientInfoModel model)throws UserServiceException {
        try{
            if (model==null){
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            }else {
            return usermapper.changeProxyNum(model);
            }
        }catch (Exception e){
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public boolean returnStatus(String id) throws UserServiceException{

        try {
            if (StringUtils.isEmpty(id)){
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);}
            else {
                int i = usermapper.restoreStatus(id);
                if (i>0){
                    return true;
                }else {
                    return false;
                }

            }
        }catch (Exception e){
            throw new UserServiceException(e.getMessage());
        }

    }

//    @Override
//    public List<AskMoneyInfoModel> insideMoney(AskMoneyInfoModel model)throws UserServiceException {
//        try {
//                List<AskMoneyInfoModel> users=null;
//                if (model==null){
//                    List<AskMoneyInfo> all = askMoneyInfoRepository.findAll();
//                    all.forEach(user1->{
//                        UserClientInfo oneById = userClientInfoRepository.findOneByUserId(user1.getUserId());
//                        users.add(EntityToModel.coverAskMoneyInfo(user1,oneById));
//                    });
//                }else {
//                List<AskMoneyInfo> alluser = askMapper.findAlluser(model);
//                if (alluser==null||alluser.size()==0){throw new UserServiceException(ResultCode.USER_EXITS);}else {
//                    alluser.forEach(user2->{
//                        UserClientInfo oneById = userClientInfoRepository.findOneByUserId(user2.getUserId());
//                        users.add(EntityToModel.coverAskMoneyInfo(user2,oneById));
//                    });
//                }
//            }
//                return users;
//        }catch (Exception e){
//                throw new UserServiceException(e.getMessage());
//        }
//    }

    @Override
    public List<BankCardModel> findAllBankCard(BankCardModel model) throws UserServiceException {
        try {

                List<BankCardModel> alluser = iBankMapper.findAllBankCard(model);
                if (alluser==null||alluser.size()==0){throw new UserServiceException(ResultCode.USER_EXITS);}else {
                    return alluser;
            }

        }catch (Exception e){
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public boolean updatebankCard(String id) throws UserServiceException {
        try {
            if (StringUtils.isEmpty(id)){
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);}
            else {
                BankCard oneByUuid = iBankIdRepository.findOneByUuid(id);
                if(oneByUuid==null||"0".equals(oneByUuid.getStatus())){
                    throw new UserServiceException(ResultCode.USER_EXITS);
                } else {
                    oneByUuid.setStatus("1");
                    BankCard save = iBankIdRepository.save(oneByUuid);
                    if (StringUtils.isEmpty(save)) {
                        return false;
                    }
                    return true;
                }

            }
        }catch (Exception e){
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public List<AdminClientInfoModel> findAllDetails(String id) {
        List<AdminClientInfoModel> allDetails = usermapper.findAllDetails(id);
        allDetails.forEach(a->{
         if (a.getAccountStatus().equalsIgnoreCase("0")){
             a.setAccountStatus("正常");
         }
         if (a.getAccountStatus().equalsIgnoreCase("1")){
             a.setAccountStatus("冻结");
         }
         if (StringUtils.isEmpty(a.getEmplooyeeId())){
             a.setEmplooyeeId("无下级员工");
         }
        });
        return allDetails;
    }
    /*未实现*/
//    @Override
//    public AskMoneyInfoModel changeMoneyBySelf(AskMoneyInfoModel model) {
//        return null;
//    }

//    @Override
//    public AskMoneyInfoModel insideMoneyHandel(AskMoneyInfoModel model) {
//        AskMoneyInfo askMoneyInfo = ModelToEntity.coverAskMoneyInfo2entity(model);
//        AskMoneyInfo save = askMoneyInfoRepository.save(askMoneyInfo);
//        UserClientInfo oneByPhone = userClientInfoRepository.findOneByUserId(save.getUserId());
//        AskMoneyInfoModel askMoneyInfoModel = EntityToModel.coverAskMoneyInfo(save, oneByPhone);
//        return askMoneyInfoModel;
//    }

//    @Override
//    public List<ContactInfoModel> contactRecode(String goodsType) {
//        if (StringUtils.isEmpty(goodsType))
//        {
//           return EntityToModel.coverContactInfo(contactInfoRepository.findAll());
//        }
//        List<ContactInfo> all = contactInfoRepository.findAllByGoodsType(goodsType);
//        return EntityToModel.coverContactInfo(all);
//    }

//    private List<AdminClientInfoModel> convertQuestionList(List<AdminClientInfo> userInfos){
//        List<AdminClientInfoModel> models = new ArrayList<>();
//        List<UserClientInfo> clientInfos=null;
//        userInfos.forEach(userInfo -> {
//            //根据手机号查询UserClientInfo
//            UserClientInfo oneByUuid = userClientInfoRepository.findOneByUserId(userInfo.getUserId());
//            clientInfos.add(oneByUuid);
//            EntityToModel.coverUserClientInfo(oneByUuid);
//        });
////        models.add(EntityToModel.coverUserInfo(userInfos,oneByUuid));
//        return models;
//    }

//    private AdminClientInfoModel convertQuestionList(AdminClientInfo userInfo){
//        AdminClientInfoModel model =new AdminClientInfoModel();
//            //根据手机号查询UserClientInfo
//        UserClientInfo oneByPhone = userClientInfoRepository.findOneByUserId(userInfo.getUuid());
//         return EntityToModel.coverAdminClientInfo(userInfo,oneByPhone);
//


}