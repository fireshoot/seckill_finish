package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.SecKill;
import org.seckill.enums.SeckillStateEnums;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author yangxin
 * @time 2018/12/8  12:06
 */
@Controller
@RequestMapping("/seckill")//模块
public class SeckillController {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        //model存储的是数据。list.jsp+model=ModelAndView。
        //获取列表页，调用service.
        List<SecKill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value="/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId==null){
            return "redirect:/seckill/list";
        }
        SecKill secKill=seckillService.getById(seckillId);
        if(secKill==null) {
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill",secKill);
        return "detail";
    }

    //ajax json 接口。
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"/*这个解决中文的乱码问题*/
    )
    @ResponseBody//表明返回值需要封装成一个json接口。
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try{
            Exposer exposer=seckillService.exportSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result=new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone",required = false) Long phone){
        if(phone==null)
            return new SeckillResult<SeckillExecution>(false,"无效手机号码");
        try{
            SeckillExecution execution=seckillService.executeSeckill2(seckillId,phone,md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch (SeckillCloseException e2){
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnums.END);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }catch (RepeatKillException e1){
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnums.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnums.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false,seckillExecution);
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date date=new Date();
        return new SeckillResult<Long>(true,date.getTime());
    }

}
