/**
* 以下代码片段为模拟单点登录代码
* 
*/

	@RequestMapping("/sso")
	@Auth(verifyLogin = false, verifyURL = false)
	public void sso(String sso,String sessionid, HttpServletRequest request,
			HttpServletResponse response) {
		//sso标识字段，1标识单点登录
		if("1".equals(sso)){
			if (sessionid != null && !"".equals(sessionid)) {
				JedisPool pool = (JedisPool)SpringContextUtil.getBean("jedisPool");
				Jedis jedis = pool.getResource();
				//根据sessionid获取byteuser数组
				byte[] byteuser = jedis.get(sessionid.getBytes());
				pool.returnResource(jedis); 
				//实例化BaseUser 对象，可取BaseUser里的所有属性值
				BaseUser baseuser = (BaseUser)SerializeUtil.unserialize(byteuser);
				Map<String, Object> result = new HashMap<String, Object>();
				result.put(SUCCESS, true);
				result.put(MSG, baseuser);
				HtmlUtil.writerJson(response, result);
			} else {
				sendFailureMessage(response, "会话已结束，请重新登录...");
				return;
			}
		}else{
			sendFailureMessage(response, "该链接非单点登录...");
		}
	}
/**
* 产生并传输json数据
*/
	public static void writerJson(HttpServletResponse response,Object object){
			try {
				response.setContentType("application/json");
				writer(response,JSONUtil.toJSONString(object));
				
			} catch (JSONException e) {
				log.error(e);
			}
	}