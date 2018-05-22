package com.longlu.pojo;

/**
 * @author yangcan
 * 类描述:有赞-阿里物流转换类
 * 创建时间:2017-10-24 下午4:39:39

 CREATE TABLE `express_manager` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` varchar(50) NOT NULL unique,
  `company` varchar(50) NOT NULL unique,
  `ali_company` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
public class ExpressManager {
    private Long id;

    private String num;

    private String company;

    private String aliCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAliCompany() {
        return aliCompany;
    }

    public void setAliCompany(String aliCompany) {
        this.aliCompany = aliCompany;
    }
}