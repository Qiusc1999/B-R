CREATE PROCEDURE dbo.CombinedSearch
@searchbno varchar(max),
@searchbno1 varchar(max),
@searchbna varchar(max),
@searchbna1 varchar(max),
@searchbda varchar(max),
@searchbda1 varchar(max),
@searchbpu varchar(max),
@searchbpu1 varchar(max)
AS
BEGIN
DECLARE @BNO VARCHAR(20)
SET @BNO=''
DECLARE @RestNum int
SET @RestNum=0
DECLARE cursor_tborrow CURSOR FOR SELECT BNO FROM TBORROW WHERE RT IS NULL
DECLARE @sql varchar(max)
SET @sql='SELECT * FROM #Tab WHERE '+@searchbno+@searchbno1+@searchbna+@searchbna1+@searchbda+@searchbda1+@searchbpu+@searchbpu1

CREATE TABLE #Tab(
	BNO VARCHAR(20) NOT NULL PRIMARY KEY,
	BNA NVARCHAR(100),
	BDA NVARCHAR(100),
	BPU NVARCHAR(20),
	BPL NVARCHAR(100),
	REST int,
	BNU int,
	BPD date
)

INSERT INTO #Tab
SELECT BNO,BNA,BDA,BPU,BPL,BNU,BNU,BPD
FROM TBOOK

OPEN cursor_tborrow
FETCH NEXT FROM cursor_tborrow INTO @BNO

WHILE @@FETCH_STATUS<>-1
BEGIN
UPDATE #Tab
SET REST=REST-1
WHERE BNO=@BNO
FETCH NEXT FROM cursor_tborrow INTO @BNO
END

CLOSE cursor_tborrow
DEALLOCATE cursor_tborrow
EXEC (@sql)

END

