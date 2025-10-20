cd C:\Users\Owner\Documents\javaeditor\withjavafx\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
del C:\Users\Owner\Documents\javaeditor\withjavafx\Main.jar
START /B /WAIT cmd.exe /c javac --release 22 -cp C:\Users\Owner\Documents\javaeditor\withjavafx\gson-2.10.1.jar;C:\Users\Owner\Documents\javaeditor\withjavafx\MuckFX.jar;. *.java
START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm C:\Users\Owner\Documents\javaeditor\HasJavaFX_ForJava22_Windows11x64.jar mf.txt .
START /B /WAIT cmd.exe /c javac --release 23 -cp C:\Users\Owner\Documents\javaeditor\withjavafx\gson-2.10.1.jar;C:\Users\Owner\Documents\javaeditor\withjavafx\MuckFX.jar;. *.java
START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm C:\Users\Owner\Documents\javaeditor\HasJavaFX_ForJava23_Windows11x64.jar mf.txt .
