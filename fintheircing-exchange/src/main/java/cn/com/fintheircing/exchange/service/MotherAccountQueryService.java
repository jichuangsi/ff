/**
 * 母账户查询服务，查询用于交易的母账户信息
 */
package cn.com.fintheircing.exchange.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.com.fintheircing.exchange.model.MotherAccount;

/**
 * 
 *
 */
@Service
public class MotherAccountQueryService {

	/**
	 * 获取所有启用的母账户
	 *
	 */
	public List<MotherAccount> getAllAviable() {
		// todo 从数据库中获取
		List<MotherAccount> list = new ArrayList<>();
		MotherAccount account = new MotherAccount("56", "121.43.74.163", (short) 7708, "6.68", (short) 0, 8, "51303703",
				"51303703", "001985", "", "0258045230", "A407057371");
		list.add(account);

		return list;
	}

}
