echo "path = $GITHUB_WORKSPACE"
echo "storeFile = $STORE_FILE_KEYSTORE" > release_keystore.properties
echo "storePassword = $STORE_PASSWORD_KEYSTORE" >> release_keystore.properties
echo "keyAlias = $KEY_ALIAS_KEYSTORE" >> release_keystore.properties
echo "keyPassword = $KEY_PASSWORD_KEYSTORE" >> release_keystore.properties
echo "release_keystore = $(cat release_keystore.properties)"
echo "release_keystore_path = $(realpath release_keystore.properties)"