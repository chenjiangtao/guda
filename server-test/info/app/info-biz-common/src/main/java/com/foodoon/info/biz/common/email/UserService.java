package com.foodoon.info.biz.common.email;

public interface UserService {
	
	
	BizResult createUser(UserDO userDO);
	
	BizResult updateUser(UserDO userDO);
	
	BizResult delete(UserDO userDO);
	
	BizResult query(BaseQuery baseQuery);
	
	
	
	

}
