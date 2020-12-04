package com.li.mlibrary.aop;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Calendar;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020-08-04 15
 * 描述：
 */
@Aspect
public class FastClickAspect  {
    private long mLastClickTime ;

    private int mLastClickViewId ;
    //1 构建切点
    @Pointcut("execution(@com.li.mlibrary.aop.astClick * *(..))")
    public void  clickFast(){

    }

    //2.切片
    @Around("clickFast()")
    public  void  fastClickJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        astClick fastClick =signature.getMethod().getAnnotation(astClick.class);
        Log.e("TAG", "fastClickJoinPoint: -->" );
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View){
                view = (View) arg;
            }
        }
        if (view!=null){
            long currentClickTime = Calendar.getInstance().getTimeInMillis();
            if (currentClickTime-mLastClickTime<fastClick.value()&&view.getId()==mLastClickViewId){
                Log.e("TAG", "fastClickJoinPoint: -->过快点击" );
                mLastClickTime = currentClickTime;
                return;
            }
            mLastClickViewId = view.getId();
            mLastClickTime = currentClickTime;

            joinPoint.proceed();
        }


    }
}
