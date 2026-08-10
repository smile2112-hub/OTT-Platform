@echo off
REM ----------------------------------------------------------------------------
REM Maven Wrapper (Windows)
REM ----------------------------------------------------------------------------
@echo off
setlocal
pushd %~dp0 >nul
set BASEDIR=%CD%
popd >nul
set WRAPPER_JAR=%BASEDIR%\.mvn\wrapper\maven-wrapper.jar

if exist "%WRAPPER_JAR%" (
  java -Dmaven.multiModuleProjectDirectory="%BASEDIR%" -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
) else (
  echo Maven wrapper JAR not found: %WRAPPER_JAR%
  exit /b 1
)
