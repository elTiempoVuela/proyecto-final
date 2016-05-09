
ionic build --release android

keytool.exe -genkey -v -keystore my-release-key.keystore -alias mati -keyalg RSA -keysize 2048 -validity 10000

#1234Fea
jarsigner.exe -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore my-release-key.keystore platforms\android\build\outputs\apk\android-release-unsigned.apk mati


jarsigner.exe -verify -verbose -certs platforms\android\build\outputs\apk\android-release-unsigned.apk


set path       ---->>>     ...sdk-windows\build-tools\23.0.1

zipalign.exe -f 4 platforms\android\build\outputs\apk\android-release-unsigned.apk platforms\android\build\outputs\apk\PetsABC.apk