# Firebase Security Lab - Android Kotlin

Una aplicación Android en Kotlin para probar conexiones y operaciones de lectura/escritura en Firebase Realtime Database.

## Características

✅ **Interfaz de configuración de Firebase**
- Entrada de URL de base de datos Firebase
- Entrada de clave API

✅ **Prueba de conexión**
- Botón para probar la conexión a Firebase
- Feedback visual de éxito/error

✅ **Operaciones de lectura/escritura**
- **Write**: Escribir datos en Firebase
- **Read**: Leer datos desde Firebase
- Marca de tiempo automática
- ID único del dispositivo

## Requisitos

- Android API 24+
- Android Studio
- Firebase project configurado

## Instalación

1. Clonar el repositorio
2. Abrir en Android Studio
3. Sincronizar Gradle
4. Compilar y ejecutar

## Uso

1. **Configurar Firebase**
   - Ingrese la URL de su base de datos Firebase
   - Ingrese su clave API

2. **Probar conexión**
   - Toque el botón "Test Connection"
   - Verá el estado de la conexión

3. **Escribir datos**
   - Ingrese un nombre de campo
   - Ingrese un valor
   - Toque "Write"

4. **Leer datos**
   - Ingrese el nombre del campo a leer
   - Toque "Read"
   - Los datos se mostrarán en el estado

## Estructura del proyecto

```
app/
├── src/main/
│   ├── kotlin/com/example/firebasesecuritylab/
│   │   └── MainActivity.kt
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml
│   │   ├── drawable/
│   │   ├── values/
│   │   └── ...
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Dependencias

- Firebase Realtime Database
- Firebase Authentication
- AndroidX
- Material Design

## Licencia

MIT
