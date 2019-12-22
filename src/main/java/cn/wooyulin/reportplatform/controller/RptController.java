package cn.wooyulin.reportplatform.controller;

import cn.wooyulin.reportplatform.dao.UnitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rpt/")
public class RptController {

    @Autowired
    private UnitDao unitDao;

    @RequestMapping("test")
    public ModelAndView getUnit(){
        System.out.println(unitDao.getOne(1));
        ModelAndView mv = new ModelAndView();
        mv.addObject("unit", unitDao.getOne(1));
        mv.setViewName("/user.html");
        return mv;
    }

    @RequestMapping("scbb")
    public ModelAndView getSCBB(){
        System.out.println(unitDao.getOne(1));
        ModelAndView mv = new ModelAndView();
        mv.addObject("unit", unitDao.getOne(1));
        mv.setViewName("/scbb.html");
        return mv;
    }

    @RequestMapping("prodRpt")
    public ModelAndView prodRpt(){
        System.out.println(unitDao.getOne(1));
        ModelAndView mv = new ModelAndView();
        mv.addObject("unit", unitDao.getOne(1));
        mv.setViewName("/prod_rpt.html");
        return mv;
    }

}
