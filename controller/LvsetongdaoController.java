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

import com.entity.LvsetongdaoEntity;
import com.entity.view.LvsetongdaoView;

import com.service.LvsetongdaoService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 绿色通道
 * 后端接口
 * @author 
 * @email 
 * @date 2030-05-20 21:29:14
 */
@RestController
@RequestMapping("/lvsetongdao")
public class LvsetongdaoController {
    @Autowired
    private LvsetongdaoService lvsetongdaoService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,LvsetongdaoEntity lvsetongdao,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			lvsetongdao.setKaoshenghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("banzhuren")) {
			lvsetongdao.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<LvsetongdaoEntity> ew = new EntityWrapper<LvsetongdaoEntity>();

		PageUtils page = lvsetongdaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, lvsetongdao), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,LvsetongdaoEntity lvsetongdao, 
		HttpServletRequest request){
        EntityWrapper<LvsetongdaoEntity> ew = new EntityWrapper<LvsetongdaoEntity>();

		PageUtils page = lvsetongdaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, lvsetongdao), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( LvsetongdaoEntity lvsetongdao){
       	EntityWrapper<LvsetongdaoEntity> ew = new EntityWrapper<LvsetongdaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( lvsetongdao, "lvsetongdao")); 
        return R.ok().put("data", lvsetongdaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(LvsetongdaoEntity lvsetongdao){
        EntityWrapper< LvsetongdaoEntity> ew = new EntityWrapper< LvsetongdaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( lvsetongdao, "lvsetongdao")); 
		LvsetongdaoView lvsetongdaoView =  lvsetongdaoService.selectView(ew);
		return R.ok("查询绿色通道成功").put("data", lvsetongdaoView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        LvsetongdaoEntity lvsetongdao = lvsetongdaoService.selectById(id);
        return R.ok().put("data", lvsetongdao);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        LvsetongdaoEntity lvsetongdao = lvsetongdaoService.selectById(id);
        return R.ok().put("data", lvsetongdao);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody LvsetongdaoEntity lvsetongdao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(lvsetongdao);
        lvsetongdaoService.insert(lvsetongdao);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody LvsetongdaoEntity lvsetongdao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(lvsetongdao);
        lvsetongdaoService.insert(lvsetongdao);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody LvsetongdaoEntity lvsetongdao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(lvsetongdao);
        lvsetongdaoService.updateById(lvsetongdao);//全部更新
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<LvsetongdaoEntity> list = new ArrayList<LvsetongdaoEntity>();
        for(Long id : ids) {
            LvsetongdaoEntity lvsetongdao = lvsetongdaoService.selectById(id);
            lvsetongdao.setSfsh(sfsh);
            lvsetongdao.setShhf(shhf);
            list.add(lvsetongdao);
        }
        lvsetongdaoService.updateBatchById(list);
        return R.ok();
    }


    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        lvsetongdaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
