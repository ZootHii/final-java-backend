//package com.zoothii.finaljavabackend.business.concretes;
//
//import com.zoothii.finaljavabackend.business.abstracts.UserService;
//import com.zoothii.finaljavabackend.core.data_access.UserDao;
//import com.zoothii.finaljavabackend.core.entities.User;
//import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
//import com.zoothii.finaljavabackend.core.utulities.results.SuccessDataResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserManager implements UserService {
//
//    private final UserDao userDao;
//
//    @Autowired
//    public UserManager(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    @Override
//    public DataResult<User> createUser(User user) {
//        return new SuccessDataResult<>(this.userDao.save(user));
//    }
//
//    @Override
//    public DataResult<User> getByEmail(String email) {
//        return new SuccessDataResult<>(this.userDao.getByEmail(email));
//    }
//}
