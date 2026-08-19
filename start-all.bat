@echo off
echo Starting Eureka Server...
start "Eureka" cmd /k "java -jar eureka-server\target\eureka-server-1.0.0.jar"

timeout /t 15 /nobreak

echo Starting User-Service...
start "User-Service" cmd /k "java -jar user-service\target\user-service-1.0.0.jar"

timeout /t 10 /nobreak

echo Starting Skill-Service...
start "Skill-Service" cmd /k "java -jar skill-service\target\skill-service-1.0.0.jar"

timeout /t 10 /nobreak

echo Starting Tracking-Service...
start "Tracking-Service" cmd /k "java -jar tracking-service\target\tracking-service-1.0.0.jar"

timeout /t 10 /nobreak

echo Starting API Gateway...
start "API-Gateway" cmd /k "java -jar api-gateway\target\api-gateway-1.0.0.jar"

echo All services started!