CREATE DATABASE BANDR
GO

CREATE TABLE TGDINFO(					--�û�������Ϣ
UGD nvarchar(20) NOT NULL PRIMARY KEY,	--����֤�ţ��10λ��
UAT int,								--����
UUP int,								--��������
)

CREATE TABLE TUSER(						--�û���
UNO varchar(20) NOT NULL PRIMARY KEY,	--����֤��
UNA nvarchar(40),						--�������20λ��
UDE nvarchar(20),						--ϵ��
USP nvarchar(20),						--רҵ
UGD nvarchar(20) NOT NULL,				--����
PWD nvarchar(32),						--���루�16λ��
CONSTRAINT UGD_FK FOREIGN KEY(UGD) REFERENCES TGDINFO(UGD),	--������� UGDΪTGDINFO����
)

CREATE TABLE TBOOK(						--ͼ���
BNO varchar(20) NOT NULL PRIMARY KEY,	--ͼ����
BNA nvarchar(100),						--ͼ������
BDA nvarchar(20),								--��������
BPU nvarchar(100),						--ͼ�������
BPL nvarchar(100),						--ͼ����λ��
BNU int,								--ͼ��������
BPD date								--ͼ���ϼ�����
)

CREATE TABLE TBORROW(		--���ı�
UNO varchar(20) NOT NULL,	--����֤��
BNO varchar(20) NOT NULL,	--ͼ����
BT datetime,				--����ʱ��
RT datetime,				--����ʱ��
WOM int DEFAULT 0 NOT NULL,	--�Ƿ�Ƿ�Ĭ��0 0-δ����Ƿ�� 1-����Ƿ��δ�ɷ� 2-Ƿ���ѹ黹��Whether owe money
PRIMARY KEY (UNO,BNO,BT),		--���루UNO,BNO,BT��
CONSTRAINT UNO_FK FOREIGN KEY(UNO) REFERENCES TUSER(UNO),	--������� UNOΪTUSER����
CONSTRAINT BNO_FK FOREIGN KEY(BNO) REFERENCES TBOOK(BNO)	--������� BNOΪTBOOK����
)

CREATE TABLE TADMIN(					--����Ա��Ϣ��
ANO varchar(20) NOT NULL PRIMARY KEY,	--����Ա��ţ��10λ��
ANA nvarchar(40),						--����Ա�������20λ��
PWD nvarchar(32)						--���루�16λ��
)
INSERT INTO TADMIN VALUES ('1','�߼�����Ա','1')

INSERT INTO TGDINFO VALUES ('������',60,20)
INSERT INTO TGDINFO VALUES ('˶ʿ�о���',60,30)
INSERT INTO TGDINFO VALUES ('��ʿ�о���',60,40)
INSERT INTO TGDINFO VALUES ('��ʦ',60,60)
