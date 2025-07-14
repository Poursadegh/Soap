#!/bin/bash

echo "Testing SOAP Microservices Solution"
echo "=================================="

# Function to wait for service to be ready
wait_for_service() {
    local url=$1
    local service_name=$2
    echo "Waiting for $service_name to be ready..."
    
    for i in {1..30}; do
        if curl -s "$url" > /dev/null 2>&1; then
            echo "$service_name is ready!"
            return 0
        fi
        sleep 2
    done
    
    echo "$service_name failed to start"
    return 1
}

# Start User Service
echo "Starting User Service..."
cd user-service
mvn spring-boot:run > ../user-service.log 2>&1 &
USER_SERVICE_PID=$!
cd ..

# Wait for User Service
if wait_for_service "http://localhost:8080/actuator/health" "User Service"; then
    echo "User Service started successfully"
else
    echo "User Service failed to start"
    exit 1
fi

# Start Profile Service
echo "Starting Profile Service..."
cd profile-service
mvn spring-boot:run > ../profile-service.log 2>&1 &
PROFILE_SERVICE_PID=$!
cd ..

# Wait for Profile Service
if wait_for_service "http://localhost:8081/actuator/health" "Profile Service"; then
    echo "Profile Service started successfully"
else
    echo "Profile Service failed to start"
    exit 1
fi

echo ""
echo "Testing REST APIs..."
echo "==================="

# Test User Service APIs
echo "1. Creating a user..."
USER_RESPONSE=$(curl -s -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "email": "john.doe@example.com"}')

echo "User created: $USER_RESPONSE"

# Extract user ID from response
USER_ID=$(echo $USER_RESPONSE | grep -o '"id":[0-9]*' | cut -d':' -f2)
echo "User ID: $USER_ID"

echo ""
echo "2. Getting user by ID..."
curl -s -X GET "http://localhost:8080/users/$USER_ID"

echo ""
echo "3. Getting all users..."
curl -s -X GET http://localhost:8080/users

echo ""
echo "4. Creating a profile..."
PROFILE_RESPONSE=$(curl -s -X POST http://localhost:8081/profiles \
  -H "Content-Type: application/json" \
  -d "{\"userId\": $USER_ID, \"bio\": \"Software Developer\", \"location\": \"New York\", \"age\": 30}")

echo "Profile created: $PROFILE_RESPONSE"

# Extract profile ID from response
PROFILE_ID=$(echo $PROFILE_RESPONSE | grep -o '"id":[0-9]*' | cut -d':' -f2)
echo "Profile ID: $PROFILE_ID"

echo ""
echo "5. Getting profile with user data..."
curl -s -X GET "http://localhost:8081/profiles/$PROFILE_ID"

echo ""
echo "Testing SOAP Communication..."
echo "============================"

# Test SOAP endpoint
echo "6. Testing SOAP endpoint..."
curl -s -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:user="http://example.com/users">
       <soapenv:Header/>
       <soapenv:Body>
         <user:getUserByIdRequest>
           <user:id>'$USER_ID'</user:id>
         </user:getUserByIdRequest>
       </soapenv:Body>
     </soapenv:Envelope>'

echo ""
echo "Service URLs:"
echo "============="
echo "User Service REST API: http://localhost:8080"
echo "User Service Swagger: http://localhost:8080/swagger-ui.html"
echo "User Service SOAP WSDL: http://localhost:8080/ws/users.wsdl"
echo "Profile Service REST API: http://localhost:8081"
echo "Profile Service Swagger: http://localhost:8081/swagger-ui.html"

echo ""
echo "Press Ctrl+C to stop services"

# Wait for user to stop
trap "echo 'Stopping services...'; kill $USER_SERVICE_PID $PROFILE_SERVICE_PID; exit" INT
wait 