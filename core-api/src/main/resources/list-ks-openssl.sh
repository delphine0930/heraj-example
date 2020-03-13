#!/bin/bash

readonly KEYSTORE_FILE="keystore.p12"

openssl pkcs12 -info -in ${KEYSTORE_FILE}
