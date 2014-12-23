#!/bin/bash

clear

host="http://localhost:8080/rs/telemetry/api/"

CLIENT_CERTIFICATE=''

ENTITLEMENT_DATA=''

ENTITLEMENT_SIG=''

curl -vk -H "X-RH-Client-Certificate: $CLIENT_CERTIFICATE" -H "X-RH-Entitlement-Data: $ENTITLEMENT_DATA" -H "X-RH-Entitlement-Sig: $ENTITLEMENT_SIG" $host


