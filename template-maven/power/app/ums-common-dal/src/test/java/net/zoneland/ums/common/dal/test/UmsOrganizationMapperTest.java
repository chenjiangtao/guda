package net.zoneland.ums.common.dal.test;

import java.util.Iterator;
import java.util.List;

import net.zoneland.ums.common.dal.UmsOrganizationMapper;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(defaultRollback = false)
public class UmsOrganizationMapperTest extends BaseDaoTest {
    @Autowired
    private UmsOrganizationMapper uMapper;

    @Test
    public void test1() {
        boolean isSame = false;
        String name = "拱墅区电力";
        String pId = "3d1a3724-04b6-434b-993c-dac409fbe986";
        List<String> pNameList = uMapper.selectPname(pId);
        Iterator<String> it = pNameList.iterator();
        while (it.hasNext()) {
            String pName = it.next();
            if (StringUtils.trim(pName).equals(StringUtils.trim(name))) {
                isSame = true;
            }
        }
        System.out.println(isSame);

    }

}
