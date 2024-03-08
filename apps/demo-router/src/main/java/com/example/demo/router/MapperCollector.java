//package com.example.demo.router;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @ClassName RouteItemGather
// * @Des 收集解析后的 routeItem
// * @Author lipengfei
// * @Date 2023/8/7 19:36
// */
//public class MapperCollector {
//    public static final void init() {
//        List<Segment> segments = Arrays.asList(new Segment("{question_id}", "question_id", null, "string", true), new Segment("answer", "answer", "answer", "string", false), new Segment("{answer_id}", "answer_id", null, "string", true));
//        RouterManager.instance.addMapper(new Mapper("scheme://test_clazz/{question_id}/answer/{answer_id}",
//                "scheme", "test_clazz", segments, TestClazz.class, 101, null));
//
//    }
//}
