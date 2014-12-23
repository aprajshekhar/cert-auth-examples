#!/bin/bash
clear
host="http://localhost:8080/rs/telemetry/api/"
CLIENT_CERTIFICATE_V1='' #client cert and entitlement data

 curl  -vk -H "X-RH-Client-Certificate: $CLIENT_CERTIFICATE_V1" $host


