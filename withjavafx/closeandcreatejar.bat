cd C:\Users\Owner\Documents\javaeditor\withjavafx\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
del C:\Users\Owner\Documents\javaeditor\withjavafx\Main.jar
START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\javac.exe" -cp .;C:\Users\Owner\Documents\javaeditor\withjavafx\gson-2.10.1.jar;C:\Users\Owner\Documents\javaeditor\withjavafx\MuckFX.jar;C:\Users\Owner\Documents\javaeditor\withjavafx\okhttp-3.0.0-RC1.jar;C:\Users\Owner\Documents\javaeditor\withjavafx\okio-1.6.0.jar *.java
START /B /WAIT cmd.exe /c jar cfm C:\Users\Owner\Documents\javaeditor\withjavafx\Main.jar mf.txt .

java -jar C:\Users\Owner\Documents\javaeditor\withjavafx\Main.jar
