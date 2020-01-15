#!/bin/bash

readonly KEYSTORE_FILE="keystore.p12"
readonly KEYSTORE_PASSWORD="password"

keytool -list -keystore ${KEYSTORE_FILE} -storepass ${KEYSTORE_PASSWORD}
