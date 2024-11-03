--- (a) Mostrar el nombre y la localidad de todos los departamentos  ---
SELECT nombredeptno, localidad FROM departamentos;

--- (b) Mostrar los departamentos que no se encuentren en 'Madrid' ---
SELECT * FROM Departamentos WHERE localidad != 'Madrid';

--- (c) Mostrar los datos de los empleados que no pertenezcan a departamentos localizados en 'Madrid' y que cobren de sueldo más de 2000 € ---
SELECT * FROM Empleados
JOIN Departamentos ON Empleados.deptno = Departamentos.deptno
WHERE Departamentos.localidad != 'Madrid' AND Empleados.salario > 2000;

--- (e) Mostrar la media de los salarios calculados por departamento. ---
SELECT Departamentos.nombredeptno, AVG(salario) FROM Empleados
JOIN Departamentos ON Empleados.deptno = Departamentos.deptno
GROUP BY Empleados.deptno, Departamentos.nombredeptno;

