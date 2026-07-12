cd C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
del C:\Users\Owner\Documents\javaeditor\minorbugsfixwithai\Main.jar
START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm Main.jar mf.txt .
java -jar Main.jar

