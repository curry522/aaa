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

import com.entity.ShuidianchongzhiEntity;
import com.entity.view.ShuidianchongzhiView;

import com.service.ShuidianchongzhiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 水电充值
 * 后端接口
 * @author 
 * @email 
 * @date 2030-05-20 21:29:14
 */
@RestController
@RequestMapping("/shuidianchongzhi")
public class ShuidianchongzhiController {
    @Autowired
    private ShuidianchongzhiService shuidianchongzhiService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShuidianchongzhiEntity shuidianchongzhi,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			shuidianchongzhi.setKaoshenghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ShuidianchongzhiEntity> ew = new EntityWrapper<ShuidianchongzhiEntity>();

		PageUtils page = shuidianchongzhiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shuidianchongzhi), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShuidianchongzhiEntity shuidianchongzhi, 
		HttpServletRequest request){
        EntityWrapper<ShuidianchongzhiEntity> ew = new EntityWrapper<ShuidianchongzhiEntity>();

		PageUtils page = shuidianchongzhiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shuidianchongzhi), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShuidianchongzhiEntity shuidianchongzhi){
       	EntityWrapper<ShuidianchongzhiEntity> ew = new EntityWrapper<ShuidianchongzhiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shuidianchongzhi, "shuidianchongzhi")); 
        return R.ok().put("data", shuidianchongzhiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShuidianchongzhiEntity shuidianchongzhi){
        EntityWrapper< ShuidianchongzhiEntity> ew = new EntityWrapper< ShuidianchongzhiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shuidianchongzhi, "shuidianchongzhi")); 
		ShuidianchongzhiView shuidianchongzhiView =  shuidianchongzhiService.selectView(ew);
		return R.ok("查询水电充值成功").put("data", shuidianchongzhiView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShuidianchongzhiEntity shuidianchongzhi = shuidianchongzhiService.selectById(id);
        return R.ok().put("data", shuidianchongzhi);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShuidianchongzhiEntity shuidianchongzhi = shuidianchongzhiService.selectById(id);
        return R.ok().put("data", shuidianchongzhi);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShuidianchongzhiEntity shuidianchongzhi, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(shuidianchongzhi);
        shuidianchongzhiService.insert(shuidianchongzhi);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShuidianchongzhiEntity shuidianchongzhi, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(shuidianchongzhi);
        shuidianchongzhiService.insert(shuidianchongzhi);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ShuidianchongzhiEntity shuidianchongzhi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shuidianchongzhi);
        shuidianchongzhiService.updateById(shuidianchongzhi);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shuidianchongzhiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
