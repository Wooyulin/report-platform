package cn.wooyulin.reportplatform.controller;

import cn.wooyulin.reportplatform.domain.Unit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prod/")
public class ProdCtr {


//    public JsonResult GetDepartment(int limit, int offset, string departmentname, string statu)
//    {
//        var lstRes = new List<Department>();
//        for (var i = 0; i < 50; i++)
//        {
//            var oModel = new Department();
//            oModel.ID = Guid.NewGuid().ToString();
//            oModel.Name = "销售部" + i ;
//            oModel.Level = i.ToString();
//            oModel.Desc = "暂无描述信息";
//            lstRes.Add(oModel);
//        }
//
//        var total = lstRes.Count;
//        var rows = lstRes.Skip(offset).Take(limit).ToList();
//        return Json(new { total = total, rows = rows }, JsonRequestBehavior.AllowGet);
//    }

    @RequestMapping(value = "getUnit",method = RequestMethod.GET)
    public List<Map> getUnit(int limit, int offset, String departmentname, String statu){
        System.out.println(departmentname + "ssss"+ statu);
        List<Map> list = new ArrayList<>();
//        for (int i=1;i<50;i++){
//            Unit u = new Unit();
//            u.setId(i);
//            u.setName("测试"+i);
//            if (i%2==0)
//            u.setCountType("计算方式"+i);
//            list.add(u);
//        }
//        return list;
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", "1");
        hashMap.put("name", "120030002");
        hashMap.put("countType", "120030002");
        Map<String, Object> hashMap2 = new HashMap<String, Object>();
        hashMap2.put("id", "3");
        hashMap2.put("name", "你好");
        hashMap2.put("tt", "司思思");
        Map<String, Object> hashMap3 = new HashMap<String, Object>();
        hashMap3.put("id", "2");
        hashMap3.put("name", "我是");
        list.add(hashMap);
        list.add(hashMap2);
        list.add(hashMap3);
        return list;
    }

    @RequestMapping(value = "getProdTitle",method = RequestMethod.GET)
    public List<Unit> getProdTitle(){
        List<Unit> list = new ArrayList<>();
        for (int i=1;i<15;i++){
            Unit u = new Unit();
            u.setId(i);
            u.setName("测试"+i);
            if (i%2==0)
                u.setCountType("计算方式"+i);
            list.add(u);
        }
        return list;
    }

}
