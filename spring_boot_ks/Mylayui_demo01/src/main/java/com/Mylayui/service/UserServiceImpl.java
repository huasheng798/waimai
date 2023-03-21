package com.Mylayui.service;

import com.Mylayui.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Mylayui.dao.UserDao;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/5 10:35
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryListUser(Integer page,
                                    Integer limit) {
        return userDao.queryListUser(page, limit);
    }

    @Override
    public int countUser() {
        return userDao.countUser();
    }

    @Override
    public User selectUserById(Integer id) {
        return userDao.selectUserById(id);
    }

    /**
     * 实现删除
     * @param id
     * @return
     */
    @Override
    public boolean deleteUserById(Integer id) {
        boolean f=false;
        try {
            Integer integer = userDao.deleteUserById(id);
            if (integer>0){
                f=true;
            }else {
                f=false;
            }
        }catch(Exception e){
            e.printStackTrace();
            f=false;
            throw new RuntimeException("删除失败了- -");
        }
        return f;
    }
}
