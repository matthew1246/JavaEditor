cd C:\Users\Owner\Documents\javaeditor\minorbugsfixlinux\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
del C:\Users\Owner\Documents\javaeditor\minorbugsfixlinux\Main.jar
START /B /WAIT cmd.exe /c javac -cp .;C:\Users\Owner\Documents\javaeditor\minorbugsfixlinux\gson-2.10.1.jar;C:\Users\Owner\Documents\javaeditor\minorbugsfixlinux\Muck.jar;C:\Users\Owner\Documents\javaeditor\minorbugsfixlinux\okhttp-3.0.0-RC1.jar;C:\Users\Owner\Documents\javaeditor\minorbugsfixlinux\okio-1.6.0.jar *.java
START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm C:\Users\Owner\Documents\javaeditor\minorbugsfixlinux\Main.jar mf.txt .

"C:\Program Files\Java\jdk-23\bin\java.exe" -jar C:\Users\Owner\Documents\javaeditor\minorbugsfixlinux\Main.jar