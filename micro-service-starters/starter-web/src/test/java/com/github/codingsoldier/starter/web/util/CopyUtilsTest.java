package com.github.codingsoldier.starter.web.util;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenpq05
 * @since 2022/8/15 18:05
 */
class CopyUtilsTest {

    static class BeanA {

        private String id;

        private String name;

        public BeanA() {
        }

        public BeanA(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class BeanB {

        public BeanB() {
        }

        public BeanB(String id, String name) {
            this.id = id;
            this.name = name;
        }

        private String id;

        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    void listCopy() {
        ArrayList<BeanA> listA = new ArrayList<>();
        listA.add(new BeanA("110000", "名字"));
        listA.add(new BeanA("11111", "名字1111"));
        List<BeanB> listB = CopyUtils.listCopy(listA, BeanB.class);
        assertEquals("110000", listB.get(0).getId());
        assertEquals("11111", listB.get(1).getId());

    }
}