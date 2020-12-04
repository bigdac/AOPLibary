package com.li.mlibrary.aop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.li.mlibrary.utils.CheckNetUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020-07-10 14
 * 描述： 网络切片
 */
@Aspect
public class NetWorkAspect {

    /**
     * @description  构建切点
     * @param
     */
    @Pointcut("execution(@com.li.mlibrary.aop.CheckNetWork * *(..))")
    public void checkNet(){}

    @Around("checkNet()")
    public void checkNetJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNetWork checkNetWork = signature.getMethod().getAnnotation(CheckNetWork.class);
        if (checkNetWork!=null){
            //判断上下文
            Object object = joinPoint.getThis();
            Context context = getContext(object);
            if (!CheckNetUtils.isConnected(context)){
                Toast.makeText(context,checkNetWork.value(),Toast.LENGTH_SHORT).show();
                Log.e("TAG", "checkNetJoinPoint: -->无网络服务" );
                return;
            }
        }
        joinPoint.proceed();
    }

    private Context getContext(Object object) {
        if (object instanceof Activity){
            return (Activity) object;
        }else if (object instanceof Fragment){
            return  ((Fragment) object).getActivity();
        }else if (object instanceof androidx.fragment.app.Fragment){
            return ((androidx.fragment.app.Fragment) object).getActivity();
        } else if (object instanceof View){
            return ((View) object).getContext();
        }
        return null;
    }

}
