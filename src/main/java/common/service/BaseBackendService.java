/*
 *
 *  * Copyright 2016 com.blogadmin.gaoshiqing.com All right reserved. This software is the
 *  * confidential and proprietary information of com.blogadmin.gaoshiqing.com ("Confidential
 *  * Information"). You shall not disclose such Confidential Information and shall
 *  * use it only in accordance with the terms of the license agreement you entered
 *  * into with com.blogadmin.gaoshiqing.com .
 *
 */

package common.service;

import java.util.UUID;

/**
 * Created by Arvin on 16/12/12.
 */
public class BaseBackendService {

    public String generateRequestId(){
        return UUID.randomUUID().toString();
    }
}
