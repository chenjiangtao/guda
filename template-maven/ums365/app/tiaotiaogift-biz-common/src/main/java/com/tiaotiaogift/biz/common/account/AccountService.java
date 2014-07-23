/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.account;

import java.util.List;

/**
 * 
 * @author gang
 * @version $Id: AccountService.java, v 0.1 2013-4-30 下午5:41:37 gang Exp $
 */
public interface AccountService {

    public boolean send(String userId, int count);

    public boolean sendSuccess(String userId, int count);

    public boolean sendFail(String userId, int count);

    public boolean sendApiSuccess(List<String> msgIds, String userId, int count);

    public boolean sendApiFail(List<String> msgIds, String userId, int count);

}
