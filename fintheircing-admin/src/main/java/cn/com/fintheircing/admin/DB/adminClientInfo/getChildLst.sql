drop FUNCTION  IF EXISTS `getChildLst`;
CREATE FUNCTION `getChildLst`(rootId INT) RETURNS varchar(1000) CHARSET utf8 COLLATE utf8_unicode_ci
BEGIN 
        DECLARE sTemp VARCHAR(1000); 
        DECLARE sTempChd VARCHAR(1000); 
 
        SET sTemp = '^'; 
        SET sTempChd =cast(rootId as CHAR); 
 
        WHILE sTempChd is not null DO 
            SET sTemp = concat(sTemp,',',sTempChd); 
            SELECT group_concat(uuid) INTO sTempChd FROM admin_client_info where FIND_IN_SET(boss_id,sTempChd)>0; 
        END WHILE; 
        RETURN sTemp; 
END;