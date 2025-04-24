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

import com.entity.ShenghuoyongpinEntity;
import com.entity.view.ShenghuoyongpinView;

import com.service.ShenghuoyongpinService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 生活用品
 * 后端接口
 * @author 
 * @email 
 * @date 2030-05-20 21:29:14
 */
@RestController
@RequestMapping("/shenghuoyongpin")
public class ShenghuoyongpinController {
    @Autowired
    private ShenghuoyongpinService shenghuoyongpinService;

    @Autowired
    private StoreupService storeupService;



    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShenghuoyongpinEntity shenghuoyongpin,
		HttpServletRequest request){
        EntityWrapper<ShenghuoyongpinEntity> ew = new EntityWrapper<ShenghuoyongpinEntity>();

		PageUtils page = shenghuoyongpinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shenghuoyongpin), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShenghuoyongpinEntity shenghuoyongpin, 
		HttpServletRequest request){
        EntityWrapper<ShenghuoyongpinEntity> ew = new EntityWrapper<ShenghuoyongpinEntity>();

		PageUtils page = shenghuoyongpinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shenghuoyongpin), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShenghuoyongpinEntity shenghuoyongpin){
       	EntityWrapper<ShenghuoyongpinEntity> ew = new EntityWrapper<ShenghuoyongpinEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shenghuoyongpin, "shenghuoyongpin")); 
        return R.ok().put("data", shenghuoyongpinService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShenghuoyongpinEntity shenghuoyongpin){
        EntityWrapper< ShenghuoyongpinEntity> ew = new EntityWrapper< ShenghuoyongpinEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shenghuoyongpin, "shenghuoyongpin")); 
		ShenghuoyongpinView shenghuoyongpinView =  shenghuoyongpinService.selectView(ew);
		return R.ok("查询生活用品成功").put("data", shenghuoyongpinView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShenghuoyongpinEntity shenghuoyongpin = shenghuoyongpinService.selectById(id);
        return R.ok().put("data", shenghuoyongpin);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShenghuoyongpinEntity shenghuoyongpin = shenghuoyongpinService.selectById(id);
        return R.ok().put("data", shenghuoyongpin);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShenghuoyongpinEntity shenghuoyongpin, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(shenghuoyongpin);
        shenghuoyongpinService.insert(shenghuoyongpin);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShenghuoyongpinEntity shenghuoyongpin, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(shenghuoyongpin);
        shenghuoyongpinService.insert(shenghuoyongpin);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ShenghuoyongpinEntity shenghuoyongpin, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shenghuoyongpin);
        shenghuoyongpinService.updateById(shenghuoyongpin);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shenghuoyongpinService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
