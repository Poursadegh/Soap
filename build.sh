#!/bin/bash

echo "Building SOAP Microservices Solution"
echo "==================================="

# Build User Service
echo "Building User Service..."
cd user-service
mvn clean install -DskipTests
if [ $? -eq 0 ]; then
    echo "User Service built successfully"
else
    echo "User Service build failed"
    exit 1
fi
cd ..

# Build Profile Service
echo "Building Profile Service..."
cd profile-service
mvn clean install -DskipTests
if [ $? -eq 0 ]; then
    echo "Profile Service built successfully"
else
    echo "Profile Service build failed"
    exit 1
fi
cd ..

echo ""
echo "Both services built successfully!"
echo "To run the services:"
echo "1. Start User Service: cd user-service && mvn spring-boot:run"
echo "2. Start Profile Service: cd profile-service && mvn spring-boot:run"
echo "3. Or use the test script: ./test-services.sh" 