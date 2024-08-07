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




