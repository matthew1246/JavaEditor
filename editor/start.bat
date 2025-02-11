@echo off
if exist Main.jar (
	echo java -jar Main.jar
	java -jar Main.jar
) else (
	for /l %%x in (23,-1,1) do (
		if exist ForJava%%x_Main.jar (
			echo java -jar ForJava%%x_Main.jar
			java -jar ForJava%%x_Main.jar
			break
		)
	)
) 



