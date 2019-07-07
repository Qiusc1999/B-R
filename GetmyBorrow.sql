CREATE PROCEDURE dbo.GetmyBorrowInfo
@UNO varchar(max),
@nowAccount varchar(max),
@search varchar(max),
@search_dateBT varchar(max),
@search_dateRT varchar(max),
@screentype  varchar(max),
@orderby varchar(max),
@uat int
AS
BEGIN
DECLARE @UNO_c varchar(20)
SET @UNO_c=''
DECLARE @BNO_c varchar(20)
SET @BNO_c=''
DECLARE @BNA_c nvarchar(100)
SET @BNA_c=''
DECLARE @BT_c date
SET @BT_c = null
DECLARE @RT_c date
SET @RT_c = null
DECLARE @BT_show varchar(100)
SET @BT_show=''
DECLARE @RT_show varchar(100)
SET @RT_show=''
DECLARE @WOM_c int
SET @WOM_c=0
DECLARE @BTtoNOW_c int	---用于计算
SET @BTtoNOW_c=0
DECLARE @BTtoRT_c int	---用于计算
SET @BTtoRT_c=0
DECLARE @BTtoNOW_show varchar(20)
SET @BTtoNOW_show=''---用于table显示
DECLARE @FINE_show money
SET @FINE_show=0
DECLARE @WetherOweMoney_show varchar(100)
SET @WetherOweMoney_show=''
DECLARE cursor_tborrow CURSOR FOR SELECT TBORROW.UNO,TBORROW.BNO,TBOOK.BNA,BT,RT,DATEDIFF(DAY,BT,GETDATE()),DATEDIFF(DAY,BT,RT),WOM FROM TBORROW,TBOOK WHERE TBORROW.BNO=TBOOK.BNO
DECLARE @sql varchar(max)
SET @sql='SELECT * FROM #Tab WHERE '+@nowAccount+@search+@search_dateBT+@search_dateRT+@screentype+@orderby

CREATE TABLE #Tab(
	UNO varchar(20) NOT NULL,
	BNO varchar(20) NOT NULL,
	BNA nvarchar(100),
	BT varchar(100),
	RT varchar(100),
	BTtoNOW varchar(20),
	FINE money,
	WOM varchar(100),
	PRIMARY KEY(UNO,BNO,BT)
)

OPEN cursor_tborrow
FETCH NEXT FROM cursor_tborrow INTO @UNO_c,@BNO_c,@BNA_C,@BT_c,@RT_c,@BTtoNOW_c,@BTtoRT_c,@WOM_c


WHILE @@FETCH_STATUS<>-1
BEGIN
SET @FINE_show=0
SET @BT_show=CONVERT(VARCHAR,@BT_c,120)

IF @WOM_c=0 AND @RT_c is null
BEGIN
SET @FINE_show=(@BTtoNOW_c-@uat)*0.02
END
ELSE IF @WOM_c=0 AND @RT_c is not null
BEGIN
SET @BTtoNOW_show='已归还'
END
ELSE IF @WOM_c=1
BEGIN
SET @FINE_show=(@BTtoNOW_c-@uat)*0.02
SET @BTtoNOW_show=CAST(@BTtoNOW_c AS varchar)
END
ELSE IF @WOM_c=2
BEGIN
SET @BTtoNOW_show='已缴费'
END

IF @WOM_c=0
BEGIN
SET @WetherOweMoney_show='无欠款'
END
ELSE IF @WOM_c=1
BEGIN
SET @WetherOweMoney_show='未缴费'
END
ELSE IF @WOM_c=1
BEGIN
SET @WetherOweMoney_show='已缴费'
END

IF(@FINE_show<=0)BEGIN SET @FINE_show=0 END

IF @RT_c is null
BEGIN
SET @RT_show='未归还'
END
ELSE
BEGIN
SET @RT_show=CONVERT(varchar,@RT_c,120)
END


INSERT INTO #Tab
SELECT @UNO_c,@BNO_c,@BNA_C,@BT_show,@RT_show ,@BTtoNOW_show ,@FINE_show ,@WetherOweMoney_show


FETCH NEXT FROM cursor_tborrow INTO @UNO_c,@BNO_c,@BNA_C,@BT_c,@RT_c,@BTtoNOW_c,@BTtoRT_c,@WOM_c
END

CLOSE cursor_tborrow
DEALLOCATE cursor_tborrow

EXEC (@sql)
END




EXEC GetmyBorrowInfo  '1',' TUSER.UNO = ''1'' ' , ' TBOOK.BNA LIKE ''%%'' ' ,'','','','',60
EXEC GetmyBorrowInfo  '1',  ' UNO = ''1'' ' , ' AND BNA LIKE ''%%'' ' ,'','','','',60