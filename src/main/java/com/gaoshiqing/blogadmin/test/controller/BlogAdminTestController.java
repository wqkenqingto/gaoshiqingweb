package com.gaoshiqing.blogadmin.test.controller;


import com.gaoshiqing.test.BlogAdminTestManagerSevice;

/**
 * Created by wqkenqing on 2017/2/20.
 */
public class BlogAdminTestController {
        private static BlogAdminTestManagerSevice blogAdminTestManagerSeviceImpl;

    public static void main(String[] args) {
        blogAdminTestManagerSeviceImpl.saveBlog();
    }
}
