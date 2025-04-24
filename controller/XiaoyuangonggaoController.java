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

import com.entity.XiaoyuangonggaoEntity;
import com.entity.view.XiaoyuangonggaoView;

import com.service.XiaoyuangonggaoService;
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
 * 校园公告
 * 后端接口
 * @author 
 * @email 
 * @date 2030-05-20 21:29:14
 */
@RestController
@RequestMapping("/xiaoyuangonggao")
public class XiaoyuangonggaoController {
    @Autowired
    private XiaoyuangonggaoService xiaoyuangonggaoService;

    @Autowired
    private StoreupService storeupService;



    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,XiaoyuangonggaoEntity xiaoyuangonggao,
		HttpServletRequest request){
        EntityWrapper<XiaoyuangonggaoEntity> ew = new EntityWrapper<XiaoyuangonggaoEntity>();

		PageUtils page = xiaoyuangonggaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xiaoyuangonggao), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,XiaoyuangonggaoEntity xiaoyuangonggao, 
		HttpServletRequest request){
        EntityWrapper<XiaoyuangonggaoEntity> ew = new EntityWrapper<XiaoyuangonggaoEntity>();

		PageUtils page = xiaoyuangonggaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xiaoyuangonggao), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( XiaoyuangonggaoEntity xiaoyuangonggao){
       	EntityWrapper<XiaoyuangonggaoEntity> ew = new EntityWrapper<XiaoyuangonggaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( xiaoyuangonggao, "xiaoyuangonggao")); 
        return R.ok().put("data", xiaoyuangonggaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(XiaoyuangonggaoEntity xiaoyuangonggao){
        EntityWrapper< XiaoyuangonggaoEntity> ew = new EntityWrapper< XiaoyuangonggaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( xiaoyuangonggao, "xiaoyuangonggao")); 
		XiaoyuangonggaoView xiaoyuangonggaoView =  xiaoyuangonggaoService.selectView(ew);
		return R.ok("查询校园公告成功").put("data", xiaoyuangonggaoView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        XiaoyuangonggaoEntity xiaoyuangonggao = xiaoyuangonggaoService.selectById(id);
        return R.ok().put("data", xiaoyuangonggao);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        XiaoyuangonggaoEntity xiaoyuangonggao = xiaoyuangonggaoService.selectById(id);
        return R.ok().put("data", xiaoyuangonggao);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody XiaoyuangonggaoEntity xiaoyuangonggao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(xiaoyuangonggao);
        xiaoyuangonggaoService.insert(xiaoyuangonggao);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody XiaoyuangonggaoEntity xiaoyuangonggao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(xiaoyuangonggao);
        xiaoyuangonggaoService.insert(xiaoyuangonggao);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody XiaoyuangonggaoEntity xiaoyuangonggao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xiaoyuangonggao);
        xiaoyuangonggaoService.updateById(xiaoyuangonggao);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        xiaoyuangonggaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
