package com.longlu.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.longlu.dao.YouzanShopConfigMapper;
import com.longlu.pojo.YouzanShopConfig;
import com.longlu.service.YouzanShopConfigService;
import com.youzan.open.sdk.client.auth.Sign;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
@Service
public class YouzanShopConfigServiceImpl implements YouzanShopConfigService{

	@Autowired
	private YouzanShopConfigMapper youzanShopConfigMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return youzanShopConfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(YouzanShopConfig record) {
		// TODO Auto-generated method stub
		return youzanShopConfigMapper.insert(record);
	}

	@Override
	public int insertSelective(YouzanShopConfig record) {
		// TODO Auto-generated method stub
		return youzanShopConfigMapper.insertSelective(record);
	}

	@Override
	public YouzanShopConfig selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return youzanShopConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YouzanShopConfig record) {
		// TODO Auto-generated method stub
		return youzanShopConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YouzanShopConfig record) {
		// TODO Auto-generated method stub
		return youzanShopConfigMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map<String, Object>> selectBySelective(
			Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return youzanShopConfigMapper.selectBySelective(paramsMap);
	}

	@Override
	public boolean saveOrUpdate(YouzanShopConfig record) {
		int count = 0;
		try{
			if(record.getId() != null) {
				//更新用户信息
				count = youzanShopConfigMapper.updateByPrimaryKeySelective(record);
			}else {
				//新增用户信息
				count = youzanShopConfigMapper.insertSelective(record);
			}
			return count > 0 ? true : false;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<YouzanShopConfig> findAll() {
		return youzanShopConfigMapper.findAll();
	}
	
	@Override
	public List<YouzanShopConfig> findAllUseShop() {
		return youzanShopConfigMapper.findAllUseShop();
	}

	@Override
	public YouzanShopConfig selectByShopName(String shopName) {
		return youzanShopConfigMapper.selectByShopName(shopName);
	}

	@Override
	public YZClient getYzClient(Long shopId) {
		//通过shopId 获取店铺配置信息
		YouzanShopConfig youzanShopConfig = youzanShopConfigMapper.selectByPrimaryKey(Long.valueOf(shopId));
		YZClient client = null;
		if(StringUtils.isNotBlank(youzanShopConfig.getKdtId())) {
			client = new DefaultYZClient(new Token(youzanShopConfig.getToken()));
		}else {
			client = new DefaultYZClient(new Sign(youzanShopConfig.getClientId(), youzanShopConfig.getClientSecret()));
		}
		return client;
	}

}
