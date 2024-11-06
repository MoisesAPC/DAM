INSERT INTO Empleados VALUES ('12345678X', 'Pepe', 'Maspalomas', 'Vendedor', 202);
INSERT INTO Empleados VALUES ('98765432T', 'Manolo', 'Las Palmas', 'Comercial', 500);
INSERT INTO Empleados VALUES ('98759732Z', 'Marisa', 'Arucas', 'Coordinador', 500);
INSERT INTO Empleados VALUES ('77889944Y', 'Lucía', 'Arinaga', 'Vendedor', 666);
INSERT INTO Empleados VALUES ('33221166B', 'María', 'Agaete', 'Supervisor', 202);
INSERT INTO Empleados VALUES ('98715432A', 'Luis', 'Teror', 'Director', 500);
INSERT INTO Empleados VALUES ('77887944Q', 'Juan', 'La Garita', 'Vendedor', 666);
INSERT INTO Empleados VALUES ('17887944B', 'Dámaso', 'Moya', 'Director', 358);
INSERT INTO Empleados VALUES ('47887944K', 'Juana', 'Moya', 'Comercial', 987);

SELECT * FROM Empleados;

SELECT emple.Nombre, emple.Direccion FROM Empleados emple JOIN Oficinas ofi ON emple.Cod_ofi = ofi.Cod_ofi WHERE emple.cargo = 'Vendedor' AND ofi.Localidad = 'Telde' ORDER BY emple.Nombre DESC;

SELECT ofi.Nombre, COUNT(*) FROM Oficinas ofi JOIN Empleados emple ON ofi.Cod_ofi = emple.Cod_ofi WHERE ofi.Cod_ofi = emple.Cod_ofi GROUP BY ofi.Nombre;

SELECT * FROM Empleados;
DELETE FROM Empleados WHERE Cod_ofi IN (SELECT Cod_ofi FROM Oficinas WHERE Localidad = 'Telde');
SELECT * FROM Empleados;