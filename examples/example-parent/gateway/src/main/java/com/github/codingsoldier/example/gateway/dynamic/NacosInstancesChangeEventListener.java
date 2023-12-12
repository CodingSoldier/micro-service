// package com.github.codingsoldier.example.gateway.dynamic;
//
// import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
// import com.alibaba.nacos.common.notify.NotifyCenter;
// import com.alibaba.nacos.common.notify.listener.Subscriber;
// import com.alibaba.nacos.common.utils.JacksonUtils;
// import jakarta.annotation.PostConstruct;
// import jakarta.annotation.Resource;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.cache.Cache;
// import org.springframework.cache.CacheManager;
// import org.springframework.stereotype.Component;
//
// import static org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier.SERVICE_INSTANCE_CACHE_NAME;
//
// /**
//  * 监听nacos客户端下线事件，清除loadBalance缓存
//  * caffeineBasedLoadBalancerCacheManager清除微服务缓存时间太长，约等于1分钟，需改小
//  * @author chenpq05
//  * @since 2023/12/11 16:50
//  */
// @Component
// @Slf4j
// public  class NacosInstancesChangeEventListener extends Subscriber<InstancesChangeEvent> {
//
//   @Resource
//   private CacheManager caffeineBasedLoadBalancerCacheManager;
//
//   @PostConstruct
//   public void registerToNotifyCenter(){
//     NotifyCenter.registerSubscriber(this);
//   }
//
//   @Override
//   public void onEvent(InstancesChangeEvent event) {
//     log.info("Gateway 接收实例刷新事件：{}, 开始刷新缓存", JacksonUtils.toJson(event));
//     Cache cache = caffeineBasedLoadBalancerCacheManager.getCache(SERVICE_INSTANCE_CACHE_NAME);
//     if (cache != null) {
//       cache.evictIfPresent(event.getServiceName());
//     }
//     log.info("Gateway 实例刷新完成");
//   }
//
//   @Override
//   public Class<? extends com.alibaba.nacos.common.notify.Event> subscribeType() {
//     return InstancesChangeEvent.class;
//   }
//
// }
