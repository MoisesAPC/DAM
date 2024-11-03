SELECT * FROM Productos;
SELECT * FROM Proveedores;

SELECT prod.Nombre_prod, prov.Nombre_prov FROM Productos prod JOIN Proveedores prov ON prod.Cod_prov = prov.Cod_prov WHERE prod.Precio > 2000 ORDER BY prod.Precio DESC;
SELECT prov.Nombre_prov, prov.Telefono FROM Proveedores prov JOIN Productos prod ON prov.Cod_prov = prod.Cod_prov WHERE LOWER(prod.Nombre_prod) LIKE '%ordenador%';
SELECT prod.Nombre_prod FROM Productos prod WHERE prod.Stock < 20;
UPDATE Productos SET Precio = Precio * 0.5 WHERE Cod_prov IN (SELECT Cod_prov FROM Proveedores WHERE Bonifica = 0);
SELECT prov.Nombre_prov, COUNT(prod.Cod_prod) AS Num_Productos, AVG(prod.Precio) AS Media_Precios FROM Proveedores prov JOIN Productos prod ON prov.Cod_prov = prod.Cod_prov GROUP BY prov.Nombre_prov;
SELECT prov.Nombre_prov, prov.Direccion, prov.Telefono FROM Proveedores prov JOIN Productos prod ON prov.Cod_prov = prod.Cod_prov WHERE prod.Stock = (SELECT MAX(Stock) FROM Productos);