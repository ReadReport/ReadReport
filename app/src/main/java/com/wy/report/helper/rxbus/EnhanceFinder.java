package com.wy.report.helper.rxbus;

import com.hwangjr.rxbus.entity.EventType;
import com.hwangjr.rxbus.entity.ProducerEvent;
import com.hwangjr.rxbus.entity.SubscriberEvent;
import com.hwangjr.rxbus.finder.Finder;

import java.util.Map;
import java.util.Set;

/**
 * @author cantalou
 * @date 2018年01月17日 16:14
 * <p>
 */
public interface EnhanceFinder extends Finder {

    EnhanceFinder ANNOTATED = new EnhanceFinder() {
        @Override
        public Map<EventType, ProducerEvent> findAllProducers(Object listener) {
            return AnnotatedFinder.findAllProducers(listener);
        }

        @Override
        public Map<EventType, Set<SubscriberEvent>> findAllSubscribers(Object listener) {
            return AnnotatedFinder.findAllSubscribers(listener);
        }
    };


}
