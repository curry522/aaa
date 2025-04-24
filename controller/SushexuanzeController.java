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

import com.entity.SushexuanzeEntity;
import com.entity.view.SushexuanzeView;

import com.service.SushexuanzeService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 宿舍选择
 * 后端接口
 * @author 
 * @email 
 * @date 2030-05-20 21:29:14
 */
@RestController
@RequestMapping("/sushexuanze")
public class SushexuanzeController {
    @Autowired
    private SushexuanzeService sushexuanzeService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,SushexuanzeEntity sushexuanze,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			sushexuanze.setKaoshenghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<SushexuanzeEntity> ew = new EntityWrapper<SushexuanzeEntity>();

		PageUtils page = sushexuanzeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, sushexuanze), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,SushexuanzeEntity sushexuanze, 
		HttpServletRequest request){
        EntityWrapper<SushexuanzeEntity> ew = new EntityWrapper<SushexuanzeEntity>();

		PageUtils page = sushexuanzeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, sushexuanze), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( SushexuanzeEntity sushexuanze){
       	EntityWrapper<SushexuanzeEntity> ew = new EntityWrapper<SushexuanzeEntity>();
      	ew.allEq(MPUtil.allEQMapPre( sushexuanze, "sushexuanze")); 
        return R.ok().put("data", sushexuanzeService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(SushexuanzeEntity sushexuanze){
        EntityWrapper< SushexuanzeEntity> ew = new EntityWrapper< SushexuanzeEntity>();
 		ew.allEq(MPUtil.allEQMapPre( sushexuanze, "sushexuanze")); 
		SushexuanzeView sushexuanzeView =  sushexuanzeService.selectView(ew);
		return R.ok("查询宿舍选择成功").put("data", sushexuanzeView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SushexuanzeEntity sushexuanze = sushexuanzeService.selectById(id);
        return R.ok().put("data", sushexuanze);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        SushexuanzeEntity sushexuanze = sushexuanzeService.selectById(id);
        return R.ok().put("data", sushexuanze);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SushexuanzeEntity sushexuanze, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(sushexuanze);
        sushexuanzeService.insert(sushexuanze);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody SushexuanzeEntity sushexuanze, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(sushexuanze);
        sushexuanzeService.insert(sushexuanze);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody SushexuanzeEntity sushexuanze, HttpServletRequest request){
        //ValidatorUtils.validateEntity(sushexuanze);
        sushexuanzeService.updateById(sushexuanze);//全部更新
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<SushexuanzeEntity> list = new ArrayList<SushexuanzeEntity>();
        for(Long id : ids) {
            SushexuanzeEntity sushexuanze = sushexuanzeService.selectById(id);
            sushexuanze.setSfsh(sfsh);
            sushexuanze.setShhf(shhf);
            list.add(sushexuanze);
        }
        sushexuanzeService.updateBatchById(list);
        return R.ok();
    }


    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        sushexuanzeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
