# CostoGasolina: agregar, mostrar, modificar y eliminar

## Abrir el proyecto

1. Extrae el ZIP en una carpeta nueva para conservar tu proyecto anterior.
2. En Android Studio selecciona File > Open y elige la carpeta
   `CostoGasolina-navigation_view`, donde está `settings.gradle.kts`.
3. Espera a que termine la sincronización de Gradle.
4. Selecciona un emulador o tu teléfono y presiona Run (triángulo verde).

La configuración y las versiones de Gradle y las dependencias son las que
venían en tu ZIP. Android Studio necesita internet para descargar las que
no tenga. La app utiliza una base de datos local; las operaciones de registros
no necesitan internet.

## Probar las cuatro operaciones

1. Agregar: presiona +, escribe precio 24 y litros 10. Debe mostrar un total
   de 240 en la moneda configurada en el dispositivo. Presiona Guardar Registro.
2. Mostrar: al volver al historial debe aparecer la carga. Cierra y vuelve
   a abrir la app para comprobar que el registro quedó guardado.
3. Modificar: presiona el lápiz de esa carga. Los campos deben tener sus
   valores anteriores. Cambia los litros a 20 y guarda; el total debe ser 480.
   Se modifica la misma carga; no debe aparecer una segunda tarjeta.
4. Eliminar: presiona la papelera. Comprueba los datos y presiona Cancelar:
   el registro debe seguir en el historial. Repite y presiona Eliminar:
   la tarjeta debe desaparecer.
5. Agrega también una carga con propina y kilómetros. Edita esos valores
   y comprueba que se actualizan. Al desactivar una opción se guarda en cero.

La fecha original se conserva al editar. Los botones de guardar se habilitan
cuando precio y litros son mayores que cero.

## Qué hace cada archivo

| Archivo | Función |
| --- | --- |
| `room/GasolineDao.kt` | Instrucciones que Room usa para insertar, consultar, actualizar y eliminar. |
| `viewmodel/GasolineViewModel.kt` | Recibe las acciones de las pantallas y llama al DAO. |
| `navigation/NavManager.kt` | Conecta las pantallas y pasa el ID del registro seleccionado. |
| `ui/views/HomeView.kt` | Muestra las cargas y los botones +, lápiz y papelera. |
| `ui/views/AddView.kt` | Formulario para agregar una carga. |
| `ui/views/EditView.kt` | Consulta una carga por ID, llena el formulario y guarda sus cambios. |
| `ui/views/DeleteView.kt` | Muestra la carga y permite confirmar o cancelar su eliminación. |

Estas rutas están dentro de `app/src/main/java/com/mexiti/costogasolina/`.

## Las partes nuevas, explicado sencillo

- `id`: número único que identifica cada registro. Lo genera Room.
- `getTransactionById(id)`: busca únicamente el registro seleccionado.
- `@Update`: actualiza el registro cuyo ID coincide con el objeto recibido.
- `@Delete`: elimina el registro cuyo ID coincide con el objeto recibido.
- `transaction.copy(...)`: crea la versión modificada del registro y conserva
  su ID y fecha. Así se actualiza el original en lugar de insertar otro.
- `LaunchedEffect(transactionId)`: permite consultar Room al entrar a la pantalla.
  Mientras se obtiene el registro se muestra un indicador de carga.
- `viewModelScope.launch`: ya se usaba para insertar. También se utiliza para
  actualizar y eliminar sin bloquear la interfaz.
- `onComplete`: una función que se ejecuta cuando Room termina. En las pantallas
  contiene `navController.popBackStack()` para regresar al historial.
- `collectAsState()`: ya estaba en HomeView. Escucha la lista de Room y hace
  que el historial se actualice al agregar, modificar o eliminar.

## Otros ajustes pequeños

- Los campos numéricos usan teclado decimal para introducir precios y litros.
- La etiqueta de kilómetros ahora dice “Kilómetros”; antes decía “100000”.
- Se corrigió NuevoMontoUITest: 24 por 10 da 240, no 440.
- Las pruebas existentes conservan los argumentos de CostGasLayout que ya
  habías corregido.
- Al agregar se regresa al historial después de insertar en Room, sin esperar
  un mensaje ni usar un retraso artificial. Editar y eliminar siguen esa idea.

Se siguió el README del profesor y se reutilizaron sus componentes, formularios,
tarjetas, Room y navegación. No se agregaron dependencias ni se modificó el
modelo o la versión de la base de datos. El README original se conserva.

## Revisión realizada y pendiente

Se revisaron las rutas, llamadas al DAO/ViewModel y recursos utilizados por las
pantallas. Se intentó compilar y ejecutar las pruebas, pero este entorno no pudo
descargar la distribución de Gradle; tampoco dispone del SDK de Android.
La compilación y la prueba en un dispositivo quedan pendientes en Android Studio.

Para ejecutar las pruebas existentes, abre los archivos de androidTest y usa
el botón verde junto a cada prueba con un emulador o teléfono seleccionado.

Si prefieres llevar los cambios a tu proyecto abierto, copia estos siete archivos:
GasolineDao.kt, GasolineViewModel.kt, NavManager.kt, HomeView.kt, AddView.kt,
EditView.kt y DeleteView.kt en sus carpetas correspondientes. Copia también
`app/src/main/res/values/strings.xml` y la corrección de NuevoMontoUITest.kt.
