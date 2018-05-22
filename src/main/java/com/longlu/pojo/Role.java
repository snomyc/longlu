package com.longlu.pojo;

public class Role {
    private Long id;

    private String authoritysid; //权限ID集合，用逗号隔开如(1,2,3)

    private String rolename; //角色名称

    private String bz; //备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthoritysid() {
        return authoritysid;
    }

    public void setAuthoritysid(String authoritysid) {
        this.authoritysid = authoritysid == null ? null : authoritysid.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }
}