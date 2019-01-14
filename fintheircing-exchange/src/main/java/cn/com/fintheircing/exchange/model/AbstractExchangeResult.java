//交易结果
package cn.com.fintheircing.exchange.model;

public abstract class AbstractExchangeResult {

	protected boolean sucess;
	protected String msg;

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
