# mysql binlog to hive by kafka-debezium Control Server


##### build
```bash
gradle :cdc-server:build
```

#### run
```
# 运行
java -jar -Dfile.encoding=utf-8 -Djava.awt.headless=true -Duser.timezone=GMT+08 cdc-server-1.0.jar cdc.sample.properties >/tmp/cdc_start.log 2>&1 &
```