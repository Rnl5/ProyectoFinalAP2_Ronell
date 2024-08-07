package com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.edu.ucne.proyectofinalap2_ronell.R
import com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthViewModel


@Composable
fun PerfilUsuarioScreen(
    irADashboard: () -> Unit,
    authViewModel: AuthViewModel,
    irALogin: () -> Unit,
) {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color(0xFFF5F5F5)
    val accentColor = if (isDark) Color(0xFF6200EE) else Color(0xFF3700B3)
    val textColor = if (isDark) Color.White else Color.Black
    val cardBackgroundColor = if (isDark) Color(0xFF2A2A2A) else Color.White
    val authState = authViewModel.authState.collectAsState()


    val userData by authViewModel.userData.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.35f)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(accentColor, accentColor.copy(alpha = 0.7f))
                        )
                    )
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = Path()
                    path.moveTo(0f, size.height - 50)
                    path.cubicTo(
                        size.width * 0.10f, size.height * 0.95f,
                        size.width * 0.70f, size.height * 1.05f,
                        size.width, size.height - 50
                    )
                    drawPath(
                        path = path,
                        color = backgroundColor
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
            ) {

                IconButton(onClick = irADashboard,
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Ir a Dashboard",
                        tint = Color.White
                    )
                }
            }


            Spacer(modifier = Modifier.height(40.dp))
            ProfileImageMejorado(modifier = Modifier.size(130.dp))
            Spacer(modifier = Modifier.height(16.dp))
            userData?.let {usuario ->
                Text(
                    text = usuario.nombreUsuario,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text(text = usuario.correoElectronico,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
                ?: Text(text = "Cargando la informacion del usuario")
//            Text(
//                text = "Nombre de Usuario",
//                style = MaterialTheme.typography.headlineSmall,
//                color = Color.White,
//                fontWeight = FontWeight.Bold
//            )
//            Text(
//                text = "usuario@email.com",
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color.White.copy(alpha = 0.7f)
//            )
            Spacer(modifier = Modifier.height(40.dp))
            CurvedButtonMejorado("Editar Perfil",
                Icons.Outlined.Edit,
                textColor,
                cardBackgroundColor,
                onClick = { })

            Spacer(modifier = Modifier.height(16.dp))
            CurvedButtonMejorado("Configuración", Icons.Outlined.Settings, textColor, cardBackgroundColor, onClick = {})

            Spacer(modifier = Modifier.height(16.dp))
            CurvedButtonMejorado("Ayuda y Soporte", Icons.Outlined.Phone, textColor, cardBackgroundColor, onClick = {})

            Spacer(modifier = Modifier.height(16.dp))
            CurvedButtonMejorado("Cerrar Sesión", Icons.Outlined.ExitToApp, Color.White, Color.Red.copy(alpha = 0.8f),
                onClick = {authViewModel.signout()
                irALogin()})
        }
    }
}

@Composable
fun ProfileImageMejorado(modifier: Modifier) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                shape = CircleShape,
                spotColor = Color.Black.copy(alpha = 0.5f)
            )
            .border(width = 4.dp, color = Color.White, shape = CircleShape)
    ) {
        Image(
            painter = painterResource(id = R.drawable.freedom),
            contentDescription = "Imagen de ",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}

@Composable
fun CurvedButtonMejorado(text: String, icon: ImageVector, textColor: Color, backgroundColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = textColor.copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}




//@Composable
//fun PerfilCurvasMejorado() {
//    val isDark = isSystemInDarkTheme()
//    val backgroundColor = if (isDark) Color(0xFF121212) else Color(0xFFF5F5F5)
//    val accentColor = if (isDark) Color(0xFF6200EE) else Color(0xFF3700B3)
//    val textColor = if (isDark) Color.White else Color.Black
//    val secondaryTextColor = if (isDark) Color.LightGray else Color.DarkGray
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(backgroundColor)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.4f)
//                    .background(
//                        Brush.verticalGradient(
//                            colors = listOf(accentColor, accentColor.copy(alpha = 0.6f))
//                        )
//                    )
//            ) {
//                Canvas(modifier = Modifier.fillMaxSize()) {
//                    val path = Path()
//                    path.moveTo(0f, size.height - 50)
//                    path.cubicTo(
//                        size.width * 0.10f, size.height * 0.95f,
//                        size.width * 0.70f, size.height * 1.05f,
//                        size.width, size.height - 50
//                    )
//                    drawPath(
//                        path = path,
//                        color = backgroundColor
//                    )
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.6f)
//            )
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(48.dp))
//            ProfileImageMejorado(modifier = Modifier.size(140.dp))
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Nombre de Usuario",
//                style = MaterialTheme.typography.headlineSmall,
//                color = Color.White,
//                fontWeight = FontWeight.Bold
//            )
//            Text(
//                text = "@usuario",
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color.White.copy(alpha = 0.7f)
//            )
//            Spacer(modifier = Modifier.height(24.dp))
//            EstadisticasUsuario()
//            Spacer(modifier = Modifier.height(32.dp))
//            CurvedButtonMejorado("Editar Perfil", Icons.Outlined.Edit, textColor, accentColor)
//            Spacer(modifier = Modifier.height(16.dp))
//            CurvedButtonMejorado("Configuración", Icons.Outlined.Settings, textColor, accentColor)
//            Spacer(modifier = Modifier.height(16.dp))
//            CurvedButtonMejorado("Cerrar Sesión", Icons.Outlined.ExitToApp, Color.White, Color.Red.copy(alpha = 0.8f))
//        }
//    }
//}
//
//@Composable
//fun ProfileImageMejorado(modifier: Modifier) {
//    Box(
//        modifier = modifier
//            .shadow(elevation = 8.dp, shape = CircleShape)
//            .border(width = 4.dp, color = Color.White, shape = CircleShape)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.freedom),
//            contentDescription = "Imagen de Perfil",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(CircleShape)
//        )
//    }
//}
//
//@Composable
//fun EstadisticasUsuario() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        EstadisticaItem("Seguidores", "1.2K")
//        EstadisticaItem("Siguiendo", "500")
//        EstadisticaItem("Publicaciones", "102")
//    }
//}
//
//@Composable
//fun EstadisticaItem(titulo: String, valor: String) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(
//            text = valor,
//            style = MaterialTheme.typography.headlineSmall,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//        Text(
//            text = titulo,
//            style = MaterialTheme.typography.bodySmall,
//            color = Color.White.copy(alpha = 0.7f)
//        )
//    }
//}
//
//@Composable
//fun CurvedButtonMejorado(text: String, icon: ImageVector, textColor: Color, backgroundColor: Color) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(60.dp)
//            .clickable { },
//        shape = RoundedCornerShape(30.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        colors = CardDefaults.cardColors(containerColor = backgroundColor)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 24.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                imageVector = icon,
//                contentDescription = null,
//                tint = textColor,
//                modifier = Modifier.size(24.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(
//                text = text,
//                style = MaterialTheme.typography.titleMedium,
//                color = textColor,
//                fontWeight = FontWeight.Medium
//            )
//        }
//    }
//}
















//PERFIL GOOOOOODARDOOOO
//@Composable
//fun PerfilCurvas() {
//    val isDark = isSystemInDarkTheme()
//    val backgroundColor = if (isDark) Color(0xFF121212) else Color(0xFFF5F5F5)
//    val accentColor = if (isDark) Color(0xFF3700B3) else Color(0xFF6200EE)
//    val textColor = if (isDark) Color.White else Color.Black
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(backgroundColor)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.4f)
//                    .background(
//                        Brush.verticalGradient(
//                            colors = listOf(accentColor, accentColor.copy(alpha = 0.6f))
//                        )
//                    )
//            ) {
//                Canvas(modifier = Modifier.fillMaxSize()) {
//                    val path = Path()
//                    path.moveTo(0f, size.height)
//                    path.quadraticBezierTo(
//                        size.width / 2,
//                        size.height * 1.2f,
//                        size.width,
//                        size.height
//                    )
//                    drawPath(
//                        path = path,
//                        color = backgroundColor
//                    )
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.6f)
//            )
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(48.dp))
//            ProfileImage(modifier = Modifier.size(120.dp))
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Nombre de Usuario",
//                style = MaterialTheme.typography.titleSmall,
//                color = Color.White,
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(modifier = Modifier.height(32.dp))
//            CurvedButton("Editar Perfil", Icons.Default.Edit, textColor)
//            Spacer(modifier = Modifier.height(16.dp))
//            CurvedButton("Configuración", Icons.Default.Settings, textColor)
//            Spacer(modifier = Modifier.height(16.dp))
//            CurvedButton("Cerrar Sesión", Icons.Default.ExitToApp, textColor)
//        }
//    }
//}
//
//@Composable
//fun ProfileImage(modifier: Modifier) {
//    Image(
//        painter = painterResource(id = R.drawable.freedom),
//        contentDescription = "Imagen de Presentacion",
//        contentScale = ContentScale.Crop,
//        modifier = Modifier
//            .size(120.dp)
//            .clip(CircleShape)
//            .border(2.dp, Color.Black, CircleShape)
//            .clickable {  }
//    )
//
//}
//
//@Composable
//fun CurvedButton(text: String, icon: ImageVector, textColor: Color) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(60.dp)
//            .clickable { },
////        elevation = 4.dp,
//        shape = RoundedCornerShape(30.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 24.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                imageVector = icon,
//                contentDescription = null,
//                tint = textColor,
//                modifier = Modifier.size(24.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(
//                text = text,
//                style = MaterialTheme.typography.labelLarge,
//                color = textColor
//            )
//        }
//    }
//}






















































//DISE;O MEH
//@Composable
//fun PerfilConTarjetaScreen() {
//    val isDarkTheme = isSystemInDarkTheme()
//    val backgroundColor = if (isDarkTheme) Color.DarkGray else Color.LightGray
//    val cardColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color.White
//    val textColor = if (isDarkTheme) Color.White else Color.Black
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(backgroundColor)
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
////            backgroundColor = cardColor,
////            elevation = 8.dp
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
////                ProfileImage()
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = "Nombre de Usuario",
//                    style = MaterialTheme.typography.titleSmall,
//                    color = textColor
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = "correo@ejemplo.com",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = textColor
//                )
//                Spacer(modifier = Modifier.height(24.dp))
//                ProfileActionButton("Editar Perfil", Icons.Default.Edit, textColor)
//                ProfileActionButton("Preferencias", Icons.Default.Settings, textColor)
//                ProfileActionButton("Cerrar Sesión", Icons.Default.ExitToApp, textColor)
//            }
//        }
//    }
//}
//
//@Composable
//fun ProfileActionButton(text: String, icon: ImageVector, textColor: Color) {
//    TextButton(
//        onClick = { },
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Icon(imageVector = icon, contentDescription = null, tint = textColor)
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text = text, color = textColor)
//    }
//}




