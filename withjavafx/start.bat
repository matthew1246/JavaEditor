@echo off
if exist HasJavaFX_ForJava23_Windows11x64.jar (
	echo java -jar HasJavaFX_ForJava23_Windows11x64.jar
	java -jar HasJavaFX_ForJava23_Windows11x64.jar
) else if exist HasJavaFX_ForJava22_Windows11x64.jar (
	echo java -jar HasJavaFX_ForJava22_Windows11x64.jar
	java -jar HasJavaFX_ForJava22_Windows11x64.jar
) else if exist Main.jar (
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



