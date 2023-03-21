package com.dzqc;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class SpringbootRedisApplicationTests {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    //string类型
    @Test
    void contextLoads() {
//        //判断是否存在某个键
//        String  name="张三";
//        redisTemplate.opsForValue().set("address1",name);
//        Boolean address1 = redisTemplate.hasKey("address1");
//        Boolean address2 = redisTemplate.hasKey("address2");
//        System.out.println(address2);
//        System.out.println(address1);
//
//        Boolean address11 = redisTemplate.delete("address1");
//        System.out.println(address11);
//
//        Object address13 = redisTemplate.opsForValue().get("address1");
//        System.out.println(address13);

        //设置过期时间
        redisTemplate.opsForValue().set("aname", "zhangsanaaa字");
        // redisTemplate.expire("aname",20, TimeUnit.SECONDS);
        Object aname = redisTemplate.opsForValue().get("aname");
        System.out.println(aname);


    }

    @Test
    void test1() {
        Object aname = redisTemplate.opsForValue().get("aname");
        System.out.println("aname:" + aname);
    }

    //123321
    @Test
    void test2() {
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("name:" + name);
    }


    //hash类型

    @Test
    void hash() {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        //   hashOperations.put("users:1","id","1");
        //   hashOperations.put("users:1","name","lisi");
        //   hashOperations.put("users:1","password","123");


        Object o = hashOperations.get("users:1", "id");
        Object name = hashOperations.get("users:1", "name");
        Object password = hashOperations.get("users:1", "password");
        System.out.println(o + "name:" + name + "password" + password);
        //上面都是单个单个的操作,下面操作所有的
        Map<Object, Object> entries = hashOperations.entries("users:1");
        System.out.println("--------------------------------");
        entries.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });
        System.out.println("========================");
        for (Object s : entries.keySet()) {
            System.out.println("键" + s);
            System.out.println("值" + entries.get(s));
        }
        //设置多个字段信息
//        Map<String,Object> map=new HashMap<>();
//        map.put("id",1);
//        map.put("uname","liyi");
//        map.put("age",30);
//        hashOperations.putAll("user:2",map);
        System.out.println("======================");
        Map<Object, Object> entries1 = hashOperations.entries("user:2");
        entries1.forEach((key, value) -> {
            System.out.print("键" + key);
            System.out.print("值" + value);
            System.out.println();
        });
        System.out.println("遍历器==============================");
        Iterator<Map.Entry<Object, Object>> iterator = entries1.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> next = iterator.next();
            System.out.print("键" + next.getKey());
            System.out.print("值" + next.getValue());
            System.out.println();
        }

        //删除小key
        //  redisTemplate.boundHashOps("users:1").delete("name");
        //删除大key
        //    redisTemplate.delete("users:1");


        System.out.println("判断字段使用以及杂七杂八操作=====================");
        //查看hash表中指定字段是否存在
        Boolean name1 = redisTemplate.boundHashOps("users:1").hasKey("name");
        System.out.println("小字段" + name1);

        Boolean aBoolean = redisTemplate.hasKey("users:1");
        System.out.println("大字段" + aBoolean);
        //获取hash表中的字段数量
        Long size = redisTemplate.opsForHash().size("users:1");
        System.out.println(size);
        //获取所有hash表中的字段
        Set<Object> keys = redisTemplate.opsForHash().keys("users:1");
        Iterator<Object> iterator1 = keys.iterator();
        while (iterator1.hasNext()) {
            System.out.print("获取的所有字段" + iterator1.next() + " ");
        }
        System.out.println();
        System.out.println("隔离隔离隔离隔离隔离隔离隔离隔离隔离隔离隔离隔离");
        keys.forEach(i -> {
            System.out.println("获取的所有字段" + i);
        });
        System.out.println("隔离隔离隔离隔离隔离隔离隔离隔离隔离隔离隔离隔离");
        for (Object ab : keys) {
            System.out.println("获取的所有字段" + ab);
        }
        //获取hash表中存在的所有值
        List<Object> values = redisTemplate.opsForHash().values("users:1");
        values.forEach(value -> {
            System.out.println("hash表中的值" + value);
        });

//        redisTemplate.opsForHash().scan("user1",);
    }

    //list列表

    @Test
    public void list() {
//        //将内容挨个存入list集合
//        redisTemplate.opsForList().leftPush("llst","a");
//        redisTemplate.opsForList().leftPush("llst","b");
//        redisTemplate.opsForList().leftPush("llst","c");
//        redisTemplate.opsForList().leftPush("llst","d");
//        redisTemplate.opsForList().leftPush("llst","e");
//        redisTemplate.opsForList().leftPush("llst","f");
//        //根据下标获取单个对象
//        String s = redisTemplate.opsForList().index("llst",5);
//        System.out.println(s);

        List<String> llst = redisTemplate.opsForList().range("llst", 0, -1);
        for (String a : llst) {
            System.out.print("值:" + a);
        }
        llst.forEach(v -> {
            System.out.print("值" + v + "");
        });
    }


    //set集合
    @Test
    public void Test() {
        //添加数据
        // redisTemplate.opsForSet().add("set","a","b","c","d");
        // redisTemplate.opsForSet().add("set1","d","f","e","c");
        //查看set集合中的数据
        Set<String> set = redisTemplate.opsForSet().members("set");
        set.forEach(v -> {
            System.out.print(v + " ");
        });
        System.out.println();
        Set<String> set1 = redisTemplate.opsForSet().members("set1");
        set1.forEach(v -> {
            System.out.print(v + " ");
        });
        System.out.println();
        System.out.println("_-----------------------");
        //求并集 求两个或多个共同的元素
        List<String> list = new ArrayList<>();
        list.add("set");
        list.add("set1");
        Set<String> union = redisTemplate.opsForSet().union(list);
        Iterator<String> iterator = union.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        //求差集
        Set<String> difference = redisTemplate.opsForSet().difference("set", "set1");
        for (String s : difference) {
            System.out.print(s);
        }
        //求交集
        System.out.println();
        Set<String> intersect = redisTemplate.opsForSet().intersect(list);
        intersect.forEach(value -> {
            System.out.print("值:" + value + " ");
        });
        //随机删除并返回一个元素
        //  String set2 = redisTemplate.opsForSet().pop("set");
        //  System.out.println(set2);

        System.out.println("=======================");
        //随机获取集合中count个元素,不会去除重复,去重使用distinctRandomMembers
        List<String> list1 = redisTemplate.opsForSet().randomMembers("set", 2);
        list1.forEach(s -> {
            System.out.print(s);
        });
        System.out.println();
        //1.获取集合大小
        Long set11 = redisTemplate.opsForSet().size("set1");
        System.out.println("值的长度" + set11);
        //2.判断集合是否包含value
        Boolean member = redisTemplate.opsForSet().isMember("set1", "d");
        System.out.println("判断是否包含某个值:" + member);
        //3.key集合与otherKey集合的并集存储到destKey中(otherKeys可以为单个值或者是集合)
        Long aLong = redisTemplate.opsForSet().intersectAndStore("set", "set1", "setall");
        System.out.println(aLong);//返回的是插入进去的数据
    }

    //zset集合
    @Test
    public void zSet() {

//        redisTemplate.opsForZSet().add("gz","lucy",4000);
//        redisTemplate.opsForZSet().add("gz","tom",5000);
//        redisTemplate.opsForZSet().add("gz","jack",6000);
//        redisTemplate.opsForZSet().add("gz","rose",7000);
//根据工资降序查询所有员工姓名
        Set<ZSetOperations.TypedTuple<String>> gz = redisTemplate.opsForZSet().reverseRangeWithScores("gz", 0, -1);
        gz.forEach(value -> {
            System.out.print("员工姓名" + value.getValue() + " ");
        });
        System.out.println();
        //查看指定员工的工资
//        redisTemplate.opsForZSet().score()
        Long gz1 = redisTemplate.opsForZSet().count("gz", 4000, 5000);
        System.out.println(gz1);

        Long gz2 = redisTemplate.opsForZSet().count("gz", (4000), (5000));
        System.out.println(gz2);

    }

    @Test
    public void Test4() {
        //遍历list集合
        List<Integer> list = new ArrayList<>();
        //普通for
        for (int i = 0; i <= 5; i++) {
            list.add(i);
        }
        //增强for
        for (Integer i : list) {
            System.out.print(i);
        }
        System.out.println();
        System.out.println("------------------");
        //lambda
        list.forEach(System.out::println);
        System.out.println("------------------");
        //strer流
        list.stream().filter(c -> c != null).forEach(c -> {
            System.out.print(c);
        });
        System.out.println();
        System.out.println("------------------");
//        //迭代器
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println();
        System.out.println("-------------------");

        //遍历set集合
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i <= 5; i++) {
            set.add(i);
        }
        Iterator<Integer> iterator1 = set.iterator();
        while (iterator1.hasNext()){
            System.out.print(iterator1.next()+" ");
        }
        System.out.println();
        System.out.println("----------------------");
        for (Integer i:set) {
            System.out.print(i+" ");
        }
        set.forEach(System.out::print);
        //遍历map集合
        Map<String,Integer> map=new HashMap<>();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        map.put("d",4);
        System.out.println("--------------map------------------");
        for (String a:map.keySet()) {
            System.out.print("值"+map.get(a));
        }
        System.out.println();
        map.forEach((String a,Integer b)->{
            System.out.print("键"+a);
            System.out.print("值"+b);
        });
//        Collection<Integer> values = map.values();
//        Iterator<Integer> iterator2 = values.iterator();
//        while (iterator2.hasNext()){
//            iterator2.
//        }

    }


}
