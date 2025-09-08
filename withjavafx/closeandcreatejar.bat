cd C:\Users\Owner\Documents\javaeditor\withjavafx\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
del C:\Users\Owner\Documents\javaeditor\withjavafx\HasJavaFX_ForJava23_Windows11x64.jar
START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm ForJava22_Main.jar mf.txt .
java -jar ForJava22_Main.jar

