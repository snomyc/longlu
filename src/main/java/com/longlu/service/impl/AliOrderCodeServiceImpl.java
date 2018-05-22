package com.longlu.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.longlu.dao.AliOrderCodeMapper;
import com.longlu.pojo.AliOrderCode;
import com.longlu.service.AliOrderCodeService;
import com.longlu.util.pagination.Pagination;
@Service
public class AliOrderCodeServiceImpl implements AliOrderCodeService{

	@Autowired
	private AliOrderCodeMapper aliOrderCodeMapper;
	
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return aliOrderCodeMapper.deleteByPrimaryKey(id);
	}

	public int insert(AliOrderCode record) {
		// TODO Auto-generated method stub
		return aliOrderCodeMapper.insert(record);
	}

	public int insertSelective(AliOrderCode record) {
		// TODO Auto-generated method stub
		return aliOrderCodeMapper.insertSelective(record);
	}

	public AliOrderCode selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return aliOrderCodeMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(AliOrderCode record) {
		// TODO Auto-generated method stub
		return aliOrderCodeMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(AliOrderCode record) {
		// TODO Auto-generated method stub
		return aliOrderCodeMapper.updateByPrimaryKey(record);
	}

	public List<Map<String, Object>> selectBySelective(
			Map<String, Object> paramsMap) {
		return aliOrderCodeMapper.selectBySelective(paramsMap);
	}

	public boolean saveOrUpdate(AliOrderCode record){
		int count = 0;
		try{
			if(record.getId() != null) {
				//更新用户信息
				count = aliOrderCodeMapper.updateByPrimaryKeySelective(record);
			}else {
				//新增用户信息
				count = aliOrderCodeMapper.insert(record);
			}
			return count > 0 ? true : false;
		}catch (Exception e) {
			return false;
		}
	}

	public void addGoodsDetailCode(Pagination pagination) {
//		GoodsDetail[]  detail = YouZanServerUtils.getSaleGoodsList(pagination, null);
//		if(detail != null && detail.length >0) {
//			//业务处理
//			for (GoodsDetail goodsDetail : detail) {
//				if(goodsDetail.getSkus() !=null && goodsDetail.getSkus().length >0) {
//					for (GoodsSku goodsSku :goodsDetail.getSkus()) {
//						AliOrderCode record = new AliOrderCode();
//						record.setOuterCode(goodsSku.getOuterId());
//						record.setAlibabaUrl(goodsDetail.getDetailUrl());
//						record.setGoodsName(goodsDetail.getTitle());
//						this.saveOrUpdate(record);
//					}
//				}else {
//					AliOrderCode record = new AliOrderCode();
//					record.setOuterCode(goodsDetail.getOuterId());
//					record.setAlibabaUrl(goodsDetail.getDetailUrl());
//					record.setGoodsName(goodsDetail.getTitle());
//					this.saveOrUpdate(record);
//				}
//			}
//			//继续查找下一页数据
//			pagination.setPage(pagination.getPage()+1);
//			addGoodsDetailCode(pagination);
//		}
	}

	public AliOrderCode selectByOuterCode(String outerCode) {
		return aliOrderCodeMapper.selectByOuterCode(outerCode);
	}

}
