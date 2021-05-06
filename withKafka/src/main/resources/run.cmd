@REM FOR PRODUCER
@REM compile:   javac -cp “/path/to/kafka/kafka_2.11-0.9.0.0/lib/*” *.java
@REM run:       java -cp “/path/to/kafka/kafka_2.11-0.9.0.0/lib/*”:. SimpleProducer <topic-name>

@REM FOR CONSUMER
@REM compile:   javac -cp “/path/to/kafka/kafka_2.11-0.9.0.0/lib/*” *.java
@REM run:       java -cp “/path/to/kafka/kafka_2.11-0.9.0.0/lib/*”:. SimpleConsumer <topic-name>

@REM FOR GROUP_CONSUMER
@REM compile:   javac -cp “/path/to/kafka/kafka_2.11-0.9.0.0/libs/*" ConsumerGroup.java
@REM run:       >>java -cp “/path/to/kafka/kafka_2.11-0.9.0.0/libs/*":. ConsumerGroup <topic-name> my-group
@REM            >>java -cp "/home/bala/Workspace/kafka/kafka_2.11-0.9.0.0/libs/*":. ConsumerGroup <topic-name> my-group

@echo off

set CLASSPATH="C:\Project\myKafka\target\classes";

for %%i in (C:\kafka_2.11-1.1.0\libs\*) do (
	call :concat "%%i"
)

set COMMAND=java -classpath %CLASSPATH% %*
%COMMAND%

:concat
IF not defined CLASSPATH (
  set CLASSPATH="%~1"
) ELSE (
  set CLASSPATH=%CLASSPATH%;"%~1"
)