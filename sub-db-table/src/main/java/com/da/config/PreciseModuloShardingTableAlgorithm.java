package com.da.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @Description: 表分片规则
 */
public final class PreciseModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * PreciseShardingValue 是
     * org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue<Long>
     * 类型的，所以需要强制类型转换。
     *
     * @param tableNames
     * @param shardingValue
     * @return
     */
    @Override
    public String doSharding(final Collection<String> tableNames, final PreciseShardingValue<Long> shardingValue) {//根据id取模分表
        for (String each : tableNames) {//循环判断是否匹配
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                //匹配成功
                return each;
            }
        }
        //UnsupportedOperationException 是
        //java.lang.UnsupportedOperationException
        //这个是运行时异常，可以不用处理，如果不处理，会抛出这个异常。
        throw new UnsupportedOperationException();
    }
}