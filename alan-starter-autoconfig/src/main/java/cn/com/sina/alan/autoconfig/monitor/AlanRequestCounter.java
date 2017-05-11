package cn.com.sina.alan.autoconfig.monitor;

import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wanghongfei on 27/04/2017.
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "alan.auto.monitor", name = "flow", havingValue = "true", matchIfMissing = false)
public class AlanRequestCounter {
    public static final Logger log = LoggerFactory.getLogger(AlanRequestCounter.class);

    @Autowired
    private InfluxDB influxDB;

    @Autowired
    private InfluxdbConnectionProperties influxdbConnectionProperties;

    public static AtomicLong reqCount = new AtomicLong(0);
    public static AtomicLong failedCount = new AtomicLong(0);

    /**
     * 每秒执行
     */
    @Scheduled(cron = "${alan.auto.monitor.rate:0/1 * * * * ?}")
    public void resetCounter() {
        try {
            sendData(influxDB, influxdbConnectionProperties.getDatabase());

        } catch (Exception e) {
            log.warn("统计数据写入失败: {}", e.getMessage());

        }

        // 计数清零
        reqCount.set(0);
        failedCount.set(0);
    }

    private void sendData(InfluxDB db, String dbName) {
        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                //.tag("async", "true")
                .retentionPolicy("autogen")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();


        //Random random = new Random();
        Point point1 = Point.measurement("requests")
                //.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("total", AlanRequestCounter.reqCount.longValue())
                .addField("failed", failedCount.longValue())
                //.addField("failed", random.nextInt(500))
                //.addField("total", random.nextInt(1000))
                .build();

        batchPoints.point(point1);

        db.write(batchPoints);
    }
}
