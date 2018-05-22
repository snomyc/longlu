package com.longlu.shiro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.longlu.service.UsersService;

public class MonitorRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(MonitorRealm.class);
	
	@Autowired
	private UsersService usersService;

	// public MonitorRealm() {
	// //设置Realm名称
	// setName("monitorRealm");
	// //设置加密方式
	// setCredentialsMatcher(new
	// HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
	// //设置shiro授权缓存.即ehcache-shiro.xml
	// //setAuthorizationCacheName("shiroCache");
	// }

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		/* 这里编写授权代码 */
		Set<String> roleNames = new HashSet<String>();
		Set<String> permissions = new HashSet<String>();
		// 授权角色
		Session session = SecurityUtils.getSubject().getSession();
		Map<String,Object> userMap = (Map<String, Object>) session.getAttribute("userMap");
		roleNames.add(userMap.get("rolename").toString());
		// 授权权限
//		permissions.add("home");
//		permissions.add("main");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
		info.setStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {

		/* 这里编写认证代码 */
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		String password = new String((char[]) token.getPassword());
		
		//调用service
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("password", password);
		List<Map<String,Object>> userList = usersService.selectUsersBySelective(params);

		if(CollectionUtils.isNotEmpty(userList)) {
			Map<String,Object> userMap = userList.get(0);
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
					userMap.get("username"), userMap.get("password"), this.getName());
			this.setSession("userMap", userMap);
			return authcInfo;
		}
			
		return null;
	}

	/**
	 * 更新用户授权信息缓存
	 * 
	 * @param principal
	 *            用户唯一身份标识，相当于userId
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			logger.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	/**
	 * @author acer
	 *
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = 1L;
		public Integer id;
		public String loginName;
		public String name;

		public ShiroUser(Integer id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
