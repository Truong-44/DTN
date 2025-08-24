use noithat;

CREATE TRIGGER trg_xoa_taikhoan
ON taikhoan
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DELETE FROM taikhoanlienket
    WHERE taikhoanid IN (SELECT id FROM deleted);

    DELETE FROM khachhang
    WHERE taikhoanid IN (SELECT id FROM deleted);

    DELETE FROM nhanvien
    WHERE taikhoanid IN (SELECT id FROM deleted);
END;
