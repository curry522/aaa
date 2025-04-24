package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.CaiwubuEntity;
import com.entity.view.CaiwubuView;

import com.service.CaiwubuService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 财务部
 * 后端接口
 * @author 
 * @email 
 * @date 2030-05-20 21:29:13
 */
@RestController
@RequestMapping("/caiwubu")
public class CaiwubuController {
    @Autowired
    private CaiwubuService caiwubuService;




    
	@Autowired
	private TokenService tokenService;
	
	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		CaiwubuEntity u = caiwubuService.selectOne(new EntityWrapper<CaiwubuEntity>().eq("caiwugonghao", username));
		if(u==null || !u.getMima().equals(password)) {
			return R.error("账号或密码不正确");
		}
		
		String token = tokenService.generateToken(u.getId(), username,"caiwubu",  "财务部" );
		return R.ok().put("token", token);
	}


	
	/**
     * 注册
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody CaiwubuEntity caiwubu){
    	//ValidatorUtils.validateEntity(caiwubu);
    	CaiwubuEntity u = caiwubuService.selectOne(new EntityWrapper<CaiwubuEntity>().eq("caiwugonghao", caiwubu.getCaiwugonghao()));
		if(u!=null) {
			return R.error("注册用户已存在");
		}
		Long uId = new Date().getTime();
		caiwubu.setId(uId);
        caiwubuService.insert(caiwubu);
        return R.ok();
    }

	
	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}
	
	/**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        CaiwubuEntity u = caiwubuService.selectById(id);
        return R.ok().put("data", u);
    }
    
    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
    	CaiwubuEntity u = caiwubuService.selectOne(new EntityWrapper<CaiwubuEntity>().eq("caiwugonghao", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        u.setMima("123456");
        caiwubuService.updateById(u);
        return R.ok("密码已重置为：123456");
    }



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,CaiwubuEntity caiwubu,
		HttpServletRequest request){
        EntityWrapper<CaiwubuEntity> ew = new EntityWrapper<CaiwubuEntity>();

		PageUtils page = caiwubuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, caiwubu), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,CaiwubuEntity caiwubu, 
		HttpServletRequest request){
        EntityWrapper<CaiwubuEntity> ew = new EntityWrapper<CaiwubuEntity>();

		PageUtils page = caiwubuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, caiwubu), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CaiwubuEntity caiwubu){
       	EntityWrapper<CaiwubuEntity> ew = new EntityWrapper<CaiwubuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( caiwubu, "caiwubu")); 
        return R.ok().put("data", caiwubuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CaiwubuEntity caiwubu){
        EntityWrapper< CaiwubuEntity> ew = new EntityWrapper< CaiwubuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( caiwubu, "caiwubu")); 
		CaiwubuView caiwubuView =  caiwubuService.selectView(ew);
		return R.ok("查询财务部成功").put("data", caiwubuView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CaiwubuEntity caiwubu = caiwubuService.selectById(id);
        return R.ok().put("data", caiwubu);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CaiwubuEntity caiwubu = caiwubuService.selectById(id);
        return R.ok().put("data", caiwubu);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CaiwubuEntity caiwubu, HttpServletRequest request){
        if(caiwubuService.selectCount(new EntityWrapper<CaiwubuEntity>().eq("caiwugonghao", caiwubu.getCaiwugonghao()))>0) {
            return R.error("财务工号已存在");
        }
    	caiwubu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(caiwubu);
    	CaiwubuEntity u = caiwubuService.selectOne(new EntityWrapper<CaiwubuEntity>().eq("caiwugonghao", caiwubu.getCaiwugonghao()));
		if(u!=null) {
			return R.error("用户已存在");
		}
		caiwubu.setId(new Date().getTime());
        caiwubuService.insert(caiwubu);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CaiwubuEntity caiwubu, HttpServletRequest request){
        if(caiwubuService.selectCount(new EntityWrapper<CaiwubuEntity>().eq("caiwugonghao", caiwubu.getCaiwugonghao()))>0) {
            return R.error("财务工号已存在");
        }
    	caiwubu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(caiwubu);
    	CaiwubuEntity u = caiwubuService.selectOne(new EntityWrapper<CaiwubuEntity>().eq("caiwugonghao", caiwubu.getCaiwugonghao()));
		if(u!=null) {
			return R.error("用户已存在");
		}
		caiwubu.setId(new Date().getTime());
        caiwubuService.insert(caiwubu);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CaiwubuEntity caiwubu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(caiwubu);
        if(caiwubuService.selectCount(new EntityWrapper<CaiwubuEntity>().ne("id", caiwubu.getId()).eq("caiwugonghao", caiwubu.getCaiwugonghao()))>0) {
            return R.error("财务工号已存在");
        }
        caiwubuService.updateById(caiwubu);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        caiwubuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
