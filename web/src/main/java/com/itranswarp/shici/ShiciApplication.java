package com.itranswarp.shici;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.itranswarp.warpdb.WarpDb;
import com.itranswarp.wxapi.WeixinClient;
import com.itranswarp.wxapi.token.cache.SimpleAccessTokenCache;

/**
 * Application entry.
 * 
 * @author michael
 */
@SpringBootApplication
public class ShiciApplication {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Value("${wxapi.app.id}")
	String wxapiAppId;

	@Value("${wxapi.app.secret}")
	String wxapiAppSecret;

	@Value("${wxapi.app.token}")
	String wxapiAppToken;

	@Value("${wxapi.app.aeskey}")
	String wxapiAppAesKey;

	@Bean
	public WarpDb createWarpDb() {
		WarpDb db = new WarpDb();
		db.setJdbcTemplate(jdbcTemplate);
		db.setBasePackages(Arrays.asList(ShiciApplication.class.getPackage().getName() + ".model"));
		db.init();
		return db;
	}

	@Bean
	public WeixinClient createWeixinClient() {
		WeixinClient weixinClient = new WeixinClient();
		weixinClient.setAppId(wxapiAppId);
		weixinClient.setAppSecret(wxapiAppSecret);
		weixinClient.setAppToken(wxapiAppToken);
		weixinClient.setAesKey(wxapiAppAesKey);
		weixinClient.setCache(new SimpleAccessTokenCache());
		return weixinClient;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ShiciApplication.class, args);
	}
}
