package com.mleekko.test.dao;

import com.mleekko.test.domain.Asset;
import com.mleekko.test.domain.Hatcher;
import com.mleekko.test.domain.TimedHatcher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Mleekko
 */
public interface PeerMapper {

    @Select("select hatcher as hatcherId, count(*) as blocksHatched from blocks where createTime > UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL #{minutes} MINUTE)) group by hatcher order by blocksHatched desc")
    List<Hatcher> getHatchers(@Param("minutes")int minutes);

    @Select("select count(*) from blocks where createTime > UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL #{minutes} MINUTE))")
    int getBlocksCount(@Param("minutes")int minutes);


    @Select("SELECT\n" +
            "    hatcher as hatcherId,\n" +
            "    CEIL(createTime / #{seconds}) as timeBlock,\n" +
            "    COUNT(*) as blocksHatched\n" +
            "FROM blocks \n" +
            "    where createTime > UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL #{minutes} MINUTE)) \n" +
            "    group by hatcher, timeBlock\n" +
            "    order by timeBlock ASC")
    List<TimedHatcher> getHatchersPerInterval(@Param("minutes")int minutes, @Param("seconds") int groupingInterval);


    @Select("SELECT\n" +
            "    'Total Blocks' as hatcherId,\n" +
            "    CEIL(createTime / #{seconds}) as timeBlock,\n" +
            "    COUNT(*) as blocksHatched\n" +
            "FROM blocks \n" +
            "    where createTime > UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL #{minutes} MINUTE)) \n" +
            "    group by timeBlock ASC")
    List<TimedHatcher> getTotalBlocksPerInterval(@Param("minutes")int minutes, @Param("seconds") int groupingInterval);



    @Select("SELECT\n" +
            "    assets.iso as 'iso',\n" +
            "    assets.label as 'label',\n" +
            "    SUM(balances.value / assets.subUnits) as 'amount'\n" +
            "FROM peer.balances\n" +
            "INNER JOIN assets\n" +
            "ON (assets.id = balances.asset)\n" +
            "GROUP BY balances.asset")
    List<Asset> listAssets();

}
