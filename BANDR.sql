CREATE DATABASE BANDR
GO

CREATE TABLE TGDINFO(					--用户级别信息
UGD nvarchar(20) NOT NULL PRIMARY KEY,	--借书证号（最长10位）
UAT int,								--借期
UUP int,								--借书上限
)

CREATE TABLE TUSER(						--用户表
UNO varchar(20) NOT NULL PRIMARY KEY,	--借书证号
UNA nvarchar(40),						--姓名（最长20位）
UDE nvarchar(20),						--系别
USP nvarchar(20),						--专业
UGD nvarchar(20) NOT NULL,				--级别
PWD nvarchar(32),						--密码（最长16位）
CONSTRAINT UGD_FK FOREIGN KEY(UGD) REFERENCES TGDINFO(UGD),	--设置外键 UGD为TGDINFO主键
)

CREATE TABLE TBOOK(						--图书表
BNO varchar(20) NOT NULL PRIMARY KEY,	--图书编号
BNA nvarchar(100),						--图书名称
BDA nvarchar(20),								--出版日期
BPU nvarchar(100),						--图书出版社
BPL nvarchar(100),						--图书存放位置
BNU int,								--图书总数量
BPD date								--图书上架日期
)

CREATE TABLE TBORROW(		--借阅表
UNO varchar(20) NOT NULL,	--借书证号
BNO varchar(20) NOT NULL,	--图书编号
BT datetime,				--借书时间
RT datetime,				--还书时间
WOM int DEFAULT 0 NOT NULL,	--是否欠款（默认0 0-未产生欠款 1-产生欠款未缴费 2-欠款已归还）Whether owe money
PRIMARY KEY (UNO,BNO,BT),		--主码（UNO,BNO,BT）
CONSTRAINT UNO_FK FOREIGN KEY(UNO) REFERENCES TUSER(UNO),	--设置外键 UNO为TUSER主键
CONSTRAINT BNO_FK FOREIGN KEY(BNO) REFERENCES TBOOK(BNO)	--设置外键 BNO为TBOOK主键
)

CREATE TABLE TADMIN(					--管理员信息表
ANO varchar(20) NOT NULL PRIMARY KEY,	--管理员编号（最长10位）
ANA nvarchar(40),						--管理员姓名（最长20位）
PWD nvarchar(32)						--密码（最长16位）
)
INSERT INTO TADMIN VALUES ('1','高级管理员','1')

INSERT INTO TGDINFO VALUES ('本科生',60,20)
INSERT INTO TGDINFO VALUES ('硕士研究生',60,30)
INSERT INTO TGDINFO VALUES ('博士研究生',60,40)
INSERT INTO TGDINFO VALUES ('教师',60,60)
