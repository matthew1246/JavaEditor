cd C:\javaeditor\minorbugsfixwithai\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
del C:\javaeditor\minorbugsfixwithai\Main.jar
START /B /WAIT cmd.exe /c "C:\Program Files\MatthewJavaEditor\runtime\bin\javac.exe" *.java
START /B /WAIT cmd.exe /c "C:\Program Files\MatthewJavaEditor\runtime\bin\jar.exe" cfm C:\javaeditor\minorbugsfixwithai\Main.jar mf.txt .

java -jar C:\javaeditor\minorbugsfixwithai\Main.jar