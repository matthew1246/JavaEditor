cd C:\Users\Owner\Documents\javaeditor\minorbugsfix\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
START /B /WAIT cmd.exe /c RENAME Main.jar HasJavaFX_ForJava23_Windows11x64.jar
java -jar Main.jar

