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

import com.entity.YongpindinggouEntity;
import com.entity.view.YongpindinggouView;

import com.service.YongpindinggouService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 用品订购
 * 后端接口
 * @author 
 * @email 
 * @date 2030-05-20 21:29:14
 */
@RestController
@RequestMapping("/yongpindinggou")
public class YongpindinggouController {
    @Autowired
    private YongpindinggouService yongpindinggouService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,YongpindinggouEntity yongpindinggou,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			yongpindinggou.setKaoshenghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<YongpindinggouEntity> ew = new EntityWrapper<YongpindinggouEntity>();

		PageUtils page = yongpindinggouService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yongpindinggou), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,YongpindinggouEntity yongpindinggou, 
		HttpServletRequest request){
        EntityWrapper<YongpindinggouEntity> ew = new EntityWrapper<YongpindinggouEntity>();

		PageUtils page = yongpindinggouService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yongpindinggou), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( YongpindinggouEntity yongpindinggou){
       	EntityWrapper<YongpindinggouEntity> ew = new EntityWrapper<YongpindinggouEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yongpindinggou, "yongpindinggou")); 
        return R.ok().put("data", yongpindinggouService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(YongpindinggouEntity yongpindinggou){
        EntityWrapper< YongpindinggouEntity> ew = new EntityWrapper< YongpindinggouEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yongpindinggou, "yongpindinggou")); 
		YongpindinggouView yongpindinggouView =  yongpindinggouService.selectView(ew);
		return R.ok("查询用品订购成功").put("data", yongpindinggouView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        YongpindinggouEntity yongpindinggou = yongpindinggouService.selectById(id);
        return R.ok().put("data", yongpindinggou);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        YongpindinggouEntity yongpindinggou = yongpindinggouService.selectById(id);
        return R.ok().put("data", yongpindinggou);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody YongpindinggouEntity yongpindinggou, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(yongpindinggou);
        yongpindinggouService.insert(yongpindinggou);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody YongpindinggouEntity yongpindinggou, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(yongpindinggou);
        yongpindinggouService.insert(yongpindinggou);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody YongpindinggouEntity yongpindinggou, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yongpindinggou);
        yongpindinggouService.updateById(yongpindinggou);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        yongpindinggouService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
