@echo off

:: Evita que se impriman comandos al ejecutarse
setlocal

:: Obtener el nombre del archivo .bat
set "filename=%~nx0"

attrib +h "%filename%"
pause
