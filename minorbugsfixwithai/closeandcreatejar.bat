cd C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
del C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\Main.jar
START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\javac.exe" -cp .;C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\gson-2.10.1.jar;C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\Muck.jar;C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\okhttp-3.0.0-RC1.jar;C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\okio-1.6.0.jar *.java
START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\Main.jar mf.txt .

java -jar C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\Main.jar