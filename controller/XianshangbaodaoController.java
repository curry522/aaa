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

import com.entity.XianshangbaodaoEntity;
import com.entity.view.XianshangbaodaoView;

import com.service.XianshangbaodaoService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 线上报到
 * 后端接口
 * @author 
 * @email 
 * @date 2030-05-20 21:29:14
 */
@RestController
@RequestMapping("/xianshangbaodao")
public class XianshangbaodaoController {
    @Autowired
    private XianshangbaodaoService xianshangbaodaoService;




    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,XianshangbaodaoEntity xianshangbaodao,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			xianshangbaodao.setKaoshenghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("banzhuren")) {
			xianshangbaodao.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<XianshangbaodaoEntity> ew = new EntityWrapper<XianshangbaodaoEntity>();

		PageUtils page = xianshangbaodaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xianshangbaodao), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,XianshangbaodaoEntity xianshangbaodao, 
		HttpServletRequest request){
        EntityWrapper<XianshangbaodaoEntity> ew = new EntityWrapper<XianshangbaodaoEntity>();

		PageUtils page = xianshangbaodaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xianshangbaodao), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( XianshangbaodaoEntity xianshangbaodao){
       	EntityWrapper<XianshangbaodaoEntity> ew = new EntityWrapper<XianshangbaodaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( xianshangbaodao, "xianshangbaodao")); 
        return R.ok().put("data", xianshangbaodaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(XianshangbaodaoEntity xianshangbaodao){
        EntityWrapper< XianshangbaodaoEntity> ew = new EntityWrapper< XianshangbaodaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( xianshangbaodao, "xianshangbaodao")); 
		XianshangbaodaoView xianshangbaodaoView =  xianshangbaodaoService.selectView(ew);
		return R.ok("查询线上报到成功").put("data", xianshangbaodaoView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        XianshangbaodaoEntity xianshangbaodao = xianshangbaodaoService.selectById(id);
        return R.ok().put("data", xianshangbaodao);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        XianshangbaodaoEntity xianshangbaodao = xianshangbaodaoService.selectById(id);
        return R.ok().put("data", xianshangbaodao);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody XianshangbaodaoEntity xianshangbaodao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(xianshangbaodao);
        xianshangbaodaoService.insert(xianshangbaodao);
        return R.ok();
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody XianshangbaodaoEntity xianshangbaodao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(xianshangbaodao);
        xianshangbaodaoService.insert(xianshangbaodao);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody XianshangbaodaoEntity xianshangbaodao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xianshangbaodao);
        xianshangbaodaoService.updateById(xianshangbaodao);//全部更新
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<XianshangbaodaoEntity> list = new ArrayList<XianshangbaodaoEntity>();
        for(Long id : ids) {
            XianshangbaodaoEntity xianshangbaodao = xianshangbaodaoService.selectById(id);
            xianshangbaodao.setSfsh(sfsh);
            xianshangbaodao.setShhf(shhf);
            list.add(xianshangbaodao);
        }
        xianshangbaodaoService.updateBatchById(list);
        return R.ok();
    }


    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        xianshangbaodaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	






    /**
     * （按值统计）
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}")
    public R value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        EntityWrapper<XianshangbaodaoEntity> ew = new EntityWrapper<XianshangbaodaoEntity>();
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
            ew.eq("kaoshenghao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("banzhuren")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
		}
        List<Map<String, Object>> result = xianshangbaodaoService.selectValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * （按值统计(多)）
     */
    @RequestMapping("/valueMul/{xColumnName}")
    public R valueMul(@PathVariable("xColumnName") String xColumnName,@RequestParam String yColumnNameMul, HttpServletRequest request) {
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<XianshangbaodaoEntity> ew = new EntityWrapper<XianshangbaodaoEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("xuesheng")) {
            ew.eq("kaoshenghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("banzhuren")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = xianshangbaodaoService.selectValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * （按值统计）时间统计类型
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
    public R valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        params.put("timeStatType", timeStatType);
        EntityWrapper<XianshangbaodaoEntity> ew = new EntityWrapper<XianshangbaodaoEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("xuesheng")) {
            ew.eq("kaoshenghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("banzhuren")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = xianshangbaodaoService.selectTimeStatValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * （按值统计）时间统计类型(多)
     */
    @RequestMapping("/valueMul/{xColumnName}/{timeStatType}")
    public R valueMulDay(@PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,@RequestParam String yColumnNameMul,HttpServletRequest request) {
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("timeStatType", timeStatType);
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<XianshangbaodaoEntity> ew = new EntityWrapper<XianshangbaodaoEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("xuesheng")) {
            ew.eq("kaoshenghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("banzhuren")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = xianshangbaodaoService.selectTimeStatValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * 分组统计
     */
    @RequestMapping("/group/{columnName}")
    public R group(@PathVariable("columnName") String columnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("column", columnName);
        EntityWrapper<XianshangbaodaoEntity> ew = new EntityWrapper<XianshangbaodaoEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("xuesheng")) {
            ew.eq("kaoshenghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("banzhuren")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = xianshangbaodaoService.selectGroup(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }




    /**
     * 总数量
     */
    @RequestMapping("/count")
    public R count(@RequestParam Map<String, Object> params,XianshangbaodaoEntity xianshangbaodao, HttpServletRequest request){
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("xuesheng")) {
            xianshangbaodao.setKaoshenghao((String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("banzhuren")) {
            xianshangbaodao.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
        }
        EntityWrapper<XianshangbaodaoEntity> ew = new EntityWrapper<XianshangbaodaoEntity>();
        int count = xianshangbaodaoService.selectCount(MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xianshangbaodao), params), params));
        return R.ok().put("data", count);
    }



}
