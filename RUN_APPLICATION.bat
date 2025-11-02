@echo off
echo ========================================
echo Student Management System
echo ========================================
echo.

echo Compiling...
javac -cp "lib/*" -d out src/main/java/com/student/*.java src/main/java/com/student/model/*.java src/main/java/com/student/dao/*.java src/main/java/com/student/controller/*.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Starting application...
echo.

java --module-path "C:\Users\eedup\Downloads\openjfx-25.0.1_windows-x64_bin-sdk\javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp "out;src/main/resources;lib/mysql-connector-j-9.5.0.jar" com.student.Main

pause

