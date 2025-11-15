@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script
@REM
@REM Required ENV vars:
@REM JAVA_HOME - location of a JDK home dir
@REM
@REM Optional ENV vars
@REM M2_HOME - location of maven2's installed home dir
@REM MAVEN_BATCH_ECHO - set to 'on' to enable the echoing of the batch commands
@REM MAVEN_BATCH_PAUSE - set to 'on' to wait for a keystroke before ending
@REM MAVEN_OPTS - parameters passed to the Java VM when running Maven
@REM     e.g. to debug Maven itself, use
@REM set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
@REM MAVEN_SKIP_RC - flag to disable loading of mavenrc files
@REM ----------------------------------------------------------------------------

@REM Begin all REM lines with '@' in case MAVEN_BATCH_ECHO is 'on'
@echo off
@REM set title of command window
title %0
@REM enable echoing by setting MAVEN_BATCH_ECHO to 'on'
@if "%MAVEN_BATCH_ECHO%" == "on"  echo %MAVEN_BATCH_ECHO%

@REM set %HOME% to equivalent of $HOME
if "%HOME%" == "" (set "HOME=%HOMEDRIVE%%HOMEPATH%")

@REM Execute a user defined script before this one
if not "%MAVEN_SKIP_RC%" == "" goto skipRcPre
@REM check for pre script, once with legacy .bat ending and once with .cmd ending
if exist "%HOME%\mavenrc_pre.bat" call "%HOME%\mavenrc_pre.bat" %*
if exist "%HOME%\mavenrc_pre.cmd" call "%HOME%\mavenrc_pre.cmd" %*
:skipRcPre

@setlocal

set ERROR_CODE=0

@REM To isolate internal variables from possible post scripts, we use another setlocal
@setlocal

@if "%M2_HOME%"=="" goto initM2Home

@REM M2_HOME is set, so we can use it to set MAVEN_HOME
set "MAVEN_HOME=%M2_HOME%"

goto checkMHome

:initM2Home

@REM M2_HOME is not set, try to derive it from the location of the mvn executable
@REM as it might be on the PATH
set "MAVEN_CMD_LINE_ARGS=%*"
set MAVEN_CMD_LINE_ARGS=%MAVEN_CMD_LINE_ARGS:-Dmaven.ext.class.path=%
for %%i in ("%MAVEN_CMD_LINE_ARGS:-Dmaven.ext.class.path=%") do (
  if "%%i" == "-Dmaven.ext.class.path=" (
    set MAVEN_CMD_LINE_ARGS=
  ) else (
    if "%%i" == "-Dmaven.ext.class.path" (
      set MAVEN_CMD_LINE_ARGS=
    )
  )
)
set "MAVEN_JAVA_EXE=%JAVA_HOME%\bin\java.exe"
set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@REM Check if Maven is installed in the system path
where mvn.cmd >nul 2>nul
if %ERRORLEVEL% EQU 0 (
  for /f "tokens=* delims=" %%i in ('where mvn.cmd') do set "MAVEN_CMD=%%i"
  for %%i in ("%MAVEN_CMD%\..\..") do set "MAVEN_HOME=%%~fi"
)

:checkMHome

@REM If MAVEN_HOME is still not set, try to find it from the current directory
if "%MAVEN_HOME%"=="" (
  if exist "%MAVEN_PROJECTBASEDIR%\mvnw.cmd" (
    set "MAVEN_HOME=%MAVEN_PROJECTBASEDIR%"
  )
)

@REM If MAVEN_HOME is still not set, try to find it from the parent directory
if "%MAVEN_HOME%"=="" (
  if exist "%MAVEN_PROJECTBASEDIR%\..\mvnw.cmd" (
    set "MAVEN_HOME=%MAVEN_PROJECTBASEDIR%\.."
  )
)

@REM If we still don't have MAVEN_HOME, give up
if "%MAVEN_HOME%"=="" (
  echo Error: Maven not found in the system path and MAVEN_HOME is not set.
  echo Please set MAVEN_HOME or add Maven to your system path.
  set ERROR_CODE=1
  goto end
)

@REM Resolve any .. and .
for %%i in ("%MAVEN_HOME%\bin\mvn.cmd") do set "MAVEN_CMD=%%~fi"
if not exist "%MAVEN_CMD%" (
  echo Error: Could not find mvn.cmd in %MAVEN_HOME%\bin
  set ERROR_CODE=1
  goto end
)

@REM Set up the MAVEN_HOME environment variable
set "MAVEN_HOME=%MAVEN_HOME%"

:end

if not "%MAVEN_SKIP_RC%" == "" goto skipRcPost
@REM check for post script, once with legacy .bat ending and once with .cmd ending
if exist "%HOME%\mavenrc_post.bat" call "%HOME%\mavenrc_post.bat"
if exist "%HOME%\mavenrc_post.cmd" call "%HOME%\mavenrc_post.cmd"
:skipRcPost

@REM Restore original directory and exit
cd /d "%CD%"
if %ERRORLEVEL% NEQ 0 exit /b %ERRORLEVEL%

@REM Execute Maven
"%MAVEN_CMD%" %MAVEN_CMD_LINE_ARGS% %*
