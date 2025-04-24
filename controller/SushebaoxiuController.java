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

import com.entity.SushebaoxiuEntity;
import com.entity.view.SushebaoxiuView;

import com.service.SushebaoxiuService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 宿舍报修
 * 后端接口
 * @author 
 * @email 
 * @date 2030-05-20 21:29:14
 */
@RestController
@RequestMapping("/sushebaoxiu")
public class SushebaoxiuController {
    @Autowired
    private SushebaoxiuService sushebaoxiuService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,SushebaoxiuEntity sushebaoxiu,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			sushebaoxiu.setKaoshenghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<SushebaoxiuEntity> ew = new EntityWrapper<SushebaoxiuEntity>();

		PageUtils page = sushebaoxiuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, sushebaoxiu), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,SushebaoxiuEntity sushebaoxiu, 
		HttpServletRequest request){
        EntityWrapper<SushebaoxiuEntity> ew = new EntityWrapper<SushebaoxiuEntity>();

		PageUtils page = sushebaoxiuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, sushebaoxiu), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( SushebaoxiuEntity sushebaoxiu){
       	EntityWrapper<SushebaoxiuEntity> ew = new EntityWrapper<SushebaoxiuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( sushebaoxiu, "sushebaoxiu")); 
        return R.ok().put("data", sushebaoxiuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(SushebaoxiuEntity sushebaoxiu){
        EntityWrapper< SushebaoxiuEntity> ew = new EntityWrapper< SushebaoxiuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( sushebaoxiu, "sushebaoxiu")); 
		SushebaoxiuView sushebaoxiuView =  sushebaoxiuService.selectView(ew);
		return R.ok("查询宿舍报修成功").put("data", sushebaoxiuView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SushebaoxiuEntity sushebaoxiu = sushebaoxiuService.selectById(id);
        return R.ok().put("data", sushebaoxiu);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        SushebaoxiuEntity sushebaoxiu = sushebaoxiuService.selectById(id);
        return R.ok().put("data", sushebaoxiu);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SushebaoxiuEntity sushebaoxiu, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(sushebaoxiu);
        sushebaoxiuService.insert(sushebaoxiu);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody SushebaoxiuEntity sushebaoxiu, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(sushebaoxiu);
        sushebaoxiuService.insert(sushebaoxiu);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody SushebaoxiuEntity sushebaoxiu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(sushebaoxiu);
        sushebaoxiuService.updateById(sushebaoxiu);//全部更新
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<SushebaoxiuEntity> list = new ArrayList<SushebaoxiuEntity>();
        for(Long id : ids) {
            SushebaoxiuEntity sushebaoxiu = sushebaoxiuService.selectById(id);
            sushebaoxiu.setSfsh(sfsh);
            sushebaoxiu.setShhf(shhf);
            list.add(sushebaoxiu);
        }
        sushebaoxiuService.updateBatchById(list);
        return R.ok();
    }


    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        sushebaoxiuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
