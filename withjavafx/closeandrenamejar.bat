cd C:\Users\Owner\Documents\javaeditor\withjavafx\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
START /B /WAIT cmd.exe /c RENAME ForJava22_Main.jar HasJavaFX_ForJava22_Windows11x64.jar
java -jar HasJavaFX_ForJava22_Windows11x64.jar

