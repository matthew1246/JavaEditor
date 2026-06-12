cd C:\Users\Owner\Documents\javaeditor\withjavafx\
START /B /WAIT taskkill /F /im java.exe
START /B /WAIT taskkill /F /im javaw.exe
del C:\Users\Owner\Documents\javaeditor\withjavafx\Main.jar
START /B /WAIT cmd.exe /c jar cfm Main.jar mf.txt .
java -jar Main.jar

