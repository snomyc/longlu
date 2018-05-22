package com.longlu.service;

import java.util.List;
import java.util.Map;
import com.longlu.pojo.YouzanShopConfig;
import com.youzan.open.sdk.client.core.YZClient;

public interface YouzanShopConfigService {
	public int deleteByPrimaryKey(Long id);

	public int insert(YouzanShopConfig record);

    public int insertSelective(YouzanShopConfig record);

    public YouzanShopConfig selectByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(YouzanShopConfig record);

    public int updateByPrimaryKey(YouzanShopConfig record);
    
    public boolean saveOrUpdate(YouzanShopConfig record);
    
    public List<Map<String,Object>> selectBySelective(Map<String, Object> paramsMap);
    
    public List<YouzanShopConfig> findAll();
    
    /**
     * 查询所有启用的有赞店铺
     * @return
     */
    public List<YouzanShopConfig> findAllUseShop();
    
    public YouzanShopConfig selectByShopName(String shopName);
    
    public YZClient getYzClient(Long shopId);

}
