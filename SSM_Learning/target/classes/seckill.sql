--执行秒杀的存储过程
  DELIMITER $$  --把console的隔离符；转换成$$
--定义一个存储过程
--参数 in 表示输入参数；out 表示输出参数。
--row_count()表示修改上一条操作影响的行数。
--row_count()。1.如果返回0，表示未修改数据。2.如果返回1，表示修改一条数据。3.<0.sql错误。



----
CREATE PROCEDURE `seckill`.`execute_seckill`
  (in v_seckill_id bigint,in v_phone bigint,
    in v_kill_time timestamp ,out r_result int)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION ;
    INSERT ignore INTO success_killed
      (seckill_id,user_phone,create_time,state)
      VALUES(v_seckill_id,v_phone,v_kill_time,0);
    SELECT ROW_COUNT() INTO insert_count;
    IF(insert_count=0) THEN
      ROLLBACK;
      SET r_result=-1;
    ELSEIF(insert_count<0) THEN
      ROLLBACK;
      SET  r_result=-2;
    ELSE
      UPDATE seckill
      SET number=number-1
      WHERE seckill_id=v_seckill_id
      AND end_time > v_kill_time
      AND start_time<v_kill_time
      AND number>0;
      SELECT  ROW_COUNT () INTO insert_count;
      IF (insert_count=0) THEN
        ROLLBACK;
        SET r_result=-1;
      ELSEIF(insert_count<0) THEN
        ROLLBACK;
        SET r_result=-2;
      ELSE
        COMMIT;
        SET  r_result=1;
      END IF;
    END IF;
  END;
$$
--存储过程定义结束。
DELIMITER ;
set @r_result=-3;

call execute_seckill(1003,13255446633,now(),@r_result);

--获取结果
SELECT @r_result;


