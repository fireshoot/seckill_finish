//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)
//需要显示本页面注解，需要将本页面代码调成utf-8格式。
var seckill= {
    //封装秒杀相关ajax的url
    URl: {
        now:function () {
            return '/seckill/seckill/time/now';
        },
        exposer:function(seckillId){
            return '/seckill/seckill/'+seckillId+'/exposer';
        },
        execution:function(md5,seckillId){
            return '/seckill/seckill/'+seckillId+'/'+md5+'/execution';
        }

    },
    handlerSeckillkill:function(seckillId,node){
        //获取秒杀地址,控制显示器,执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URl.exposer(seckillId),{},function(result){
            //再回调函数中执行交互流程。
            if(result&&result['success']){
                var exposer =result['data'];
                if(exposer['exposed']){
                    //开始秒杀。
                    //获取秒杀地址。
                    var md5=exposer['md5'];
                    var killUrl=seckill.URl.execution(md5,seckillId);
                    console.log("killURrl:"+killUrl);
                    //绑定一次点击事件，防止用户连续点击，造成服务器时间在同一时间受到大量的请求。
                    $('#killBtn').one('click',function(){
                        //执行秒杀请求的操作
                        //首先禁用按钮。
                        $(this).addClass('disabled');
                        //发送秒杀请求。执行秒杀。
                        $.post(killUrl,{},function(result){
                            if(result&&result['success']){
                                var killResult=result['data'];
                                var state=killResult['state'];
                                var stateinfo=killResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">'+stateinfo+'</span>');
                            }
                        });
                    });
                    node.show();
                }else{
                    //未开始秒杀。
                    var now=exposer['now'];
                    var start=exposer['start'];
                    var end=exposer['end'];
                    //防止用户端的计时偏移导致等待时间过长，重新计时
                    seckill.countdown(seckillId,now,start,end);
                }
            }else{
                console.log('result='+result);
            }
        });
    },

    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true
        }else{
            return false;
        }
    },



    countdown:function (seckillId,nowTime,startTime,endTime) {
        var seckillBox=$("#seckill-box");
        if(nowTime>endTime){
            //秒杀结束
            seckillBox.html('秒杀结束');
        }else if(nowTime<startTime){
            //秒杀未开始,计时事件绑定
            var killTime=new Date(startTime+1000);
            seckillBox.countdown(killTime,function (event) {
                //时间格式
                var format=event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function () {
                //时间完成后回调事件
                //获取秒杀地址,控制现实逻辑,执行秒杀
                seckill.handlerSeckillkill(seckillId,seckillBox);
            });
        }else{
            //seckillBox.html('秒杀开始！');
            seckill.handlerSeckillkill(seckillId,seckillBox);
        }
    }
    ,
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //手机验证和登录,计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            //验证手机号
            if (!seckill.validatePhone(killPhone)) {
                //绑定手机 控制输出
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false   //关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        //电话写入cookie(7天过期)
                        $.cookie('killPhone', inputPhone, {expries: 7, path: '/seckill/seckill'})
                        //验证通过　　刷新页面
                        window.location.reload();
                    } else {
                        //todo 错误文案信息抽取到前端字典里
                        $('#killPhoneMessage').hide().html("<label>phone error</label>").show(200);
                    }
                });
            }
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            //已经登录
            //计时交互
            $.get(seckill.URl.now(),{},function (result){
                if(result&&result['success']){
                    var nowTime=result['data'];
                    //时间判断 计时交互
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else {
                    console.log('result='+result);
                }
            });
        }
    }
}