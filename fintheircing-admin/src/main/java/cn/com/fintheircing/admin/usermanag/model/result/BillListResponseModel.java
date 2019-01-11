package cn.com.fintheircing.admin.usermanag.model.result;

import java.util.List;

/**
 * 返回结果集
 *
 * @author keriezhang
 * @date 2016/10/31
 */
public class BillListResponseModel<T> {
    private String result;
    private String failReason;
    private List<T> transList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public List<T> getTransList() {
        return transList;
    }

    public void setTransList(List<T> transList) {
        this.transList = transList;
    }
}
